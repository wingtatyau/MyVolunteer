package com.eeproject.myvolunteer.myvolunteer;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Altman on 2015/11/5.
 */
public class fragment_QuestDetails extends Fragment {
    Context context;


    SQLiteDatabase db;
    DBHelper helper;
    Cursor cursor;

    //Setup parameter for calling the elements
    TextView name, expirydate, situaitonright, situationleft, detailsleft, detailsright, location, contact, durationtextview, actualduration;
    Button accept;

    //position value
    int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_questdetails, null); //Create quest details layout
        context = container.getContext();
        init(v);
        database_loadDatabase.setArrayList(context);
        return v;
    }

    public void init(View v){
        //put the function that are going to run here
        //Setup onClickListener for ACCEPT button, which is for logging back to the database who accepted the quest already
        name = (TextView) v.findViewById(R.id.name);
        expirydate = (TextView) v.findViewById(R.id.expirydate);
        situaitonright = (TextView) v.findViewById(R.id.situationright);
        situationleft = (TextView) v.findViewById(R.id.situationleft);
        detailsleft = (TextView) v.findViewById(R.id.detailsleft);
        detailsright = (TextView) v.findViewById(R.id.detailsright);
        location = (TextView) v.findViewById(R.id.location);
        contact = (TextView) v.findViewById(R.id.contact);
        accept = (Button) v.findViewById(R.id.acceptbutton);

        durationtextview = (TextView) v.findViewById(R.id.durationtextview);
        actualduration = (TextView) v.findViewById(R.id.actualduration);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //log to database

            }
        });
        getinformation();
    }

    public void updateInfo(int position){
        this.position = position;
    }

    public int getPosition(){
        return position;
    }

    public void getinformation(){
        Log.d("Position received", String.valueOf(position));
        name.setText(database_loadDatabase.titlelist.get(position));
        expirydate.setText("Expiry Date: " + database_loadDatabase.expirydatelist.get(position));

        situationleft.setText("Number of participants");
        situaitonright.setText(database_loadDatabase.questcurrentpartilist.get(position) + "/" + database_loadDatabase.partinumberlist.get(position));

        detailsleft.setText(database_loadDatabase.infolist.get(position));

        location.setText(database_loadDatabase.locationlist.get(position));
        contact.setText(database_loadDatabase.questuserlist.get(position));

        durationtextview.setText("Duration");
        actualduration.setText(database_loadDatabase.requiredtime.get(position));


    }
}
