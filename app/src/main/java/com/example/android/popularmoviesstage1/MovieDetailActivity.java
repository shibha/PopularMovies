package com.example.android.popularmoviesstage1;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import com.example.android.popularmoviesstage1.adapter.MovieReviewRecycleViewAdaptor;
import com.example.android.popularmoviesstage1.adapter.MovieTrailerRecycleViewAdaptor;
import com.example.android.popularmoviesstage1.adapter.MovieFavoritesRecycleViewAdaptor;
import com.example.android.popularmoviesstage1.db.AppDatabase;
import com.example.android.popularmoviesstage1.db.FavMovie;
import com.example.android.popularmoviesstage1.model.Review;
import com.example.android.popularmoviesstage1.model.Trailer;
import com.example.android.popularmoviesstage1.model.TrailerAndReviews;
import com.example.android.popularmoviesstage1.utilities.JsonUtils;
import com.example.android.popularmoviesstage1.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.popularmoviesstage1.model.Movie;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class MovieDetailActivity extends AppCompatActivity implements MovieTrailerRecycleViewAdaptor.ListItemClickListener {

    private static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";

    private MovieTrailerRecycleViewAdaptor trailerAdapter;
    private MovieReviewRecycleViewAdaptor reviewAdapter;
    private AppDatabase database;
    private MovieFavoritesRecycleViewAdaptor favoritesAdapter;
    private FloatingActionButton favoriteButton;
    private ImageView coverImg;
    private static final int DEFAULT_ID = -1;
    private int movieId = DEFAULT_ID;
    public static final String INSTANCE_ID = "instanceId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        Intent intent = getIntent();
        final Movie movieSelected = intent.getParcelableExtra(getString(R.string.param));
        if(movieId == DEFAULT_ID){
            movieId = movieSelected.getId();
        }
        database = AppDatabase.getInstance(getApplicationContext());
        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_ID)) {
            movieId = savedInstanceState.getInt(INSTANCE_ID, DEFAULT_ID);
        }

        setContent(movieSelected);
        trailerAdapter = new MovieTrailerRecycleViewAdaptor(this);
        reviewAdapter = new MovieReviewRecycleViewAdaptor(this);
        favoritesAdapter = new MovieFavoritesRecycleViewAdaptor(this, new ArrayList<FavMovie>());
        URL movieTrailerURL = NetworkUtils.buildTrailersDataUrl(movieSelected.getId());
        URL movieReviewURL = NetworkUtils.buildReviewsDataUrl(movieSelected.getId());
        new MovieDetailActivity.GetDataTask(MovieDetailActivity.this).execute(movieTrailerURL, movieReviewURL);

        favoriteButton = findViewById(R.id.favButton);
        Logger.getLogger("CHECK NAME").info("movieId   " + movieId);
        if (isFavAlready(String.valueOf(movieId))) {
            favoriteButton.setImageResource(R.drawable.favorites_red);
        } else {
            favoriteButton.setImageResource(R.drawable.favorites);
        }
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteButtonClicked(movieSelected);
            }
        });

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (isFavAlready(String.valueOf(movieId))) {
            favoriteButton.setImageResource(R.drawable.favorites_red);
        } else {
            favoriteButton.setImageResource(R.drawable.favorites);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_ID, movieId);
        super.onSaveInstanceState(outState);
    }

    public void favoriteButtonClicked(final Movie currentMovie) {
        final String idStr = String.valueOf(currentMovie.getId());
        if (!isFavAlready(idStr)) {
            String title = currentMovie.getTitle();
            String overview = currentMovie.getPlotSynopsis();
            String releaseDate = currentMovie.getReleaseDate();
            int userRating = currentMovie.getUserRating();
            String imgPath = currentMovie.getImgPath();
            favoriteButton.setImageResource(R.drawable.favorites_red);
            final FavMovie favMovie;
            favMovie = new FavMovie(currentMovie.getId(), title, overview, releaseDate, String.valueOf(userRating), imgPath, imgPath, idStr);
            PopularMovieThreads.getInstance().secondaryStorageIOThread().execute(new Runnable() {
                @Override
                public void run() {
                    database.favoriteMovieDao().insertMovie(favMovie);
                    reset();
                }
            });
        } else {
            PopularMovieThreads.getInstance().secondaryStorageIOThread().execute(new Runnable() {
                @Override
                public void run() {
                    favoriteButton.setImageResource(R.drawable.favorites);
                    database.favoriteMovieDao().deleteMovieMy(idStr);
                    reset();
                }
            });
        }
    }

    public void reset() {
        Model viewModel = ViewModelProviders.of(this).get(Model.class);
        viewModel.getFavoriteMovies().observe(this, new Observer<List<FavMovie>>() {
            @Override
            public void onChanged(@Nullable List<FavMovie> favMovies) {
                favoritesAdapter.setFavoriteMovies(favMovies);
            }
        });
    }


    private void setContent(Movie movieSelected) {
        ImageView imageView = findViewById(R.id.movieImg);
        TextView titleView = findViewById(R.id.movieDetailTitle);
        TextView releaseDate = findViewById(R.id.movieDetailReleaseDate);
        TextView synopsis = findViewById(R.id.movieDetailPlotSynopsis);
        TextView userRating = findViewById(R.id.movieDetailUserRating);
        coverImg = findViewById(R.id.coverImg);
        Picasso.with(this).load(movieSelected.getImgPath()).fit().into(coverImg);
        releaseDate.setText(movieSelected.getReleaseDate());
        synopsis.setText(movieSelected.getPlotSynopsis());
        userRating.setText(String.valueOf(movieSelected.getUserRating()) + " / 10");
        titleView.setText(movieSelected.getTitle());
        Picasso.with(this).load(movieSelected.getImgPath()).fit().into(imageView);
    }

    @Override
    public void onClick(Trailer clickedTrailer) {
        Uri trailerUri = Uri.parse(YOUTUBE_URL + clickedTrailer.getKey());
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, trailerUri);
        startActivity(websiteIntent);
    }


    public boolean isFavAlready(String id) {
        return (database.favoriteMovieDao().getFavMoviesBy(id) != null);
    }

    public class GetDataTask extends AsyncTask<URL, Void, TrailerAndReviews> {
        private Activity activity;

        public GetDataTask(Activity context) {
            activity = context;
        }

        @Override
        protected TrailerAndReviews doInBackground(URL... params) {
            TrailerAndReviews trailersAndReviews = new TrailerAndReviews();
            URL trailerURL = params[0];
            String trailerData = null;
            try {
                trailerData = NetworkUtils.getResponseFromHttpUrl(trailerURL);
            } catch (IOException e) {
                e.printStackTrace();
            }

            final List<Trailer> movieTrailers = JsonUtils.createMovieTrailers(trailerData);
            trailersAndReviews.setTrailers(movieTrailers);


            URL reviewUrl = params[1];

            String reviewData = null;
            try {
                reviewData = NetworkUtils.getResponseFromHttpUrl(reviewUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }

            final List<Review> movieReviews = JsonUtils.createMovieReviews(reviewData);
            trailersAndReviews.setReviews(movieReviews);
            return trailersAndReviews;
        }

        @Override
        protected void onPostExecute(final TrailerAndReviews trailersAndReviews) {

            List<Trailer> movieTrailers = trailersAndReviews.getTrailers();
            List<Review> movieReviews = trailersAndReviews.getReviews();
            RecyclerView recycleTrailerView = findViewById(R.id.recycleTrailers);
            recycleTrailerView.setNestedScrollingEnabled(false);
            recycleTrailerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
            trailerAdapter.setTrailerData(movieTrailers);
            recycleTrailerView.setAdapter(trailerAdapter);
            RecyclerView recyclerReviewView = findViewById(R.id.recycleReviews);
            recyclerReviewView.setNestedScrollingEnabled(false);
            recyclerReviewView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
            reviewAdapter.setReviewData(movieReviews);
            recyclerReviewView.setAdapter(reviewAdapter);
        }
    }

}

