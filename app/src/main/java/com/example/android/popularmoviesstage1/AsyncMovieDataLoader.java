package com.example.android.popularmoviesstage1;

import android.content.AsyncTaskLoader;
import android.content.Context;
import com.example.android.popularmoviesstage1.model.Movie;
import com.example.android.popularmoviesstage1.utilities.JsonUtils;
import com.example.android.popularmoviesstage1.utilities.NetworkUtils;
import java.io.IOException;
import java.net.URL;
import java.util.List;

class AsyncMovieDataLoader extends AsyncTaskLoader<List<Movie>> {

    private URL searchUrl;

    public AsyncMovieDataLoader(Context context, URL searchUrl) {
        super(context);
        this.searchUrl = searchUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        if (searchUrl == null) {
            return null;
        }

        try {
            return JsonUtils.createMovieList(NetworkUtils.getResponseFromHttpUrl(searchUrl));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
