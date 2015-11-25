package com.eeproject.myvolunteer.myvolunteer;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Altman on 2015/10/30.
 */
public class fragment_QuestList extends Fragment{
    Context context;

    //setup DBHelper
    SQLiteDatabase db;
    public DBHelper helper;
    Cursor cursor;

    //Define for list view
    TextView title, expirydate;
    ImageView icon;
    ListAdapter adapter;

    Spinner catagoryspinner, languagespinner;

    PassValue passvalue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_questlistfragment, null);
        context = container.getContext();
        setlist(v);
        database_loadDatabase.setArrayList(context);
        return v;
    }

    //Interface
    public interface PassValue
    {
        public void setPosition(int position);
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            passvalue = (PassValue) activity;
        }catch(Exception e){
            Log.d("Unable to pass position", null);
        }

    }

    //Set up list
    public void setlist(View v){
        ListView list = (ListView) v.findViewById(R.id.list);

        catagoryspinner = (Spinner) v.findViewById(R.id.catspinner);
        languagespinner = (Spinner) v.findViewById(R.id.langspinner);

        ArrayAdapter<String> catadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, parameter.catagory);
        catagoryspinner.setAdapter(catadapter);

        ArrayAdapter<String> langadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, parameter.language);
        languagespinner.setAdapter(langadapter);

        adapter = new ListAdapter(context);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);

        //onClick item -> go to quest details, pass postion to the details fragment
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                passvalue.setPosition(position);
                Log.d("Position passed", String.valueOf(position));
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
            return database_loadDatabase.titlelist.size();
        }

        @Override
        public Object getItem(int position) {
            return database_loadDatabase.titlelist.get(position);
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

            title.setText(database_loadDatabase.titlelist.get(position));
            expirydate.setText(database_loadDatabase.expirydatelist.get(position));

            int id = getResources().getIdentifier(database_loadDatabase.icon.get(position), "drawable", "com.eeproject.myvolunteer.myvolunteer");
            icon.setImageResource(id);


            return convertView;
        }
    }
}
