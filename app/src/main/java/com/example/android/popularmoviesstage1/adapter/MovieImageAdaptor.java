package com.example.android.popularmoviesstage1.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.example.android.popularmoviesstage1.R;
import com.example.android.popularmoviesstage1.model.Movie;
import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.logging.Logger;

public class MovieImageAdaptor extends ArrayAdapter<Movie> {

    public MovieImageAdaptor(Activity c, List<Movie> movies) {
        super(c, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_view_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.grid_item_img);
        Picasso.with(getContext()).load(movie.getImgPath()).fit().into(imageView);
        return convertView;
    }


}

