package edu.uga.cs.project4countryquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main activity
 */
public class MainActivity extends AppCompatActivity {

    /**
     * When the activity is created, set the layout and add button functionality
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //button listener and intent for the starting quiz activity
        Intent r = new Intent(this, CountryQuizChildActivity.class);
        Button newQuiz = findViewById( R.id.newquiz );
        newQuiz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(r);
            }
        });

        //button listener and intent for the results activity
        Button previous_quizzes = findViewById( R.id.button2 );
        Intent c  = new Intent(this, CountryQuizPreviousActivity.class);
        previous_quizzes.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(c);
            }
        });

    }
}