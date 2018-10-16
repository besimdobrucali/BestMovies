package com.dobrucali.bestmovies.utilities;

import android.content.Context;

import com.dobrucali.bestmovies.data.FavoriteRepository;
import com.dobrucali.bestmovies.data.MovieRepository;
import com.dobrucali.bestmovies.data.database.FavoriteDatabase;
import com.dobrucali.bestmovies.data.database.MovieDatabase;
import com.dobrucali.bestmovies.data.network.FavoriteNetworkDataSource;
import com.dobrucali.bestmovies.data.network.MovieNetworkDataSource;
import com.dobrucali.bestmovies.ui.detail.DetailFragment;
import com.dobrucali.bestmovies.ui.detail.DetailViewModelFactory;
import com.dobrucali.bestmovies.ui.list.FavoritesFragment;
import com.dobrucali.bestmovies.ui.list.FavoritesViewModelFactory;
import com.dobrucali.bestmovies.ui.list.MoviesFragment;
import com.dobrucali.bestmovies.ui.list.MoviesViewModelFactory;

import java.util.Objects;

public class InjectorUtils {

    public static MovieRepository provideRepository(Context context) {
        MovieDatabase database = MovieDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        MovieNetworkDataSource networkDataSource =
                MovieNetworkDataSource.getInstance(context.getApplicationContext(), executors);
        return MovieRepository.getInstance(database.movieDao(), networkDataSource, executors);
    }

    public static FavoriteRepository provideFavRepository(Context context) {
        FavoriteDatabase database = FavoriteDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        FavoriteNetworkDataSource favNetworkDataSource =
                FavoriteNetworkDataSource.getInstance(context.getApplicationContext(), executors);
        return FavoriteRepository.getInstance(database.favoriteDao(),favNetworkDataSource, executors);
    }

    public static DetailViewModelFactory provideDetailViewModelFactory(DetailFragment fragment, Integer movieId) {
        MovieRepository repository = provideRepository(Objects.requireNonNull(fragment.getContext()));
        FavoriteRepository favRepository = provideFavRepository(Objects.requireNonNull(fragment.getContext()));
        return new DetailViewModelFactory(repository,favRepository, movieId);
    }

    public static MoviesViewModelFactory provideMoviesViewModelFactory(MoviesFragment fragment) {
        MovieRepository repository = provideRepository(Objects.requireNonNull(fragment.getContext()));
        return new MoviesViewModelFactory(repository);
    }

    public static FavoritesViewModelFactory provideFavoritesViewModelFactory(FavoritesFragment fragment) {
        FavoriteRepository repository = provideFavRepository(Objects.requireNonNull(fragment.getContext()));
        return new FavoritesViewModelFactory(repository);
    }

}