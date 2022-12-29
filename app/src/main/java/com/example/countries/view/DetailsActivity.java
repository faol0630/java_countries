package com.example.countries.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.countries.R;
import com.example.countries.model.model.CountryModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {


    @BindView(R.id.detailImageView)
    ImageView detailImageView;

    @BindView(R.id.detailCapitalName)
    TextView detailCapitalName;

    @BindView(R.id.detailCountryName)
    TextView detailCountryName;

    @BindView(R.id.btnBack)
    AppCompatButton btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        Bundle countryModelFromRV = getIntent().getExtras();
        CountryModel countryModel = null;

        if (countryModelFromRV != null){
            countryModel = (CountryModel) countryModelFromRV.getSerializable("countryModel");

            detailCountryName.setText(countryModel.getCountryName());
            detailCapitalName.setText(countryModel.getCapital());
            Util.loadImage(detailImageView, countryModel.getFlag(), Util.getProgressDrawable(detailImageView.getContext()));

        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }




}