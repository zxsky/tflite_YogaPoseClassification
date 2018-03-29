package com.example.android.tflitecamerademo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xtian on 3/23/18.
 */

public class CalendarDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "custom_calender"; // the name of our database
    private static final int DB_VERSION = 1; // the version of the database

    CalendarDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        updateMyDatabase(sqLiteDatabase, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        updateMyDatabase(sqLiteDatabase, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE RECORD ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "YEAR INTEGER, "
                    + "MONTH INTEGER, "
                    + "DAY INTEGER, "
                    + "TIME TEXT, "
                    + "CONTENT TEXT);");
        }else if(oldVersion < 2){

        }
    }

    public void insertRecord(SQLiteDatabase db, int year, int month, int day, String time, String content) {
        ContentValues recordValues = new ContentValues();
        recordValues.put("YEAR", year);
        recordValues.put("MONTH", month);
        recordValues.put("DAY", day);
        recordValues.put("TIME", time);
        recordValues.put("CONTENT", content);
        db.insert("RECORD", null, recordValues);
    }
}
