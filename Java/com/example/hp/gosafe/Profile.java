package com.example.hp.gosafe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private TextView name;
    private TextView phone;
    private TextView email;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String profileName;
    String mailID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth=FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        phone = findViewById(R.id.tvPhone);
        name  = findViewById(R.id.tvName);
        email = findViewById(R.id.tvEmail);

        String phoneNo = user.getPhoneNumber();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("USER");
        DatabaseReference phoneRef = userRef.child(phoneNo);
        DatabaseReference nameRef = phoneRef.child("username");
        DatabaseReference emailRef= phoneRef.child("email");


        nameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profileName=dataSnapshot.getValue(String.class);

                //Toast.makeText(Profile.this ,String.valueOf(profileName),Toast.LENGTH_LONG).show();
                name.setText(profileName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        emailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mailID=dataSnapshot.getValue(String.class);

                //Toast.makeText(Profile.this ,String.valueOf(profileName),Toast.LENGTH_LONG).show();
                email.setText(mailID);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        phone.setText(phoneNo);
       // name.setText(profileName);
       //name.setVisibility(View.VISIBLE);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



    }

