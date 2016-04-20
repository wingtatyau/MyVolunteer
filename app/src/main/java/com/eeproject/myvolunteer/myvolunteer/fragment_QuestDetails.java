package com.eeproject.myvolunteer.myvolunteer;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
    positionandkey pak;
    Toolbar toolbar;

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
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);

        toolbar.setTitle("Quests Details");
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
                if(parameter.login.get() == false){
                    Toast.makeText(context, "Please Login!", Toast.LENGTH_LONG).show();
                    login f1 = new login();
                    getFragmentManager().beginTransaction().replace(R.id.content_container, f1).commit();

                }else {


                    rootRef.child(pak.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            int newParti = dataSnapshot.child("currentparti").getValue(Integer.class) + 1;

                            rootRef.child(pak.getKey()).child("currentparti").setValue(newParti, new Firebase.CompletionListener() {
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

    public void updateInfo(positionandkey pak){
        this.pak = pak;
    }


    public void getinformation(){

        Log.d("Key received", pak.getKey());
        quest quest = parameter.list.get(pak.getPosition());

        name.setText(quest.getTitle());
        expirydate.setText("Expiry Date: " + quest.getExpirydate());
        situationleft.setText("Number of participants");
        detailsleft.setText(quest.getInfo());
        location.setText(quest.getLocation());
        contact.setText(quest.getUser());
        catagorytextview.setText(quest.getCatagory());
        languagetextview.setText(quest.getRequiredLanguage());
        durationtextview.setText("Duration");
        actualduration.setText(quest.getRequiredTime());
        situaitonright.setText(String.valueOf(quest.getCurrentparti() + "/" + parameter.list.get(pak.getPosition()).getPartinumber()));


        Bitmap bitmap;
        bitmap = image_handler.decode(quest.getIcon());
        icon.setImageBitmap(bitmap);

        rootRef.child(pak.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.v("TP=0", "printed");
                situaitonright.setText(String.valueOf(dataSnapshot.child("currentparti").getValue(Integer.class)) + "/" + parameter.list.get(pak.getPosition()).getPartinumber());
                Log.d("finish snapshot", String.valueOf(dataSnapshot.child("currentparti").getValue(Integer.class)));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }
}
