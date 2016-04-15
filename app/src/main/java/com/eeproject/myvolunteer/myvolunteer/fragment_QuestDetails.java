package com.eeproject.myvolunteer.myvolunteer;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by Altman on 2015/11/5.
 */
public class fragment_QuestDetails extends Fragment {
    Context context;


    SQLiteDatabase db;
    DBHelper helper;
    Cursor cursor;

    //Setup parameter for calling the elements
    TextView name, expirydate, situaitonright, situationleft, detailsleft, catagorytextview, languagetextview, location, contact, durationtextview, actualduration;
    Button accept;
    ImageView icon;

    //key value
    String key;

    Firebase rootRef = new Firebase("https://blistering-fire-9077.firebaseio.com/android/Quest");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_questdetails, null); //Create quest details layout
        context = container.getContext();
        init(v);
        //database_loadDatabase.setArrayList(context);
        return v;
    }

    public void init(View v){
        //put the function that are going to run here
        //Setup onClickListener for ACCEPT button, which is for logging back to the database who accepted the quest already
        name = (TextView) v.findViewById(R.id.name);
        expirydate = (TextView) v.findViewById(R.id.expirydate);
        situaitonright = (TextView) v.findViewById(R.id.situationright);
        situationleft = (TextView) v.findViewById(R.id.situationleft);
        detailsleft = (TextView) v.findViewById(R.id.detailsleft);
        icon = (ImageView) v.findViewById(R.id.icon);
        //commented by Tat
        //detailsright = (TextView) v.findViewById(R.id.detailsright);
        location = (TextView) v.findViewById(R.id.location);
        contact = (TextView) v.findViewById(R.id.contact);
        accept = (Button) v.findViewById(R.id.acceptbutton);

        catagorytextview = (TextView) v.findViewById(R.id.catatextview);
        languagetextview = (TextView) v.findViewById(R.id.languagetextview);

        durationtextview = (TextView) v.findViewById(R.id.durationtextview);
        actualduration = (TextView) v.findViewById(R.id.actualduration);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //log to database
//                database_writeDatabase.updatetable(context, DBHelper.TABLE_NAME, DBHelper.QUEST_CURRENT_PARTI, position+1, (database_loadDatabase.questcurrentpartilist.get(position)+1));
//
//                database_loadDatabase.setArrayList(context);

//                rootRef.child("Quest").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.getChildrenCount() > 0) {
//
//                            for (DataSnapshot questsnapshot : dataSnapshot.getChildren()) {
//                                quest quest = questsnapshot.getValue(quest.class);
//
//
//
//                            }
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//
//                    }
//                });
//


                if(parameter.login.get() == false){
                    Toast.makeText(context, "Please Login!", Toast.LENGTH_LONG).show();
                    login f1 = new login();
                    getFragmentManager().beginTransaction().replace(R.id.content_container, f1).commit();

                }else {


                    rootRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            int newParti = dataSnapshot.child("currentparti").getValue(Integer.class) + 1;

                            rootRef.child(key).child("currentparti").setValue(newParti, new Firebase.CompletionListener() {
                                @Override
                                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                    if (firebaseError != null) {
                                        System.out.println("Data could not be saved. " + firebaseError.getMessage());
                                        Toast.makeText(context, "Register unsuccessful!", Toast.LENGTH_LONG).show();
                                    } else {
                                        System.out.println("Data saved successfully.");

                                        getinformation();
                                        Toast.makeText(context, "Quest Accepted!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });

                }


            }
        });
        getinformation();
    }

    public void updateInfo(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }

    public void getinformation(){

        Log.d("Key received", key);

        rootRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //if (dataSnapshot.getChildrenCount() > 0) {

//                    // Reverse order
//                    int tempPosition = 0;
//
//                    for (DataSnapshot questsnapshot : dataSnapshot.getChildren()) {
                        quest quest = dataSnapshot.getValue(quest.class);
//
//                        Log.v("TPosition", String.valueOf(tempPosition));
//                        Log.v("TQuest Name", quest.getTitle());
//                        Log.v("Key", key);
//                        Log.v("Number of Children", String.valueOf(dataSnapshot.getChildrenCount()));
//
//                        if(questsnapshot.getKey() == key) {

                            Log.v("TP=0", "printed");

                            name.setText(quest.getTitle());
                            expirydate.setText("Expiry Date: " + quest.getExpirydate());

                            situationleft.setText("Number of participants");
                            situaitonright.setText(quest.getCurrentparti() + "/" + quest.getPartinumber());

                            detailsleft.setText(quest.getInfo());

                            location.setText(quest.getLocation());
                            contact.setText(quest.getUser());
                            catagorytextview.setText(quest.getCatagory());
                            languagetextview.setText(quest.getRequiredLanguage());
                            durationtextview.setText("Duration");
                            actualduration.setText(quest.getRequiredTime());

                            int id = getResources().getIdentifier(quest.getIcon(), "drawable", "com.eeproject.myvolunteer.myvolunteer");
                            icon.setImageResource(id);

//                            tempPosition++;

                        //}
                        //else if(tempPosition < (dataSnapshot.getChildrenCount() - position - 1)) tempPosition++;
                        //else break;

//                    }
//
//                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }
}
