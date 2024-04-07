package edu.uga.cs.project4countryquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultsRVFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultsRVFragment extends Fragment {

    BackEnd backEndData = null;
    private RecyclerView recyclerView;
    public List<String> scores;
    public List<String> stamps;

    public ResultsRVFragment() {
        // Required empty public constructor
    }
    public static ResultsRVFragment newInstance() {
        ResultsRVFragment fragment = new ResultsRVFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu( true );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results_r_v, container, false);
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

        recyclerView = getView().findViewById( R.id.previousQuizResults);

        // use a linear layout manager for the recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getActivity() );
        recyclerView.setLayoutManager( layoutManager );

        scores = new ArrayList<String>();

        // Create a JobLeadsData instance, since we will need to save a new JobLead to the dn.
        // Note that even though more activites may create their own instances of the JobLeadsData
        // class, we will be using a single instance of the JobLeadsDBHelper object, since
        // that class is a singleton class.
       backEndData = new BackEnd( getActivity() );

        // Open that database for reading of the full list of job leads.
        // Note that onResume() hasn't been called yet, so the db open in it
        // was not called yet!
        backEndData.open();

        // Execute the retrieval of the job leads in an asynchronous way,
        // without blocking the main UI thread.
        new BackEndDBReader().execute();
    }
    private class BackEndDBReader extends AsyncTask<Void, List<String>> {
        // This method will run as a background process to read from db.
        // It returns a list of retrieved JobLead objects.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onCreate callback (the job leads review activity is started).
        @Override
        protected List<String> doInBackground( Void... params ) {
            List<String> scores = backEndData.retrieveAllResults();

            Log.d( "RV:", "JobLeadDBReader: Job leads retrieved: " + scores.size() );

            return scores;
        }

        // This method will be automatically called by Android once the db reading
        // background process is finished.  It will then create and set an adapter to provide
        // values for the RecyclerView.
        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
        @Override
        protected void onPostExecute( List<String> jobsList ) {
            Log.d( "RV", "JobLeadDBReader: jobsList.size(): " + jobsList.size() );
            scores.addAll( jobsList );

            // create the RecyclerAdapter and set it for the RecyclerView
            recyclerAdapter = new JobLeadRecyclerAdapter( getActivity(), scores );
            recyclerView.setAdapter( recyclerAdapter );
        }
    }
}