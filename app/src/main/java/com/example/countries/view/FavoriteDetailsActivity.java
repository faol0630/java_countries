package com.example.countries.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.countries.R;
import com.example.countries.model.model.Country;
import com.example.countries.model.model.CountryModelEntity;
import com.example.countries.viewmodel.ListViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteDetailsActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_details);

        //back arrow:
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        Country country = null;

        if (bundle != null) {
            country = (Country) bundle.getSerializable("country");

            favoritesDetailCountryName.setText(country.getCountryName());
            favoritesDetailCapitalName.setText(country.getCapital());
            Glide.with(getBaseContext()).
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

            Toast.makeText(FavoriteDetailsActivity.this, "Deleted from favorites", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(FavoriteDetailsActivity.this, MainActivity.class);
            startActivity(intent);


        });
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