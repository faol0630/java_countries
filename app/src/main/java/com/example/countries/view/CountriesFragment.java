package com.example.countries.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.countries.R;
import com.example.countries.databinding.FragmentCountriesBinding;
import com.example.countries.model.model.CountryModel;
import com.example.countries.viewmodel.ListViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountriesFragment extends Fragment implements CountryListAdapter.OnClickItemInterface {

    private FragmentCountriesBinding binding;

//    @BindView(R.id.rvFragmentCountriesList) //@BindView reemplaza al viewBinding
//    RecyclerView rvCountriesList;
//
//    @BindView(R.id.tvFragmentListError)
//    TextView tvListError;
//
//    @BindView(R.id.pbFragmentLoadingProgressBar)
//    ProgressBar pbLoadingProgressBar;

    private ListViewModel viewModel;//sin instancia aca
    private final CountryListAdapter adapter = new CountryListAdapter(new ArrayList<>(), this);

    //constructor
    public CountriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCountriesBinding.inflate(inflater, container, false);
        return binding.getRoot();
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_countries, container, false);

        //ButterKnife.bind(this);//esto pasa todas las variables al fragment

//        viewModel = new ViewModelProvider(requireActivity()).get(ListViewModel.class);
//        viewModel.refresh();
//
//        binding.rvFragmentCountriesList.setLayoutManager(new LinearLayoutManager(requireContext()));
//        binding.rvFragmentCountriesList.setAdapter(adapter);
//
//        observerViewModel();
    }

    private void observerViewModel(){
        viewModel.countries.observe(requireActivity(), countryModels -> {
            if (countryModels != null ){
                binding.rvFragmentCountriesList.setVisibility(View.VISIBLE);
                adapter.updateCountries(countryModels);
            }
        });

        viewModel.countryLoadError.observe(requireActivity(), isError -> {
            if (isError != null){
                binding.tvFragmentListError.setVisibility(isError ? View.VISIBLE: View.GONE);
            }
        });

        viewModel.loading.observe(requireActivity(), isLoading -> {
            if (isLoading != null){
                binding.pbFragmentLoadingProgressBar.setVisibility(isLoading ?  View.VISIBLE: View.GONE);
                if (isLoading){
                    binding.tvFragmentListError.setVisibility(View.GONE);
                    binding.rvFragmentCountriesList.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClickItem(CountryModel countryModel) {
        Toast.makeText(getContext(), "Detalle de cada pais", Toast.LENGTH_LONG).show();
    }
}