package com.eeproject.myvolunteer.myvolunteer;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Altman on 2015-11-12.
 */
public class addquestfragment extends Fragment {
    Context context;

    //Setup timepicker
    final Calendar time = Calendar.getInstance();
    int mHour = time.get(Calendar.HOUR_OF_DAY);
    int mMinute = time.get(Calendar.MINUTE);
    int mYear = time.get(Calendar.YEAR);
    int mMonth = time.get(Calendar.MONTH);
    int mDate = time.get(Calendar.DATE);
    StringBuilder builder = new StringBuilder();

    //Setup float
    Button submit;
    EditText titleedittext, infoedittext, dateedittext, locationedittext;
    Spinner catagoryspinner, languagespinner;
    CheckBox termofusecheckbox;

    //setup DBHelper
    SQLiteDatabase db;
    public DBHelper helper;
    Cursor cursor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addquest, null);
        context = container.getContext();
        helper = new DBHelper(context, DBHelper.DATABASE_NAME);
        addquest(v);
        return v;
    }

    //Implementing the add quest float
    public void addquest(View view){

        titleedittext = (EditText)view.findViewById(R.id.titleedittext);
        infoedittext = (EditText)view.findViewById(R.id.infoedittext);
        dateedittext = (EditText)view.findViewById(R.id.dateedittext);
        locationedittext = (EditText)view.findViewById(R.id.locationedittext);

        dateedittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateedittext.setText("");
                builder.setLength(0);
                datepicker();
            }
        });

        catagoryspinner = (Spinner) view.findViewById(R.id.catspinner);
        languagespinner = (Spinner) view.findViewById(R.id.langspinner);

        ArrayAdapter<String> catadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, parameter.catagory);
        catagoryspinner.setAdapter(catadapter);

        ArrayAdapter<String> langadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, parameter.language);
        languagespinner.setAdapter(langadapter);

        termofusecheckbox = (CheckBox)view.findViewById(R.id.termofusecheckBox);

        submit = (Button)view.findViewById(R.id.submitbutton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleedittext.getText().toString().equals("") || titleedittext.getText().toString().equals("Title cannot be null!")) {
                    sethighlight(titleedittext, "Title");
                } else {
                    if (infoedittext.getText().toString().equals("") || infoedittext.getText().toString().equals("Information cannot be null!")) {
                        sethighlight(infoedittext, "Information");
                    } else {
                        if (dateedittext.getText().toString().equals("") || dateedittext.getText().toString().equals("Date cannot be null!")) {
                            sethighlight(dateedittext, "Date");
                        } else {
                            if (locationedittext.getText().toString().equals("") || locationedittext.getText().toString().equals("Location cannot be null!")) {
                                sethighlight(locationedittext, "Location");
                            } else {
                                if (termofusecheckbox.isChecked() == false) {
                                    Toast.makeText(context, "You must agree the term of use before posting quest!", Toast.LENGTH_LONG).show();
                                } else {
                                    db = helper.getWritableDatabase();

                                    ContentValues cv = new ContentValues();

                                    cv.put(DBHelper.TITLE, titleedittext.getText().toString());
                                    cv.put(DBHelper.INFO, infoedittext.getText().toString());
                                    cv.put(DBHelper.EXPIRYDATE, dateedittext.getText().toString());
                                    cv.put(DBHelper.LOCATION, locationedittext.getText().toString());
                                    cv.put(DBHelper.CATAGORY, catagoryspinner.getSelectedItem().toString());
                                    cv.put(DBHelper.REQUIREDLANGUAGE, languagespinner.getSelectedItem().toString());
                                    cv.put(DBHelper.USER, "username");

                                    db.insert(DBHelper.TABLE_NAME, null, cv);
                                    db.close();
                                    Toast.makeText(context, "Add Quest Successful!", Toast.LENGTH_LONG).show();
                                    QuestListFragment fragment1 = new QuestListFragment();
                                    getFragmentManager().beginTransaction().replace(R.id.content_container, fragment1).commit();
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    public void datepicker(){
        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
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
        TimePickerDialog tpd = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
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

}
