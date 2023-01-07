package com.example.countries.repository.mapper;


import com.example.countries.model.model.Country;
import com.example.countries.model.model.CountryModel;

import java.util.List;
import java.util.stream.Collectors;

public class CountryModelMapper implements ObjectMapper<CountryModel, Country> {

    private static final CountryModelMapper instance = new CountryModelMapper();

    //private empty constructor
    public CountryModelMapper() {
    }

    public static CountryModelMapper getInstance(){
        return instance;
    }

    @Override
    public Country toModel(CountryModel object) {
        return new Country(object.getCountryName(), object.getCapital(), object.getFlag(), object.getRegion(), object.getSubregion(), object.getDemonym(), object.getNumericCode());
    }

    @Override
    public CountryModel fromModel(Country object) {
        CountryModel countryModel = new CountryModel();
        countryModel.setCountryName(object.getCountryName());
        countryModel.setCapital(object.getCapital());
        countryModel.setFlag(object.getFlag());
        countryModel.setRegion(object.getRegion());
        countryModel.setSubregion(object.getSubregion());
        countryModel.setDemonym(object.getDemonym());
        countryModel.setNumericCode(object.getNumericCode());
        return countryModel;
    }

    @Override
    public List<CountryModel> fromModel(List<Country> object) {
        return object.stream().map(this::fromModel).collect(Collectors.toList());
    }

    @Override
    public List<Country> toModel(List<CountryModel> object) {
        return object.stream().map(this::toModel).collect(Collectors.toList());
    }

}
