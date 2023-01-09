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

public class DetailsActivity extends AppCompatActivity {


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //back arrow:
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        Country country = null;

        if (bundle != null) {
            country = (Country) bundle.getSerializable("country");

            detailCountryName.setText(country.getCountryName());
            detailCapitalName.setText(country.getCapital());
            Glide.with(getBaseContext()).
                    load(country.getFlag()).into(detailImageView);
            detailRegion.setText(country.getRegion());
            detailSubregion.setText(country.getSubregion());
            detailDemonym.setText(country.getDemonym());
            detailNumericCode.setText(country.getNumericCode());

        }

        Country finalCountry = country;
        btnAddToFavorites.setOnClickListener(view -> {

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

            Toast.makeText(DetailsActivity.this, "Add to favorites", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
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