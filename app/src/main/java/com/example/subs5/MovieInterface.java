package com.example.subs5;

import com.example.subs5.Model.Movie;

import java.util.ArrayList;

public interface MovieInterface {
    void preExecute();
    void postExecute(ArrayList<Movie> movies);
}
