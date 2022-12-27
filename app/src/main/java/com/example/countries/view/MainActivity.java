package com.example.countries.view;

//import static androidx.navigation.Navigation.findNavController;

import static androidx.navigation.Navigation.findNavController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity implements OnClickItemInterface  {

    private ActivityMainBinding binding;

//    @BindView(R.id.rvCountriesList) //@BindView reemplaza al viewBinding
//    RecyclerView rvCountriesList;
//
//    @BindView(R.id.tvListError)
//    TextView tvListError;
//
//    @BindView(R.id.pbLoadingProgressBar)
//    ProgressBar pbLoadingProgressBar;
//
//    @BindView(R.id.swipeRefreshLayout)
//    SwipeRefreshLayout swipeRefreshLayout;//reemplaza al LinearLayout en este caso
//
    private ListViewModel viewModel;//sin instancia aca
    private final CountryListAdapter adapter = new CountryListAdapter(new ArrayList<>(), this);

    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
    //NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_main);

//        ButterKnife.bind(this);//esto pasa todas las variables al activity
//
        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        viewModel.refresh();

        binding.rvCountriesList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvCountriesList.setAdapter(adapter);
//
//        //para que no se quede el progressBar cuando se hace scroll:
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.refresh();
            binding.swipeRefreshLayout.setRefreshing(false);
        });

        observerViewModel();

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .setReorderingAllowed(true)
//                    .add(R.id.countriesFragment, CountriesFragment.class, null)
//                    .commit();
//        }

        //--------------------------------------------------------------------------

//        navController = Navigation.findNavController(this, R.id.fragment_container_view_tag);
//        NavigationUI.setupActionBarWithNavController(this, navController);


    }
    //-----------------------------------------------------------------------------
    //-----------------------------------------------------------------------------
    //-----------------------------------------------------------------------------
//    @Override
//    public boolean onSupportNavigateUp(){
//        return navController.navigateUp();
//    }
    //--------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------

    private void observerViewModel(){
        viewModel.countries.observe(this, countryModels -> {
            if (countryModels != null ){
                binding.rvCountriesList.setVisibility(View.VISIBLE);
                adapter.updateCountries(countryModels);
            }
        });

        viewModel.countryLoadError.observe(this, isError -> {
            if (isError != null){
                binding.tvListError.setVisibility(isError ? View.VISIBLE: View.GONE);
            }
        });

        viewModel.loading.observe(this, isLoading -> {
            if (isLoading != null){
                binding.pbLoadingProgressBar.setVisibility(isLoading ?  View.VISIBLE: View.GONE);
                if (isLoading){
                    binding.tvListError.setVisibility(View.GONE);
                    binding.rvCountriesList.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void onClickItem(CountryModel countryModel, int position) {
        Toast.makeText(this, "Vamos al detalle de cada pais!", Toast.LENGTH_LONG).show();
    }
}