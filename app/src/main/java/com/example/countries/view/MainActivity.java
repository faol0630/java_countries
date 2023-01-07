package com.example.countries.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.countries.R;
import com.example.countries.model.model.Country;
import com.example.countries.model.model.CountryModel;
import com.example.countries.viewmodel.ListViewModel;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CountryListAdapter.OnClickItemInterface {

    @BindView(R.id.rvCountriesList) //@BindView reemplaza al viewBinding
    RecyclerView rvCountriesList;

    @BindView(R.id.tvListError)
    TextView tvListError;

    @BindView(R.id.pbLoadingProgressBar)
    ProgressBar pbLoadingProgressBar;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;//reemplaza al LinearLayout en este caso

    @BindView(R.id.btnGoToFavorites)
    AppCompatButton btnGoToFavorites;
    //
    private ListViewModel viewModel;//sin instancia aca
    private CountryListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);//esto pasa todas las variables al activity

        adapter = new CountryListAdapter(this);
        rvCountriesList.setAdapter(adapter);
        rvCountriesList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);

        viewModel.getCountriesLiveData().observe(this, countryItems -> {
            adapter.setCountries(countryItems);
        });

        viewModel.requestCountryItems();

//        btnGoToFavorites.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, favoritesActivity.class);
//            startActivity(intent);
//        });


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