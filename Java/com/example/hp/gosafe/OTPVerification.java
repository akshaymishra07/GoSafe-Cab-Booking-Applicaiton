package com.example.hp.gosafe;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import static java.lang.String.valueOf;

public class OTPVerification extends AppCompatActivity {

    private Button btnVerify;
    private EditText etOtp;
    private FirebaseAuth mAuth;
    PhoneAuthCredential phoneAuthCredential;
    private String verificationID;
    private PhoneAuthProvider phoneAuthProvider;

    String phoneNo;
    String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);


        btnVerify = (Button)findViewById(R.id.btnVerify);
        etOtp     = (EditText)findViewById(R.id.etOtp);

        mAuth = FirebaseAuth.getInstance();

        Intent intent =  getIntent();
         phoneNo = intent.getStringExtra("phoneNumber");

        if(phoneNo==null){
            Toast.makeText(this , "SOMETHING WENT WRONG!" , Toast.LENGTH_LONG).show();
        }
        else
            sendVerificationCode(phoneNo);







       btnVerify.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String code = etOtp.getText().toString().trim();
               if(code.isEmpty()){
                   Toast.makeText(OTPVerification.this , "Enter Code" , Toast.LENGTH_SHORT).show();
                   etOtp.requestFocus();
                   return;

               }
               else{
                   Toast.makeText(OTPVerification.this , "VERIFICATION IN PROGRESS!!" , Toast.LENGTH_LONG).show();

                   PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID , code);
                   signInWithPhoneAuthCredential(credential);
               }

           }
       });

    }


    private void sendVerificationCode(String number){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                OTPVerification.this,
                mCallbacks

        );





    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationID = s;
        }
    };


    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information


                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("USER");
                            DatabaseReference phoneRef = userRef.child(phoneNo);
                            DatabaseReference nameRef = phoneRef.child("username");

                             nameRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    uname=dataSnapshot.getValue(String.class);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            FirebaseUser user = task.getResult().getUser();

                            DatabaseReference table_user = FirebaseDatabase.getInstance().getReference("USER");

                            table_user.addListenerForSingleValueEvent(new ValueEventListener() {


                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {


                                        if(dataSnapshot.child(phoneNo).exists()){


                                        Toast.makeText( OTPVerification.this , "Welcome "+uname+"!!" , Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(OTPVerification.this , UserHome.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{

                                        Toast.makeText( OTPVerification.this , "Verification Sucessfull!!" , Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(OTPVerification.this , RegistrationForm.class);
                                        startActivity(intent);
                                        finish();
                                    }






                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });






                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(OTPVerification.this , " Verification Unsucessfull" , Toast.LENGTH_LONG).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }






}
