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
    private final String[] continents = {"Asia", "Europe", "North America", "South America", "Africa", "Oceania"};
    private String continent;
    private String countryName;
    private String[] answers = new String[3];
    static int score = 0;
    static String[] correctAnswers = new String[6];
    static String[] selectedAnswers = new String[6];
    static String[] usedCountries = new String[6];
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

        backendData.open();

        if (savedInstanceState != null) {
            countryName = savedInstanceState.getString("currentCountry", null);
            continent = savedInstanceState.getString("currentContinent", null);
            answers = savedInstanceState.getStringArray("currentAnswers");
        }

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
            Random random = new Random();
            Country correctCountry;
            if (continent == null) {
                do {
                    int randomIndex = random.nextInt(country_list.size());
                    correctCountry = country_list.get(randomIndex);
                } while (isUsed(correctCountry));
                usedCountries[versionNum] = correctCountry.name;
                continent = correctCountry.continent;
                correctAnswers[versionNum] = continent;
                String[] incorrectContinents = new String[2];
                do {
                    incorrectContinents[0] = continents[random.nextInt(continents.length)];
                } while (incorrectContinents[0].equals(continent));
                do {
                    incorrectContinents[1] = continents[random.nextInt(continents.length)];
                } while (incorrectContinents[1].equals(incorrectContinents[0]) || incorrectContinents[1].equals(continent));

                GenerateQuestion question = new GenerateQuestion(correctCountry.name, incorrectContinents, continent);
                answers[0] = question.a1;
                answers[1] = question.a2;
                answers[2] = question.a3;
                countryName = question.q2;
            }

            View fragmentView = getView();
            if (fragmentView != null) {
                TextView titleView = fragmentView.findViewById(R.id.textView2);
                RadioButton r1 = fragmentView.findViewById(R.id.radioButton);
                RadioButton r2 = fragmentView.findViewById(R.id.radioButton2);
                RadioButton r3 = fragmentView.findViewById(R.id.radioButton3);

                //Log.d("TITLE-VIEW", gq[versionNum].q);
                titleView.setText("Name the continent on which " + countryName + " is located:");
                r1.setText("a) " + answers[0]);
                r2.setText("b) " + answers[1]);
                r3.setText("c) " + answers[2]);

                RadioGroup rg = fragmentView.findViewById(R.id.radioGroup);
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = fragmentView.findViewById(checkedId);
                        if (radioButton != null && radioButton.isChecked()) {
                            String selectedText;
                            if (r1.isChecked()) {
                                selectedText = answers[0];
                            } else if (r2.isChecked()) {
                                selectedText = answers[1];
                            } else {
                                selectedText = answers[2];
                            }
                            selectedAnswers[versionNum] = selectedText;
                        }
                    }
                });
            }
        }

        protected boolean isUsed(Country country) {
            boolean isUsed = false;
            for (int i = 0; i < versionNum; i++) {
                Log.d("usedCountries[i]", usedCountries[i]);
                Log.d("country.name", country.name);
                if (usedCountries[i].equals(country.name)) {
                    isUsed = true;
                    break;
                }
            }
            return isUsed;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("currentCountry", countryName);
        outState.putString("currentContinent", continent);
        outState.putStringArray("currentAnswers", answers);
    }
}