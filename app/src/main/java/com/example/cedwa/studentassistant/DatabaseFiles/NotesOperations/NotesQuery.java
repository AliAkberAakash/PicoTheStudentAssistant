package com.example.cedwa.studentassistant.DatabaseFiles.NotesOperations;

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

public class NotesQuery  {

    private Context context;


    public NotesQuery(Context context)
    {
        this.context = context;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    //insert a single Note
    public long insertNotes(NotesStructure notesStructure)
    {
        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.NOTES_TITLE, notesStructure.getNoteTitle());
        contentValues.put(Config.NOTES_DESCRIPTION, notesStructure.getNoteDescription());

        try
        {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_NOTES, null, contentValues);
        }
        catch(SQLiteException e)
        {
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Could not insert data! Running low on memory? "
                    + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        finally {
            sqLiteDatabase.close();

        }

        return id;
    }


    //return list of all Notes
    public List<NotesStructure> getAllNotes()
    {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;

        try
        {
            cursor = sqLiteDatabase.query(Config.TABLE_NOTES, null, null,
                    null, null, null, null, null);

            if(cursor!=null)
            {
                if(cursor.moveToFirst())
                {
                    List<NotesStructure> notesList = new ArrayList<>();
                    do {
                        long id = cursor.getLong(cursor.getColumnIndex(Config.NOTES_ID));
                        String title = cursor.getString(cursor.getColumnIndex(Config.NOTES_TITLE));
                        String description = cursor.getString(cursor.getColumnIndex(Config.NOTES_DESCRIPTION));


                        notesList.add(new NotesStructure(id,title,description));
                    }while(cursor.moveToNext());

                    return notesList;
                }

            }
        }
        catch (Exception e)
        {
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Failed to load data", Toast.LENGTH_SHORT).show();
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

    //Update a single Note by id
    public long updateNotes(NotesStructure notesStructure)
    {
        long id=-1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.NOTES_TITLE, notesStructure.getNoteTitle());
        contentValues.put(Config.NOTES_DESCRIPTION, notesStructure.getNoteDescription());

        try {
            id = sqLiteDatabase.update(Config.TABLE_NOTES, contentValues,
                    Config.NOTES_ID + " = ? ",
                    new String[] {String.valueOf(notesStructure.getId())});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
        return id;
    }

    //delete note by ID
    public long deleteSingleNote(long id)
    {
        long deletedRow=-1;

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            deletedRow = sqLiteDatabase.delete(Config.TABLE_NOTES,
                    Config.NOTES_ID + " = ? ",
                    new String[]{ String.valueOf(id)});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deletedRow;
    }

    //delete all Notes
    public boolean deleteAllRoutines(){
        boolean deleteStatus=false;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {

            sqLiteDatabase.delete(Config.TABLE_NOTES, null, null);

            long count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_NOTES);
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

    //get Notes by id
    public NotesStructure getNotesById(long id){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        NotesStructure notesStructure = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_NOTES, null,
                    Config.NOTES_ID + " = ? ", new String[]{String.valueOf(id)},
                    null, null, null);

            if(cursor.moveToFirst()){
                String title = cursor.getString(cursor.getColumnIndex(Config.NOTES_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(Config.NOTES_DESCRIPTION));

                notesStructure = new NotesStructure(id, title,description);
            }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return notesStructure;
    }

}
