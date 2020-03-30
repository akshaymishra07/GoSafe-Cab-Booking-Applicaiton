package com.example.hp.gosafe;

import android.content.Intent;
import android.provider.ContactsContract;
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

import java.util.concurrent.TimeUnit;

import static java.lang.String.valueOf;

public class PhoneNumber extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText phone;
    private Button btnNext;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String verificationID ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        mAuth = FirebaseAuth.getInstance();
        phone    =   (EditText)findViewById(R.id.etPhoneNumber);
        btnNext   =   (Button)findViewById(R.id.btnNext);



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phone.getText().toString().trim();
                phoneNumber = "+91"+phoneNumber; //preparation of phone number

                if(phoneNumber.isEmpty()||phoneNumber.length()<10||phoneNumber.length()>13){

                    Toast.makeText(PhoneNumber.this , "Please Enter a valid Number !!" , Toast.LENGTH_LONG).show();
                    phone.requestFocus();

                    return;

                }


                Intent intent = new Intent(PhoneNumber.this , OTPVerification.class);
                intent.putExtra("phoneNumber" , phoneNumber);
                startActivity(intent);
                finish();




            }
        });







    }






}
