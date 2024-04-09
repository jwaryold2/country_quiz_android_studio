package edu.uga.cs.project4countryquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

/**
 * Activity to view the user's previous quiz results
 */
public class CountryQuizPreviousActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ResultsRecycleAdapter adapter;

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackEnd be = new BackEnd(this);
        be.open();
        List<String> results = be.retrieveAllResults();
        Log.d("RESULTS:", Arrays.toString(results.toArray()));
        setContentView(R.layout.activity_country_quiz_previous);
        recyclerView = findViewById(R.id.previousQuizResults);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ResultsRecycleAdapter(this, results);
        recyclerView.setAdapter(adapter);
    }
}