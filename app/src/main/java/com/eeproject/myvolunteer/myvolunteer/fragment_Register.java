package com.eeproject.myvolunteer.myvolunteer;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
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

import java.io.FileNotFoundException;

/**
 * Created by Chris on 11/18/15.
 */

public class fragment_Register extends Fragment{
    Context context;

    EditText firstName, lastName, organization;
    Button finish;

    public static ImageView image;

    public static Bitmap bitmap;

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
        image = (ImageView)v.findViewById(R.id.imageView);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
        bitmap = bm;
        Bitmap resized = Bitmap.createScaledBitmap(bm, 400, 400, true);
        Bitmap conv_bm = Bitmap_factory.getRoundedRectBitmap(resized, 400);
        image.setImageBitmap(conv_bm);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK){
            Uri targetUri = data.getData();
            //textTargetUri.setText(targetUri.toString());
            try {
                Bitmap temp = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(targetUri));
                bitmap = temp;
                Bitmap resized = Bitmap.createScaledBitmap(temp, 400, 400, true);
                Bitmap conv_bm = Bitmap_factory.getRoundedRectBitmap(resized, 400);
                image.setImageBitmap(conv_bm);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
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

        //Setup the BASE64 image string
        String imgfile = image_handler.encode(bitmap);
        user.setIconpath(imgfile);

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
