package com.eeproject.myvolunteer.myvolunteer;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Altman on 2015/10/30.
 */
public class fragment_MyAccount extends Fragment {
    Context context;

    ImageView icon;
    TextView name, organization, contacttextview, contactleft, situationright, questissuetextview, issueleft,
            detailsright, questaccepttextview, location, acceptright;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_myaccount, null);
        context = container.getContext();

        //Initialize the boolean, if login-ed, changed to true;

        init(v);
        return v;
    }

    public void init(View v) {
        if(parameter.login.get() == false) {
            login fragment1 = new login();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.activity_main, null);
            getFragmentManager().beginTransaction().replace(R.id.content_container, fragment1).commit();
        }else{
            //perform displaying actions

            icon = (ImageView) v.findViewById(R.id.icon);
            name = (TextView) v.findViewById(R.id.name);
            organization = (TextView) v.findViewById(R.id.organization);
            contacttextview = (TextView) v.findViewById(R.id.contacttextview);
            contactleft = (TextView) v.findViewById(R.id.contactleft);
            situationright = (TextView) v.findViewById(R.id.situationleft);
            questissuetextview = (TextView) v.findViewById(R.id.questissuetextview);
            issueleft = (TextView) v.findViewById(R.id.issueleft);
            detailsright = (TextView) v.findViewById(R.id.detailsright);
            questaccepttextview = (TextView) v.findViewById(R.id.questaccepttextview);
            location = (TextView) v.findViewById(R.id.location);
            acceptright = (TextView) v.findViewById(R.id.acceptright);

        }
    }

    //Open database, table_name = user, retrieve the data from the database
    //User database is not yet built

    public void getinformation(){

        name.setText("");
        organization.setText("");
        contacttextview.setText("Contact");
        contactleft.setText("");
        situationright.setText("");
        questaccepttextview.setText("");
        issueleft.setText("");
        detailsright.setText("");
        questaccepttextview.setText("");
        location.setText("");
        acceptright.setText("");


    }

}
