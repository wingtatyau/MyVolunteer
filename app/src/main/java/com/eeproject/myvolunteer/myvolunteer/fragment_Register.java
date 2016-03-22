package com.eeproject.myvolunteer.myvolunteer;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by Chris on 11/18/15.
 */

public class fragment_Register extends Fragment{
    Context context;

    EditText firstName, lastName, organization;
    Button finish;

    public static user user;

    public void setUser(user user){
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, null);
        context = container.getContext();
        init(v);
        return v;
    }

    public void init(View v){
        ImageView image = (ImageView)v.findViewById(R.id.imageView);
        image.setImageResource(R.drawable.loginlogo);
        firstName = (EditText) v.findViewById(R.id.firstNameEditText);
        lastName = (EditText) v.findViewById(R.id.lastNameEditText);
        organization = (EditText) v.findViewById(R.id.organizationEditText);
        finish = (Button) v.findViewById(R.id.finishButton);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstName.getText().toString().equals("")) {
                    sethighlight(firstName, "First Name ");
                } else {
                    if (lastName.getText().toString().equals("")) {
                        sethighlight(lastName, "Last Name ");
                    } else {
                        finish(firstName.getText().toString(), lastName.getText().toString(), organization.getText().toString());
                    }
                }
            }
        });
    }

    //Perform the finish process
    public void finish(String First, String Last, String Organization){

        user.setFirstname(First);
        user.setLastname(Last);
        if(Organization.equals("")){
            user.setOrganization("No Organization");
        }else{
            user.setOrganization(Organization);
        }

        Firebase rootRef = new Firebase("https://blistering-fire-9077.firebaseio.com/android/");

        if(Organization.equals("")){
            //For personal use account

            Log.v("DEBUGGING", user.getUsername());
            Firebase f_user = rootRef.child("User").child(user.getFirstname());
            f_user.setValue(user, new Firebase.CompletionListener() {
                @Override
                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                    if (firebaseError != null) {
                        System.out.println("Data could not be saved. " + firebaseError.getMessage());
                        Toast.makeText(context, "Register unsuccessful!", Toast.LENGTH_LONG).show();
                    } else {
                        System.out.println("Data saved successfully.");
                        Toast.makeText(context, "Register Successful!", Toast.LENGTH_LONG).show();

                        //Jump to fragment_MyAccount
                        fragment_MyAccount f1 = new fragment_MyAccount();
                        f1.setUser(user);
                        getFragmentManager().beginTransaction().replace(R.id.content_container, f1).commit();
                    }
                }
            });

        } else {
            //For NGO use account

            Firebase user_to_be_approved = rootRef.child("User to be approved").child(user.getFirstname());
            user_to_be_approved.setValue(user, new Firebase.CompletionListener() {
                @Override
                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                    if (firebaseError != null) {
                        System.out.println("Data could not be saved. " + firebaseError.getMessage());
                        Toast.makeText(context, "Register unsuccessful!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Please wait until we approved your account, Thank you!", Toast.LENGTH_LONG).show();
                        //Jump to fragmnet_QuestList
                        fragment_QuestList f1 = new fragment_QuestList();
                        getFragmentManager().beginTransaction().replace(R.id.content_container, f1).commit();
                    }
                }
            });



        }

    }

    public void sethighlight(EditText v, String type){
        v.setHint(type + " cannot be empty!");
        v.setHintTextColor(Color.GRAY);
    }
}
