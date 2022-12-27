package com.example.countries.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.countries.model.local.AppDatabase;
import com.example.countries.model.model.CountryModel;
import com.example.countries.model.model.CountryModelEntity;
import com.example.countries.model.remote.CountriesAPI;
import com.example.countries.model.remote.CountriesService;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

import io.reactivex.Single;

//FALTA PONER ACA LO QUE VIENE DE RETROFIT.AHORA MISMO SE ESTAN COMUNICANDO
//DIRECTAMENTE RETROFIT CON VIEWMODEL SIN PASAR POR REPOSITORY.

public class Repository {

    private final Executor executor = Executors.newSingleThreadExecutor();

    private AppDatabase appDatabase;

    private CountriesAPI countriesAPI;

    public Repository() {
    }

    public Repository(Context context, CountriesAPI countriesAPI) {
        appDatabase = AppDatabase.getInstance(context);
        this.countriesAPI = countriesAPI;
    }

    public Single<List<CountryModel>> getCountries(){
        return countriesAPI.getCountries();
    }

    //-------------------------------------------------------------
    //-----Room------Room------Room------Room------Room------Room--
    //-------------------------------------------------------------
    public LiveData<List<CountryModelEntity>> getAllCountries()  {

        return appDatabase.countryDao.getAllCountries();

    }

    public void insertCountry(CountryModelEntity countryModelEntity) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.countryDao.insert(countryModelEntity);
            }
        });
    }

    public LiveData<CountryModelEntity> getCountryByName(String name)  {

        return appDatabase.countryDao.getCountryByName(name);

    }


    public void deleteCountry(CountryModelEntity countryModelEntity) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.countryDao.deleteCountry(countryModelEntity);
            }
        });
    }

    public void deleteAllCountries() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.countryDao.deleteAllCountries();
            }
        });
    }
    //-------------------------------------------------------------
    //--EndRoom---EndRoom---EndRoom---EndRoom---EndRoom---EndRoom--
    //-------------------------------------------------------------

}
