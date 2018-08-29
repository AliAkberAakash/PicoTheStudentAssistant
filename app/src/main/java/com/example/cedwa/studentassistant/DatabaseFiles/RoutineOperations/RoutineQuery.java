package com.example.cedwa.studentassistant.DatabaseFiles.RoutineOperations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.example.cedwa.studentassistant.DatabaseFiles.Config;
import com.example.cedwa.studentassistant.DatabaseFiles.DatabaseHelper;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoutineQuery {

    private Context context;

    public RoutineQuery(Context context)
    {
        this.context=context;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public void insertRoutine (RoutineStructure routine)
    {
        int id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.START, routine.getStart());
        contentValues.put(Config.SUBJECT, routine.getSubject());
        contentValues.put(Config.TEACHER, routine.getTeacher());
        contentValues.put(Config.ALARM, routine.getAlarm());

        try
        {
            sqLiteDatabase.insertOrThrow(Config.TABLE_ROUTINE, null, contentValues);
        }
        catch(SQLiteException e)
        {
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Could not insert data! Running low on memory? " +
                    e.getMessage(), Toast.LENGTH_LONG).show();
        }
        finally {
            sqLiteDatabase.close();
        }
    }

    public List<RoutineStructure> getAllRoutines()
    {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;

        try
        {
            cursor = sqLiteDatabase.query(Config.TABLE_ROUTINE, null, null,
                    null, null, null, null, null);

            if(cursor!=null)
            {
                if(cursor.moveToFirst())
                {
                    List<RoutineStructure> routineList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.ROUTINE_ID));
                        String start = cursor.getString(cursor.getColumnIndex(Config.START));
                        String subject = cursor.getString(cursor.getColumnIndex(Config.SUBJECT));
                        String teacher = cursor.getString(cursor.getColumnIndex(Config.TEACHER));
                        int alarm = cursor.getInt(cursor.getColumnIndex(Config.ALARM));

                        routineList.add(new RoutineStructure(id,start,subject,teacher,alarm));
                    }while(cursor.moveToNext());

                    return routineList;
                }

            }
        }
        catch (Exception e)
        {
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        }
        finally {
            if(cursor!=null)
            {
                cursor.close();
                sqLiteDatabase.close();
            }
        }

        return Collections.emptyList();
    }

    public void updateRoutine(RoutineStructure routine)
    {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.START, routine.getStart());
        contentValues.put(Config.SUBJECT, routine.getSubject());
        contentValues.put(Config.TEACHER, routine.getTeacher());
        contentValues.put(Config.ALARM, routine.getAlarm());

        try {
            sqLiteDatabase.update(Config.TABLE_ROUTINE, contentValues,
                    Config.ROUTINE_ID + " = ? ",
                    new String[] {String.valueOf(routine.getId())});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

    }


    public void deleteSingleRoutine(int id)
    {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            sqLiteDatabase.delete(Config.TABLE_ROUTINE,
                    Config.ROUTINE_ID + " = ? ",
                    new String[]{ String.valueOf(id)});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
    }

    public boolean deleteAllRoutines(){
        boolean deleteStatus=false;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {

            sqLiteDatabase.delete(Config.TABLE_ROUTINE, null, null);

            long count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_ROUTINE);
            if(count==0)
                deleteStatus=true;

        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deleteStatus;
    }


}
