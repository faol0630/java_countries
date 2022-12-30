package com.example.countries.view;

//import static androidx.navigation.Navigation.findNavController;

import static androidx.navigation.Navigation.findNavController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.countries.R;
import com.example.countries.databinding.ActivityMainBinding;
import com.example.countries.model.model.CountryModel;
import com.example.countries.viewmodel.ListViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CountryListAdapter.OnClickItemInterface {

    private ActivityMainBinding binding;

    @BindView(R.id.rvCountriesList) //@BindView reemplaza al viewBinding
    RecyclerView rvCountriesList;

    @BindView(R.id.tvListError)
    TextView tvListError;

    @BindView(R.id.pbLoadingProgressBar)
    ProgressBar pbLoadingProgressBar;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;//reemplaza al LinearLayout en este caso

    @BindView(R.id.btnGoToFavorites)
    AppCompatButton btnGoToFavorites;
    //
    private ListViewModel viewModel;//sin instancia aca
    private final CountryListAdapter adapter = new CountryListAdapter(new ArrayList<>(), this);

    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
    //------------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        ButterKnife.bind(this);//esto pasa todas las variables al activity
//
        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        viewModel.refresh();

        rvCountriesList.setLayoutManager(new LinearLayoutManager(this));
        rvCountriesList.setAdapter(adapter);
//
//        //para que no se quede el progressBar cuando se hace scroll:
        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.refresh();
            swipeRefreshLayout.setRefreshing(false);
        });

        observerViewModel();

        btnGoToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, favoritesActivity.class);
                startActivity(intent);
            }
        });


    }
    //-----------------------------------------------------------------------------
    //-----------------------------------------------------------------------------
    //-----------------------------------------------------------------------------
    //--------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------

    private void observerViewModel() {
        viewModel.countries.observe(this, countryModels -> {
            if (countryModels != null) {
                rvCountriesList.setVisibility(View.VISIBLE);
                adapter.updateCountries(countryModels);
            }
        });

        viewModel.countryLoadError.observe(this, isError -> {
            if (isError != null) {
                tvListError.setVisibility(isError ? View.VISIBLE : View.GONE);
            }
        });

        viewModel.loading.observe(this, isLoading -> {
            if (isLoading != null) {
                pbLoadingProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    tvListError.setVisibility(View.GONE);
                    rvCountriesList.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void onClickItem(CountryModel countryModel) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("countryModel", countryModel);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}