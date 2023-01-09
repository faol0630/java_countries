package com.example.countries.repository;

import androidx.annotation.NonNull;

import com.example.countries.model.local.AppDatabase;
import com.example.countries.model.model.Country;
import com.example.countries.model.model.CountryModel;
import com.example.countries.model.model.CountryModelEntity;
import com.example.countries.model.remote.RemoteDataAccess;
import com.example.countries.repository.mapper.CountryModelEntityMapper;
import com.example.countries.repository.mapper.CountryModelMapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryRepository {

    private final AppDatabase localStorage; //Room
    private final RemoteDataAccess remote; //Retrofit
    private final CountryModelEntityMapper entityMapper;
    private final CountryModelMapper dtoMapper;

    public CountryRepository(AppDatabase localStorage, RemoteDataAccess remote, CountryModelEntityMapper entityMapper, CountryModelMapper dtoMapper) {
        this.localStorage = localStorage;
        this.remote = remote;
        this.entityMapper = entityMapper;
        this.dtoMapper = dtoMapper;
    }

    public void getRoomCountryItems(AsyncTaskReceiver<List<Country>> callback) {

        ExecutorSupplier.getInstance().execute(() -> {

            List<CountryModelEntity> localItems = localStorage.countryDao().getAllRoomCountries();
            callback.onSuccess(entityMapper.toModel(localItems));
        });

    }

    public void saveRoomCountry(CountryModelEntity countryModelEntity){

        ExecutorSupplier.getInstance().execute(() -> localStorage.countryDao().insertCountry(countryModelEntity));
    }

    public void deleteCountry(CountryModelEntity countryModelEntity){

        ExecutorSupplier.getInstance().execute(() -> localStorage.countryDao().deleteCountry(countryModelEntity));
    }

    public void deleteAllRoomCountries(){

        ExecutorSupplier.getInstance().execute(localStorage::clearAllTables);
    }


    public void getCountryItems(AsyncTaskReceiver<List<Country>> callback) {

        Call<List<CountryModel>> call = remote.getCountriesItemFromRemoteDataAccess().getCountries();


        call.enqueue(new Callback<List<CountryModel>>() {
            @Override
            public void onResponse
                    (@NonNull Call<List<CountryModel>> call, @NonNull Response<List<CountryModel>> response) {
                if (response.body() != null && !response.body().isEmpty()) {

                    List<Country> items = dtoMapper.toModel(response.body());

                    callback.onSuccess(items);

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CountryModel>> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });

    }

}