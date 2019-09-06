package com.example.subs5.Fragment;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.example.subs5.Adapter.TVRecyclerViewAdapter;
import com.example.subs5.Model.TV;
import com.example.subs5.R;
import com.example.subs5.ViewModel.TVViewModel;

import java.util.ArrayList;

public class FragmentTV extends Fragment {


    View v;
    private RecyclerView rvMovie;
    private TVViewModel tvViewModel;
    private TVRecyclerViewAdapter adapter;
    private ProgressBar progressBar;

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;


    public FragmentTV() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvViewModel = ViewModelProviders.of(this).get(TVViewModel.class);
        tvViewModel.getListTV().observe(this, getTV);
        tvViewModel.setListTV();

        adapter = new TVRecyclerViewAdapter();
        adapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tv,container,false);
        rvMovie = v.findViewById(R.id.recyclertv_id);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));



        SearchView simpleSearchView = v.findViewById(R.id.searchTV_id); // inititate a search view
        simpleSearchView.setQueryHint(getResources().getString(R.string.search_hint));

        // perform set on query text listener event
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // do something on text submit
                tvViewModel.searchTV(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                // do something when text changes
                tvViewModel.searchTV(newText);
                return false;
            }
        });


        rvMovie.setAdapter(adapter);

        progressBar = v.findViewById(R.id.progressBarTV);
        showLoading(true);
        return v;
    }

    private Observer<ArrayList<TV>> getTV = new Observer<ArrayList<TV>>() {
        @Override
        public void onChanged(ArrayList<TV> tvItems) {
            if (tvItems != null) {
                adapter.setDataTV(tvItems);
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



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_id);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    tvViewModel.searchTV(newText);

                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    tvViewModel.searchTV(query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_id:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }


}
