package com.eeproject.myvolunteer.myvolunteer;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

    Spinner catagoryspinner, languagespinner;

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
                Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
                intent.putExtra("position", position);
                getActivity().startActivity(intent);
                questdetailsfragment fragment1 = new questdetailsfragment();
                getFragmentManager().beginTransaction().replace(R.id.content_container, fragment1).addToBackStack(null).commit();
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
            int rand = (int)Math.random();
            String drawableName = parameter.defaulticonpath[rand];
            int resID = getResources().getIdentifier(drawableName, "drawable", "com.eeproject.myvolunteer.myvolunteer");
            icon.setImageResource(resID);

            return convertView;
        }
    }
}
