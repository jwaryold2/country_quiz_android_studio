package edu.uga.cs.project4countryquiz;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BackEnd {
    private static final String DB_NAME = "countriesQuiz.db";
    private static final int DB_VERSION = 1;

    //table structure
    public static final String DEBUG_TAG = "backend";

    //table for quiz results
    private SQLiteDatabase   db;
    private SQLiteOpenHelper Backendhelper;
    private static final String[] allColumns = {
            BackendHelper.COUNTRIES_COLUMN_ID,
            BackendHelper.COUNTRIES_COLUMN_COUNTRY,
            BackendHelper.COUNTRIES_COLUMN_CONTINENT
    };

    public BackEnd(Context context) {
        this.Backendhelper = BackendHelper.getInstance( context );
    }

    //open the database
    public void open(){
        db = Backendhelper.getWritableDatabase();
        Log.d( DEBUG_TAG, "JobLeadsData: db open" );
    }
    public void close() {
        if( Backendhelper != null ) {
            Backendhelper.close();
            Log.d(DEBUG_TAG, "JobLeadsData: db closed");
        }
    }

    public boolean isDBOpen()
    {
        return db.isOpen();
    }

    //Retrieve all DB entries as a list of strings
    //For each row we can create a new Country
    public List<Country> retrieveAllCountries() {
        ArrayList<Country> countries = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;
        try{
            cursor = db.query( BackendHelper.TABLE_COUNTRIES, allColumns, null, null,null,null,null);

            if( cursor!=null && cursor.getCount() > 0) {

                while( cursor.moveToNext() ) {

                    if( cursor.getColumnCount() >= 0){

                        //get all attribute values of country
                        columnIndex = cursor.getColumnIndex( BackendHelper.COUNTRIES_COLUMN_COUNTRY );
                        String country = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex( BackendHelper.COUNTRIES_COLUMN_CONTINENT );
                        String continent  = cursor.getString(columnIndex);

                        Country dbc = new Country( country, continent );
                        countries.add(dbc);
                       // Log.d(DEBUG_TAG, "Retrieved Country: " + dbc);
                    }//if

                }//while

            }//if
            if( cursor != null )
                Log.d( DEBUG_TAG, "Number of records from DB: " + cursor.getCount() );
            else
                Log.d( DEBUG_TAG, "Number of records from DB: 0" );
        } //try
        catch( Exception e){
            Log.d( DEBUG_TAG, "EXCEPTION CAUGHT: "+e);
        }
        finally{
            if(cursor!=null){
                cursor.close();
            }
        }
        //Log.d(DEBUG_TAG, "COUNTRIES:"+ Arrays.toString(new ArrayList[]{countries}));
        return countries;
    }
}
