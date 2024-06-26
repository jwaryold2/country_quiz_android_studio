package edu.uga.cs.project4countryquiz;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CountryQuizPagerAdapter extends FragmentStateAdapter {
    public CountryQuizPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle ) {
        super( fragmentManager, lifecycle );
    }
    @Override
    public Fragment createFragment(int position){
        if (position < 6) {
            return CountryQuestionFragment.newInstance(position);
        } else {
            return ScoringFragment.newInstance();
        }
    }
    @Override
    public int getItemCount() {
        return CountryQuestionFragment.getNumberOfQuestions() + 1;
    }
}
