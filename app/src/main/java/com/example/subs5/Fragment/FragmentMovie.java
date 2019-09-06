package com.example.subs5.Fragment;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.subs5.Activity.MainActivity;
import com.example.subs5.Adapter.RecyclerViewAdapter;
import com.example.subs5.Model.Movie;
import com.example.subs5.R;
import com.example.subs5.ViewModel.MovieViewModel;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.getSystemService;


public class FragmentMovie extends Fragment  {

    View v;
    private RecyclerView rvMovie;
    private MovieViewModel movieViewModel;
    private RecyclerViewAdapter adapter;
    private ProgressBar progressBar;

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    public FragmentMovie() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RecyclerViewAdapter();
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getListMovie().observe(this, getMovie);
        movieViewModel.setListMovie();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_movie,container,false);
        rvMovie = v.findViewById(R.id.recyclermovie_id);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));



        SearchView simpleSearchView = v.findViewById(R.id.searchMovie_id);
        simpleSearchView.setQueryHint(getResources().getString(R.string.search_hint));

        // perform set on query text listener event
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // do something on text submit
                movieViewModel.searchMovie(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                // do something when text changes
                movieViewModel.searchMovie(newText);
                return false;
            }
        });


        rvMovie.setAdapter(adapter);

        progressBar = v.findViewById(R.id.progressBarMovie);
        showLoading(true);

        return v;
    }

    private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movieItems) {
            if (movieItems != null) {
                adapter.setData(movieItems);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }


/*

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu); // Put your search menu in "menu_search" menu file.
        MenuItem mSearchItem = menu.findItem(R.id.search_id);
        searchView = (SearchView) MenuItemCompat.getActionView(mSearchItem);
        searchView.setIconified(true);

        SearchManager searchManager = (SearchManager)  getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieViewModel.searchMovie(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                movieViewModel.searchMovie(query);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        if (item.getItemId() == R.id.search_id) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    movieViewModel.searchMovie(query);
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    movieViewModel.searchMovie(newText);
                    return false;
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }*/

}
