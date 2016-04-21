package com.eeproject.myvolunteer.myvolunteer;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Altman on 2015-11-13.
 */
public class fragment_Ranking extends Fragment {
    Context context;
    Cursor cursor;
    Button tomonth, tooverall;

    TextView mark;
    RelativeLayout relativelayout;

    Toolbar toolbar;
    user user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ranking, null);
        context = container.getContext();

        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setTitle("Ranking");

        user = parameter.logineduser;
        setlist(v);
        return v;
    }

    //Retrieve date from table
    private void setlist(View v) {
        relativelayout = (RelativeLayout) v.findViewById(R.id.relativelayout);
        mark = (TextView) v.findViewById(R.id.mark);

        if(!parameter.login.get()) {
            mark.setText("0");
        } else {
            mark.setText(String.valueOf(user.getRanking_mark()));
        }

        tomonth = (Button) v.findViewById(R.id.tomonth);
        tooverall = (Button) v.findViewById(R.id.tooverall);

        tooverall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_OverallRank fragment = new fragment_OverallRank();
                getFragmentManager().beginTransaction().replace(R.id.content_container, fragment).commit();
            }
        });

        tomonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_MonthRank fragment = new fragment_MonthRank();
                getFragmentManager().beginTransaction().replace(R.id.content_container, fragment).commit();
            }
        });
    }
}


