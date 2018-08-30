package com.example.cedwa.studentassistant.DatabaseFiles.ContactsOperations;

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

public class ContactsQuery {

    private Context context;


    public ContactsQuery(Context context)
    {
        this.context = context;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }


    //TODO
    //insert a single Contact
    public long insertContact(ContactStructure contactStructure)
    {
        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.CONTACTS_IMAGE_PATH, contactStructure.getImagePath());
        contentValues.put(Config.CONTACTS_NAME, contactStructure.getName());
        contentValues.put(Config.CONTACTS_PHONE, contactStructure.getPhone());
        contentValues.put(Config.CONTACTS_EMAIL, contactStructure.getEmail());
        contentValues.put(Config.CONTACTS_TYPE, contactStructure.getContactType());


        try
        {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_CONTACTS, null, contentValues);
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


    //return list of all Contacts
    public List<ContactStructure> getAllContacts(String contactsType)
    {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;

        try
        {
            cursor = sqLiteDatabase.query(Config.TABLE_CONTACTS, null, Config.CONTACTS_TYPE+" = ? ",
                    new String[] {contactsType}, null, null, null, null);

            if(cursor!=null)
            {
                if(cursor.moveToFirst())
                {
                    List<ContactStructure> contactStructureList = new ArrayList<>();
                    do {
                        long id = cursor.getLong(cursor.getColumnIndex(Config.CONTACTS_ID));
                        String imagePath = cursor.getString(cursor.getColumnIndex(Config.CONTACTS_IMAGE_PATH));
                        String name = cursor.getString(cursor.getColumnIndex(Config.CONTACTS_NAME));
                        String phone = cursor.getString(cursor.getColumnIndex(Config.CONTACTS_PHONE));
                        String email = cursor.getString(cursor.getColumnIndex(Config.CONTACTS_EMAIL));
                        String contactType = cursor.getString(cursor.getColumnIndex(Config.CONTACTS_TYPE));



                        contactStructureList.add(new ContactStructure(id,imagePath,name,phone,email,contactType));
                    }while(cursor.moveToNext());

                    return contactStructureList;
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

    //Update a single Contact by id
    public long updateContact(ContactStructure contactStructure)
    {
        long updatedRows=-1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.CONTACTS_IMAGE_PATH, contactStructure.getImagePath());
        contentValues.put(Config.CONTACTS_NAME, contactStructure.getName());
        contentValues.put(Config.CONTACTS_PHONE, contactStructure.getPhone());
        contentValues.put(Config.CONTACTS_EMAIL, contactStructure.getEmail());
        contentValues.put(Config.CONTACTS_TYPE, contactStructure.getContactType());


        try {
            updatedRows = sqLiteDatabase.update(Config.TABLE_CONTACTS, contentValues,
                    Config.CONTACTS_ID + " = ? ",
                    new String[] {String.valueOf(contactStructure.getId())});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
        return updatedRows;
    }

    //delete contact by ID
    public long deleteSingleContact(long id)
    {
        long deletedRow=-1;

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            deletedRow = sqLiteDatabase.delete(Config.TABLE_CONTACTS,
                    Config.CONTACTS_ID + " = ? ",
                    new String[]{ String.valueOf(id)});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deletedRow;
    }

    //delete all Contacts
    public boolean deleteAllContacts(){
        boolean deleteStatus=false;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {

            sqLiteDatabase.delete(Config.TABLE_CONTACTS, null, null);

            long count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_CONTACTS);
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

    //get Contacts by id
    public ContactStructure getContactsById(long id){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        ContactStructure contactStructure = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_CONTACTS, null,
                    Config.CONTACTS_ID + " = ? ", new String[]{String.valueOf(id)},
                    null, null, null);

            if(cursor.moveToFirst()){

                String imagePath = cursor.getString(cursor.getColumnIndex(Config.CONTACTS_IMAGE_PATH));
                String name = cursor.getString(cursor.getColumnIndex(Config.CONTACTS_NAME));
                String phone = cursor.getString(cursor.getColumnIndex(Config.CONTACTS_PHONE));
                String email = cursor.getString(cursor.getColumnIndex(Config.CONTACTS_EMAIL));
                String contactType = cursor.getString(cursor.getColumnIndex(Config.CONTACTS_TYPE));

                contactStructure = new ContactStructure(id,imagePath,name,phone,email,contactType);
            }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return contactStructure;
    }

}
