package com.eeproject.myvolunteer.myvolunteer;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.facedemo.facedemo.ChatActivity;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
/**
 * Created by HousFamily on 2016/3/15.
 */
public class fragment_Chat extends Fragment {
    Context context;
    TextView contact_List;
    ListAdapter1 adapter1;
    TextView chatt_username, user_mail;
    Button chatbtn;
    TextView testing;


    Firebase ref = new Firebase("https://blistering-fire-9077.firebaseio.com/android/User");

    private void changepage()
    {
        ChatActivity fragment1 = new ChatActivity();
        getFragmentManager().beginTransaction().replace(R.id.content_container, fragment1).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.chatt_select, null);
        context = container.getContext();
        init(v);
        return v;
    }


    private void init(View v)
    {
        contact_List = (TextView) v.findViewById(R.id.contact_List);
        chatbtn = (Button) v.findViewById(R.id.BtnChat);
        ListView PChating_List = (ListView) v.findViewById(R.id.PChating_List);

        adapter1 = new ListAdapter1(context);
        adapter1.notifyDataSetChanged();
        PChating_List.setAdapter(adapter1);

        chatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changepage();
            }
        });
    }

    public class ListAdapter1 extends BaseAdapter {
        private LayoutInflater ListInflater;

        public ListAdapter1(Context c)
        {
            ListInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount()
        {
            return 1;
        }

        @Override
        public Object getItem(int position)
        {
            return position;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            convertView = ListInflater.inflate(R.layout.chatt_item, null);

            chatt_username = (TextView)convertView.findViewById(R.id.chatt_username);
            user_mail = (TextView)convertView.findViewById(R.id.user_mail);

            chatt_username.setText("Ricky Lau");
            user_mail.setText("drlau@cityu.com");


            return convertView;
        }
    }



}