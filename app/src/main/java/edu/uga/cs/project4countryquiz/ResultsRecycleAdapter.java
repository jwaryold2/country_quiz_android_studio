package edu.uga.cs.project4countryquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultsRecycleAdapter extends RecyclerView.Adapter<ResultsRecycleAdapter.MyViewHolder>  {
    private Context context;
    private List<String> results;


    public ResultsRecycleAdapter (Context context,List<String> results){
        this.context = context;
        this.results = results;
    }
    public void sync()
    {
        results = new ArrayList<String>( results);
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView score;
        TextView time_stamp;
        public MyViewHolder ( View itemView) {
            super(itemView);
            score = (TextView) itemView.findViewById(R.id.score);
            time_stamp=(TextView) itemView.findViewById(R.id.time_stamp);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View vie = LayoutInflater.from(parent.getContext()).inflate(R.layout.results, null);
        return new MyViewHolder(vie);
    }
    @Override
    public void onBindViewHolder( MyViewHolder holder, int position){
        String[] parts = results.get(position).split(" ");
        String score = parts[0]; // Assuming score is the first part of the string
        String stamp = parts[1]; // Assuming stamp is the second part of the string
        holder.score.setText("Score:"+score);
        holder.time_stamp.setText("Timestamp:" + stamp);
    }
    @Override
    public int getItemCount(){
        return results.size();
    }
}