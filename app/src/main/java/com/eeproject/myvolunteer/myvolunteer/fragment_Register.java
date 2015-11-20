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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
                finish(firstName.getText().toString(), lastName.getText().toString(), organization.getText().toString());
            }
        });
    }

    //Perform the finish process
    public void finish(String First, String Last, String Organization){
        user.setFirstname(First);
        user.setLastname(Last);
        user.setOrganization(Organization);

        if(database_writeDatabase.writeUser(user, context) == true) {
            Toast.makeText(context, "Register Successful!", Toast.LENGTH_LONG).show();

            //Jump to fragment_MyAccount
            fragment_MyAccount f1 = new fragment_MyAccount();
            f1.setUser(user);
            getFragmentManager().beginTransaction().replace(R.id.content_container, f1).commit();
        }else{
            Toast.makeText(context, "Register unsuccessful!", Toast.LENGTH_LONG).show();

        }
    }
}
