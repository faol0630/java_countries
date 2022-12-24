package com.example.countries.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.countries.model.CountriesService;
import com.example.countries.model.CountryModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel {

    //livedata son objetos observables que generan valores
    public MutableLiveData<List<CountryModel>> countries = new MutableLiveData<List<CountryModel>>();
    public MutableLiveData<Boolean> countryLoadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();


    //comunicandose con retrofit backend:
    private CountriesService countriesService = CountriesService.getInstance();

    private CompositeDisposable disposable = new CompositeDisposable();

    public void refresh() {
        fetchCountries();
    }

    private void fetchCountries() {

        loading.setValue(true);
        disposable.add(
                //conecta con retrofit service:
                countriesService.getCountries()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<CountryModel>>() {

                            @Override
                            public void onSuccess(List<CountryModel> countryModels) {
                                countries.setValue(countryModels);
                                countryLoadError.setValue(false);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                countryLoadError.setValue(true);
                                loading.setValue(false);
                                e.printStackTrace();
                            }
                        })

        );

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
