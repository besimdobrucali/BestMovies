package com.dobrucali.bestmovies.ui.detail;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dobrucali.bestmovies.R;
import com.dobrucali.bestmovies.data.database.FavoriteEntry;
import com.dobrucali.bestmovies.data.database.MovieEntry;
import com.dobrucali.bestmovies.databinding.FragmentDetailBinding;
import com.dobrucali.bestmovies.utilities.InjectorUtils;
import com.squareup.picasso.Picasso;

import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements View.OnClickListener {

    private static final String MOVIE_ID = "MOVIE_ID";
    private static final String IMAGE_URL = "https://image.tmdb.org/t/p/w500/";
    private FragmentDetailBinding mDataBinding;
    private DetailFragmentViewModel mViewModel;
    private DetailViewModelFactory factory;
    private boolean isFavorite = false;

    public static DetailFragment newInstance(Integer movieId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(MOVIE_ID, movieId);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        int movieId = getArguments().getInt(MOVIE_ID);
        factory = InjectorUtils.provideDetailViewModelFactory(this, movieId);
        mViewModel = ViewModelProviders.of(this, factory).get(DetailFragmentViewModel.class);

        mViewModel.getMovie().observe(this, movieEntry -> {
            if (movieEntry != null){
                bindMovieToUI(movieEntry);
            }
        });

        return mDataBinding.getRoot();
    }

    private void bindMovieToUI(MovieEntry movieEntry) {
        Picasso.get().load(IMAGE_URL.concat(movieEntry.getPosterPath()))
                .into(mDataBinding.movieImage);
        mDataBinding.movieTitle.setText(movieEntry.getTitle());
        mDataBinding.movieOverview.setText(movieEntry.getOverview());
        mViewModel.getFavorites().observe(this,favoriteEntries -> {
            if (favoriteEntries != null && favoriteEntries.size() > 0) {
                Iterator<FavoriteEntry> iterator = favoriteEntries.iterator();
                while (iterator.hasNext()) {
                    FavoriteEntry favoriteEntry = iterator.next();
                    if (favoriteEntry.getUniqueId() == movieEntry.getId()){
                        changeFabDrawable(true);
                        break;
                    }
                    if (!iterator.hasNext()) {
                        changeFabDrawable(false);
                    }
                }
            } else {
                changeFabDrawable(false);
            }
        });
        mDataBinding.favoriteFab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.favorite_fab:
                if(isFavorite) {
                    AsyncTask.execute(() -> mViewModel.deleteFromFavorites());
                } else {
                    AsyncTask.execute(() -> mViewModel.addMovieToFavorites());
                }
                break;
        }
    }

    public void changeFabDrawable(boolean isFav){
        if(isFav){
            isFavorite = true;
            setFabDrawable(R.drawable.ic_favorite_white);
        } else {
            isFavorite = false;
            setFabDrawable(R.drawable.ic_favorite_border_white);
        }
    }

    public void setFabDrawable(int fabDrawableId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDataBinding.favoriteFab.setImageDrawable(getResources().getDrawable(fabDrawableId, getContext().getTheme()));
        } else {
            mDataBinding.favoriteFab.setImageDrawable(getResources().getDrawable(fabDrawableId));
        }
    }
}
