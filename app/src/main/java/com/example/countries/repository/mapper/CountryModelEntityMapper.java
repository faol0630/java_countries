package com.example.countries.repository.mapper;


import com.example.countries.model.model.Country;
import com.example.countries.model.model.CountryModelEntity;

import java.util.List;
import java.util.stream.Collectors;

public class CountryModelEntityMapper implements ObjectMapper<CountryModelEntity, Country>{

    private static final CountryModelEntityMapper COUNTRY_MODEL_ENTITY_MAPPER = new CountryModelEntityMapper();

    public static CountryModelEntityMapper getInstance(){
        return COUNTRY_MODEL_ENTITY_MAPPER;
    }

    //private empty constructor
    private CountryModelEntityMapper() {
    }

    @Override
    public Country toModel(CountryModelEntity object) {
        return new Country(object.getCountryName(), object.getCapital(), object.getFlag(), object.getRegion(), object.getSubregion(), object.getDemonym(), object.getNumericCode());
    }

    @Override
    public CountryModelEntity fromModel(Country object) {
        return new CountryModelEntity(object.getCountryName(), object.getCapital(), object.getFlag(), object.getRegion(), object.getSubregion(), object.getDemonym(), object.getNumericCode()) ;
    }

    //minSdk 24:
    @Override
    public List<CountryModelEntity> fromModel(List<Country> object) {
        return object.stream().map(this::fromModel).collect(Collectors.toList());
    }

    @Override
    public List<Country> toModel(List<CountryModelEntity> object) {
        return object.stream().map(this::toModel).collect(Collectors.toList());
    }


}
