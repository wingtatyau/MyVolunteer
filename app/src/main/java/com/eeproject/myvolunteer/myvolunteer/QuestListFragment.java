package com.eeproject.myvolunteer.myvolunteer;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Altman on 2015/10/30.
 */
public class QuestListFragment extends Fragment {
    Context context;

    //setup DBHelper
    SQLiteDatabase db;
    public DBHelper helper;
    Cursor cursor;

    //Define for list view
    TextView title, expirydate;
    ImageView icon;
    List<String> titlelist = new ArrayList<>();
    List<String> expirydatelist = new ArrayList<>();
    List<String> catagorylist = new ArrayList<>();
    List<String> infolist = new ArrayList<>();
    List<String> langlist = new ArrayList<>();
    List<String> locationlist = new ArrayList<>();
    ListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_questlistfragment, null);
        context = container.getContext();
        setlist(v);
        return v;
    }


    //Set up list
    public void setlist(View v){
        ListView list = (ListView) v.findViewById(R.id.list);

        helper  = new DBHelper(context, DBHelper.DATABASE_NAME);
        db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME, null);

        titlelist.clear();
        expirydatelist.clear();
        infolist.clear();
        catagorylist.clear();
        langlist.clear();
        locationlist.clear();

        while(cursor.moveToNext()){
            titlelist.add(cursor.getString(cursor.getColumnIndex("quest_title")));
            expirydatelist.add(cursor.getString(cursor.getColumnIndex("expiry_date")));
            infolist.add(cursor.getString(cursor.getColumnIndex("quest_info")));
            catagorylist.add(cursor.getString(cursor.getColumnIndex("catagory")));
            langlist.add(cursor.getString(cursor.getColumnIndex("required_language")));
            locationlist.add(cursor.getString(cursor.getColumnIndex("quest_location")));
        }
        db.close();

        adapter = new ListAdapter(context);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);

    }

    public class ListAdapter extends BaseAdapter {
        private LayoutInflater ListInflater;

        public ListAdapter(Context c){
            ListInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return titlelist.size();
        }

        @Override
        public Object getItem(int position) {
            return titlelist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            convertView = ListInflater.inflate(R.layout.questitem, null);

            title = (TextView)convertView.findViewById(R.id.titletextview);
            expirydate = (TextView)convertView.findViewById(R.id.expirydatetextview);
            icon = (ImageView)convertView.findViewById(R.id.imageView);

            title.setText(titlelist.get(position));
            expirydate.setText(expirydatelist.get(position));


            return convertView;
        }
    }
}
