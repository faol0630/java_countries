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

    private AppDatabase localStorage; //Room
    private RemoteDataAccess remote; //Retrofit
    private CountryModelEntityMapper entityMapper;
    private CountryModelMapper dtoMapper;

    public CountryRepository(AppDatabase localStorage, RemoteDataAccess remote, CountryModelEntityMapper entityMapper, CountryModelMapper dtoMapper) {
        this.localStorage = localStorage;
        this.remote = remote;
        this.entityMapper = entityMapper;
        this.dtoMapper = dtoMapper;
    }

    //lamada a la lista de Room:

    public void getRoomCountryItems(AsyncTaskReceiver<List<Country>> callback) {

        ExecutorSupplier.getInstance().execute(() -> {
            //lista del entity
            //esto no puede estar por fuera de la funcion:
            List<CountryModelEntity> localItems = localStorage.countryDao().getAllRoomCountries();
            //En caso de conexion exitosa, del entity al neutro
            callback.onSuccess(entityMapper.toModel(localItems));
        });

    }


    public void getCountryItems(AsyncTaskReceiver<List<Country>> callback) {

//        ExecutorSupplier.getInstance().execute(() -> {
//            //lista del entity
//            //esto no puede estar por fuera de la funcion:
//            List<CountryModelEntity> localItems = localStorage.countryDao().getAllRoomCountries();
//            //En caso de conexion exitosa, del entity al neutro
//            callback.onSuccess(entityMapper.toModel(localItems));
//        });

        //llamado a la lista del DTO remote
        Call<List<CountryModel>> call = remote.getCountriesItemFromRemoteDataAccess().getCountries();


        call.enqueue(new Callback<List<CountryModel>>() {
            @Override
            public void onResponse
                    (@NonNull Call<List<CountryModel>> call, @NonNull Response<List<CountryModel>> response) {
                if (response.body() != null && !response.body().isEmpty()) {

                    //del DTO a neutro
                    //esto no puede estar por fuera de la funcion:
                    List<Country> items = dtoMapper.toModel(response.body());

                    callback.onSuccess(items); //del DTO al neutro

                    //--------------------------------------------
                    //salvar toda la info que viene de retrofit en Room:
//                    ExecutorSupplier.getInstance().execute(() -> {
//                        //convirtiendo de neutro a entity:
//                        localStorage.countryDao().saveAllRoomCountryItems(entityMapper.fromModel(items)); ; //se deben salvar en modo Entity
//                    });


                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CountryModel>> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });

    }


}