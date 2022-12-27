package com.example.countries.model.remote;

import com.example.countries.model.model.CountryModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountriesService {

    private static CountriesService instance;

    private static final String BASE_URL = "https://raw.githubusercontent.com/";

//    @Inject  //esto para cuando se cree la inyeccion de dependencias
//    public CountriesAPI api;

    private CountriesAPI api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CountriesAPI.class);

    //private CountriesService(){
        //esto se deja vacio hasta que se aplique la inyeccion de dependencias
        //DaggerApiComponent.create().inject(this);

    //}

    public static CountriesService getInstance(){
        if (instance == null){
            instance = new CountriesService();
        }
        return instance;
    }

    public Single<List<CountryModel>> getCountries(){
        return api.getCountries();
    }


}

//web fuente :
//https://raw.githubusercontent.com/DevTides/countries/master/countriesV2.json
