package com.eeproject.myvolunteer.myvolunteer;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Setup context
    Context context = this;

    //Setup timepicker
    final Calendar time = Calendar.getInstance();
    int mHour = time.get(Calendar.HOUR_OF_DAY);
    int mMinute = time.get(Calendar.MINUTE);
    int mYear = time.get(Calendar.YEAR);
    int mMonth = time.get(Calendar.MONTH);
    int mDate = time.get(Calendar.DATE);
    StringBuilder builder = new StringBuilder();

    //Define for list view
    TextView title, expirydate;
    ImageView icon;
    List<String> titlelist = new ArrayList<>();
    List<String> expirydatelist = new ArrayList<>();
    List<String> catagorylist = new ArrayList<>();
    List<String> infolist = new ArrayList<>();
    List<String> langlist = new ArrayList<>();
    List<String> locationlist = new ArrayList<>();
    ListAdapter adapter;

    String[] catagoryspinnerlist = {"Resource Donation", "Manpower recruitment", "Specialist recruitment"};
    String[] languagespinnerlist = {"Chinese", "English", "Mandarin", "Japanese", "French"};


    //setup DBHelper
    SQLiteDatabase db;
    public DBHelper helper = new DBHelper(MainActivity.this, DBHelper.DATABASE_NAME);
    Cursor cursor;

    //Setup float
    Button submit;
    EditText titleedittext, infoedittext, dateedittext, locationedittext;
    Spinner catagoryspinner, languagespinner;
    CheckBox termofusecheckbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addquest();
            }
        });

    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        setlist();
    }

    public void setlist(){
        ListView list = (ListView) findViewById(R.id.list);

        db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME, null);

        titlelist.clear();
        expirydatelist.clear();
        infolist.clear();
        catagorylist.clear();
        langlist.clear();
        locationlist.clear();

        while(cursor.moveToNext()){
            titlelist.add(cursor.getString(cursor.getColumnIndex("quest_title")));
            expirydatelist.add(cursor.getString(cursor.getColumnIndex("expiry_date")));
            infolist.add(cursor.getString(cursor.getColumnIndex("quest_info")));
            catagorylist.add(cursor.getString(cursor.getColumnIndex("catagory")));
            langlist.add(cursor.getString(cursor.getColumnIndex("required_language")));
            locationlist.add(cursor.getString(cursor.getColumnIndex("quest_location")));
        }
        db.close();

        adapter = new ListAdapter(this);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);

    }
    public class ListAdapter extends BaseAdapter {
        private LayoutInflater ListInflater;

        public ListAdapter(Context c){
            ListInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return titlelist.size();
        }

        @Override
        public Object getItem(int position) {
            return titlelist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            convertView = ListInflater.inflate(R.layout.questitem, null);

            title = (TextView)convertView.findViewById(R.id.titletextview);
            expirydate = (TextView)convertView.findViewById(R.id.expirydatetextview);
            icon = (ImageView)convertView.findViewById(R.id.imageView);

            title.setText(titlelist.get(position));
            expirydate.setText(expirydatelist.get(position));


            return convertView;
        }
    }


    public void addquest(){
        //Set variable
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.addquestfloat, null);

        titleedittext = (EditText)view.findViewById(R.id.titleedittext);
        infoedittext = (EditText)view.findViewById(R.id.infoedittext);
        dateedittext = (EditText)view.findViewById(R.id.dateedittext);
        locationedittext = (EditText)view.findViewById(R.id.locationedittext);

        dateedittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker();
                //timepicker();
            }
        });
        catagoryspinner = (Spinner) view.findViewById(R.id.catspinner);
        languagespinner = (Spinner) view.findViewById(R.id.langspinner);

        ArrayAdapter<String> catadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, catagoryspinnerlist);
        catagoryspinner.setAdapter(catadapter);

        ArrayAdapter<String> langadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languagespinnerlist);
        languagespinner.setAdapter(langadapter);

        termofusecheckbox = (CheckBox)view.findViewById(R.id.termofusecheckBox);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Input a quest here!");
        builder.setView(view);

        builder.setPositiveButton("Submit", null);

        final AlertDialog alert = builder.create();
        alert.show();

        alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titleedittext.getText().toString().equals("")||titleedittext.getText().toString().equals("Title cannot be null!")){
                    sethighlight(titleedittext, "Title");
                    return;
                }else {
                    if (infoedittext.getText().toString().equals("")||infoedittext.getText().toString().equals("Information cannot be null!")) {
                        sethighlight(infoedittext, "Information");
                        return;
                    } else {
                        if (dateedittext.getText().toString().equals("")||dateedittext.getText().toString().equals("Date cannot be null!")) {
                            sethighlight(dateedittext, "Date");
                            return;
                        } else {
                            if (locationedittext.getText().toString().equals("")||locationedittext.getText().toString().equals("Location cannot be null!")) {
                                sethighlight(locationedittext, "Location");
                                return;
                            } else {
                                if (termofusecheckbox.isChecked() == false) {
                                    Toast.makeText(context, "You must agree the term of use before posting quest!", Toast.LENGTH_LONG).show();
                                    return;
                                } else {
                                    db = helper.getWritableDatabase();

                                    ContentValues cv = new ContentValues();

                                    cv.put(DBHelper.TITLE, titleedittext.getText().toString());
                                    cv.put(DBHelper.INFO, infoedittext.getText().toString());
                                    cv.put(DBHelper.EXPIRYDATE, dateedittext.getText().toString());
                                    cv.put(DBHelper.LOCATION, locationedittext.getText().toString());
                                    cv.put(DBHelper.CATAGORY, catagoryspinner.getSelectedItem().toString());
                                    cv.put(DBHelper.REQUIREDLANGUAGE, languagespinner.getSelectedItem().toString());
                                    //cv.put(DBHelper.USER, username);

                                    db.insert(DBHelper.TABLE_NAME, null, cv);
                                    db.close();
                                    alert.dismiss();

                                }
                            }
                        }
                    }
                }
            }
        });

    }
    public void datepicker(){
        builder.setLength(0);
        DatePickerDialog dpd = new DatePickerDialog(this, new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
                String datestring = dayOfMonth + "-" + (monthOfYear+1) + "-" + year;
                Date datetime;
                try {
                    datetime = sdf.parse(datestring);
                    builder.append(sdf.format(datetime).toString() + " ");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                timepicker();
            }
        }, mYear, mMonth, mDate);
        dpd.show();
    }


    public void timepicker(){
        TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Display Selected time in textbox
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String timestring = hourOfDay + ":" + minute;
                Date datetime;
                try {
                    datetime = sdf.parse(timestring);
                    builder.append(sdf.format(datetime).toString());
                    dateedittext.setText(builder.toString());

                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        }, mHour, mMinute, false);
        tpd.show();
    }

    public void sethighlight(EditText v, String type){
        v.setText(type + " cannot be null!");
        v.setTextColor(Color.RED);
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
