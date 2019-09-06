package com.example.subs5.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subs5.Model.Movie;
import com.example.subs5.Model.TV;
import com.example.subs5.R;
import com.example.subs5.db.MovieHelper;
import com.squareup.picasso.Picasso;


public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String Extra_Movie = "extra_movie";
    public static final String Extra_TV = "extra_tv";
    private final AppCompatActivity activity = DetailActivity.this;

    TextView tvTitle;
    TextView tvDesc;
    ImageView imgPhoto;
    Button btnDownload;
    private MovieHelper movieHelper;
    private Movie movie;
    private TV tv;

    String title,desc,pic,ismovie;
    int movie_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Movie selectedMovie = getIntent().getParcelableExtra(Extra_Movie);
        TV selectedTV = getIntent().getParcelableExtra(Extra_TV);

        if(selectedMovie!=null){

            tvTitle = findViewById(R.id.tv_detail_title);
            tvDesc = findViewById(R.id.tv_detail_desc);
            imgPhoto = findViewById(R.id.iv_detail_photo);


            tvTitle.setText(selectedMovie.getTitle());
            tvDesc.setText(selectedMovie.getDesc());
            Picasso.get().load(selectedMovie.getImg()).into(imgPhoto);

            setActionBarTitle("Movie Detail");
        }
        else if (selectedTV!=null){
            tvTitle = findViewById(R.id.tv_detail_title);
            tvDesc = findViewById(R.id.tv_detail_desc);
            imgPhoto = findViewById(R.id.iv_detail_photo);


            tvTitle.setText(selectedTV.getTitle());
            tvDesc.setText(selectedTV.getDesc());
            Picasso.get().load(selectedTV.getImg()).into(imgPhoto);

            setActionBarTitle("TV Detail");
        }


        btnDownload = findViewById(R.id.bt_download);
        btnDownload.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Movie selectedMovie = getIntent().getParcelableExtra(Extra_Movie);
        TV selectedTV = getIntent().getParcelableExtra(Extra_TV);
        if(selectedMovie!=null){
            saveFavorite();
        }
        else if (selectedTV!=null) {
            saveTVFavorite();
        }
    }



    private void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }


    public void saveFavorite(){
        movieHelper = new MovieHelper(this);
        Movie selectedMovie = getIntent().getParcelableExtra(Extra_Movie);

        if (movieHelper.CheckData(selectedMovie) != true){
            movieHelper.addMovieFavorite(selectedMovie);
            Toast.makeText(DetailActivity.this, getString(R.string.SaveFavMovie), Toast.LENGTH_SHORT).show();
        }else{
            movieHelper.deleteFavorite(selectedMovie.getTitle());
            Toast.makeText(DetailActivity.this, getString(R.string.DeleteFavMovie), Toast.LENGTH_SHORT).show();
        }

    }

    public void saveTVFavorite(){
        movieHelper = new MovieHelper(this);
        TV selectedTV = getIntent().getParcelableExtra(Extra_TV);

        if (movieHelper.CheckDataTV(selectedTV) != true){
            movieHelper.addTVFavorite(selectedTV);
            Toast.makeText(DetailActivity.this, getString(R.string.SaveFavTV), Toast.LENGTH_SHORT).show();
        }else{
            movieHelper.deleteFavorite(selectedTV.getTitle());
            Toast.makeText(DetailActivity.this, getString(R.string.DeleteFavTV), Toast.LENGTH_SHORT).show();
        }
    }

}
