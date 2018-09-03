package com.example.cedwa.studentassistant.DatabaseFiles;

public class Config {

    public static final String DATABASE_NAME = "assistant-db";

    /*
     *Information for Routine Table
     */

    //column names of Routines Table
    public static final String ROUTINE_ID = "_id";
    public static final String START = "start";
    public static final String SUBJECT = "subject";
    public static final String TEACHER = "teacher";
    public static final String ALARM = "alarm";

    //Day names for routine table
    public static final String DAY_SAT = "saturday";
    public static final String DAY_SUN = "sunday";
    public static final String DAY_MON = "monday";
    public static final String DAY_TUE = "tuesday";
    public static final String DAY_WED = "wednesday";
    public static final String DAY_THU = "Thursday";
    public static final String DAY_FRI = "friday";

    //Routine Table name
    public static String TABLE_ROUTINE = "routine";

    //create routine table
    public static String CREATE_ROUTINE_TABLE = "CREATE TABLE "+TABLE_ROUTINE+"("+
                                                ROUTINE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                                                START+" TEXT, "+
                                                SUBJECT+" TEXT, "+TEACHER+" TEXT, "+
                                                ALARM+" INTEGER )";

    /*
     *Information for Notes Table
     */

    //Table name for Notes
    public static final String TABLE_NOTES = "notes";

    //column name for Notes
    public static final String NOTES_ID = "_id";
    public static final String NOTES_TITLE = "title";
    public static final String NOTES_DESCRIPTION = "description";

    //create Notes Table
    public static final String CREATE_TABLE_NOTES = "CREATE TABLE "+TABLE_NOTES+"("+
                                                    NOTES_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                                                    NOTES_TITLE+" TEXT, "+
                                                    NOTES_DESCRIPTION+" TEXT )";

    /*
     *Information for Contacts Table
     */

    //Table name for Notes
    public static final String TABLE_CONTACTS = "contacts";

    //column name for Notes
    public static final String CONTACTS_ID = "_id";
    public static final String CONTACTS_IMAGE_PATH = "image";
    public static final String CONTACTS_NAME = "name";
    public static final String CONTACTS_PHONE = "phone";
    public static final String CONTACTS_EMAIL = "email";
    public static final String CONTACTS_TYPE = "type";
    
    //create Notes Table
    public static final String CREATE_TABLE_CONTACTS = "CREATE TABLE "+TABLE_CONTACTS+"("+
            CONTACTS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            CONTACTS_IMAGE_PATH+" TEXT, "+
            CONTACTS_NAME+" TEXT, "+
            CONTACTS_PHONE+" TEXT, "+
            CONTACTS_EMAIL+" TEXT, "+
            CONTACTS_TYPE+" TEXT )";

}
