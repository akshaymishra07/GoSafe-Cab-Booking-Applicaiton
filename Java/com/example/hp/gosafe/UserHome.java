package com.example.hp.gosafe;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class UserHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private  static final String TAG = "UserHome";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    //for clock begins
    Button button_stpd;
    static final int DIALOG_ID=0;
    int hour_x;
    int minute_x;
    int cyear;
    int cmonth;
    int cday;

    private TextView time;
    String date;


    ConstraintLayout li;
    Fragment fragment=null;
    Intent intent;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String profileName;



    private TextView timeset;

    private TextView headName;
    private RadioGroup radioGroup;

    String category;

    DatabaseReference ref_rides;
    String phoneNo;

    Spinner sPick;
    Spinner sDrop;
    Spinner sCity;

    TextView dates;

    private Button book;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        time=findViewById(R.id.tvTime);
        dates = findViewById(R.id.tvDate);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        sPick=findViewById(R.id.spinner2);
        sCity=findViewById(R.id.spinner);
        sDrop=findViewById(R.id.spinner4);


        showTimePickerDialog();

        final String phoneNo = user.getPhoneNumber();
        headName   = findViewById(R.id.tvHeaderName);
        radioGroup = findViewById(R.id.radioCateg);
        book = (Button) findViewById(R.id.btnBook);
        timeset=findViewById(R.id.tvTime);



        ref_rides = FirebaseDatabase.getInstance().getReference("RIDES");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle =  new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
       final View  header = navigationView.getHeaderView(0);



        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int  year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(UserHome.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                //Log.d(TAG, "onDateSet: mm/dd/yyy:" + month + "/" + dayOfMonth + "/" + year);
                date =  dayOfMonth + "/" + month + "/" + year;
                cday=dayOfMonth;
                cmonth=month;
                cyear=year;
                mDisplayDate.setText(date);
            }
        };
        //spinner code starts
        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.Select_City,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinner2 =(Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this,R.array.pickup_point,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        Spinner spinner3 =(Spinner)findViewById(R.id.spinner4);
        ArrayAdapter<CharSequence> adapter3=ArrayAdapter.createFromResource(this,R.array.drop_point,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);


        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("USER");
        DatabaseReference phoneRef= userRef.child(phoneNo);
        DatabaseReference nameRef = phoneRef.child("username");

        nameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profileName=dataSnapshot.getValue(String.class);

                Toast.makeText(UserHome.this ,"WELCOME " +profileName+"!!",Toast.LENGTH_LONG).show();
                headName   = header.findViewById(R.id.tvHeaderName);
                headName.setText(profileName);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




       radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int id = radioGroup.getCheckedRadioButtonId();

                RadioButton rb = findViewById(id);

                category = rb.getText().toString();


            }
        });


         book.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {


                  String time = timeset.getText().toString();
                  String pick = sPick.getSelectedItem().toString();
                  String drop = sDrop.getSelectedItem().toString();
                  String date = dates.getText().toString();
                  String cat = String.valueOf(category);
                  if(cat.isEmpty() || pick.isEmpty() ||time.isEmpty() || drop.isEmpty() ){
                       Toast.makeText(UserHome.this , "INSUFFICIENT DETAILS !!" , Toast.LENGTH_LONG).show();
                       return;
                  }
                  if(pick.equals("Select Pick-up point") || drop.equals("Select Drop-point")  || time.isEmpty()){
                      Toast.makeText(UserHome.this , "SELECT PROPER PICKUP AND DROP Details" , Toast.LENGTH_LONG).show();

                  }

                  else {
                      RidesInformation rideinfo = new RidesInformation(phoneNo, pick, drop, time, category, date);
                      String bookingId = ref_rides.push().getKey(); //generate a uid for every ride

                      ref_rides.child(bookingId).setValue(rideinfo);


                      Intent intent = new Intent(UserHome.this, BookingConfirmation.class);
                      intent.putExtra("pickuploc", pick);
                      intent.putExtra("droploc", drop);
                      intent.putExtra("pickuptime", time);
                      intent.putExtra("rideCategory", category);
                      intent.putExtra("booking", bookingId);

                      startActivity(intent);
                      finish();
                  }

              }
          });





    }

    //clock code begins.........
    public  void showTimePickerDialog() {
        button_stpd=(Button)findViewById(R.id.button2);
        button_stpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if(id== DIALOG_ID)
            return new TimePickerDialog(UserHome.this,kTimePickerListener,hour_x,minute_x,false);
        return null;
    }
    protected TimePickerDialog.OnTimeSetListener kTimePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {



                    Calendar c= Calendar.getInstance();
                    Calendar utime = Calendar.getInstance();

                    utime.set(Calendar.HOUR_OF_DAY , hourOfDay);
                    utime.set(Calendar.MINUTE , minute);

                    int dy = c.get(Calendar.DAY_OF_MONTH);
                    int mth= c.get(Calendar.MONTH);

                    int yr= c.get(Calendar.YEAR);




                        if(cyear > yr){
                                    time.setText(hourOfDay + ":" + minute);
                                }
                                 if(cyear == yr){
                                    if(cmonth > mth){
                                        time.setText(hourOfDay + ":" + minute);
                                     }
                                     if(cmonth == mth){
                                          if(cday > dy){
                                               time.setText(hourOfDay + ":" + minute);
                                            }
                                            if(cday == dy){
                                                   if(utime.getTimeInMillis() >= c.getTimeInMillis()){
                                                       time.setText(hourOfDay + ":" + minute);
                                    }
                                 }
                            }

                        }
                        else
                        {
                            time.setText("");
                            Toast.makeText(UserHome.this,"ENTER A VALID TIME" , Toast.LENGTH_SHORT).show();

                        }










                }
            };






    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);


        } else {

            super.onBackPressed();


        }

    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {



        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.profile) {

            Intent profileIntent = new Intent(this , Profile.class);
            startActivity(profileIntent);


        } else if (id == R.id.rides) {

            Intent intent = new Intent(this , YourRides.class );
            startActivity(intent);


        } else if (id == R.id.ratecard) {
            Intent intent6 = new Intent(UserHome.this,RateCard.class);
            startActivity(intent6);

        } else if (id == R.id.info) {

            Intent intent3 = new Intent(UserHome.this,AboutUs.class);
            startActivity(intent3);


        } else if (id == R.id.support) {

            Intent intent4 = new Intent(UserHome.this,Support.class);
            startActivity(intent4);



        } else if (id == R.id.exit) {

            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(this , BookYourRide.class);
            startActivity(intent);

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
