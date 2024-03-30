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
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;


public class CountryQuestionFragment extends Fragment {
    private BackEnd backendData = null;
    private List<Country> country_list;
    private List<String[]> continents;
    static int score = 0;
    int listsize;
    @SuppressLint("NewApi")
    LocalDate ld = LocalDate.now();
    @SuppressLint("NewApi")
    LocalTime lt = LocalTime.now();
    // Array of Android version code names

    public static final GenerateQuestion [] sixQuestions = new GenerateQuestion[7];
    // Array of Android version highlights/brief descriptions



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
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country_question, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView titleView = view.findViewById(R.id.textView2);
        RadioGroup rg = view.findViewById(R.id.radioGroup);
        RadioButton r1 = view.findViewById(R.id.radioButton);
        RadioButton r2 = view.findViewById(R.id.radioButton2);
        RadioButton r3 = view.findViewById(R.id.radioButton3);

        country_list = new ArrayList<>();
        backendData = new BackEnd(getActivity());
        continents = new ArrayList<String[]>();

        backendData.open();

        new BackendReader().execute();
    }

    public static int getScore() {
        return score;
    }

    public static int getNumberOfVersions() {
        return sixQuestions.length;
    }

    private class BackendReader extends AsyncTask<Void, List<Country>> {
        @Override
        protected List<Country> doInBackground(Void... params) {
            return backendData.retrieveAllCountries();
        }

        @Override
        protected void onPostExecute(List<Country> countries) {
            country_list.addAll(countries);
            GenerateQuestion[] Questions = new GenerateQuestion[7];
            for (int i = 0; i < 6; i++) {
                Questions[i] = new GenerateQuestion(country_list.get(i));
                Log.d("QUESTIONS-ARRAY:", "QUESTION ARRAY: "+ Questions[i].q +" "+Questions[i].cA);
            }

            Random random = new Random();

            for (int i = 0; i < 6; i++) {
                String a = Questions[i].cA;
                String[] b = new String[2];
                for (int j = 0; j < 2; j++) {
                    int randomIndex;
                    String continent;
                    do {
                        randomIndex = random.nextInt(country_list.size());
                        continent = country_list.get(randomIndex).continent;
                    } while (randomIndex == i || continent.equals(b[0])); // Ensure random index is different from current country index and continent is different from previous one
                    b[j] = continent;
                }
                Log.d("QUESTIONS-ARRAY:", "B: " + Arrays.toString(b));
                continents.add(b);
            }
            //Log.d("QUESTIONS-ARRAY:", "continent ARRAY: "+ Arrays.toString(continents.get(1)));


            GenerateQuestion[] gq = new GenerateQuestion[7];
            for(int i = 0; i < 6; i++){
                gq[i] = new GenerateQuestion(Questions[i].q, continents.get(i), Questions[i].cA);
                //Log.d("QUESTIONS-ARRAY:", "gq: " + gq[i].q2);
            }

            View fragmentView = getView();
            if (fragmentView != null) {
                TextView titleView = fragmentView.findViewById(R.id.textView2);
                RadioButton r1 = fragmentView.findViewById(R.id.radioButton);
                RadioButton r2 = fragmentView.findViewById(R.id.radioButton2);
                RadioButton r3 = fragmentView.findViewById(R.id.radioButton3);

                //Log.d("TITLE-VIEW", gq[versionNum].q);
                titleView.setText(gq[versionNum].q2);
                r1.setText(gq[versionNum].a1);
                r2.setText(gq[versionNum].a2);
                r3.setText(gq[versionNum].a3);

                RadioGroup rg = fragmentView.findViewById(R.id.radioGroup);
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = fragmentView.findViewById(checkedId);
                        if (radioButton != null && radioButton.isChecked()) {
                            String selectedText = radioButton.getText().toString();
                            if (selectedText.equals(gq[versionNum].ccc)) {
                                score+=2;
                            }else{
                                score+=1;
                            }
                        }
                    }
                });
            }
        }
    }
}