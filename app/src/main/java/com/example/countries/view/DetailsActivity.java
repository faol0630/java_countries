package com.example.countries.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.countries.R;
import com.example.countries.model.model.Country;
import com.example.countries.model.model.CountryModel;
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

    //viewModel para poder enviar el objeto a room:
    //se va a usar cuando se cree el respectivo metodo en repository y en viewModel
    //private ListViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //back arrow:
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //esto solamente muestra la flecha

        //viewModel = new ViewModelProvider(this).get(ListViewModel.class);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        Country country = null; //dejarlo aca afuera para que sea reconocido por el btn addToFavorites

        if (bundle != null){
            country = (Country) bundle.getSerializable("country");

            detailCountryName.setText(country.getCountryName());
            detailCapitalName.setText(country.getCapital());
            Util.loadImage(detailImageView, country.getFlag(), Util.getProgressDrawable(detailImageView.getContext()));
            detailRegion.setText(country.getRegion());
            detailSubregion.setText(country.getSubregion());
            detailDemonym.setText(country.getDemonym());
            detailNumericCode.setText(country.getNumericCode());

        }

        Country finalCountry = country;
        btnAddToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String countryName = detailCountryName.getText().toString();
                String capitalName = detailCapitalName.getText().toString();
                Util.loadImage(detailImageView, finalCountry.getFlag(), Util.getProgressDrawable(detailImageView.getContext()));
                String region = finalCountry.getRegion();
                String subregion = finalCountry.getSubregion();
                String demonym = finalCountry.getDemonym();
                String numericCode = finalCountry.getNumericCode();

                //viewModel.insertCountry(countryModelEntity); //este metodo hay que crearlo en repository y en viewModel

                Toast.makeText(DetailsActivity.this, "Add to favorites", Toast.LENGTH_SHORT).show();

            }
        });
    }

    //funcionalidad del back arrow:
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}