package com.example.countries.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.countries.R;
import com.example.countries.model.model.Country;
import com.example.countries.model.model.CountryModelEntity;
import com.example.countries.viewmodel.ListViewModel;


import java.util.Objects;


public class FavoritesDetailsFragment extends Fragment {

    ImageView favoritesDetailImageView;

    TextView favoritesDetailCapitalName;

    TextView favoritesDetailCountryName;

    AppCompatButton btnDeleteFromFavorites;

    TextView favoritesDetailNumericCode;

    TextView favoritesDetailDemonym;

    TextView favoritesDetailRegion;

    TextView favoritesDetailSubregion;

    private ListViewModel viewModel;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //back arrow:
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        favoritesDetailImageView = (ImageView) requireView().findViewById(R.id.favoritesDetailImageView);
        favoritesDetailCapitalName = (TextView) requireView().findViewById(R.id.favoritesDetailCapitalName);
        favoritesDetailCountryName = (TextView) requireView().findViewById(R.id.favoritesDetailCountryName);
        btnDeleteFromFavorites = (AppCompatButton) requireView().findViewById(R.id.btnDeleteFromFavorites);
        favoritesDetailNumericCode = (TextView) requireView().findViewById(R.id.favoritesDetailNumericCode);
        favoritesDetailDemonym = (TextView) requireView().findViewById(R.id.favoritesDetailDemonym);
        favoritesDetailRegion = (TextView) requireView().findViewById(R.id.favoritesDetailRegion);
        favoritesDetailSubregion = (TextView) requireView().findViewById(R.id.favoritesDetailSubregion);

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);

        Bundle bundle = getArguments();
        Country country = null;

        if (bundle != null) {
            country = (Country) bundle.getSerializable("country");

            favoritesDetailCountryName.setText(country.getCountryName());
            favoritesDetailCapitalName.setText(country.getCapital());
            Glide.with(requireContext()).
                    load(country.getFlag()).into(favoritesDetailImageView);
            favoritesDetailRegion.setText(country.getRegion());
            favoritesDetailSubregion.setText(country.getSubregion());
            favoritesDetailDemonym.setText(country.getDemonym());
            favoritesDetailNumericCode.setText(country.getNumericCode());

        }

        Country finalCountry = country;
        btnDeleteFromFavorites.setOnClickListener(v -> {

            String countryName = favoritesDetailCountryName.getText().toString();
            String capitalName = favoritesDetailCapitalName.getText().toString();
            String image = Util.loadImage(favoritesDetailImageView, finalCountry.getFlag(), Util.getProgressDrawable(favoritesDetailImageView.getContext()));
            String region = finalCountry.getRegion();
            String subregion = finalCountry.getSubregion();
            String demonym = finalCountry.getDemonym();
            String numericCode = finalCountry.getNumericCode();

            CountryModelEntity countryModelEntity = new CountryModelEntity(
                    countryName, capitalName, image, region, subregion, demonym, numericCode
            );

            viewModel.deleteCountryVM(countryModelEntity);

            Toast.makeText(getContext(), "Deleted from favorites", Toast.LENGTH_SHORT).show();

            Fragment homeFragment = new HomeFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, homeFragment, "home_fragment")
                    .addToBackStack("home_fragment")
                    .commit();


        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.favorites_details_fragment, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            requireActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
