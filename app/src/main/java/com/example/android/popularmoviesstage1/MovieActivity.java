package com.example.android.popularmoviesstage1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.android.popularmoviesstage1.adapter.MyRecyclerViewAdapter;
import com.example.android.popularmoviesstage1.model.Movie;
import com.example.android.popularmoviesstage1.utilities.JsonUtils;
import com.example.android.popularmoviesstage1.utilities.NetworkUtils;
import com.example.android.popularmoviesstage1.utilities.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;


public class MovieActivity extends AppCompatActivity {


    private Logger logger = Logger.getLogger(this.getClass().toString());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie);
        View rootView = findViewById(R.id.recycleMovies);
        URL movieDataURL = NetworkUtils.buildUrl(true);
        new MovieActivity.GetMoviesDataTask(MovieActivity.this, rootView).execute(movieDataURL);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.sort_menu);
        final Spinner spinner = (Spinner) item.getActionView(); // get the spinner
        final ArrayAdapter<CharSequence> sortMenuAdaptor = ArrayAdapter.createFromResource(this,
                R.array.sort_array, android.R.layout.simple_spinner_item);
        sortMenuAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sortMenuAdaptor);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                URL movieDataURL = null;
                View root_view = findViewById(R.id.recycleMovies);
                switch (position) {
                    case 0:
                        movieDataURL = NetworkUtils.buildUrl(true);
                        break;
                    case 1:
                        movieDataURL = NetworkUtils.buildUrl(false);
                        break;
                }
                new MovieActivity.GetMoviesDataTask(MovieActivity.this, root_view).execute(movieDataURL);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // sometimes you need nothing here
            }
        });

        return true;
    }



    public class GetMoviesDataTask extends AsyncTask<URL, Void, String> {
        private Logger logger = Logger.getLogger(this.getClass().toString());
        private Activity activity;

        public GetMoviesDataTask(Activity context, View view) {
            activity = context;
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];

            String moviesData = null;
            try {
                logger.info("Making a network call to get Movies Data");
                moviesData = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                logger.info("Got Movies Data" + moviesData);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return moviesData;
        }

        @Override
        protected void onPostExecute(final String moviesData) {

            final List<Movie> movieList = JsonUtils.createMovieList(moviesData);
            if (movieList == null || movieList.size() == 0) {
                setContentView(R.layout.error);
                return;
            }

            RecyclerView recyclerView = findViewById(R.id.recycleMovies);
            recyclerView.setLayoutManager(new GridLayoutManager(activity.getApplicationContext(),
                    Utils.calculateNoOfColumns(getApplicationContext())));
            MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(activity, movieList);
            recyclerView.setAdapter(adapter);
        }


    }
}
