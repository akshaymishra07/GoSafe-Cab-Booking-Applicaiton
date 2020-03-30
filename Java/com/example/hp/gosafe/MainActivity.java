package com.example.hp.gosafe;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.content.Intent;
import android.os.Handler;
import android.app.Activity;
import android.os.Bundle;
public class MainActivity extends AppCompatActivity {

    ImageView imageView2;
    Animation fromleft;

    private Handler mWaitHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView2=(ImageView)findViewById(R.id.imageView2);
        fromleft = AnimationUtils.loadAnimation(this,R.anim.fromleft);
        imageView2.setAnimation(fromleft);


       mWaitHandler.postDelayed(new Runnable() {

            @Override
            public void run() {

                //The following code will execute after the 3 seconds.

                try {

                    imageView2=(ImageView)findViewById(R.id.imageView2);
                    fromleft = AnimationUtils.loadAnimation(MainActivity.this,R.anim.fromleft);
                    imageView2.setAnimation(fromleft);
                    //Go to next page i.e, start the next activity.
                    Intent intent = new Intent(getApplicationContext(), BookYourRide.class);
                    startActivity(intent);

                    //Let's Finish Splash Activity since we don't want to show this when user press back button.
                    finish();
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }, 2500);  // Give a 2.5 seconds delay.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //Remove all the callbacks otherwise navigation will execute even after activity is killed or closed.
        mWaitHandler.removeCallbacksAndMessages(null);
    }
    }









