package com.eeproject.myvolunteer.myvolunteer;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


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
        toolbar.setTitle("We are Volunteer!");
        toolbar.setNavigationIcon(R.drawable.nomoregood_small);

        //Get Intent
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
                    changeQuestList();
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
        addquestfragment fragment1 = new addquestfragment();
        getFragmentManager().beginTransaction().replace(R.id.content_container, fragment1).addToBackStack(null).commit();
    }

    private void changeSetting(){
    }

    private void changeShare() {
    }

    private void changezuyoChat() {

    }

    private void changeRanking() {
        rankingfragment fragment1 = new rankingfragment();
        getFragmentManager().beginTransaction().replace(R.id.content_container, fragment1).commit();
    }

    private void changeMyAccount() {
        changeMyAccount fragment1 = new changeMyAccount();
        getFragmentManager().beginTransaction().replace(R.id.content_container, fragment1).addToBackStack(null).commit();
    }

    public void changeQuestList() {
        QuestListFragment fragment1 = new QuestListFragment();
        getFragmentManager().beginTransaction().replace(R.id.content_container, fragment1).addToBackStack(null).commit();
    }

    public void changelogin(){
        login fragment1 = new login();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
