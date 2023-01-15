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

public class DetailsFragment extends Fragment {

    ImageView detailImageView;

    TextView detailCapitalName;

    TextView detailCountryName;

    AppCompatButton btnAddToFavorites;

    TextView detailNumericCode;

    TextView detailDemonym;

    TextView detailRegion;

    TextView detailSubregion;

    private ListViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.details_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //back arrow:
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        detailImageView = (ImageView) requireView().findViewById(R.id.detailImageView);
        detailCapitalName = (TextView) requireView().findViewById(R.id.detailCapitalName);
        detailCountryName = (TextView) requireView().findViewById(R.id.detailCountryName);
        btnAddToFavorites = (AppCompatButton) requireView().findViewById(R.id.btnAddToFavorites);
        detailNumericCode = (TextView) requireView().findViewById(R.id.detailNumericCode);
        detailDemonym = (TextView) requireView().findViewById(R.id.detailDemonym);
        detailRegion = (TextView) requireView().findViewById(R.id.detailRegion);
        detailSubregion = (TextView) requireView().findViewById(R.id.detailSubregion);

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);

        Bundle bundle = getArguments();
        Country country = null;

        if (bundle != null) {
            country = (Country) bundle.getSerializable("country");

            detailCountryName.setText(country.getCountryName());
            detailCapitalName.setText(country.getCapital());
            Glide.with(requireContext()).
                    load(country.getFlag()).into(detailImageView);
            detailRegion.setText(country.getRegion());
            detailSubregion.setText(country.getSubregion());
            detailDemonym.setText(country.getDemonym());
            detailNumericCode.setText(country.getNumericCode());

        }

        Country finalCountry = country;
        btnAddToFavorites.setOnClickListener(view1 -> {

            String countryName = detailCountryName.getText().toString();
            String capitalName = detailCapitalName.getText().toString();
            String image = Util.loadImage(detailImageView, finalCountry.getFlag(), Util.getProgressDrawable(detailImageView.getContext()));
            String region = finalCountry.getRegion();
            String subregion = finalCountry.getSubregion();
            String demonym = finalCountry.getDemonym();
            String numericCode = finalCountry.getNumericCode();

            CountryModelEntity countryModelEntity = new CountryModelEntity(
                    countryName, capitalName, image, region, subregion, demonym, numericCode
            );

            viewModel.saveRoomCountryVM(countryModelEntity);

            Toast.makeText(getContext(), "Added to favorites", Toast.LENGTH_SHORT).show();

            Fragment homeFragment = new HomeFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, homeFragment, "home_fragment")
                    .addToBackStack("home_fragment")
                    .commit();

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

}
