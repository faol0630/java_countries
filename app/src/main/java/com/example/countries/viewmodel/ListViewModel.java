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
import com.example.countries.repository.ExecutorSupplier;
import com.example.countries.repository.mapper.CountryModelEntityMapper;
import com.example.countries.repository.mapper.CountryModelMapper;

import java.util.List;

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

    private final MutableLiveData<List<Country>> itemLiveData = new MutableLiveData<>();//postValue

    public LiveData<List<Country>> getCountriesLiveData() {
        return itemLiveData;
    }  //observer

    public void requestCountryItems() {

        ExecutorSupplier.getInstance().execute(() -> repository.getCountryItems(new AsyncTaskReceiver<List<Country>>() {
            @Override
            public void onSuccess(List<Country> result) {
                itemLiveData.postValue(result);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        }));
    }

    private final MutableLiveData<List<Country>> itemRoomLiveData = new MutableLiveData<>();//postValue

    public LiveData<List<Country>> getRoomCountriesLiveData() { //observer
        return itemRoomLiveData;
    }

    public void requestRoomCountryItems() {

        ExecutorSupplier.getInstance().execute(() -> repository.getRoomCountryItems(new AsyncTaskReceiver<List<Country>>() {
            @Override
            public void onSuccess(List<Country> result) {
                itemRoomLiveData.postValue(result);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        }));
    }

    public void saveRoomCountryVM(CountryModelEntity countryModelEntity) {

        ExecutorSupplier.getInstance().execute(() -> repository.saveRoomCountry(countryModelEntity));


    }

    public void deleteCountryVM(CountryModelEntity countryModelEntity) {

        ExecutorSupplier.getInstance().execute(() -> repository.deleteCountry(countryModelEntity));

    }

    public void deleteAllRoomCountriesVM() {

        ExecutorSupplier.getInstance().execute(repository::deleteAllRoomCountries);

    }

}
