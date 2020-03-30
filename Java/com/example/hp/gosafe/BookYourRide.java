package com.example.hp.gosafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BookYourRide extends AppCompatActivity {

    private Button buttonBYR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_your_ride);

        buttonBYR = (Button) findViewById(R.id.btnBYR);

        buttonBYR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user==null){
                Intent intent = new Intent(BookYourRide.this , PhoneNumber.class );
                startActivity(intent);
                }

                else
                {
                    Intent intent = new Intent(BookYourRide.this ,UserHome.class );
                    startActivity(intent);

                }

            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();



    }
}
