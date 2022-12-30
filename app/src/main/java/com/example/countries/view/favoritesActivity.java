package com.example.countries.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.countries.R;
import com.example.countries.model.model.CountryModel;
import com.example.countries.model.model.CountryModelEntity;
import com.example.countries.viewmodel.ListViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class favoritesActivity extends AppCompatActivity implements CountryListAdapter.OnClickItemInterface {

    @BindView(R.id.rvFavCountriesList) //@BindView reemplaza al viewBinding
    RecyclerView rvFavCountriesList;

    @BindView(R.id.tvFavListError)
    TextView tvFavListError;

    @BindView(R.id.pbFavLoadingProgressBar)
    ProgressBar pbFavLoadingProgressBar;

    private ListViewModel viewModel;//sin instancia aca
    private final CountryListAdapter adapter = new CountryListAdapter(new ArrayList<>(), (CountryListAdapter.OnClickItemInterface) this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        ButterKnife.bind(this);//esto pasa todas las variables al activity

        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        viewModel.getAllCountries();

        rvFavCountriesList.setLayoutManager(new LinearLayoutManager(this));


        observerViewModel();
    }

    private void observerViewModel(){
        viewModel.entityList.observe(this, countryModelsEntityList -> {

            rvFavCountriesList.setAdapter(adapter);

            List<CountryModel> list = (List<CountryModel>) countryModelsEntityList.stream().map(it ->
                    new CountryModel(it.countryName, it.capital, it.flag, it.region, it.subregion, it.demonym, it.numericCode)
            );

            rvFavCountriesList.setVisibility(View.VISIBLE);
                adapter.updateCountries(list);

        });

        viewModel.countryLoadError.observe(this, isError -> {
            if (isError != null){
                tvFavListError.setVisibility(isError ? View.VISIBLE: View.GONE);
            }
        });

        viewModel.loading.observe(this, isLoading -> {
            if (isLoading != null){
                pbFavLoadingProgressBar.setVisibility(isLoading ?  View.VISIBLE: View.GONE);
                if (isLoading){
                    tvFavListError.setVisibility(View.GONE);
                    rvFavCountriesList.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClickItem(CountryModel countryModel) {

    }
}