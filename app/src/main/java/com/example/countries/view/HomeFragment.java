package com.example.countries.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countries.R;
import com.example.countries.model.model.Country;
import com.example.countries.viewmodel.ListViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment implements CountryListAdapter.OnClickItemInterface {

    @BindView(R.id.rvCountriesList)
    RecyclerView rvCountriesList;

    @BindView(R.id.tvListError)
    TextView tvListError;

    @BindView(R.id.btnGoToFavorites)
    AppCompatButton btnGoToFavorites;

    private ListViewModel viewModel;
    private CountryListAdapter adapter;

    private Unbinder unbinder; //para liberar cuando se termina

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

        adapter = new CountryListAdapter(this);
        rvCountriesList.setAdapter(adapter);
        rvCountriesList.setLayoutManager(new LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false));

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);

        viewModel.getCountriesLiveData().observe(requireActivity(), countryItems -> {
            adapter.setCountries(countryItems);

            if (!countryItems.isEmpty()) {

                rvCountriesList.setVisibility(View.VISIBLE);
                tvListError.setVisibility(View.GONE);

            } else {
                rvCountriesList.setVisibility(View.GONE);
                tvListError.setVisibility(View.VISIBLE);

            }

        });

        viewModel.requestCountryItems();

        btnGoToFavorites.setOnClickListener(v -> {
            Fragment favoritesFragment = new FavoritesFragment();
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, favoritesFragment, "favorites_fragment")
                    .addToBackStack("favorites_fragment")
                    .commit();
        });

        return view;
    }

    @Override
    public void onClickItem(Country country) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("country", country);
        Fragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, detailsFragment, "details_fragment")
                .addToBackStack("details_fragment")
                .commit();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
