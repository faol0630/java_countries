package com.example.countries.model.remote;

import com.example.countries.model.model.CountryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CountriesAPI {

    @GET(ConstantsAPI.ENDPOINT)
    Call<List<CountryModel>> getCountries();

}

//single es un tipo de observador que emite un solo valor y termina
