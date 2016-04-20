package com.eeproject.myvolunteer.myvolunteer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Altman on 2015-11-12.
 */
public class fragment_AddQuestMaterial extends Fragment {
    Context context;
    Toolbar toolbar;

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
    EditText titleedittext, infoedittext, dateedittext, locationedittext, participantedittext, timeedittext;
    Spinner catagoryspinner, languagespinner;
    CheckBox termofusecheckbox;
    List<EditText> edittextlist = new ArrayList<>();


    public static ImageView title;
    public static Bitmap bitmap;

    boolean loading;
    ProgressDialog dialog;

    //setup DBHelper
//    SQLiteDatabase db;
//    public DBHelper helper;
//    Cursor cursor;

    Firebase rootRef = new Firebase("https://blistering-fire-9077.firebaseio.com/android/");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addquestmaterial, null);
        context = container.getContext();
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setTitle("Add Quest");
//        helper = new DBHelper(context, DBHelper.DATABASE_NAME);
        addquest(v);
        return v;
    }


    public void setedittextarray(){
        edittextlist.add(titleedittext);
        edittextlist.add(infoedittext);
        edittextlist.add(dateedittext);
        edittextlist.add(locationedittext);
        edittextlist.add(participantedittext);
        edittextlist.add(timeedittext);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            Uri targetUri = data.getData();
            //textTargetUri.setText(targetUri.toString());
            try {
                Bitmap temp = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(targetUri));
                bitmap = temp;
                //Bitmap resized = Bitmap.createScaledBitmap(temp, 400, 400, true);
                //Bitmap conv_bm = Bitmap_factory.getRoundedRectBitmap(resized, 400);
                title.setImageBitmap(temp);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    //Implementing the add quest float
    public void addquest(View view){

        titleedittext = (EditText)view.findViewById(R.id.titleedittext);
        infoedittext = (EditText)view.findViewById(R.id.infoedittext);
        dateedittext = (EditText)view.findViewById(R.id.dateedittext);
        locationedittext = (EditText)view.findViewById(R.id.locationedittext);
        participantedittext = (EditText)view.findViewById(R.id.participantsedittext);
        timeedittext = (EditText) view.findViewById(R.id.timeedittext);
        submit = (Button) view.findViewById(R.id.submit);


        participantedittext.setRawInputType(Configuration.KEYBOARD_QWERTY);
        timeedittext.setRawInputType(Configuration.KEYBOARD_QWERTY);

        title = (ImageView) view.findViewById(R.id.img_upload);

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

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

        setedittextarray();

        catagoryspinner = (Spinner) view.findViewById(R.id.catspinner);
        languagespinner = (Spinner) view.findViewById(R.id.langspinner);

        ArrayAdapter<String> catadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, parameter.catagory);
        catagoryspinner.setAdapter(catadapter);

        ArrayAdapter<String> langadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, parameter.language);
        languagespinner.setAdapter(langadapter);

        termofusecheckbox = (CheckBox)view.findViewById(R.id.termofusecheckBox);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < edittextlist.size(); i++) {
                    if (edittextlist.get(i).getText().toString().equals("")) {
                        sethighlight(edittextlist.get(i), parameter.edittextname[i]);
                        Log.v("DEBUG", String.valueOf(i));
                    }else if(termofusecheckbox.isChecked() == false){
                        Toast.makeText(v.getContext(), "You must agree the term of use", Toast.LENGTH_LONG).show();
                    }else if(i == edittextlist.size()-1){
                        loading = true;
                        //Create object
                        dialog = new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
                        dialog.setTitle("Loading");
                        dialog.setMessage("Please wait... Our Monkeys are logging down the data");
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        dialog.show();
                        new Thread(new Runnable(){
                            @Override
                            public void run() {
                                try{
                                    while (loading);
                                }
                                catch(Exception e){
                                    e.printStackTrace();
                                }
                                finally{
                                    dialog.dismiss();
                                }
                            }
                        }).start();

                        String imgfile = image_handler.encode(bitmap);

                        quest Quest = new quest(titleedittext.getText().toString(), infoedittext.getText().toString(),
                                dateedittext.getText().toString(), locationedittext.getText().toString(),
                                catagoryspinner.getSelectedItem().toString(), languagespinner.getSelectedItem().toString(),
                                parameter.logineduser.getFirstname() + " " + parameter.logineduser.getLastname(),
                                timeedittext.getText().toString(), 0, Integer.parseInt(participantedittext.getText().toString()), imgfile);

                        Log.v("DEBUG", parameter.logineduser.getIconpath());

                        rootRef.child("Quest").push().setValue(Quest, new Firebase.CompletionListener() {
                            @Override
                            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                loading = false;
                                if (firebaseError != null) {
                                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                                    Toast.makeText(context, "Add Quest unsuccessful!", Toast.LENGTH_LONG).show();
                                } else {
                                    System.out.println("Data saved successfully.");
                                    Toast.makeText(context, "Add Quest Successful!", Toast.LENGTH_LONG).show();

                                    //Return to quest list fragment
                                    fragment_QuestListRecyclerView fragment1 = new fragment_QuestListRecyclerView();
                                    getFragmentManager().beginTransaction().replace(R.id.content_container, fragment1).commit();
                                }
                            }
                        });

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
