package com.eeproject.myvolunteer.myvolunteer;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Altman on 2015/10/30.
 */
public class fragment_QuestList extends Fragment {
    Context context;

    //setup DBHelper
    SQLiteDatabase db;
    public DBHelper helper;
    Cursor cursor;

    //Define for list view
    TextView title, expirydate;
    ImageView icon;
    ListAdapter adapter;

    List<String> sortedTitle = new ArrayList<>();
    List<String> sortedDate = new ArrayList<>();
    List<String> sortedIcon = new ArrayList<>();
    List<Integer> originalposition = new ArrayList<>();

    Spinner catagoryspinner, languagespinner;

    PassValue passvalue;

    ListView list;

    Toolbar toolbar;

    Firebase rootRef = new Firebase("https://blistering-fire-9077.firebaseio.com/android/");

    public void resetList(){
        sortedDate.clear();
        sortedIcon.clear();
        sortedTitle.clear();
        originalposition.clear();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_questlistfragment, null);
        context = container.getContext();
        database_loadDatabase.setArrayList(context);

        setlist(v);
        return v;
    }

    //Interface
    public interface PassValue {
        public void setPosition(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            passvalue = (PassValue) activity;
        } catch (Exception e) {
            Log.d("Unable to pass position", null);
        }

    }

    //Set up list
    public void setlist(final View v) {


        list = (ListView) v.findViewById(R.id.list);

        adapter = new ListAdapter(context);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);


        catagoryspinner = (Spinner) v.findViewById(R.id.catspinner);
        languagespinner = (Spinner) v.findViewById(R.id.langspinner);

        parameter.sCatagory = parameter.catagory[0];
        parameter.sLanguage = parameter.language[0];

        Log.v("sCatagory", parameter.sCatagory);
        Log.v("sLanguage", parameter.sLanguage);

        ArrayAdapter<String> catadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, parameter.catagory);
        catagoryspinner.setAdapter(catadapter);
        catagoryspinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parameter.sCatagory = parent.getSelectedItem().toString();
                regeneratelist();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> langadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, parameter.language);
        languagespinner.setAdapter(langadapter);
        languagespinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parameter.sLanguage = parent.getSelectedItem().toString();
                regeneratelist();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //onClick item -> go to quest details, pass postion to the details fragment
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                passvalue.setPosition(position+1);
                Log.d("Position passed", String.valueOf(position+1));
            }
        });


        rootRef.child("Quest").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {

                    resetList();

                    int index = 0;
                    for (DataSnapshot questsnapshot : dataSnapshot.getChildren()) {
                        quest quest = questsnapshot.getValue(quest.class);
                        sortedIcon.add(quest.getIcon());
                        sortedDate.add(quest.getExpirydate());
                        sortedTitle.add(quest.getTitle());
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

    private void regeneratelist() {
        //database_loadDatabase.setArrayList(context);

        Log.v("Inside sCatagory", parameter.sCatagory);
        Log.v("Inside sLanguage", parameter.sLanguage);

        rootRef.child("Quest").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                resetList();

                if (dataSnapshot.getChildrenCount() > 0) {
                    int index = 0;
                    for (DataSnapshot questsnapshot : dataSnapshot.getChildren()) {
                        quest quest = questsnapshot.getValue(quest.class);
                        if (parameter.sCatagory == "All" && parameter.sLanguage == "No Preference") {
                            sortedIcon.add(quest.getIcon());
                            sortedDate.add(quest.getExpirydate());
                            sortedTitle.add(quest.getTitle());
                            originalposition.add(index++);
                        }
                        else if (parameter.sCatagory == "All" && quest.getRequiredLanguage().equals(parameter.sLanguage)) {
                            sortedIcon.add(quest.getIcon());
                            sortedDate.add(quest.getExpirydate());
                            sortedTitle.add(quest.getTitle());
                            originalposition.add(index++);
                        }
                        else if (parameter.sLanguage == "No Preference" && quest.getCatagory().equals(parameter.sCatagory)) {
                            Log.v("Hereerere","YEEeeeee");
                            sortedIcon.add(quest.getIcon());
                            sortedDate.add(quest.getExpirydate());
                            sortedTitle.add(quest.getTitle());
                            originalposition.add(index++);
                        }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled (FirebaseError firebaseError){

                }
            }

            );


            adapter.notifyDataSetChanged();
            list.setAdapter(adapter);
        }


        public class ListAdapter extends BaseAdapter {
        private LayoutInflater ListInflater;

        public ListAdapter(Context c) {
            ListInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return sortedTitle.size();
        }

        @Override
        public Object getItem(int position) {
            return sortedTitle.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = ListInflater.inflate(R.layout.questitem, null);

            title = (TextView) convertView.findViewById(R.id.titletextview);
            expirydate = (TextView) convertView.findViewById(R.id.expirydatetextview);
            icon = (ImageView) convertView.findViewById(R.id.imageView);

            title.setText(sortedTitle.get(sortedTitle.size() - position - 1));
            expirydate.setText(sortedDate.get(sortedTitle.size() - position - 1));

            int id = getResources().getIdentifier(sortedIcon.get(sortedTitle.size() - position - 1), "drawable", "com.eeproject.myvolunteer.myvolunteer");
            icon.setImageResource(id);


            return convertView;
        }
    }
}
