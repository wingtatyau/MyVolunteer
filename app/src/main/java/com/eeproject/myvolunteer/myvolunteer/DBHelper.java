package com.eeproject.myvolunteer.myvolunteer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Altman on 2015/10/3.
 */
public class DBHelper extends SQLiteOpenHelper {
    //Define database for storing quests
    public static final String DATABASE_NAME = "questlist.db";
    public static final int VERSION = 1;
    public static SQLiteDatabase database;

    public static final String TABLE_NAME = "quest";

    public static final String KEY_ID = "_id";

    public static final String TITLE = "quest_title";
    public static final String INFO = "quest_info";
    public static final String CATAGORY = "catagory";
    public static final String EXPIRYDATE = "expiry_date";
    public static final String REQUIREDLANGUAGE = "required_language";
    public static final String LOCATION = "quest_location";
    public static final String TERM_OF_USE = "term_of_use";
    public static final String USER = "user";
    public static final String QUEST_CURRENT_PARTI = "quest_current_parti";
    public static final String QUEST_REQUIRED_TIME = "quest_required_time";
    public static final String PARTI_NUMBER = "parti_number";



    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TITLE + " TEXT NOT NULL, " +
                    INFO + " TEXT NOT NULL, " +
                    CATAGORY + " TEXT NOT NULL, " +
                    EXPIRYDATE + " TEXT NOT NULL, " +
                    REQUIREDLANGUAGE + " TEXT NOT NULL, " +
                    USER + " TEXT NOT NULL," +
                    QUEST_CURRENT_PARTI + " INTEGER NOT NULL, " +
                    QUEST_REQUIRED_TIME + " TEXT NOT NULL, " +
                    PARTI_NUMBER + " TEXT NOT NULL, " +
                    LOCATION + " TEXT NOT NULL)";

    //Define table for storing user login information
    public static final String USER_TABLE_NAME = "users";
    public static final String PASSWORD = "password";
    public static final String ICONPATH = "icon_path";
    public static final String RANKING_MARK = "ranking_mark";
    //------------------added by Tat-----------------------------
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String QUEST_ISSUED = "quest_issued";
    public static final String QUEST_ACCEPTED = "quest_accepted";
    //-----------------------------------------------------------

    public static final String USER_CREATE_TABLE =
            "CREATE TABLE "+USER_TABLE_NAME+"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    USER + " TEXT NOT NULL," +
                    PASSWORD + " TEXT NOT NULL," +
                    //---------- added by Tat ----------
                    FIRST_NAME + "TEXT NOT NULL" +
                    LAST_NAME + "TEXT NOT NULL" +
                    QUEST_ISSUED + "INTERGER NOT NULL, " +
                    QUEST_ACCEPTED + "INTERGER NOT NULL, " +
                    //-----------------------------------
                    RANKING_MARK + " INTEGER NOT NULL, " +
                    ICONPATH + " TEXT NOT NULL)";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    public DBHelper(Context context,String name) {
        this(context, name, null, VERSION);
    }

    public DBHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new DBHelper(context, DATABASE_NAME,
                    null, VERSION).getWritableDatabase();
        }
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(USER_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST newMemorandum");
        onCreate(database);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public synchronized void close() {
        super.close();
    }



}
