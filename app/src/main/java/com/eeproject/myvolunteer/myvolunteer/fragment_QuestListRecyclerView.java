package com.eeproject.myvolunteer.myvolunteer;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Altman on 2015/10/30.
 */
public class fragment_QuestListRecyclerView extends Fragment {
    Context context;

    //Define for list view
    List<quest> mQuest = new ArrayList<>();

    //Recycler View adapter
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    Spinner catagoryspinner, languagespinner;

    Toolbar toolbar;

    PassValue passvalue;

    Firebase rootRef = new Firebase("https://blistering-fire-9077.firebaseio.com/android/");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_questlistrecyclerview, null);
        context = container.getContext();
        database_loadDatabase.setArrayList(context);
        //mQuest.add(new quest("1", "1", "1", "1", "1", "1", "1", "1", 1, 1, "1"));
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setTitle("Quest List");
        setmRecyclerView(v);
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

        catagoryspinner = (Spinner) v.findViewById(R.id.catspinner);
        languagespinner = (Spinner) v.findViewById(R.id.langspinner);
        
        parameter.sCatagory = parameter.catagory[0];
        parameter.sLanguage = parameter.language[0];
        
        Log.v("sCatagory", parameter.sCatagory);
        Log.v("sLanguage", parameter.sLanguage);
        
        ArrayAdapter<String> catadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, parameter.catagory);
        catagoryspinner.setAdapter(catadapter);
        catagoryspinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                parameter.sCatagory = parent.getSelectedItem().toString();
                regeneratelist();
            }
        
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
        
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

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        Log.v("Clicked Item", String.valueOf(position));

                        passvalue.setPosition(position);
                        Log.d("Position passed", String.valueOf(position));
                    }
                })
        );

        rootRef.child("Quest").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    mQuest.clear();
                    for (DataSnapshot questsnapshot : dataSnapshot.getChildren()) {
                        quest quest = questsnapshot.getValue(quest.class);
                        mQuest.add(quest);
                        Log.d("mQuest", "Add success");
                    }
                }
                Log.d("mQuest", "Add fail");

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


    private void regeneratelist()
    {
    
        Log.v("Inside sCatagory", parameter.sCatagory);
        Log.v("Inside sLanguage", parameter.sLanguage);
    
        rootRef.child("Quest").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                mQuest.clear();

                if (dataSnapshot.getChildrenCount() > 0)
                {
                    int index = 0;
                    for (DataSnapshot questsnapshot : dataSnapshot.getChildren())
                    {
                        quest quest = questsnapshot.getValue(quest.class);
                        if (parameter.sCatagory == "All" && parameter.sLanguage == "No Preference")
                        {
                            mQuest.add(quest);
                        }
                        else if (parameter.sCatagory == "All" && quest.getRequiredLanguage().equals(parameter.sLanguage))
                        {
                            mQuest.add(quest);
                        }
                        else if (parameter.sLanguage == "No Preference" && quest.getCatagory().equals(parameter.sCatagory))
                        {
                            Log.v("Hereerere", "YEEeeeee");
                            mQuest.add(quest);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled (FirebaseError firebaseError)
            {

            }
        }

        );
    
    }


    public void setmRecyclerView(View v){

        mRecyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new adapter_QuestListRecyclerView(mQuest);
        mRecyclerView.setAdapter(mAdapter);
    }



}

