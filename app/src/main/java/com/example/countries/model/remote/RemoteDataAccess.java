package com.example.countries.model.remote;

import com.example.countries.model.model.CountryModel;

public class RemoteDataAccess {

    //singleton
    private static final RemoteDataAccess remoteDataInstance = new RemoteDataAccess();

    //private constructor
    private RemoteDataAccess(){

    }

    public static RemoteDataAccess getRemoteDataInstance(){
        return remoteDataInstance;
    }

    public CountriesAPI getCountriesItemFromRemoteDataAccess(){
        return CountriesService.getRetrofitInstance().create(CountriesAPI.class);
    }
}
