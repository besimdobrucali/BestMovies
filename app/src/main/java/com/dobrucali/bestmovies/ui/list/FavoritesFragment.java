package com.dobrucali.bestmovies.ui.list;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dobrucali.bestmovies.R;
import com.dobrucali.bestmovies.databinding.FragmentFavoritesBinding;
import com.dobrucali.bestmovies.ui.detail.DetailFragment;
import com.dobrucali.bestmovies.utilities.InjectorUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment implements MovieItemClickListener {

    private int mPosition = RecyclerView.NO_POSITION;
    private FragmentFavoritesBinding mDataBinding;
    private FavoriteAdapter mMovieAdapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mDataBinding.favoriteList.setLayoutManager(layoutManager);
        mDataBinding.favoriteList.setHasFixedSize(true);
        mMovieAdapter = new FavoriteAdapter(getContext(), new ArrayList<>(),this);
        mDataBinding.favoriteList.setAdapter(mMovieAdapter);

        FavoritesViewModelFactory factory = InjectorUtils.provideFavoritesViewModelFactory(this);
        FavoritesFragmentViewModel mViewModel = ViewModelProviders.of(this, factory).get(FavoritesFragmentViewModel.class);
        mViewModel.getMovies().observe(this, movieEntries -> {
            mMovieAdapter.swapForecast(movieEntries);
            if (mPosition == RecyclerView.NO_POSITION){
                mPosition = 0;
            }
            mDataBinding.favoriteList.smoothScrollToPosition(mPosition);
        });

        return mDataBinding.getRoot();

    }

    @Override
    public void onItemClick(Integer movieId) {
        DetailFragment detailFragment = DetailFragment.newInstance(movieId);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, detailFragment,"detailFragment")
                .addToBackStack(null)
                .commit();
    }

}
