package com.example.countries.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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

public class FavoritesDetailsFragment extends Fragment {

    @BindView(R.id.favoritesDetailImageView)
    ImageView favoritesDetailImageView;

    @BindView(R.id.favoritesDetailCapitalName)
    TextView favoritesDetailCapitalName;

    @BindView(R.id.favoritesDetailCountryName)
    TextView favoritesDetailCountryName;

    @BindView(R.id.btnDeleteFromFavorites)
    AppCompatButton btnDeleteFromFavorites;

    @BindView(R.id.favoritesDetailNumericCode)
    TextView favoritesDetailNumericCode;

    @BindView(R.id.favoritesDetailDemonym)
    TextView favoritesDetailDemonym;

    @BindView(R.id.favoritesDetailRegion)
    TextView favoritesDetailRegion;

    @BindView(R.id.favoritesDetailSubregion)
    TextView favoritesDetailSubregion;

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
        View view = inflater.inflate(R.layout.favorites_details_fragment, container, false);

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);

        unbinder = ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        Country country = null;

        if (bundle != null) {
            country = (Country) bundle.getSerializable("country");

            favoritesDetailCountryName.setText(country.getCountryName());
            favoritesDetailCapitalName.setText(country.getCapital());
            Glide.with(getContext()).
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

            Toast.makeText(getContext(), "Added to favorites", Toast.LENGTH_SHORT).show();

            Fragment homeFragment = new HomeFragment();
//            getFragmentManager()
//                    .beginTransaction()
//                    .replace()
//                    .addToBackStack("home_fragment", homeFragment)
//                    .commit();


        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
