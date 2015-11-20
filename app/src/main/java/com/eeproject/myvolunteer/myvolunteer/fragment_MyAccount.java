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
    TextView name, organization, contacttextview, contactleft, contactright, questissuetextview, issueleft,
            issueright, questaccepttextview, acceptleft, acceptright;
    user user;

    public void setUser(user user){
        this.user = user;
    }

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
            name = (TextView) v.findViewById(R.id.nameA);
            organization = (TextView) v.findViewById(R.id.organization);
            contacttextview = (TextView) v.findViewById(R.id.contacttextview);
            contactleft = (TextView) v.findViewById(R.id.contactleft);
            contactright = (TextView) v.findViewById(R.id.contactright);
            questissuetextview = (TextView) v.findViewById(R.id.questissuetextview);
            issueleft = (TextView) v.findViewById(R.id.issueleft);
            issueright = (TextView) v.findViewById(R.id.issueright);
            questaccepttextview = (TextView) v.findViewById(R.id.questaccepttextview);
            acceptleft = (TextView) v.findViewById(R.id.acceptleft);
            acceptright = (TextView) v.findViewById(R.id.acceptright);

            getinformation();
        }
    }

    //Open database, table_name = user, retrieve the data from the database
    //User database is not yet built

    public void getinformation(){

        name.setText(user.getFirstname() + " " + user.getLastname());
        organization.setText(user.getOrganization());
        contactleft.setText("Email");
        contactright.setText(user.getUsername());
        issueleft.setText(user.getQuest_issued());
        issueright.setText(" ");
        acceptleft.setText(user.getQuest_accepted());
        acceptright.setText(" ");
        int id = getResources().getIdentifier(user.getIconpath(), "drawable", "com.eeproject.myvolunteer.myvolunteer");
        icon.setImageResource(id);

    }

}
