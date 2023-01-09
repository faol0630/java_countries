package com.example.countries.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.countries.R;
import com.example.countries.model.model.Country;
import com.example.countries.viewmodel.ListViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesActivity extends AppCompatActivity implements CountryListAdapter.OnClickItemInterface {

    @BindView(R.id.rvFavCountriesList)
    RecyclerView rvFavCountriesList;

    @BindView(R.id.btnDeleteAllFavoritesCountries)
    AppCompatButton btnDeleteAllFavoritesCountries;

    private ListViewModel viewModel;
    private CountryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        //back arrow:
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        adapter = new CountryListAdapter(this);
        rvFavCountriesList.setAdapter(adapter);
        rvFavCountriesList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);

        viewModel.getRoomCountriesLiveData().observe(this, countryRoomItem -> adapter.setCountries(countryRoomItem));

        viewModel.requestRoomCountryItems();

        btnDeleteAllFavoritesCountries.setOnClickListener(v -> {

            List<Country> countries = new ArrayList<>();

            viewModel.deleteAllRoomCountriesVM();
            adapter.setCountries(countries);

        });


    }

    @Override
    public void onClickItem(Country country) {
        Intent intent = new Intent(FavoritesActivity.this, FavoriteDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("country", country);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //back arrow functionality:
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}