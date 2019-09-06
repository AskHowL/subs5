package com.example.subs5.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.subs5.Adapter.RecyclerViewAdapter;
import com.example.subs5.Adapter.ViewPagerAdapter;
import com.example.subs5.R;
import com.example.subs5.ViewModel.MovieViewModel;

public class FragmentFav extends Fragment  {

    private MovieViewModel movieViewModel;
    private RecyclerViewAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager firstViewPager;


    public FragmentFav() {
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_bottom_nav, container, false);

        firstViewPager = rootView.findViewById(R.id.viewpager_id);

        tabLayout = rootView.findViewById(R.id.tablayout_id);
        tabLayout.setupWithViewPager(firstViewPager);

        setupViewPager(firstViewPager);
        return rootView;
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.AddMovieFragment(new FragmentFavMovie(), getString(R.string.FavMovi));
        adapter.AddMovieFragment(new FragmentFavTV(), getString(R.string.FavTV) );
        viewPager.setAdapter(adapter);
    }



}
