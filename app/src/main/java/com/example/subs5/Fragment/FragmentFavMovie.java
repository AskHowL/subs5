package com.example.subs5.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.example.subs5.Adapter.RecyclerViewAdapterMovie;
import com.example.subs5.Model.Movie;
import com.example.subs5.MovieInterface;
import com.example.subs5.R;
import com.example.subs5.db.MovieHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FragmentFavMovie extends Fragment implements MovieInterface {
    View v;

    private RecyclerView rvMovie;
    private RecyclerViewAdapterMovie adapter;
    private ProgressBar progressBar;

    private static final String EXTRA_STATE = "EXTRA_STATE";
    private MovieHelper movieHelper;
    private boolean shouldRefreshOnResume = false;
    private ArrayList<Movie> list ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_fav_movie,container,false);
        rvMovie = v.findViewById(R.id.recyclerfavmovie_id);
        progressBar = v.findViewById(R.id.progressBarFavMovie);
/*

        // Hide Search di fragment fav
        SearchView simpleSearchView = v.findViewById(R.id.search_id);
        simpleSearchView.setVisibility(View.GONE);
*/

        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setHasFixedSize(true);

        movieHelper = MovieHelper.getInstance(getActivity().getApplicationContext());
        movieHelper.open();

        adapter = new RecyclerViewAdapterMovie(getActivity());
        adapter.setListMovie(movieHelper.getAllMovieFavorite());


        if (savedInstanceState == null) {
            new LoadNotesAsync(movieHelper, this).execute();
        } else {
            ArrayList<Movie> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListMovie(movieHelper.getAllMovieFavorite());
            }
        }

        rvMovie.setAdapter(adapter);
        return v;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListMovie() );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });

    }

    private void runOnUiThread(Runnable runnable) {
    }

    @Override
    public void postExecute(ArrayList<Movie> movies) {
        progressBar.setVisibility(View.INVISIBLE);
        adapter.setListMovie(movieHelper.getAllMovieFavorite());
    }



    private static class LoadNotesAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {

        private final WeakReference<MovieHelper> weakMovieHelper;
        private final WeakReference<MovieInterface> weakMovieInterface;

        private LoadNotesAsync(MovieHelper movieHelper, MovieInterface movieInterface) {
            weakMovieHelper = new WeakReference<>(movieHelper);
            weakMovieInterface = new WeakReference<>(movieInterface);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakMovieInterface.get().preExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return weakMovieHelper.get().getAllMovieFavorite();
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);

            weakMovieInterface.get().postExecute(movies);
        }
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.setListMovie(movieHelper.getAllMovieFavorite());
    }

    @Override
    public void onStop() {
        super.onStop();
        shouldRefreshOnResume = true;
    }


}
