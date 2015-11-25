package com.eeproject.myvolunteer.myvolunteer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Altman on 2015-11-14.
 */
public class database_loadDatabase {
    static SQLiteDatabase db;
    static DBHelper helper;
    static Cursor cursor;

    //ArrayList for storing quest table
    public static List<String> titlelist = new ArrayList<>();
    public static List<String> infolist = new ArrayList<>();
    public static List<String> catagorylist = new ArrayList<>();
    public static List<String> expirydatelist = new ArrayList<>();
    public static List<String> langlist = new ArrayList<>();
    public static List<String> locationlist = new ArrayList<>();
    public static List<String> questuserlist = new ArrayList<>();
    public static List<Integer> questcurrentpartilist = new ArrayList<>();
    public static List<Integer> partinumberlist = new ArrayList<>();
    public static List<String> requiredtime = new ArrayList<>();
    public static List<String> icon = new ArrayList<>();

    //ArrayList for storing user login information
    public static List<String> usernamelist = new ArrayList<>();
    public static List<String> passwordlist = new ArrayList<>();
    public static List<String> ranking_mark = new ArrayList<>();
    public static List<String> iconpathlist = new ArrayList<>();
    //------------------------ added by Tat-----------------------------
    public static List<String> firstnamelist = new ArrayList<>();
    public static List<String> lastnamelist = new ArrayList<>();
    public static List<String> organizationlist = new ArrayList<>();
    public static List<String> questIssuedList = new ArrayList<>();
    public static List<String> questAcceptedList = new ArrayList<>();
    public static List<String> usertablekeyid = new ArrayList<>();
    //-------------------------------------------------------------------


    public static void setArrayList(Context context){
        helper = new DBHelper(context, DBHelper.DATABASE_NAME);
        db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME, null);

        //initialize list which used for quest table
        titlelist.clear();
        expirydatelist.clear();
        infolist.clear();
        catagorylist.clear();
        langlist.clear();
        locationlist.clear();
        questuserlist.clear();
        questcurrentpartilist.clear();
        partinumberlist.clear();
        requiredtime.clear();
        icon.clear();


        while(cursor.moveToNext()){
            titlelist.add(cursor.getString(cursor.getColumnIndex("quest_title")));
            expirydatelist.add(cursor.getString(cursor.getColumnIndex("expiry_date")));
            infolist.add(cursor.getString(cursor.getColumnIndex("quest_info")));
            catagorylist.add(cursor.getString(cursor.getColumnIndex("catagory")));
            langlist.add(cursor.getString(cursor.getColumnIndex("required_language")));
            locationlist.add(cursor.getString(cursor.getColumnIndex("quest_location")));
            questuserlist.add(cursor.getString(cursor.getColumnIndex(DBHelper.USER)));
            questcurrentpartilist.add(cursor.getInt(cursor.getColumnIndex(DBHelper.QUEST_CURRENT_PARTI)));
            partinumberlist.add(cursor.getInt(cursor.getColumnIndex(DBHelper.PARTI_NUMBER)));
            requiredtime.add(cursor.getString(cursor.getColumnIndex(DBHelper.QUEST_REQUIRED_TIME)));
            icon.add(cursor.getString(cursor.getColumnIndex(DBHelper.ICONPATH)));
        }



        //initialize list which used for retrieve user information
        usernamelist.clear();
        passwordlist.clear();
        iconpathlist.clear();
        //------ added by Tat ------
        ranking_mark.clear();
        firstnamelist.clear();
        lastnamelist.clear();
        organizationlist.clear();
        questIssuedList.clear();
        questAcceptedList.clear();
        //--------------------------


        cursor = db.rawQuery("SELECT * FROM " + DBHelper.USER_TABLE_NAME, null);
        while(cursor.moveToNext()){
            usernamelist.add(cursor.getString(cursor.getColumnIndex(DBHelper.USER)));
            passwordlist.add(cursor.getString(cursor.getColumnIndex(DBHelper.PASSWORD)));
            ranking_mark.add(cursor.getString(cursor.getColumnIndex(DBHelper.RANKING_MARK)));
            iconpathlist.add(cursor.getString(cursor.getColumnIndex(DBHelper.ICONPATH)));
            //------------------------------- added by Tat ---------------------------------------
            firstnamelist.add(cursor.getString(cursor.getColumnIndex(DBHelper.FIRSTNAME)));
            lastnamelist.add(cursor.getString(cursor.getColumnIndex(DBHelper.LASTNAME)));
            organizationlist.add(cursor.getString(cursor.getColumnIndex(DBHelper.ORGANIZATION)));
            questIssuedList.add(cursor.getString(cursor.getColumnIndex(DBHelper.QUEST_ISSUED)));
            questAcceptedList.add(cursor.getString(cursor.getColumnIndex(DBHelper.QUEST_ACCEPTED)));
            //------------------------------------------------------------------------------------
        }

        db.close();

    }

}
