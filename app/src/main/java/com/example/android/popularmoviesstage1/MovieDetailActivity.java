package com.example.android.popularmoviesstage1;

import android.content.Intent;
import android.os.Bundle;
import com.squareup.picasso.Picasso;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.popularmoviesstage1.model.Movie;
import java.util.logging.Logger;

public class MovieDetailActivity extends AppCompatActivity {
    private Logger logger = Logger.getLogger(this.getClass().toString());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        Intent i = getIntent();
        Movie movieSelected = (Movie) i.getSerializableExtra("Movie");
        ImageView imageView = findViewById(R.id.movieImg);

        TextView titleView = findViewById(R.id.movieDetailTitle);

        TextView releaseDate =  findViewById(R.id.movieDetailReleaseDate);

        TextView synopsis =  findViewById(R.id.movieDetailPlotSynopsis);
        TextView userRating =  findViewById(R.id.movieDetailUserRating);

        releaseDate.setText(movieSelected.getReleaseDate());
        synopsis.setText(movieSelected.getPlotSynopsis());
        userRating.setText(String.valueOf(movieSelected.getUserRating()));

        titleView.setText(movieSelected.getTitle());
        Picasso.with(this).load(movieSelected.getImgPath()).fit().into(imageView);

    }
}
