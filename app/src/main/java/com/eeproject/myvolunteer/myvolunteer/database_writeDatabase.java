package com.eeproject.myvolunteer.myvolunteer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Altman on 2015-11-14.
 */
public class database_writeDatabase {
    static SQLiteDatabase db;
    static DBHelper helper;
    static Cursor cursor;

    ContentValues cv;

    public static void writeQuest(quest quest, Context context){
        helper = new DBHelper(context, DBHelper.DATABASE_NAME);
        db = helper.getReadableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(DBHelper.TITLE, quest.getTitle());
        cv.put(DBHelper.INFO, quest.getInfo());
        cv.put(DBHelper.EXPIRYDATE, quest.getExpirydate());
        cv.put(DBHelper.LOCATION, quest.getLocation());
        cv.put(DBHelper.CATAGORY, quest.getCatagory());
        cv.put(DBHelper.REQUIREDLANGUAGE, quest.getRequiredLanguage());
        cv.put(DBHelper.USER, quest.getUser());
        cv.put(DBHelper.QUEST_CURRENT_PARTI, quest.getCurrentparti());
        cv.put(DBHelper.QUEST_REQUIRED_TIME, quest.getRequiredTime());
        cv.put(DBHelper.PARTI_NUMBER, quest.getPartinumber());

        db.insert(DBHelper.TABLE_NAME, null, cv);
        db.close();
    }

    public static boolean writeUser(user user, Context context){
        boolean success = false;
        helper = new DBHelper(context, DBHelper.DATABASE_NAME);
        db = helper.getReadableDatabase();
        for(int i = 0 ; i < database_loadDatabase.usernamelist.size(); i++){
            if(database_loadDatabase.usernamelist.get(i).equals(user.getUsername())){
                success = false;
                Log.d("Success: false ", String.valueOf(i));
            }else{
                success = true;
                Log.d("Success: true ", String.valueOf(i));
            }
        }

        if(database_loadDatabase.usernamelist.size() == 0){
            success = true;
        }

        if(success == true) {
            ContentValues cv = new ContentValues();

            cv.put(DBHelper.USER, user.getUsername());
            cv.put(DBHelper.PASSWORD, user.getPassword());
            cv.put(DBHelper.ICONPATH, user.getIconpath());
            cv.put(DBHelper.RANKING_MARK, user.getRanking_mark());

            db.insert(DBHelper.USER_TABLE_NAME, null, cv);
            db.close();
        }
        database_loadDatabase.setArrayList(context);
        return success;
    }
}
