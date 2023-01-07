package com.example.countries.model.remote;

import com.example.countries.model.model.CountryModel;

import java.util.List;


import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountriesService {

    private static final Retrofit retrofitInstance = new Retrofit.Builder()
            .baseUrl(ConstantsAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    //empty private constructor
    private CountriesService() {
    }

    public static Retrofit getRetrofitInstance(){
        return retrofitInstance;
    }

    //private static CountriesService instance;

//    private CountriesAPI api = new Retrofit.Builder()
//            .baseUrl(ConstantsAPI.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .build()
//            .create(CountriesAPI.class);


//    public static CountriesService getInstance(){
//        if (instance == null){
//            instance = new CountriesService();
//        }
//        return instance;
//    }
//
//    public Single<List<CountryModel>> getCountries(){
//        return api.getCountries();
//    }

}

//web fuente :
//https://raw.githubusercontent.com/DevTides/countries/master/countriesV2.json
