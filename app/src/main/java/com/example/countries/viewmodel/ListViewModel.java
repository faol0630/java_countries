package com.example.countries.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.countries.model.model.CountryModelEntity;
import com.example.countries.model.remote.CountriesService;
import com.example.countries.model.model.CountryModel;
import com.example.countries.repository.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

//ACA HAY CODIGO QUE DEBERIA IR EN UN REPOSITORY

public class ListViewModel extends ViewModel {

    //livedata son objetos observables que generan valores
    public MutableLiveData<List<CountryModel>> countries = new MutableLiveData<List<CountryModel>>();
    public MutableLiveData<Boolean> countryLoadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();


    //comunicandose con retrofit backend:
    //con Dagger:
//    @Inject
//    public CountriesService countriesService;
    //constructor
    public ListViewModel(){
        super();
        //DaggerApiComponent.create().inject(this);
    }
    //antes de Dagger:
    private CountriesService countriesService = CountriesService.getInstance();

    private final Repository repository = new Repository();

    private CompositeDisposable disposable = new CompositeDisposable();

    public void refresh() {
        fetchCountries();
    }

    private void fetchCountries() {

        loading.setValue(true);
        disposable.add(
                //conecta con retrofit service:
                //repository.getCountries()
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

    //-------------------------------------------------------------
    //-----RoomViewModel------RoomViewModel------RoomViewModel-----
    //-------------------------------------------------------------

    //public Repository repository;

    public LiveData<List<CountryModelEntity>> getAllCountries() {
        return repository.getAllCountries();
    }

    public void insertCountry(CountryModelEntity countryModelEntity){
        repository.insertCountry(countryModelEntity);
    }

    public LiveData<CountryModelEntity> getCountryByName(String name) {
        return repository.getCountryByName(name);
    }

    public void deleteCountry(CountryModelEntity countryModelEntity){
        repository.deleteCountry(countryModelEntity);
    }

    public void deleteAllCountries(){
        repository.deleteAllCountries();
    }

    //-------------------------------------------------------------
    //--EndRoomViewModel----EndRoomViewModel----EndRoomViewModel---
    //-------------------------------------------------------------

}
