/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.popularmoviesstage1;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.example.android.popularmoviesstage1.adapter.ImageAdapter;
import com.example.android.popularmoviesstage1.utilities.JsonUtils;
import com.example.android.popularmoviesstage1.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    Logger logger = Logger.getLogger("MainActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        URL movieDataURL = NetworkUtils.buildUrl("f073fddf11595f71af95dfcaffbe5700");
        new GetMoviesDataTask(this).execute(movieDataURL);
    }


    public class GetMoviesDataTask extends AsyncTask<URL, Void, String> {

        private Context mContext;

        public GetMoviesDataTask (Context context){
            mContext = context;
        }

        @Override
        protected String doInBackground(URL... params) {
            logger.info("Starting activity  Data");
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
            ImageAdapter imageAdapter = new ImageAdapter(mContext, JsonUtils.createMovieList(moviesData));
            GridView gridView = (GridView) findViewById(R.id.movie_grid);
            gridView.setAdapter(imageAdapter);
        }

    }
}
