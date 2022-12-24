package com.example.countries.model.remote;

import com.example.countries.model.model.CountryModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface CountriesAPI {

    @GET("DevTides/countries/master/countriesV2.json")
    Single<List<CountryModel>> getCountries();

}

//single es un tipo de observador que emite un solo valor y termina
