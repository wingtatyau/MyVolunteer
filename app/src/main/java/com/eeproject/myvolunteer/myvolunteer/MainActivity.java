package com.eeproject.myvolunteer.myvolunteer;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facedemo.facedemo.ChatActivity;
import com.example.facedemo.facedemo.FaceConversionUtil;
import com.firebase.client.Firebase;

import java.io.FileNotFoundException;


public class MainActivity extends Activity implements fragment_QuestListRecyclerView.PassValue {
    //Setup context
    Context context = this;

    //DrawerLayout define
    TextView contentView;
    DrawerLayout drawerLayout;

    //setup DBHelper
    SQLiteDatabase db;
    public DBHelper helper;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        		getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Firebase.setAndroidContext(this);

        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                FaceConversionUtil.getInstace().getFileText(getApplication());
            }
        }).start();
        helper.getDatabase(context);

        changeQuestListRecyclerView();

        database_loadDatabase.setArrayList(context);

        //Get Intents
        Intent intent = getIntent();
        String position = intent.getStringExtra("position");
        

        //set up navigation drawer buttons
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                String title = (String) menuItem.getTitle();
                if (title.equals("Add Quest")) {
                    changeaddquest();
                }
                if (title.equals("Quest List")) {
                    changeQuestListRecyclerView();
                }
                if (title.equals("My Account")) {
                    changeMyAccount();
                }
                if (title.equals("zuyoChat!")) {
                    changezuyoChat();
                }
                if (title.equals("Share")) {
                    changeShare();
                }
                if (title.equals("Setting")) {
                    changeSetting();
                }
                if (title.equals("Ranking")) {
                    changeRanking();
                }
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }


    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();

        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }

    private void changeaddquest(){
        if(parameter.login.get() == false){
            Toast.makeText(context, "Please Login!", Toast.LENGTH_LONG).show();
            login f1 = new login();
            getFragmentManager().beginTransaction().replace(R.id.content_container, f1).commit();

        }else {
            fragment_AddQuest fragment1 = new fragment_AddQuest();
            getFragmentManager().beginTransaction().replace(R.id.content_container, fragment1).commit();
        }
    }
    private void changeSetting(){
    }

    private void changeShare() {
    }

    private void changezuyoChat() {
        fragment_Chat  fragment1 = new fragment_Chat();
        getFragmentManager().beginTransaction().replace(R.id.content_container, fragment1).commit();
    }

    private void changeRanking() {
        fragment_Ranking fragment1 = new fragment_Ranking();
        getFragmentManager().beginTransaction().replace(R.id.content_container, fragment1).commit();
    }


    private void changeMyAccount() {
        fragment_MyAccount fragment1 = new fragment_MyAccount();
        fragment1.setUser(parameter.logineduser);
        getFragmentManager().beginTransaction().replace(R.id.content_container, fragment1).addToBackStack(null).commit();
    }

    public void changeQuestList() {
        fragment_QuestList fragment1 = new fragment_QuestList();
        getFragmentManager().beginTransaction().replace(R.id.content_container, fragment1).addToBackStack(null).commit();
    }

    public void changeQuestListRecyclerView(){
        fragment_QuestListRecyclerView fragment1 = new fragment_QuestListRecyclerView();
        getFragmentManager().beginTransaction().replace(R.id.content_container, fragment1).addToBackStack(null).commit();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.zuyoChat) {
            Intent it=new Intent();
            it.setClass(MainActivity.this, ChatActivity.class);
            startActivity(it);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setKey(String key) {
        fragment_QuestDetails fragment1 = new fragment_QuestDetails();
        fragment1.updateInfo(key);
        getFragmentManager().beginTransaction().replace(R.id.content_container, fragment1).addToBackStack(null).commit();
    }


}
