package com.example.android.popularmoviesstage1;

import android.content.Intent;
import android.os.Bundle;
import com.squareup.picasso.Picasso;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.popularmoviesstage1.model.Movie;


public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        Intent intent = getIntent();
        Movie movieSelected = (Movie) intent.getSerializableExtra(getString(R.string.param));
        setContent(movieSelected);

    }

    private void setContent(Movie movieSelected) {
        ImageView imageView = findViewById(R.id.movieImg);
        TextView titleView = findViewById(R.id.movieDetailTitle);
        TextView releaseDate =  findViewById(R.id.movieDetailReleaseDate);
        TextView synopsis =  findViewById(R.id.movieDetailPlotSynopsis);
        TextView userRating =  findViewById(R.id.movieDetailUserRating);
        releaseDate.setText(movieSelected.getReleaseDate());
        synopsis.setText(movieSelected.getPlotSynopsis());
        userRating.setText(String.valueOf(movieSelected.getUserRating())+" / 10");
        titleView.setText(movieSelected.getTitle());
        Picasso.with(this).load(movieSelected.getImgPath()).fit().into(imageView);
    }
}
