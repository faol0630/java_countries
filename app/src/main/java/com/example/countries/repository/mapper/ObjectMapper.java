package com.example.countries.repository.mapper;

import java.util.List;

public interface ObjectMapper<T, Model> {
    Model toModel(T object);
    T fromModel(Model object);
    List<T> fromModel(List<Model> object);
    List<Model> toModel(List<T> object);
}


//Model = Country
//T = CountryModelEntity or CountryModel