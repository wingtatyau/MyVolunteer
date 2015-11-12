package com.eeproject.myvolunteer.myvolunteer;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Altman on 2015-11-13.
 */
public class rankingfragment extends Fragment {
    Context context;
    SQLiteOpenHelper db;
    DBHelper helper;
    Cursor cursor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addquest, null);
        context = container.getContext();
        helper = new DBHelper(context, DBHelper.DATABASE_NAME);
        init(v);
        return v;
    }

    //Retrieve date from table
    private void init(View v) {

    }
}
