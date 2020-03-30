package com.example.hp.gosafe;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RidesList extends ArrayAdapter<RidesInformation>{

    private Activity context;
    private List<RidesInformation> rideslist;


    public  RidesList(Activity context , List<RidesInformation> rideslist){
        super(context , R.layout.list_layout , rideslist);

        this.context = context;
        this.rideslist=rideslist;


    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout ,null , true );

        TextView rpick = listViewItem.findViewById(R.id.tvPickups);
        TextView rdrop = listViewItem.findViewById(R.id.tvDrops);
        TextView rdate = listViewItem.findViewById(R.id.tvDateofRide);


        RidesInformation ridesInformation = rideslist.get(position);

        rpick.setText(ridesInformation.getPickup());
        rdrop.setText(ridesInformation.getDrop());
        rdate.setText(ridesInformation.getDate());
        return listViewItem;


    }
}
