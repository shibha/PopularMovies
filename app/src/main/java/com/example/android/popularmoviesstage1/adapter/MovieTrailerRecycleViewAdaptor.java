package com.example.android.popularmoviesstage1.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.android.popularmoviesstage1.R;
import com.example.android.popularmoviesstage1.model.Trailer;
import com.squareup.picasso.Picasso;
import java.util.List;

public class MovieTrailerRecycleViewAdaptor extends RecyclerView.Adapter<MovieTrailerRecycleViewAdaptor.TrailerViewHolder> {

    private List<Trailer> trailers;

    public static final String YOUTUBE_BASE_URL = "https://img.youtube.com/vi/";

    public static final String YOUTUBE_DEFAULT_THUMBNAIL = "/default.jpg";

    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onClick(Trailer clickedItem);
    }


    public MovieTrailerRecycleViewAdaptor(ListItemClickListener trailerAdapterOnClickHandler) {
        mOnClickListener = trailerAdapterOnClickHandler;
    }

    @Override
    public MovieTrailerRecycleViewAdaptor.TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_item, parent, false);

        return new TrailerViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return trailers != null ? trailers.size() : 0;
    }

    @Override
    public void onBindViewHolder(MovieTrailerRecycleViewAdaptor.TrailerViewHolder holder, int position) {
        final Trailer trailer = trailers.get(position);
        String thumbnailUrl = YOUTUBE_BASE_URL + trailer.getKey() + YOUTUBE_DEFAULT_THUMBNAIL;
        Picasso.with(holder.imageView.getContext())
                .load(thumbnailUrl)
                .into(holder.imageView);
    }



    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView imageView;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            imageView =  itemView.findViewById(R.id.trailer_thumbnail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnClickListener.onClick(trailers.get(getAdapterPosition()));
        }

    }

    public void setTrailerData(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }


}

