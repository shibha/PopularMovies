package com.example.android.popularmoviesstage1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.android.popularmoviesstage1.adapter.MovieFavoritesRecycleViewAdaptor;
import com.example.android.popularmoviesstage1.adapter.MyRecyclerViewAdapter;
import com.example.android.popularmoviesstage1.db.FavMovie;
import com.example.android.popularmoviesstage1.model.Movie;
import com.example.android.popularmoviesstage1.utilities.NetworkUtils;
import com.example.android.popularmoviesstage1.utilities.Utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class MovieActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    private RecyclerView movieRecycleView;
    private MyRecyclerViewAdapter movieAdaptor;
    private URL MOVIE_DATA_URL;
    private static final int MOVIES_LOADER_ID = 100;
    private MovieFavoritesRecycleViewAdaptor favMovieAdaptor;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie);
        MOVIE_DATA_URL = NetworkUtils.buildGetMoviesDataUrl(true);
        final android.app.LoaderManager loaderManager = getLoaderManager();
        favMovieAdaptor = new MovieFavoritesRecycleViewAdaptor(this, new ArrayList<FavMovie>());
        loaderManager.initLoader(MOVIES_LOADER_ID, null, this);
    }

    @Override
    protected void onPause() {
        int index = -1;
        int top = -1;
        int spinnerSelection = -1;
        super.onPause();
        if (movieRecycleView != null) {
            index = ((GridLayoutManager) movieRecycleView.getLayoutManager()).findFirstVisibleItemPosition();
            View v = movieRecycleView.getChildAt(0);
            top = (v == null) ? 0 : (v.getTop() - movieRecycleView.getPaddingTop());
        }

        if (spinner != null) {
            spinnerSelection = spinner.getSelectedItemPosition();
        }

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if (spinnerSelection != -1) {
            editor.putInt("index" + spinnerSelection, index);
            editor.putInt("top" + spinnerSelection, top);
        }

        editor.putInt("currentSpinnerSelection", spinnerSelection);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        int index = -1;
        int top = -1;
        int spinnerSelection = preferences.getInt("currentSpinnerSelection", -1);

        if (spinnerSelection != -1) {
            index = preferences.getInt("index" + spinnerSelection, -1);
            top = preferences.getInt("top" + spinnerSelection, -1);

        }

        if (movieRecycleView != null && index != -1 && top != -1) {
            ((GridLayoutManager) movieRecycleView.getLayoutManager()).scrollToPositionWithOffset(index, top);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.sort_menu);
        spinner = (Spinner) item.getActionView(); // get the spinner
        final ArrayAdapter<CharSequence> sortMenuAdaptor = ArrayAdapter.createFromResource(this,
                R.array.sort_array, android.R.layout.simple_spinner_item);
        sortMenuAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sortMenuAdaptor);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        MOVIE_DATA_URL = NetworkUtils.buildGetMoviesDataUrl(true);
                        new AsyncMovieDataLoader(MovieActivity.this, MOVIE_DATA_URL);
                        reload();
                        break;
                    case 1:
                        MOVIE_DATA_URL = NetworkUtils.buildGetMoviesDataUrl(false);
                        new AsyncMovieDataLoader(MovieActivity.this, MOVIE_DATA_URL);
                        reload();
                        break;
                    case 2:
                        displayFavoriteMovies();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return true;
    }

    private void reload() {
        getLoaderManager().restartLoader(MOVIES_LOADER_ID, null, this);
    }

    private void displayFavoriteMovies() {
        Model viewModel = ViewModelProviders.of(this).get(Model.class);
        viewModel.getFavoriteMovies().observe(this, new Observer<List<FavMovie>>() {
            @Override
            public void onChanged(@Nullable List<FavMovie> favMovies) {
                favMovieAdaptor.setFavoriteMovies(favMovies);
                movieRecycleView.setAdapter(favMovieAdaptor);
            }
        });
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        setRecycleView(movies);
        movieAdaptor.notifyDataSetChanged();
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        int index = -1;
        int top = -1;
        int spinnerSelection = -1;
        if (spinner != null) {
            spinnerSelection = spinner.getSelectedItemPosition();
        }


        if (spinnerSelection != -1) {
            index = preferences.getInt("index" + spinnerSelection, -1);
            top = preferences.getInt("top" + spinnerSelection, -1);

        }
        if (index != -1 && top != -1) {
            ((GridLayoutManager) movieRecycleView.getLayoutManager()).scrollToPositionWithOffset(index, top);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
        return new AsyncMovieDataLoader(MovieActivity.this, MOVIE_DATA_URL);
    }

    private void setRecycleView(List<Movie> movies) {
        if (movies == null || movies.size() == 0) {
            setContentView(R.layout.error);
            return;
        }
        movieRecycleView = findViewById(R.id.recycleMovies);
        movieRecycleView.setLayoutManager(new GridLayoutManager(this.getApplicationContext(),
                Utils.calculateNoOfColumns(getApplicationContext()), GridLayoutManager.VERTICAL, false));
        movieAdaptor = new MyRecyclerViewAdapter(this, movies);
        movieRecycleView.setAdapter(movieAdaptor);
    }


    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        movieAdaptor.notifyDataSetChanged();
    }

}
