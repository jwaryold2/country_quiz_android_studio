package edu.uga.cs.project4countryquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

/**
 * Activity to hold the ViewPager fragment for the country quiz
 */
public class CountryQuizChildActivity extends AppCompatActivity {

    /**
     * Set layout and ViewPager adapter when the activity is created
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_quiz_child);

        //Activity that handles the generated quiz in ViewPager2 with an adapter from CountryQuizPagerAdapter.java
        ViewPager2 pager = findViewById( R.id.questionPager );
        CountryQuizPagerAdapter cqpAdapter = new CountryQuizPagerAdapter(getSupportFragmentManager(), getLifecycle());
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        pager.setAdapter(cqpAdapter);

    }
}