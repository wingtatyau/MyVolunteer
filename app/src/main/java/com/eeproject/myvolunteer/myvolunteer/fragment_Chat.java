package com.eeproject.myvolunteer.myvolunteer;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    ListAdapter adapter;
    TextView chatt_username, user_mail;
    ImageView chatt_imageView;
    List<String> sortedName = new ArrayList<>();
    List<String> sortedEmail = new ArrayList<>();
    List<String> sortedIcon = new ArrayList<>();
    List<Integer> originalposition = new ArrayList<>();
    public static String chat_receiver;

    Toolbar toolbar;

    int count_Name;
    ListView list;

    Firebase rootRef = new Firebase("https://blistering-fire-9077.firebaseio.com/android/");

    private void return2myac()
    {
        fragment_MyAccount fragment1 = new  fragment_MyAccount();
        getFragmentManager().beginTransaction().replace(R.id.content_container, fragment1).commit();
    }

    private void changepage()
    {
        ChattActivity fragment1 = new  ChattActivity();
        getFragmentManager().beginTransaction().replace(R.id.content_container, fragment1).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chatt_select, null);
        context = container.getContext();
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setTitle("Chatroom");
        setlist(v);
        return v;
    }

    public void setlist(final View v) {

        final String ACTIVITY_TAG="LogDemo";

        list = (ListView) v.findViewById(R.id.PChating_List);

        adapter = new ListAdapter(context);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);

        rootRef.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {

                    count_Name = -1;
                    int index = 0;
                    for (DataSnapshot usersnapshot : dataSnapshot.getChildren()) {
                        user user = usersnapshot.getValue(user.class);
                        sortedIcon.add(user.getIconpath());
                        sortedName.add(user.getFirstname());
                        sortedEmail.add(user.getUsername());
                        originalposition.add(index++);
                        count_Name++;
                    }

                    adapter.notifyDataSetChanged();
                } else
                    Log.v(ACTIVITY_TAG, "Cant work for reading");

            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chat_receiver = sortedName.get(count_Name - position);
                Log.v(ACTIVITY_TAG, chat_receiver);
                changepage();
            }
        });
    }


    public class ListAdapter extends BaseAdapter {
        private LayoutInflater ListInflater;

        public ListAdapter(Context c) {
            ListInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return sortedName.size();
        }

        @Override
        public Object getItem(int position) {
            return sortedName.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = ListInflater.inflate(R.layout.chatt_item, null);

            chatt_username = (TextView) convertView.findViewById(R.id.chatt_username);
            user_mail = (TextView) convertView.findViewById(R.id.user_mail);
            chatt_imageView = (ImageView) convertView.findViewById(R.id.chatt_imageView);

            chatt_username.setText(sortedName.get(sortedName.size() - position - 1));
            user_mail.setText(sortedEmail.get(sortedName.size() - position - 1));

            Bitmap bitmap = image_handler.decode(sortedIcon.get(sortedName.size()-position-1));
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
            Bitmap conv_bm = Bitmap_factory.getRoundedRectBitmap(resized, 200);
            chatt_imageView.setImageBitmap(conv_bm);

            return convertView;
        }

    }
}