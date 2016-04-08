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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Altman on 2015-11-13.
 */
public class fragment_Ranking extends Fragment {
    Context context;
    SQLiteOpenHelper db;
    DBHelper helper;
    Cursor cursor;

    TextView month, overall, name, mark;
    ListAdapter1 adapter1;
    ListAdapter2 adapter2;

    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ranking, null);
        context = container.getContext();
        helper = new DBHelper(context, DBHelper.DATABASE_NAME);
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setTitle("Monthly Ranking");
        init(v);
        return v;
    }

    //Retrieve date from table
    private void init(View v) {
        //month = (TextView) v.findViewById(R.id.month);
        overall = (TextView) v.findViewById(R.id.overall);
       ListView monthList = (ListView) v.findViewById(R.id.monthList);
       ListView overallList = (ListView) v.findViewById(R.id.overallList);

        adapter1 = new ListAdapter1(context);
        adapter1.notifyDataSetChanged();
        monthList.setAdapter(adapter1);

        adapter2 = new ListAdapter2(context);
        adapter2.notifyDataSetChanged();
        overallList.setAdapter(adapter2);
    }

    //monthList
    public class ListAdapter1 extends BaseAdapter {
        private LayoutInflater ListInflater;

        public ListAdapter1(Context c){
            ListInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return database_loadDatabase.usernamelist.size();
        }

        @Override
        public Object getItem(int position) {
            return database_loadDatabase.usernamelist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = ListInflater.inflate(R.layout.rankitem, null);

            mark = (TextView)convertView.findViewById(R.id.mark_item);
            name = (TextView)convertView.findViewById(R.id.name_item);

            /*
            Calendar cal = Calendar.getInstance();
            int thismonth = cal.get(Calendar.MONTH);
            int thisyear = cal.get(Calendar.YEAR);

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm");
            Date date = format.phrse(//date of get score);

            if (thisyear == date.getYear()){
                if (thismonth == date.getMonth()){
                    //add score to new List<string> monthrank
                }
            }
            mark.setText("Mark: " + monthrank.get(position));
            */

            Collections.sort(database_loadDatabase.ranking_mark, Collections.reverseOrder());

            mark.setText("Mark: " + database_loadDatabase.ranking_mark.get(position));
            name.setText(database_loadDatabase.usernamelist.get(position));

            return convertView;
        }
    }

    //overallList
    public class ListAdapter2 extends BaseAdapter {
        private LayoutInflater ListInflater;

        public ListAdapter2(Context c){
            ListInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return database_loadDatabase.usernamelist.size();
        }

        @Override
        public Object getItem(int position) {
            return database_loadDatabase.usernamelist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = ListInflater.inflate(R.layout.rankitem, null);

            mark = (TextView)convertView.findViewById(R.id.mark_item);
            name = (TextView)convertView.findViewById(R.id.name_item);

            Collections.sort(database_loadDatabase.ranking_mark, Collections.reverseOrder());

            mark.setText("Mark: " + database_loadDatabase.ranking_mark.get(position));
            name.setText(database_loadDatabase.usernamelist.get(position));
            return convertView;
        }
    }

}
