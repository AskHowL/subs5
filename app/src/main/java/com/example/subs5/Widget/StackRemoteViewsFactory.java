package com.example.subs5.Widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.example.subs5.Model.Movie;
import com.example.subs5.R;
import com.example.subs5.db.MovieHelper;
import java.util.ArrayList;
import java.util.List;


public class StackRemoteViewsFactory  implements RemoteViewsService.RemoteViewsFactory {

    private List<Movie> mlistMovie = new ArrayList<>();
    private final Context mContext;
    private MovieHelper movieHelper;


    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }


    @Override
    public void onCreate() {
        movieHelper = MovieHelper.getInstance(mContext);
        movieHelper.open();
        mlistMovie = movieHelper.getPicFav();

    }



    @Override
    public void onDataSetChanged() {
        //untuk refresh data
        final long identityToken = Binder.clearCallingIdentity();
        // querying ke database

        mlistMovie = movieHelper.getPicFav();

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mlistMovie.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_items);

       try {
            Bitmap bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(mlistMovie.get(position).getImg())
                    .submit(200, 200)
                    .get();

            rv.setImageViewBitmap(R.id.wimageView, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Bundle extras = new Bundle();
        extras.putInt(StackWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.wimageView, fillInIntent);
        return rv;

    }




    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
