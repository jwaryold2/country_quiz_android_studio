package edu.uga.cs.project4countryquiz;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BackEnd extends SQLiteOpenHelper {
    private static final String DB_NAME = "countriesQuiz.db";
    private static final int DB_VERSION = 1;
    private static BackEnd helperInstance;

    //table structure
    private final Context mContext;

    public static final String TABLE_COUNTRIES = "countries";
    public static final String COUNTRIES_COLUMN_ID = "_id";
    public static final String COUNTRIES_COLUMN_COUNTRY = "country";
    public static final String COUNTRIES_COLUMN_CONTINENT = "continent";

    //table for quiz results

    private static final String CREATE_COUNTRIES = "create table " + TABLE_COUNTRIES + " ("+
            COUNTRIES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+COUNTRIES_COLUMN_COUNTRY +
            " TEXT, "+ COUNTRIES_COLUMN_CONTINENT +" TEXT )";

    public BackEnd(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
        fillDatabaseFromCSV();
    }

    public static synchronized BackEnd getInstance (Context context) {
        if (helperInstance == null) {
            helperInstance = new BackEnd(context.getApplicationContext());

        }
        return helperInstance;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL( CREATE_COUNTRIES );
    }
    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "drop table if exists " + TABLE_COUNTRIES );
        onCreate( db );
    }

    public List<Country> getAllCountry() {
        List<Country> countryList = new ArrayList<>();
        //select ALL
        String selectQuery = "SELECT * FROM "+ TABLE_COUNTRIES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //add rows to list
        if(cursor.moveToFirst()){
            do{
                Country c = new Country(cursor.getString(1), cursor.getString(2));
                countryList.add(c);
            }while(cursor.moveToNext());
        }
        return countryList;
    }

    public void fillDatabaseFromCSV() {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement statement = null;

        try {
            // Construct the SQL INSERT statement
            String insertStatement = "INSERT INTO " + TABLE_COUNTRIES + " (" +
                    COUNTRIES_COLUMN_COUNTRY + ", " + COUNTRIES_COLUMN_CONTINENT + ") VALUES (?, ?)";

            // Prepare the statement for insertion
            statement = db.compileStatement(insertStatement);

            // Begin transaction for bulk insert
            db.beginTransaction();

            // Read the CSV file and fill the database
            Resources resources = mContext.getResources();
            InputStream inputStream = resources.openRawResource(R.raw.country_continent); // Place your CSV file in res/raw
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(","); // Assuming comma-separated values

                // Bind values to the prepared statement
                statement.bindString(1, data[0]); // Country
                statement.bindString(2, data[1]); // Continent

                // Execute the statement
                statement.execute();

                // Reset the statement for the next iteration
                statement.clearBindings();
            }

            // Commit the transaction
            db.setTransactionSuccessful();
        } catch (Exception e) {
            // Handle exceptions
        } finally {
            // End transaction and close resources
            if (statement != null) {
                statement.close();
            }
            db.endTransaction();
            db.close();
        }
    }

}
