package com.eeproject.myvolunteer.myvolunteer;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
public class fragment_AddQuest extends Fragment {
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
    EditText titleedittext, infoedittext, dateedittext, locationedittext, participantedittext, timerequirehouredittext, timerequireminuteedittext;
    Spinner catagoryspinner, languagespinner;
    CheckBox termofusecheckbox;

    ImageView title;

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
        participantedittext = (EditText)view.findViewById(R.id.participantsedittext);
        timerequirehouredittext = (EditText)view.findViewById(R.id.timerequireedhourittext);
        timerequireminuteedittext = (EditText)view.findViewById(R.id.timerequiredminuteedittext);


        participantedittext.setRawInputType(Configuration.KEYBOARD_QWERTY);
        timerequirehouredittext.setRawInputType(Configuration.KEYBOARD_QWERTY);
        timerequireminuteedittext.setRawInputType(Configuration.KEYBOARD_QWERTY);

        timerequireminuteedittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && timerequireminuteedittext.getText().toString().equals("MM")) {
                    timerequireminuteedittext.setText("");
                }
                return false;
            }
        });

        timerequirehouredittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && timerequirehouredittext.getText().toString().equals("HH")) {
                    timerequirehouredittext.setText("");
                }
                return false;
            }
        });

        title = (ImageView) view.findViewById(R.id.imageView3);
        title.setImageResource(R.drawable.providequestlogo);

        dateedittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    builder.setLength(0);
                    datepicker();
                }
                return false;
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
                                if (timerequirehouredittext.getText().toString().equals("") || timerequirehouredittext.getText().toString().equals("Time Required cannot be null!")) {
                                    sethighlight(timerequirehouredittext, "Time Required");
                                }else {
                                    if (participantedittext.getText().toString().equals("") || participantedittext.getText().toString().equals("Number of participants cannot be null!")) {
                                        sethighlight(participantedittext, "Number of participants");
                                    } else {
                                        if (termofusecheckbox.isChecked() == false) {
                                            Toast.makeText(context, "You must agree the term of use before posting quest!", Toast.LENGTH_LONG).show();
                                        } else {
                                                //Create object
                                            quest Quest = new quest(titleedittext.getText().toString(), infoedittext.getText().toString(),
                                                        dateedittext.getText().toString(), locationedittext.getText().toString(),
                                                        catagoryspinner.getSelectedItem().toString(), languagespinner.getSelectedItem().toString(),
                                                        parameter.logineduser.getFirstname() + " " + parameter.logineduser.getLastname(),
                                                        timerequirehouredittext.getText().toString() + ":" + timerequireminuteedittext.getText().toString(),
                                                        0, Integer.parseInt(participantedittext.getText().toString()));
                                            database_writeDatabase.writeQuest(Quest, context);
                                            Toast.makeText(context, "Add Quest Successful!", Toast.LENGTH_LONG).show();

                                            //Return to quest list fragment
                                            fragment_QuestList fragment1 = new fragment_QuestList();
                                            getFragmentManager().beginTransaction().replace(R.id.content_container, fragment1).commit();

                                        }
                                    }
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
                    dateedittext.setText(builder);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        }, mHour, mMinute, false);
        tpd.show();
    }

    public void sethighlight(EditText v, String type){
        v.setHint(type + " cannot be null!");
        v.setHintTextColor(Color.GRAY);
    }

}
