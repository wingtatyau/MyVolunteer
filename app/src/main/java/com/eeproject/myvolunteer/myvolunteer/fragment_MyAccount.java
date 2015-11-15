package com.eeproject.myvolunteer.myvolunteer;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Altman on 2015/10/30.
 */
public class fragment_MyAccount extends Fragment {
    Context context;

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
        }
    }

    //Open database, table_name = user, retrieve the data from the database
    //User database is not yet built

    public void getinformation(){

    }

}
