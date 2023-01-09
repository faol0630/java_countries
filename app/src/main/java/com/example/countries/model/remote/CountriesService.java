package com.example.countries.model.remote;


import retrofit2.Retrofit;
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

}

//web:
//https://raw.githubusercontent.com/DevTides/countries/master/countriesV2.json
