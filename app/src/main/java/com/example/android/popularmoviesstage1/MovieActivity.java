package com.example.android.popularmoviesstage1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import com.example.android.popularmoviesstage1.adapter.MovieImageAdaptor;
import com.example.android.popularmoviesstage1.utilities.JsonUtils;
import com.example.android.popularmoviesstage1.utilities.NetworkUtils;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class MovieActivity extends AppCompatActivity {

    Logger logger = Logger.getLogger(this.getClass().toString());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View root_view = findViewById(R.id.movie_grid);
        URL movieDataURL = NetworkUtils.buildUrl("f073fddf11595f71af95dfcaffbe5700", true);
        new MovieActivity.GetMoviesDataTask(MovieActivity.this, root_view).execute(movieDataURL);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.sort_menu);
        final Spinner spinner = (Spinner) item.getActionView(); // get the spinner
        final ArrayAdapter<CharSequence> sort_menu_adaptor = ArrayAdapter.createFromResource(this,
                R.array.sort_array,android.R.layout.simple_spinner_item);
        sort_menu_adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sort_menu_adaptor);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                URL movieDataURL = null;
                View root_view = findViewById(R.id.movie_grid);
                switch (position) {
                    case 0:
                        movieDataURL = NetworkUtils.buildUrl("f073fddf11595f71af95dfcaffbe5700", true);
                        new MovieActivity.GetMoviesDataTask(MovieActivity.this, root_view).execute(movieDataURL);
                        break;
                    case 1:
                        movieDataURL = NetworkUtils.buildUrl("f073fddf11595f71af95dfcaffbe5700", false);
                        new MovieActivity.GetMoviesDataTask(MovieActivity.this, root_view).execute(movieDataURL);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // sometimes you need nothing here
            }
        });

        return true;
    }

    public class GetMoviesDataTask extends AsyncTask<URL, Void, String> {

        private Activity activity;
        private View contextView;

        public GetMoviesDataTask(Activity context, View view) {
            activity = context;
            contextView = view;
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
        protected void onPostExecute(String moviesData) {
            MovieImageAdaptor imageAdapter = new MovieImageAdaptor(activity,
                    JsonUtils.createMovieList(moviesData));
            GridView gridView = (GridView) contextView;
            gridView.setAdapter(imageAdapter);
        }

    }
}
