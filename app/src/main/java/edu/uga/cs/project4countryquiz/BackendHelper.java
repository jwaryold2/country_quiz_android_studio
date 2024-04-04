package edu.uga.cs.project4countryquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BackendHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "countriesQuiz.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_COUNTRIES = "countries";
    public static final String COUNTRIES_COLUMN_ID = "_id";
    public static final String COUNTRIES_COLUMN_COUNTRY = "country";
    public static final String COUNTRIES_COLUMN_CONTINENT = "continent";

    //Reference to only instance for the helper
    private static BackendHelper helperInstance;

    private static final String CREATE_COUNTRIES = "create table " + TABLE_COUNTRIES + " ("+
            COUNTRIES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+COUNTRIES_COLUMN_COUNTRY +
            " TEXT, "+ COUNTRIES_COLUMN_CONTINENT +" TEXT )";


    public static final String result_id = "_id";
    public static final String time_stamp ="time_stamp";
    public static final String score = "score";
    public static final String TABLE_NAME1= "results";

    private static final String CREATE_RESULTS = "create table " + "results" + " ("+
            result_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "+time_stamp+
            " TEXT, "+ score +" INTEGER )";

    private BackendHelper ( Context context ) {
        super( context,DB_NAME, null, DB_VERSION );
    }

    //Access to the single instance of the class
    //Synchronized so onyl one thread can execute this method at a time
    public static synchronized BackendHelper getInstance( Context context ){
        if (helperInstance == null) {
            helperInstance = new BackendHelper( context.getApplicationContext());
        }
        return helperInstance;
    }

    //Override onCreate which will create db if not exist
    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL("create table "+TABLE_NAME1+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, timestamp TEXT, score INTEGER)");
        db.execSQL( CREATE_COUNTRIES );
        Log.d( "backend", "Table " + TABLE_COUNTRIES + " created" );
        Log.d("backend", "Table results created");
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "drop table if exists " + TABLE_COUNTRIES );
        onCreate( db );
    }
}
