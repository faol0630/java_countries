package com.example.countries.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countries.R;
import com.example.countries.model.model.Country;
import com.example.countries.viewmodel.ListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FavoritesFragment extends Fragment implements CountryListAdapter.OnClickItemInterface {

    RecyclerView rvFavCountriesList;

    AppCompatButton btnDeleteAllFavoritesCountries;

    TextView tvFavListError;

    private ListViewModel viewModel;
    private CountryListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.favorites_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //back arrow:
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        rvFavCountriesList = (RecyclerView) requireView().findViewById(R.id.rvFavCountriesList);
        btnDeleteAllFavoritesCountries = (AppCompatButton) requireView().findViewById(R.id.btnDeleteAllFavoritesCountries);
        tvFavListError = (TextView) requireView().findViewById(R.id.tvFavListError);

        adapter = new CountryListAdapter(this);
        rvFavCountriesList.setAdapter(adapter);
        rvFavCountriesList.setLayoutManager(new LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false));

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);

        viewModel.getRoomCountriesLiveData().observe(requireActivity(), countryRoomItem -> {
            adapter.setCountries(countryRoomItem);

            if (!countryRoomItem.isEmpty()) {

                rvFavCountriesList.setVisibility(View.VISIBLE);
                tvFavListError.setVisibility(View.GONE);


            } else {
                rvFavCountriesList.setVisibility(View.GONE);
                tvFavListError.setVisibility(View.VISIBLE);
            }

        });

        viewModel.requestRoomCountryItems();

        btnDeleteAllFavoritesCountries.setOnClickListener(v -> {

            List<Country> countries = new ArrayList<>();

            viewModel.deleteAllRoomCountriesVM();
            adapter.setCountries(countries);

            rvFavCountriesList.setVisibility(View.GONE);
            tvFavListError.setVisibility(View.VISIBLE);

            Toast.makeText(getContext(), "All favorites list deleted", Toast.LENGTH_SHORT).show();

        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            requireActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickItem(Country country) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("country", country);
        Fragment favoritesDetailsFragment = new FavoritesDetailsFragment();
        favoritesDetailsFragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, favoritesDetailsFragment, "favorites_details_fragment")
                .addToBackStack("favorites_details_fragment")
                .commit();

    }

}
