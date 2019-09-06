package com.example.subs5.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    private DatabaseContract() {
    }

    public static final class MovieColumn implements BaseColumns {
        public static final String TABLE_MOVIE = "favmovie";
        static final String ID = "id";
        static final String TITLE = "title";
        static final String DESCRIPTION = "description";
        public static final String PIC = "pic";
        static final String IS_MOVIE = "is_movie";
    }
}
