package com.example.countries.model.local;


import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.countries.model.model.CountryModelEntity;

import java.util.List;

@Dao
public interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CountryModelEntity countryModelEntity);
    //en la vista se debe hacer un mapping del country que viene de la web al
    //countryEntity de room para poder ingresarlo en la lista de room.

    //para meter toda la lista que viene de Retrofit en Room
    @Insert(onConflict = REPLACE)
    void saveAllRoomCountryItems(List<CountryModelEntity> countries);

    //para traer toda la lista:
    @Query("SELECT * FROM countryModelEntity ORDER BY countryName ASC")
    List<CountryModelEntity> getAllRoomCountries();

    //para traer un solo elemento:
    @Query("SELECT * FROM countryModelEntity WHERE countryName = :name")
    CountryModelEntity getCountryByName(String name);

    //borrar un objeto
    @Delete
    void deleteCountry(CountryModelEntity countryModelEntity);

    //---------------------------------------------------------------
    //borrar todos los objetos
    @Query("Delete FROM countryModelEntity")
    void deleteAllCountries();

    @Delete
    int deleteAllC(CountryModelEntity countryModelEntity);

    //-----------------------------------------------------------------

    @Update
    int updateCountry(CountryModelEntity countryModelEntity);

    @Query("Delete FROM countryModelEntity WHERE countryname = :name")
    void deleteCountry(String name);

}
