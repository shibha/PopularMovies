package com.example.android.popularmoviesstage1;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import com.example.android.popularmoviesstage1.db.AppDatabase;
import com.example.android.popularmoviesstage1.db.FavMovie;
import java.util.List;

public class Model extends AndroidViewModel {

    private LiveData<List<FavMovie>> favoriteMovies;

    public Model(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        favoriteMovies = database.favoriteMovieDao().loadFavoriteMovies();
    }

    public LiveData<List<FavMovie>> getFavoriteMovies() {
        return favoriteMovies;
    }
}
