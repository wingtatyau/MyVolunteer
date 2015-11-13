package com.eeproject.myvolunteer.myvolunteer;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Altman on 2015-11-12.
 */
public class login extends Fragment{
    Context context;

    EditText username, password;
    Button submit, register;

    SQLiteDatabase db;
    DBHelper helper;
    Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login, null);
        context = container.getContext();
        init(v);
        return v;
    }

    public void init(View v){
        ImageView image = (ImageView)v.findViewById(R.id.imageView2);
        image.setImageResource(R.drawable.loginlogo);
        username = (EditText) v.findViewById(R.id.emailedittext);
        password = (EditText) v.findViewById(R.id.passwordedittext);
        submit = (Button) v.findViewById(R.id.submitbutton);
        register = (Button) v.findViewById(R.id.regtisterbutton);



    }

    //Perform the login process
    public void login(String username, String password){
        //Get database, compare, if yes -> login, else return
    }

    //Perform the register process
    public void register(String username, String password){
        db = helper.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DBHelper.USER, username);
        cv.put(DBHelper.PASSWORD, password);
        cv.put(DBHelper.ICONPATH, "happy");

        db.insert(DBHelper.USER_TABLE_NAME, null, cv);
        db.close();
        Toast.makeText(context, "Register Succesful!", Toast.LENGTH_LONG).show();
    }
}
