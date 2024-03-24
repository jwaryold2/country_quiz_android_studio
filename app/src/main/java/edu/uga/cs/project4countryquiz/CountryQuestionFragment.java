package edu.uga.cs.project4countryquiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class CountryQuestionFragment extends Fragment {
    int score = 0;
    int listsize;
    private BackEnd BE;
    @SuppressLint("NewApi")
    LocalDate ld = LocalDate.now();
    @SuppressLint("NewApi")
    LocalTime lt = LocalTime.now();
    // Array of Android version code names
    private static final String[] androidVersions = {
            "Germany", "Japan", "USA"
    };

    public static final GenerateQuestion [] sixQuestions = new GenerateQuestion[6];
    // Array of Android version highlights/brief descriptions
    private static final String[] androidVersionsInfo = {
            // Germany
            "France", "Japan", "USA",
            // Japan
            "USA", "Germany", "China",
            // USA
            "Canada", "Mexico", "Germany"
    };

    private static final String[] correctAnswers = {
            "France", "Germany", "Canada"
    };


    // which Android version to display in the fragment
    private int versionNum;

    public CountryQuestionFragment() {
        // Required empty public constructor
    }

    public static CountryQuestionFragment newInstance( int versionNum ) {
        CountryQuestionFragment fragment = new CountryQuestionFragment();
        Bundle args = new Bundle();
        args.putInt( "versionNum", versionNum );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        if( getArguments() != null ) {
            versionNum = getArguments().getInt( "versionNum" );
        }
        BE = BackEnd.getInstance(getContext());
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country_question, container, false );
    }

    @Override
    public void onViewCreated( @NonNull View view, Bundle savedInstanceState ) {
        //public void onActivityCreated(Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        TextView titleView = view.findViewById( R.id.textView2 );
        RadioGroup rg = view.findViewById( R.id.radioGroup );
        RadioButton r1 = view.findViewById( R.id.radioButton );
        RadioButton r2 = view.findViewById( R.id.radioButton2 );
        RadioButton r3 = view.findViewById( R.id.radioButton3 );

        List<Country> Countries_from_db = BE.getAllCountry();
        //List Filled with all country names
        List<String> country_names = new ArrayList<>();
        //List Fill with corresponding country continents
        List<String> country_continents = new ArrayList<>();
        for(Country c: Countries_from_db) {
            country_names.add(c.name);
            country_continents.add(c.continent);
            Log.d("List Of Countries", c.name);
        }
        //Set of all possible unique answer choices
        Set<String> answerChoices = new HashSet<>();
        answerChoices.add(country_continents.toString());


        //THIS AND BELOW IS ALL FUCKED UP WITH RANDOM NUMBER ETCS ????
        Set<Integer> uniqueIndices = new HashSet<>();
        Random random = new Random();
        while (uniqueIndices.size() < 6) {
            int randomIndex = random.nextInt(country_names.size());
            uniqueIndices.add(randomIndex);
        }
        List<Integer> B = new ArrayList<>();
        for(int i: uniqueIndices){
            B.add(i);
        }


        for(int i = 0; i < 6; i++){
            List<Integer> used  = new ArrayList<>();
            String[] random_continents = new String[3];
            //first random answer choice
            int randomIndex1= random.nextInt(country_names.size());
            if(!used.contains(randomIndex1) && random.nextInt(country_names.size())!=B.get(i)) {
                random_continents[0] = country_continents.get(random.nextInt(country_names.size()));
                used.add(randomIndex1);
            }
            //second random answer choice
            int randomIndex2= random.nextInt(country_names.size());
            if(!used.contains(random.nextInt(country_names.size())) && random.nextInt(country_names.size())!=B.get(i)) {
                random_continents[1] = country_continents.get(random.nextInt(country_names.size()));
                used.add(randomIndex2);
            }
            //correct answer
            random_continents[2] = country_continents.get(i);
            sixQuestions[i] = new GenerateQuestion(country_names.get(B.get(i)), random_continents, country_continents.get(B.get(i)));
        }
        Log.d("sixQuestions:" , Arrays.toString(sixQuestions));

        titleView.setText(sixQuestions[versionNum].q);
        r1.setText(sixQuestions[versionNum].a1);
        r2.setText(sixQuestions[versionNum].a2);
        r3.setText(sixQuestions[versionNum].a3);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = view.findViewById(checkedId);
                if (radioButton != null && radioButton.isChecked()) {

                    String selectedText = radioButton.getText().toString();
                    if(selectedText == sixQuestions[versionNum].cA) score++;
                    // Do something with the selected text
                    Log.d("Selected Radio Button", selectedText);
                    Log.d("correctA: ", sixQuestions[versionNum].cA);
                    Log.d("Score:", String.valueOf(score));
                    Log.d("data:", BE.getAllCountry().toString());
                }
            }
        });

    }

    public static int getNumberOfVersions() {
        return sixQuestions.length;
    }

}