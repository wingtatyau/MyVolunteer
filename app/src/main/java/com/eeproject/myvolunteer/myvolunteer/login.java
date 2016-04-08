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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Arrays;

/**
 * Created by Altman on 2015-11-12.
 */
public class login extends Fragment{
    Context context;

    EditText username, password;
    TextView register;
    Button submit;

    View drawer;

    Firebase rootRef = new Firebase("https://blistering-fire-9077.firebaseio.com/android/");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login, null);
        drawer = inflater.inflate(R.layout.drawer_header, null);
        context = container.getContext();
        init(v);
        return v;
    }

    public void init(View v){
        ImageView image = (ImageView)v.findViewById(R.id.imageView2);
        image.setImageResource(R.drawable.loginlogo_white);
        username = (EditText) v.findViewById(R.id.emailedittext);
        password = (EditText) v.findViewById(R.id.passwordedittext);
        submit = (Button) v.findViewById(R.id.loginbutton);
        register = (TextView) v.findViewById(R.id.textView_register);

        // Clicked Login
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



        // Clicked Register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("")) {
                    sethighlight(username, "Username");
                } else if (password.getText().toString().equals("")) {
                    sethighlight(password, "Password");
                } else {

                    rootRef.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            System.out.println("There are " + snapshot.getChildrenCount() + " approved users");
                            if (snapshot.getChildrenCount() > 0) {
                                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                    user f_user = userSnapshot.getValue(user.class);
                                    if (!username.getText().toString().equals(f_user.getUsername())) {

                                        rootRef.child("User to be approved").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                System.out.println("There are " + dataSnapshot.getChildrenCount() + " users to be approved");
                                                if (dataSnapshot.getChildrenCount() > 0) {
                                                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                                        user A_user = userSnapshot.getValue(user.class);
                                                        if (!username.getText().toString().equals(A_user.getUsername()))
                                                            register(username.getText().toString(), password.getText().toString());

                                                    }
                                                } else
                                                    register(username.getText().toString(), password.getText().toString());
                                            }

                                            @Override
                                            public void onCancelled(FirebaseError firebaseError2) {
                                                System.out.println("The read failed: " + firebaseError2.getMessage());
                                            }
                                        });

                                    }

                                }
                            } else
                                register(username.getText().toString(), password.getText().toString());

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            System.out.println("The read failed: " + firebaseError.getMessage());
                        }
                    });

                }
            }
        });


    }

    //Perform the login process
    public void login(final String username, final String password){

        rootRef.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " approved users");
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    user f_user = userSnapshot.getValue(user.class);
                    Log.v("True User?: ", String.valueOf(username.equals(f_user.getUsername()) && password.equals(f_user.getPassword())));
                    if (username.equals(f_user.getUsername()) && password.equals(f_user.getPassword())) {

                        parameter.login.set(true);

                        TextView displayusername = (TextView) drawer.findViewById(R.id.name);
                        displayusername.setText(username);

                        int ranking_mark = f_user.getRanking_mark();
                        String iconpath = f_user.getIconpath();
                        String firstName = f_user.getFirstname();
                        String lastName = f_user.getLastname();
                        String organization = f_user.getOrganization();
                        String questIssuedList = f_user.getQuest_issued();
                        String questAcceptedList = f_user.getQuest_accepted();
                        user createuser = new user(username, password, ranking_mark, iconpath, firstName, lastName, organization, questIssuedList, questAcceptedList);

                        TextView title = (TextView) drawer.findViewById(R.id.name);
                        title.setText(createuser.getUsername());
                        parameter.setUserID(createuser.getUsername());


                        Toast.makeText(context, "You login-ed! Welcome!", Toast.LENGTH_SHORT).show();
                        //Jump to fragment_MyAccount
                        fragment_MyAccount f1 = new fragment_MyAccount();
                        parameter.logineduser = createuser;
                        f1.setUser(createuser);
                        getFragmentManager().beginTransaction().replace(R.id.content_container, f1).commit();


                    }
                    else {
                        Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show();

                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        rootRef.child("User to be approved").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("There are " + dataSnapshot.getChildrenCount() + " users to be approved");
                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        user A_user = userSnapshot.getValue(user.class);
                        if (username.equals(A_user.getUsername()) && password.equals(A_user.getPassword()))

                            Toast.makeText(context, "Account is being verified", Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError2) {
                System.out.println("The read failed: " + firebaseError2.getMessage());
            }
        });





    }

    //Perform the register process
    public void register(final String username, String password){

        int rand = (int)(Math.random()*10);
        Log.d("Math random: ", String.valueOf(rand));




//        for(int i = 0; i < database_loadDatabase.usernamelist.size(); i++){
//            if(username.equals(database_loadDatabase.usernamelist.get(i))) {
//
//            }
//        }
        boolean validEmail = true;
        int num_of_at = 0;
        int index_of_at = 1;
        for(int i=0; i < username.length(); i++){

            if (!((username.charAt(i)>=65 && username.charAt(i)<=90) ||
                    (username.charAt(i)>=97 && username.charAt(i)<=122) ||
                    (username.charAt(i)>=48 && username.charAt(i)<=57) ||
                    (username.charAt(i)>=35 && username.charAt(i)<=39) ||
                    username.charAt(i)==33 || username.charAt(i)==42 ||
                    username.charAt(i)==43 || username.charAt(i)==45 ||
                    username.charAt(i)==47 || username.charAt(i)==61 ||
                    username.charAt(i)==63 || username.charAt(i)==46 ||
                    (username.charAt(i)>=94 && username.charAt(i)<=96) ||
                    (username.charAt(i)>=123 && username.charAt(i)<=126) ||
                    username.charAt(i)=='@'))
                validEmail = false;

            switch(username.charAt(i)){
                case 0:
            }

            if(username.charAt(i) == '@') {
                num_of_at++;
                index_of_at = i;
            }
        }

        if(username.charAt(0) == '.' || username.charAt(index_of_at-1) == '.')
            validEmail = false;

        for(int i=0; i < index_of_at-1; i++){
            if(username.charAt(i) == '.' && username.charAt(i+1) == '.') {
                validEmail = false;
            }
        }

        int number_of_dot = 0;
        for(int i=index_of_at+1; i < username.length(); i++){
            if(username.charAt(i) == '.') {
                number_of_dot++;
                if(i==username.length()-1)
                    validEmail = false;
            }
        }

        if(num_of_at != 1)
            validEmail = false;

        if (number_of_dot<1)
            validEmail = false;


        if(validEmail) {
            user createuser = new user(username, password, 0, parameter.defaulticonpath[rand], " ", " ", " ", "No Quest Issued yet", "No Quest Accepted yet");
            Log.d("Username: ", createuser.getUsername());
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
