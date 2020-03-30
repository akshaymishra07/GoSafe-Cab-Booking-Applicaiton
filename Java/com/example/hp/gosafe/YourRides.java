package com.example.hp.gosafe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class YourRides extends AppCompatActivity {

    ListView listRides;
    DatabaseReference ref_rides;
    FirebaseAuth mAuth;

    List <RidesInformation> rideslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_rides);

        ref_rides = FirebaseDatabase.getInstance().getReference("RIDES");

        listRides  = findViewById(R.id.listviewRides);

        rideslist = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        String no = user.getPhoneNumber();

       Query userquery = ref_rides.orderByChild("user").equalTo(no);

       userquery.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {


               rideslist.clear();

               for(DataSnapshot rideSnapshot : dataSnapshot.getChildren()){

                   RidesInformation rides = rideSnapshot.getValue(RidesInformation.class);

                   rideslist.add(rides);




               }

               RidesList adapter = new RidesList(YourRides.this ,rideslist );
               listRides.setAdapter(adapter);



           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

    }
}
