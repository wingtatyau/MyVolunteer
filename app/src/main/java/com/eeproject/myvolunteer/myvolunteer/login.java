package com.eeproject.myvolunteer.myvolunteer;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

/**
 * Created by Altman on 2015-11-12.
 */
public class login extends Fragment{
    Context context;

    EditText username, password;
    Button submit, register;
    CheckBox rememberme;

    LayoutInflater infalter;

    View drawer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login, null);
        this.infalter = inflater;
        context = container.getContext();
        init(v);
        return v;
    }

    public void init(View v){
        ImageView image = (ImageView)v.findViewById(R.id.imageView2);
        image.setImageResource(R.drawable.loginlogo);
        username = (EditText) v.findViewById(R.id.emailedittext);
        password = (EditText) v.findViewById(R.id.passwordedittext);
        submit = (Button) v.findViewById(R.id.loginbutton);
        register = (Button) v.findViewById(R.id.regtisterbutton);
        rememberme = (CheckBox) v.findViewById(R.id.remembermecheckbox);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("")){
                    sethighlight(username, "Username");
                }else if(password.getText().toString().equals("")){
                    sethighlight(password, "Password");
                }else{
                    login(username.getText().toString(), password.getText().toString());
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("")){
                    sethighlight(username, "Username");
                }else if(password.getText().toString().equals("")){
                    sethighlight(password, "Password");
                }else{
                    register(username.getText().toString(), password.getText().toString());
                }
            }
        });
    }

    //Perform the login process
    public void login(String username, String password){
        //Get database, compare, if yes -> login, else return
        for(int i = 0; i < database_loadDatabase.usernamelist.size(); i++){
            if(username.equals(database_loadDatabase.usernamelist.get(i))){
                if(password.equals(database_loadDatabase.passwordlist.get(i))){
                    parameter.login.set(true);
                    drawer = infalter.inflate(R.layout.drawer_header, null);
                    Toast.makeText(context, "You login-ed! Welcome!", Toast.LENGTH_SHORT).show();
                    TextView displayusername = (TextView)drawer.findViewById(R.id.name);
                    displayusername.setText(username);

                    int ranking_mark = Integer.parseInt(database_loadDatabase.ranking_mark.get(i));
                    String iconpath = database_loadDatabase.iconpathlist.get(i);
                    String firstName = database_loadDatabase.firstnamelist.get(i);
                    String lastName = database_loadDatabase.lastnamelist.get(i);
                    String organization = database_loadDatabase.organizationlist.get(i);
                    String questIssuedList = database_loadDatabase.questIssuedList.get(i);
                    String questAcceptedList = database_loadDatabase.questAcceptedList.get(i);
                    user createuser = new user(username, password, ranking_mark, iconpath, firstName, lastName, organization, questIssuedList, questAcceptedList);

                    TextView title = (TextView) drawer.findViewById(R.id.name);
                    title.setText(createuser.getUsername());
                    parameter.setUserID(createuser.getUsername());

                    //Jump to fragment_MyAccount
                    fragment_MyAccount f1 = new fragment_MyAccount();
                    parameter.logineduser = createuser;
                    f1.setUser(createuser);
                    getFragmentManager().beginTransaction().replace(R.id.content_container, f1).commit();
                }
            }
        }
        if(parameter.login.get() == false){
            Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }

        if(rememberme.isChecked()){
            parameter.remeberme.set(true);
        }
    }

    //Perform the register process
    public void register(String username, String password){
        int rand = (int)(Math.random()*10);
        Log.d("Math random: ", String.valueOf(rand));
        boolean exist = false;
        for(int i = 0; i < database_loadDatabase.usernamelist.size(); i++){
            if(username.equals(database_loadDatabase.usernamelist.get(i))) {
                exist = true;
            }
        }
        user createuser = new user(username, password, 0, parameter.defaulticonpath[rand], " ", " ", " ", "No Quest Issued yet", "No Quest Accepted yet");
        Log.d("Username: ", createuser.getUsername());
        if(exist != true) {
            Toast.makeText(context, "Register Successful!", Toast.LENGTH_LONG).show();
            Log.d("Username11221: ", createuser.getUsername());
            //Jump to fragment_Register
            fragment_Register f1 = new fragment_Register();
            f1.setUser(createuser);
            getFragmentManager().beginTransaction().replace(R.id.content_container, f1).commit();
        }else{
            Toast.makeText(context, "Register unsuccessful!", Toast.LENGTH_LONG).show();

        }
    }

    public void sethighlight(EditText v, String type){
        v.setHint(type + " cannot be empty!");
        v.setHintTextColor(Color.GRAY);
    }
}
