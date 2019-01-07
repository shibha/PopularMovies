package com.example.android.popularmoviesstage1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.android.popularmoviesstage1.MovieDetailActivity;
import com.example.android.popularmoviesstage1.R;
import com.example.android.popularmoviesstage1.model.Movie;
import com.squareup.picasso.Picasso;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    private List<Movie> movies;
    private LayoutInflater mInflater;

    public MyRecyclerViewAdapter(Context context, List<Movie> data) {
        this.mInflater = LayoutInflater.from(context);
        this.movies = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.grid_view_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movie movie = movies.get(position);
        final ImageView imageView = holder.imageView;
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = imageView.getContext();
                Intent intent = new Intent(context , MovieDetailActivity.class);
                intent.putExtra(context.getString(R.string.param), movie);
                context.startActivity(intent);
            }
        });
        Picasso.with(imageView.getContext()).load(movie.getImgPath()).fit().into(imageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public interface ListItemClickListener {
        void onClick(int clickedItem);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.grid_item_img);
        }

    }

}