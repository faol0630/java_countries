package com.example.countries.view;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countries.R;
import com.example.countries.model.model.Country;
import com.example.countries.viewmodel.ListViewModel;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CountryListAdapter.OnClickItemInterface {

    @BindView(R.id.rvCountriesList)
    RecyclerView rvCountriesList;

    @BindView(R.id.tvListError)
    TextView tvListError;

    @BindView(R.id.pbLoadingProgressBar)
    ProgressBar pbLoadingProgressBar;

    @BindView(R.id.btnGoToFavorites)
    AppCompatButton btnGoToFavorites;

    private CountryListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        adapter = new CountryListAdapter(this);
        rvCountriesList.setAdapter(adapter);
        rvCountriesList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ListViewModel viewModel = new ViewModelProvider(this).get(ListViewModel.class);

        viewModel.getCountriesLiveData().observe(this, countryItems -> adapter.setCountries(countryItems));

        viewModel.requestCountryItems();

        btnGoToFavorites.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });


    }


    @Override
    public void onClickItem(Country country) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("country", country);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}