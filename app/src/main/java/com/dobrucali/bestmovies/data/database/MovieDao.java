package com.dobrucali.bestmovies.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    LiveData<List<MovieEntry>> getAllMovies();

    @Query("SELECT * FROM movie LIMIT 50")
    LiveData<List<MovieEntry>> getFiftyMovies();

    @Query("SELECT * FROM movie WHERE title LIKE :title")
    LiveData<MovieEntry> getMoviesByTitle(String title);

    @Query("SELECT * FROM movie WHERE id = :movieId")
    LiveData<MovieEntry> getMoviesByMovieId(Integer movieId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(MovieEntry... movie);

}
