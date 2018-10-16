package com.dobrucali.bestmovies.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dobrucali.bestmovies.R;
import com.dobrucali.bestmovies.databinding.FragmentMoviesBinding;
import com.dobrucali.bestmovies.ui.detail.DetailFragment;
import com.dobrucali.bestmovies.utilities.InjectorUtils;

import java.util.ArrayList;

public class MoviesFragment extends Fragment implements MovieItemClickListener{

    private int mPosition = RecyclerView.NO_POSITION;
    private FragmentMoviesBinding mDataBinding;
    private MovieAdapter mMovieAdapter;

    public MoviesFragment() {
        // Required empty public constructor
    }

    public static MoviesFragment newInstance() {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mDataBinding.movieListRecyclerView.setLayoutManager(layoutManager);
        mDataBinding.movieListRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter(getContext(), new ArrayList<>(),this);
        mDataBinding.movieListRecyclerView.setAdapter(mMovieAdapter);

        MoviesViewModelFactory factory = InjectorUtils.provideMoviesViewModelFactory(this);
        MoviesFragmentViewModel mViewModel = ViewModelProviders.of(this, factory).get(MoviesFragmentViewModel.class);
        mViewModel.getMovies().observe(this, movieEntries -> {
            mMovieAdapter.swapForecast(movieEntries);
            if (mPosition == RecyclerView.NO_POSITION){
                mPosition = 0;
            }
            mDataBinding.movieListRecyclerView.smoothScrollToPosition(mPosition);
        });

        return mDataBinding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
