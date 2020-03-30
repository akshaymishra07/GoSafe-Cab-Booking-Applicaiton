package com.example.hp.gosafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class BookingConfirmation extends AppCompatActivity {

    FirebaseAuth mAuth;

    TextView pick;
    TextView tim;
    TextView cate;
    TextView drop;
    TextView rnumber;
    TextView mdel;
    TextView bid;
    String times;
    String pickup;
    String categ;
    String drp;
    String id;
    String registr;

    Button bar;
    Button bcr;
    DatabaseReference ref_cabs;
    DatabaseReference ref_rides;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        ref_cabs = FirebaseDatabase.getInstance().getReference("CABS");
        ref_rides= FirebaseDatabase.getInstance().getReference("RIDES"); //it will create reference if not already exits

        bar=findViewById(R.id.btnBAR);
        bcr=findViewById(R.id.btnCR);
        pick=findViewById(R.id.tvPickupLocation);
        tim=findViewById(R.id.tvTimeOfRide);
        cate = findViewById(R.id.tvCatRide);
        rnumber=findViewById(R.id.tvRegistration);
        mdel = findViewById(R.id.tvModel);
        drop = findViewById(R.id.tvDrop);
        bid = findViewById(R.id.tvID);

        Intent intent = getIntent();

        times = intent.getStringExtra("pickuptime");
        pickup= intent.getStringExtra("pickuploc");
        categ = intent.getStringExtra("rideCategory");
        drp = intent.getStringExtra("droploc");
        id = intent.getStringExtra("booking");


        pick.setText(pickup);
        tim.setText(times);
        cate.setText(categ);
        drop.setText(drp);
        bid.setText(id);




             cabUpdate();



        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingConfirmation.this , UserHome.class);
                startActivity(intent);

                HashMap<String, Object> result = new HashMap<>();
                result.put("status", "OnRide");
                ref_cabs.child(registr).updateChildren(result);

                finish();


            }
        });


        bcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               ref_rides.child(id).removeValue();
               Intent intent1 = new Intent(BookingConfirmation.this , UserHome.class);
               startActivity(intent1);
               finish();


            }
        });






    }

    public void cabUpdate(){

        Query catQuery = ref_cabs.orderByChild("category").equalTo(categ);

        catQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot catsnapshot : dataSnapshot.getChildren()){

                    String status  = catsnapshot.child("status").getValue(String.class);

                    if(status.equals("OffRide")) {


                        String modelname = catsnapshot.child("model").getValue(String.class);
                        mdel.setText(modelname);

                        registr = catsnapshot.getKey();
                        rnumber.setText(registr);




                    }


                }






            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //Intent intent = new Intent(BookingConfirmation.this , UserHome.class);
        //startActivity(intent);
        finish();

    }
}
