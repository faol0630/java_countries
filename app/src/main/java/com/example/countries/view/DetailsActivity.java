package com.example.countries.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
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
    private ListViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //back arrow:
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //esto solamente muestra la flecha

        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);

        ButterKnife.bind(this);

        Bundle countryModelFromRV = getIntent().getExtras();
        CountryModel countryModel = null;

        if (countryModelFromRV != null){
            countryModel = (CountryModel) countryModelFromRV.getSerializable("countryModel");

            detailCountryName.setText(countryModel.getCountryName());
            detailCapitalName.setText(countryModel.getCapital());
            Util.loadImage(detailImageView, countryModel.getFlag(), Util.getProgressDrawable(detailImageView.getContext()));
            detailRegion.setText(countryModel.getRegion());
            detailSubregion.setText(countryModel.getSubregion());
            detailDemonym.setText(countryModel.getDemonym());
            detailNumericCode.setText(countryModel.getNumericCode());

        }

        CountryModel finalCountryModel = countryModel;
        btnAddToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String countryName = detailCountryName.getText().toString();
                String capitalName = detailCapitalName.getText().toString();
                String flag = "";
                String region = finalCountryModel.getRegion();
                String subregion = finalCountryModel.getSubregion();
                String demonym = finalCountryModel.getDemonym();
                String numericCode = finalCountryModel.getNumericCode();

                //estos elementos vienen del objeto CountryModel y van a ser pasados al
                //CountryModelEntity como parametros

                CountryModelEntity countryModelEntity = new CountryModelEntity(countryName, capitalName, flag, region, subregion, demonym, numericCode);

                viewModel.insertCountry(countryModelEntity);

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