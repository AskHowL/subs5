package com.example.subs5.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> movieFragment = new ArrayList<>();
    private final List<String> movieTitle = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return movieFragment.get(i);
    }

    @Override
    public int getCount() {
        return movieTitle.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return movieTitle.get(position);
    }

    public void AddMovieFragment(Fragment fragment,String title){
        movieFragment.add(fragment);
        movieTitle.add(title);
    }

}
