package com.eeproject.myvolunteer.myvolunteer;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Michael on 4/19/2016.
 */
public class fragment_OverallRank extends Fragment {
    Context context;
    SQLiteOpenHelper db;
    DBHelper helper;
    Cursor cursor;
    Button button;

    TextView overall, name, mark;
    ImageView imageView;

    List<String> sortedName = new ArrayList<>();
    List<Integer> sortedMark = new ArrayList<>();
    List<String> sortedIcon = new ArrayList<>();
    List<Integer> originalposition = new ArrayList<>();

    ListAdapter adapter;

    Toolbar toolbar;

    Firebase rootRef = new Firebase("https://blistering-fire-9077.firebaseio.com/android/");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_overallrank, null);
        context = container.getContext();
        helper = new DBHelper(context, DBHelper.DATABASE_NAME);
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setTitle("Overall Ranking");
        setlist(v);
        return v;
    }

    //Retrieve date from table
    private void setlist(View v) {

        overall = (TextView) v.findViewById(R.id.overall);
        button = (Button) v.findViewById(R.id.back);

        ListView overallList = (ListView) v.findViewById(R.id.overallList);


        adapter = new ListAdapter(context);
        adapter.notifyDataSetChanged();
        overallList.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_Ranking fragment = new fragment_Ranking();
                getFragmentManager().beginTransaction().replace(R.id.content_container, fragment).commit();
            }
        });

        rootRef.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {

                    int index = 0;
                    for (DataSnapshot usersnapshot : dataSnapshot.getChildren()) {
                        user user = usersnapshot.getValue(user.class);
                        sortedIcon.add(user.getIconpath());
                        sortedName.add(user.getFirstname());
                        sortedMark.add(user.getRanking_mark());
                        originalposition.add(index++);
                    }

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }



    public class ListAdapter extends BaseAdapter {
        private LayoutInflater ListInflater;

        public ListAdapter(Context c){
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
            convertView = ListInflater.inflate(R.layout.rankitem, null);

            imageView = (ImageView) convertView.findViewById(R.id.imageView);
            mark = (TextView)convertView.findViewById(R.id.mark_item);
            name = (TextView)convertView.findViewById(R.id.name_item);

            Collections.sort(sortedMark);
            mark.setText("Mark: " + sortedMark.get(sortedName.size() - position - 1));
            name.setText(sortedName.get(sortedName.size() - position - 1));

            Bitmap bitmap = image_handler.decode(sortedIcon.get(sortedName.size()-position-1));
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
            Bitmap conv_bm = Bitmap_factory.getRoundedRectBitmap(resized, 200);
            imageView.setImageBitmap(conv_bm);

            return convertView;
        }
    }

}
