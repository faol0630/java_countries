package com.example.countries.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.countries.model.local.AppDatabase;
import com.example.countries.model.model.CountryModelEntity;
import com.example.countries.model.remote.CountriesService;
import com.example.countries.model.model.CountryModel;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel {

    //livedata son objetos observables que generan valores
    public MutableLiveData<List<CountryModel>> countries = new MutableLiveData<List<CountryModel>>();
    public MutableLiveData<Boolean> countryLoadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();


    public ListViewModel() {
        super();
    }

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

    //-------------------------------------------------------------
    //-----RoomViewModel------RoomViewModel------RoomViewModel-----
    //-------------------------------------------------------------

    private final Executor executor = Executors.newSingleThreadExecutor();

    private AppDatabase appDatabase;

    private final MutableLiveData<List<CountryModelEntity>> _entityList = new MutableLiveData<>();
    public LiveData<List<CountryModelEntity>> entityList = _entityList;


    public LiveData<List<CountryModelEntity>> getAllCountries() {

        _entityList.setValue((List<CountryModelEntity>) appDatabase.countryDao.getAllCountries());

        return _entityList;

    }

    public void insertCountry(CountryModelEntity countryModelEntity) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.countryDao.insert(countryModelEntity);
            }
        });
    }

    public LiveData<CountryModelEntity> getCountryByName(String name) {

        return appDatabase.countryDao.getCountryByName(name);

    }


    public void deleteCountry(CountryModelEntity countryModelEntity) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.countryDao.deleteCountry(countryModelEntity);
            }
        });
    }

    public void deleteAllCountries() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.countryDao.deleteAllCountries();
            }
        });
    }


    //-------------------------------------------------------------
    //--EndRoomViewModel----EndRoomViewModel----EndRoomViewModel---
    //-------------------------------------------------------------

}
