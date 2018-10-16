package com.dobrucali.bestmovies.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {FavoriteEntry.class}, version = 1)
public abstract class FavoriteDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "favorite";

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static volatile FavoriteDatabase sInstance;

    public static FavoriteDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            FavoriteDatabase.class, FavoriteDatabase.DATABASE_NAME).build();
                }
            }
        }
        return sInstance;
    }
    public abstract FavoriteDao favoriteDao();

}
