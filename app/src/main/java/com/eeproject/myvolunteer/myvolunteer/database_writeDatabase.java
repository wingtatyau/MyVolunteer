package com.eeproject.myvolunteer.myvolunteer;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Altman on 2015-11-14.
 */
public class database_writeDatabase {
    static SQLiteDatabase db;
    static DBHelper helper;

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
        cv.put(DBHelper.USER_ID, parameter.userID);
        cv.put(DBHelper.ICONPATH, parameter.logineduser.getIconpath());

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
            //------------------- added by Tat ---------------------
            cv.put(DBHelper.FIRSTNAME, user.getFirstname());
            cv.put(DBHelper.LASTNAME, user.getLastname());
            cv.put(DBHelper.ORGANIZATION, user.getLastname());
            cv.put(DBHelper.QUEST_ISSUED, user.getQuest_issued());
            cv.put(DBHelper.QUEST_ACCEPTED, user.getQuest_accepted());
            //------------------------------------------------------

            db.insert(DBHelper.USER_TABLE_NAME, null, cv);
            db.close();
        }
        database_loadDatabase.setArrayList(context);
        return success;
    }

    public static void updatetable(Context context, String table, String field, int key_id, int content){
        Log.d("input content", key_id + " " + content);
        helper = new DBHelper(context, DBHelper.DATABASE_NAME);
        db = helper.getReadableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(field, content);
        db.update(table, cv, DBHelper.KEY_ID + "=" + String.valueOf(key_id) , null);
        db.close();
    }
}
