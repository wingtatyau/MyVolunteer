package com.eeproject.myvolunteer.myvolunteer;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Altman on 2015/11/5.
 */
public class questdetailsfragment extends Fragment {
    Context context;


    SQLiteDatabase db;
    DBHelper helper;
    Cursor cursor;

    //Setup parameter for calling the elements
    TextView name, expirydate, situaitonright, situationleft, detailsleft, detailsright, location, contact;
    Button accept;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_questdetails, null); //Create quest details layout
        context = container.getContext();
        init(v);
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

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //log to database
            }
        });
    }

    public void getinformation(){
        //Retrieve the quest details from the database, table_name = quest
        //Get the key_id of the quest from the fragment - [Quest List]
        db = helper.getReadableDatabase();
    }
}
