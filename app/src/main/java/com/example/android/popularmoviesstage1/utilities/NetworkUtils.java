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
package com.example.android.popularmoviesstage1.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {


    private final static String GITHUB_BASE_URL = "http://api.themoviedb.org/3/movie/";

    private final static String GITHUB_URL_POPULAR = "popular";

    private final static String GITHUB_URL_TOP_RATED = "top_rated";

    private final static String GITHUB_TRAILER_URL= "videos";

    private final static String PARAM_QUERY = "api_key";

    private final static String PARAM_QUERY_VALUE = "api_value";

    private static final String GITHUB_REVIEWS_URL = "reviews";

    /**
     * Builds the URL used to query GitHub.
     *
     * @param isSortByMostPopular is the boolean which is used to choose the rest url to call
     * @return The URL to use to query the GitHub.
     */
    public static URL buildGetMoviesDataUrl(boolean isSortByMostPopular) {
        String final_url = GITHUB_BASE_URL;

        if (isSortByMostPopular) {
            final_url = final_url + GITHUB_URL_POPULAR;
        } else {
            final_url = final_url + GITHUB_URL_TOP_RATED;
        }

        return buildFinalUrl(final_url);
    }

    public static URL buildTrailersDataUrl(int movieId){
        return buildFinalUrl(GITHUB_BASE_URL  +  movieId + "/" + GITHUB_TRAILER_URL);
    }

    public static URL buildReviewsDataUrl(int movieId){
        return buildFinalUrl(GITHUB_BASE_URL  +  movieId + "/" + GITHUB_REVIEWS_URL);
    }

    private static URL buildFinalUrl(String final_url) {
        Uri builtUri = Uri.parse(final_url).buildUpon()
                    .appendQueryParameter(PARAM_QUERY, PARAM_QUERY_VALUE)
                    .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            return scanner.next();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return null;
    }
}
