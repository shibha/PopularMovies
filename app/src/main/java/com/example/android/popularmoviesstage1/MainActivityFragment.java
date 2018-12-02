package com.example.android.popularmoviesstage1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import com.example.android.popularmoviesstage1.adapter.MovieImageAdaptor;
import com.example.android.popularmoviesstage1.utilities.JsonUtils;
import com.example.android.popularmoviesstage1.utilities.NetworkUtils;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class MainActivityFragment extends Fragment {

    Logger logger = Logger.getLogger(this.getClass().toString());

    MovieImageAdaptor imageAdapter;


    public static void setIsPopularMoviesSelected(boolean isPopularMoviesSelected) {
        MainActivityFragment.isPopularMoviesSelected = isPopularMoviesSelected;
    }

    private static boolean isPopularMoviesSelected;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        URL movieDataURL = NetworkUtils.buildUrl("f073fddf11595f71af95dfcaffbe5700", isPopularMoviesSelected);
        new MainActivityFragment.GetMoviesDataTask(getActivity(), rootView).execute(movieDataURL);
        return rootView;
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
            GridView gridView = (GridView) contextView.findViewById(R.id.movie_grid);
            gridView.setAdapter(imageAdapter);
        }

    }
}
