package com.example.android.popularmoviesstage1.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.android.popularmoviesstage1.model.Movie;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<Movie> movies;

    public ImageAdapter(Context c, List<Movie> movies) {
        mContext = c;
        this.movies = movies;
    }

    public int getCount() {
        return 0;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext).load(movies.get(position).getImgPath()).into(imageView);
        return imageView;
    }

}