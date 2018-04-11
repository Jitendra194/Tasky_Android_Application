package com.example.android.taskreminder.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.taskreminder.db.TaskContract.TaskEntry;

/**
 * Created by jiten on 2/1/2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database";
    private static final int DATABASE_VERSION = 32
            ;

    private static final String CREATE_TASK_TABLE = "CREATE TABLE " + TaskEntry.TABLE_NAME + " (" +
            TaskEntry.COLUMN_TASK_CREATION_DATE + " INTEGER PRIMARY KEY, " +
            TaskEntry.COLUMN_TASK_NAME + " TEXT NOT NULL, " +
            TaskEntry.COLUMN_TASK_DESCRIPTION + " TEXT NOT NULL, " +
            TaskEntry.COLUMN_TASK_TIME_AND_DATE + " INTEGER, " +
            " UNIQUE (" + TaskEntry.COLUMN_TASK_CREATION_DATE + ") ON CONFLICT REPLACE);";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskEntry.TABLE_NAME);
        onCreate(db);
    }
}
