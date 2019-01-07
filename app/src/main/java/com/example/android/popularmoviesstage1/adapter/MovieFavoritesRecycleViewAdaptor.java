package com.example.android.popularmoviesstage1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.android.popularmoviesstage1.MovieDetailActivity;
import com.example.android.popularmoviesstage1.R;
import com.example.android.popularmoviesstage1.db.FavMovie;
import com.example.android.popularmoviesstage1.model.Movie;
import com.example.android.popularmoviesstage1.utilities.Utils;
import com.squareup.picasso.Picasso;
import java.util.List;

public class MovieFavoritesRecycleViewAdaptor extends RecyclerView.Adapter<MovieFavoritesRecycleViewAdaptor.FavoritesAdapterViewHolder> {

    private Context mContext;
    public List<FavMovie> favMovies;
    private ListItemClickListener mClickListener;

    public MovieFavoritesRecycleViewAdaptor(Context context, List<FavMovie> favoriteMovies) {
        this.favMovies = favoriteMovies;
        this.mContext = context;
    }

    @Override
    public MovieFavoritesRecycleViewAdaptor.FavoritesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new FavoritesAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull  FavoritesAdapterViewHolder holder, int position) {
        final FavMovie favMovie = favMovies.get(position);
        final Movie movie = Utils.convert(favMovie);
        final ImageView imageView = holder.mainImage;
        holder.mainImage.setOnClickListener(new View.OnClickListener() {
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




    public class FavoritesAdapterViewHolder extends RecyclerView.ViewHolder {
        public ImageView mainImage;

        public FavoritesAdapterViewHolder(View view) {
            super(view);
            mainImage = view.findViewById(R.id.item_img);
        }
    }

    public void setFavoriteMovies(List<FavMovie> favoriteMovies) {
        favMovies = favoriteMovies;
        notifyDataSetChanged();
    }

    public void setClickListener(ListItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    public interface ListItemClickListener {
        void onItemClickListener(int clickedItem);
    }


    @Override
    public int getItemCount() {
        return favMovies != null ? favMovies.size() : 0;
    }
}

