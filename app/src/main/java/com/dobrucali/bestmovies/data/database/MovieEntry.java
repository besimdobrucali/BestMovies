package com.dobrucali.bestmovies.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "movie")
public class MovieEntry extends BaseMovieEntry{

    @PrimaryKey
    private int uniqueId;

    public MovieEntry(Integer id, Integer voteCount, Boolean video, Double voteAverage, String title, Double popularity, String posterPath, String originalLanguage, String originalTitle, String backdropPath, Boolean adult, String overview, String releaseDate) {
        super(id, voteCount, video, voteAverage, title, popularity, posterPath, originalLanguage, originalTitle, backdropPath, adult, overview, releaseDate);
        this.uniqueId = id;
    }

    public MovieEntry() {
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }
}
