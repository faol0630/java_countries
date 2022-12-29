package com.example.countries.model.local;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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

    //para traer toda la lista:
    @Query("SELECT * FROM countryModelEntity ORDER BY countryName ASC")
    MutableLiveData<List<CountryModelEntity>> getAllCountries();

    //para traer un solo elemento:
    @Query("SELECT * FROM countryModelEntity WHERE countryName = :name")
    MutableLiveData<CountryModelEntity> getCountryByName(String name);

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

}
