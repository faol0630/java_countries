package com.example.countries.model.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.countries.model.model.CountryModelEntity;

@Database(entities = {CountryModelEntity.class}, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "project_database.db";
    public static AppDatabase databaseInstance;

    public abstract CountryDao countryDao();

    public synchronized static AppDatabase getInstance(Context context) {
        if (databaseInstance == null) {

            databaseInstance = Room
                    .databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return databaseInstance;
    }
}
