package com.example.countries.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.countries.R;
import com.example.countries.viewmodel.ListViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvCountriesList) //@BindView reemplaza al viewBinding
    RecyclerView rvCountriesList;

    @BindView(R.id.tvListError)
    TextView tvListError;

    @BindView(R.id.pbLoadingProgressBar)
    ProgressBar pbLoadingProgressBar;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;//reemplaza al LinearLayout en este caso

    private ListViewModel viewModel;//sin instancia aca
    private CountryListAdapter adapter = new CountryListAdapter(new ArrayList<>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);//esto pasa todas las variables al activity

        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        viewModel.refresh();

        rvCountriesList.setLayoutManager(new LinearLayoutManager(this));
        rvCountriesList.setAdapter(adapter);

        //para que no se quede el progressBar cuando se hace scroll:
        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.refresh();
            swipeRefreshLayout.setRefreshing(false);
        });

        observerViewModel();

    }

    private void observerViewModel(){
        viewModel.countries.observe(this, countryModels -> {
            if (countryModels != null ){
                rvCountriesList.setVisibility(View.VISIBLE);
                adapter.updateCountries(countryModels);
//                pbLoadingProgressBar.setVisibility(View.GONE);
            }
        });

        viewModel.countryLoadError.observe(this, isError -> {
            if (isError != null){
                tvListError.setVisibility(isError ? View.VISIBLE: View.GONE);
            }
        });

        viewModel.loading.observe(this, isLoading -> {
            if (isLoading != null){
                pbLoadingProgressBar.setVisibility(isLoading ?  View.VISIBLE: View.GONE);
                if (isLoading){
                    tvListError.setVisibility(View.GONE);
                    rvCountriesList.setVisibility(View.GONE);
                }
            }
        });
    }
}