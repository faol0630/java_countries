package com.example.countries.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countries.R;
import com.example.countries.model.model.Country;
import com.example.countries.viewmodel.ListViewModel;

public class HomeFragment extends Fragment implements CountryListAdapter.OnClickItemInterface {

    RecyclerView rvCountriesList;

    TextView tvListError;

    AppCompatButton btnGoToFavorites;

    private CountryListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        rvCountriesList = (RecyclerView) requireView().findViewById(R.id.rvCountriesList);
        tvListError = (TextView) requireView().findViewById(R.id.tvListError);
        btnGoToFavorites = (AppCompatButton) requireView().findViewById(R.id.btnGoToFavorites);
        adapter = new CountryListAdapter(this);
        rvCountriesList.setAdapter(adapter);
        rvCountriesList.setLayoutManager(new LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false));

        ListViewModel viewModel = new ViewModelProvider(this).get(ListViewModel.class);

        viewModel.getCountriesLiveData().observe(requireActivity(), countryItems -> {
            adapter.setCountries(countryItems);

            if (!countryItems.isEmpty()) {

                rvCountriesList.setVisibility(View.VISIBLE);
                tvListError.setVisibility(View.GONE);

            } else {
                rvCountriesList.setVisibility(View.GONE);
                tvListError.setVisibility(View.VISIBLE);

            }

        });

        viewModel.requestCountryItems();

        btnGoToFavorites.setOnClickListener(v -> {
            Fragment favoritesFragment = new FavoritesFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, favoritesFragment, "favorites_fragment")
                    .addToBackStack("favorites_fragment")
                    .commit();
        });

    }

    @Override
    public void onClickItem(Country country) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("country", country);
        Fragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, detailsFragment, "details_fragment")
                .addToBackStack("details_fragment")
                .commit();

    }

}
