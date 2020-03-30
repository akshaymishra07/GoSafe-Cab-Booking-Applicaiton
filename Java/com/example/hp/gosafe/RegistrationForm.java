package com.example.hp.gosafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.RenderProcessGoneDetail;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationForm extends AppCompatActivity {

    private EditText username;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Button   register;
    FirebaseUser user;

    DatabaseReference dataBaseUser;

    FirebaseAuth mAuth;
    String uid ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

        mAuth = FirebaseAuth.getInstance();

        dataBaseUser = FirebaseDatabase.getInstance().getReference("USER");

        username = findViewById(R.id.etUname);
        email=findViewById(R.id.etEmail);
        password=findViewById(R.id.etPword);
        confirmPassword = findViewById(R.id.etCnfPword);
        register = findViewById(R.id.btnRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = mAuth.getCurrentUser();

                String uname = username.getText().toString();
                String phone = user.getPhoneNumber();
                String uemail = email.getText().toString();
                String pword = password.getText().toString();

                if(uname.isEmpty() || phone.isEmpty() || uemail.isEmpty() || pword.isEmpty()){

                    Toast.makeText(RegistrationForm.this , "Please Fill All The Details!" , Toast.LENGTH_LONG).show();


                }
                else {

                    UserInformation userinfo = new UserInformation(uname, uemail, pword);
                    dataBaseUser.child(phone).setValue(userinfo);

                    Toast.makeText(RegistrationForm.this, "Welcome "+uname+"!! ", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(RegistrationForm.this, UserHome.class);
                    startActivity(intent);
                    finish();

                }
            }
        });





    }


}
