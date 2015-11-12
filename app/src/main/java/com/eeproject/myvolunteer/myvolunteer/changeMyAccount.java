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
public class changeMyAccount extends Fragment {
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_questlistfragment, null);
        context = container.getContext();
        return v;
    }

    //Iniitalize the content of the layout
    //use findViewbyId to get the element and edit the element
    //maybe using for loop or arraylist to append the value into the id
    public void init(View v){

    }

    //Open database, table_name = user, retrieve the data from the database
    //User database is not yet built

    public void getinformation(){

    }

}
