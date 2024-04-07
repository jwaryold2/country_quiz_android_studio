package edu.uga.cs.project4countryquiz;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScoringFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoringFragment extends Fragment {

    private BackEnd backendData = null;

    public ScoringFragment() {
        // Required empty public constructor
    }
    public static ScoringFragment newInstance() {
        ScoringFragment fragment = new ScoringFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scoring, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        //public void onActivityCreated(Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int score = 0;
        for (int i = 0; i < 6; i++) {
            if (CountryQuestionFragment.correctAnswers[i].equals(CountryQuestionFragment.selectedAnswers[i])) {
                score++;
            }
        }
        TextView scoreView = view.findViewById(R.id.score);
        scoreView.setText("Score: " + score);
        Log.d("Score:", String.valueOf(score));
        Button returnButton = view.findViewById(R.id.returnButton);

        Intent i = new Intent(this.getContext(), MainActivity.class);

        returnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(i);
            }
        });

        backendData = new BackEnd(getActivity());
        backendData.open();

        new BackendWriter().execute(score);
    }
    private class BackendWriter extends AsyncTask<Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer ... arguments) {
                backendData.postScore(arguments[0]);
            return arguments[0];
        }

        @Override
        protected void onPostExecute(Integer integer) {
            Toast.makeText( getActivity(), "Score Posted: "+ integer,
                    Toast.LENGTH_SHORT).show();
        }
    }
}