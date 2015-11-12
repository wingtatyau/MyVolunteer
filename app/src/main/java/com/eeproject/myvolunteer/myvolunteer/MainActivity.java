package com.eeproject.myvolunteer.myvolunteer;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends FragmentActivity {
    //Setup context
    Context context = this;



    //DrawerLayout define
    TextView contentView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeQuestList();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        changeQuestList();
        //set up navigation drawer buttons
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                String title = (String) menuItem.getTitle();
                if(title.equals("Add Quest")){
                    changeaddquest();
                }
                if(title.equals("Quest List")){
                    changeQuestList();
                }
                if(title.equals("My Account")){
                    changeMyAccount();
                }
                if(title.equals("zuyoChat!")){
                    changezuyoChat();
                }
                if(title.equals("Share")){
                    changeShare();
                }
                if(title.equals("Setting")){
                    changeSetting();
                }
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void changeaddquest(){
        addquestfragment fragment1 = new addquestfragment();
        getFragmentManager().beginTransaction().replace(R.id.content_container, fragment1).commit();
    }

    private void changeSetting(){
    }

    private void changeShare() {
    }

    private void changezuyoChat() {

    }

    private void changeMyAccount() {
        changeMyAccount fragment1 = new changeMyAccount();
        getFragmentManager().beginTransaction().replace(R.id.content_container, fragment1).commit();
    }

    public void changeQuestList() {
        QuestListFragment fragment1 = new QuestListFragment();
        getFragmentManager().beginTransaction().replace(R.id.content_container, fragment1).commit();
    }

    public void changelogin(){
        login fragment1 = new login();
        getFragmentManager().beginTransaction().replace(R.id.content_container, fragment1).commit();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
