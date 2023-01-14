package com.example.countries.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FavoritesFragment extends Fragment implements CountryListAdapter.OnClickItemInterface{

    @BindView(R.id.rvFavCountriesList)
    RecyclerView rvFavCountriesList;

    @BindView(R.id.btnDeleteAllFavoritesCountries)
    AppCompatButton btnDeleteAllFavoritesCountries;

    @BindView(R.id.tvFavListError)
    TextView tvFavListError;

    private ListViewModel viewModel;
    private CountryListAdapter adapter;

    private Unbinder unbinder; //para liberar cuando se termina

    //back arrow:
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

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

        });

        return view;
    }

    @Override
    public void onClickItem(Country country) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("country", country);
        Fragment favoritesDetailsFragment = new FavoritesDetailsFragment();
        favoritesDetailsFragment.setArguments(bundle);
//        getFragmentManager()
//                .beginTransaction()
//                .replace()
//                .addToBackStack("favorites_details_fragment", favoritesDetailsFragment)
//                .commit();

    }

}
