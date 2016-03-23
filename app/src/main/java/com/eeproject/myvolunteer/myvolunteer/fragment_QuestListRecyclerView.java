package com.eeproject.myvolunteer.myvolunteer;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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

    Firebase rootRef = new Firebase("https://blistering-fire-9077.firebaseio.com/android/");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_questlistrecyclerview, null);
        context = container.getContext();
        database_loadDatabase.setArrayList(context);
        //mQuest.add(new quest("1", "1", "1", "1", "1", "1", "1", "1", 1, 1, "1"));
        setmRecyclerView(v);
        setlist(v);
        return v;
    }

    //Set up list
    public void setlist(final View v) {
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
