package com.example.cedwa.studentassistant.DatabaseFiles;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;

    private static int version = 1;
    private static final String db_name = Config.DATABASE_NAME;

    private DatabaseHelper(Context context) {
        super(context,db_name, null, version);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static synchronized DatabaseHelper getInstance(Context context)
    {
        if(databaseHelper==null){
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        //creating the Routine Table
        db.execSQL(Config.CREATE_ROUTINE_TABLE);
        Logger.d("Table create SQL: " + Config.CREATE_ROUTINE_TABLE);

        //creating the Notes Table
        db.execSQL(Config.CREATE_TABLE_NOTES);
        Logger.d("Table create SQL: " + Config.CREATE_TABLE_NOTES);

        db.execSQL(Config.CREATE_TABLE_CONTACTS);
        Logger.d("Table create SQL: " + Config.CREATE_TABLE_CONTACTS);

        Logger.d("DB created!");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_ROUTINE);

        // Create tables again
        onCreate(db);
    }
}
