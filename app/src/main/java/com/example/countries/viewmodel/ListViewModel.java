package com.example.countries.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.countries.model.local.AppDatabase;
import com.example.countries.model.model.Country;
import com.example.countries.model.model.CountryModelEntity;
import com.example.countries.model.remote.RemoteDataAccess;
import com.example.countries.repository.AsyncTaskReceiver;
import com.example.countries.repository.CountryRepository;
import com.example.countries.repository.mapper.CountryModelEntityMapper;
import com.example.countries.repository.mapper.CountryModelMapper;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ListViewModel extends AndroidViewModel {

    private final CountryRepository repository;

    public ListViewModel(@NonNull Application application) {
        super(application);
        repository = new CountryRepository(
                AppDatabase.getInstance(application),
                RemoteDataAccess.getRemoteDataInstance(),
                CountryModelEntityMapper.getInstance(),
                CountryModelMapper.getInstance()
        );
    }

    private final MutableLiveData<List<Country>> itemLiveData = new MutableLiveData<>();//este se usa para el postValue

    public LiveData<List<Country>> getCountriesLiveData(){
        return itemLiveData;
    }  //sobre esto se monta el observer en el activity

    public void requestCountryItems(){ //este es el metodo que se llama dentro de onCreate en el activity
        //dentro del getPicturesItems de repository está room incluido.Por lo tanto con este llamado
        //se llaman las dos cosas.Por eso aca no hay logica de Room.
        repository.getCountryItems(new AsyncTaskReceiver<List<Country>>() {
            @Override
            public void onSuccess(List<Country> result) {
                itemLiveData.postValue(result);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    //-------------------------------------------------------------
    //-----RoomViewModel------RoomViewModel------RoomViewModel-----
    //-------------------------------------------------------------
    //PARA MOSTRAR TODA LA LISTA DE ROOM EN EL ACTIVITY FAVORITES:

    private final MutableLiveData<List<Country>> itemRoomLiveData = new MutableLiveData<>();//este se usa para el postValue

    public LiveData<List<Country>> getRoomCountriesLiveData(){ //sobre esto se monta el observer en el activity
        return itemRoomLiveData;
    }  //sobre esto se monta el observer en el activity


    public void requestRoomCountryItems(){

        repository.getRoomCountryItems(new AsyncTaskReceiver<List<Country>>() {
            @Override
            public void onSuccess(List<Country> result) {
                itemRoomLiveData.postValue(result);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    //-------------------------------------------------------------
    //--EndRoomViewModel----EndRoomViewModel----EndRoomViewModel---
    //-------------------------------------------------------------

}
