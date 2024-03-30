package edu.uga.cs.project4countryquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

public class CountryQuizChildActivity extends AppCompatActivity {

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