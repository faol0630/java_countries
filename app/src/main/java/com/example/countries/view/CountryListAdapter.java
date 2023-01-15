package com.example.countries.view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.countries.R;
import com.example.countries.model.model.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.CountryViewHolder> {

    private List<Country> countries = new ArrayList<>();

    private final OnClickItemInterface onClickItemInterface;

    public interface OnClickItemInterface {

        void onClickItem(Country country);

    }

    public CountryListAdapter(OnClickItemInterface onClickItemInterface) {
        this.onClickItemInterface = onClickItemInterface;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        holder.countryName.setText(countries.get(position).getCountryName());
        holder.capitalName.setText(countries.get(position).getCapital());
        Glide.with(holder.itemView.getContext()).
                load(countries.get(position).getFlag()).into(holder.countryImage);
        holder.itemView.getRootView().setOnClickListener(v -> onClickItemInterface.onClickItem(countries.get(position)));

    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCountries(List<Country> countries){
        this.countries = countries;
        notifyDataSetChanged();
    }

    static class CountryViewHolder extends RecyclerView.ViewHolder {

        ImageView countryImage;

        TextView countryName;

        TextView capitalName;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            countryImage = itemView.findViewById(R.id.imageView);
            countryName = itemView.findViewById(R.id.countryName);
            capitalName = itemView.findViewById(R.id.capitalName);
        }

    }
}
