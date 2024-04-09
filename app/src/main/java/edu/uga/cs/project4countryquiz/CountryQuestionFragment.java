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

/**
 * Fragment to store the UI for each quiz question
 */
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

    public static final GenerateQuestion [] sixQuestions = new GenerateQuestion[7];

    // which quiz question to display in the fragment
    private int questionNum;

    public CountryQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new instance of the fragment
     * @param questionNum the current quiz question
     * @return a new instance of the fragment
     */
    public static CountryQuestionFragment newInstance( int questionNum ) {
        CountryQuestionFragment fragment = new CountryQuestionFragment();
        Bundle args = new Bundle();
        args.putInt( "questionNum", questionNum );
        fragment.setArguments( args );
        return fragment;
    }

    /**
     * When fragment is created, save questionNum
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        if( getArguments() != null ) {
            questionNum = getArguments().getInt( "questionNum" );
        }
    }

    /**
     * As view is created, create UI by inflating the XML
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return the inflated view
     */
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country_question, container, false );
    }

    /**
     * Once view is created, save the current country, continent, and answers if they exist yet, then execute BackendReader()
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

    /**
     * Get the number of questions in the quiz (should always be 6)
     * @return number of questions
     */
    public static int getNumberOfQuestions() {
        return sixQuestions.length;
    }

    /**
     * Class to asynchronously retrieve all the countries and then generate the quiz from them
     */
    private class BackendReader extends AsyncTask<Void, List<Country>> {
        /**
         * Asynchronously retrieve all the countries
         * @param params params
         * @return a list of the countries retrieved
         */
        @Override
        protected List<Country> doInBackground(Void... params) {
            return backendData.retrieveAllCountries();
        }

        /**
         * After the countries have been retrieved, generate the quiz
         * @param countries list of the countries retrieved
         */
        @Override
        protected void onPostExecute(List<Country> countries) {
            country_list.addAll(countries);
            Random random = new Random();
            Country correctCountry;

            // if continent has not been chosen, choose a random continent and create the current quiz question from that
            if (continent == null) {
                do {
                    int randomIndex = random.nextInt(country_list.size());
                    correctCountry = country_list.get(randomIndex);
                } while (isUsed(correctCountry));
                usedCountries[questionNum] = correctCountry.name;
                continent = correctCountry.continent;
                correctAnswers[questionNum] = continent;
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
                    /**
                     * When a new country is selected, add the selected country to selectedAnswers[]
                     * @param group the group in which the checked radio button has changed
                     * @param checkedId the unique identifier of the newly checked radio button
                     */
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
                            selectedAnswers[questionNum] = selectedText;
                        }
                    }
                });
            }
        }

        /**
         * Helper method to ensure the same country can't be used more than once in a single quiz
         * @param country the country to check if it has been used yet
         * @return true if the country has been used in the quiz, false otherwise
         */
        protected boolean isUsed(Country country) {
            boolean isUsed = false;
            for (int i = 0; i < questionNum; i++) {
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

    /**
     * Save necessary information about the current quiz question should the user rotate the screen
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("currentCountry", countryName);
        outState.putString("currentContinent", continent);
        outState.putStringArray("currentAnswers", answers);
    }
}