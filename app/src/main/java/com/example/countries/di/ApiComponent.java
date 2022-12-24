package com.example.countries.di;

import com.example.countries.model.remote.CountriesService;
import com.example.countries.viewmodel.ListViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ApiComponent  {

    void inject(CountriesService service);

    void inject(ListViewModel viewModel);
}
