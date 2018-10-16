package com.dobrucali.bestmovies.ui.main;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.dobrucali.bestmovies.R;
import com.dobrucali.bestmovies.ui.list.FavoritesFragment;
import com.dobrucali.bestmovies.ui.list.MoviesFragment;
import com.dobrucali.bestmovies.ui.list.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private MoviesFragment moviesFragment;
    private FavoritesFragment favoritesFragment;
    private SettingsFragment settingsFragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_movies:
                        if(moviesFragment == null){
                            moviesFragment = MoviesFragment.newInstance();
                        }
                        this.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, moviesFragment,"moviesFragment")
                                .addToBackStack(null)
                                .commit();
                        return true;
                    case R.id.navigation_favorites:
                        if(favoritesFragment == null){
                            favoritesFragment = FavoritesFragment.newInstance();
                        }
                        this.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, favoritesFragment,"favoritesFragment")
                                .addToBackStack(null)
                                .commit();
                        return true;
                    case R.id.navigation_settings:
                        if(settingsFragment == null){
                            settingsFragment = SettingsFragment.newInstance();
                        }
                        this.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, settingsFragment,"settingsFragment")
                                .addToBackStack(null)
                                .commit();
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        moviesFragment = MoviesFragment.newInstance();
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, moviesFragment,"moviesFragment")
                .addToBackStack(null)
                .commit();
    }
}
