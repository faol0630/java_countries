package com.example.countries.model.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.countries.model.model.CountryModelEntity;

import java.util.List;

@Dao
public interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCountry(CountryModelEntity countryModelEntity);

    @Query("SELECT * FROM countryModelEntity ORDER BY countryName ASC")
    List<CountryModelEntity> getAllRoomCountries();

    @Delete
    void deleteCountry(CountryModelEntity countryModelEntity);


}
