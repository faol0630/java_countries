package com.example.countries.model.remote;

public class RemoteDataAccess {

    private static final RemoteDataAccess remoteDataInstance = new RemoteDataAccess();

    //empty private constructor
    private RemoteDataAccess(){

    }

    public static RemoteDataAccess getRemoteDataInstance(){
        return remoteDataInstance;
    }

    public CountriesAPI getCountriesItemFromRemoteDataAccess(){
        return CountriesService.getRetrofitInstance().create(CountriesAPI.class);
    }
}
