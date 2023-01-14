package com.example.countries.view;

import android.content.Intent;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailsFragment extends Fragment {

    @BindView(R.id.detailImageView)
    ImageView detailImageView;

    @BindView(R.id.detailCapitalName)
    TextView detailCapitalName;

    @BindView(R.id.detailCountryName)
    TextView detailCountryName;

    @BindView(R.id.btnAddToFavorites)
    AppCompatButton btnAddToFavorites;

    @BindView(R.id.detailNumericCode)
    TextView detailNumericCode;

    @BindView(R.id.detailDemonym)
    TextView detailDemonym;

    @BindView(R.id.detailRegion)
    TextView detailRegion;

    @BindView(R.id.detailSubregion)
    TextView detailSubregion;

    private ListViewModel viewModel;

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

        View view = inflater.inflate(R.layout.details_fragment, container,false);
        unbinder = ButterKnife.bind(this, view); //diferente a como se hace en activity

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);

        Bundle bundle = getArguments();
        Country country = null;

        if (bundle != null) {
            country = (Country) bundle.getSerializable("country");

            detailCountryName.setText(country.getCountryName());
            detailCapitalName.setText(country.getCapital());
            Glide.with(getContext()).
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
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, homeFragment, "home_fragment")
                    .addToBackStack("home_fragment")
                    .commit();

        });

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
