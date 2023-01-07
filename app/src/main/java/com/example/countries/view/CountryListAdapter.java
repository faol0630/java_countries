package com.example.countries.view;

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
import com.example.countries.model.model.CountryModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


//segundo, extend la class para implementar metodos:
public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.CountryViewHolder> {

    //tercero, crear la lista:
    private List<Country> countries = new ArrayList<>();
    //decimo, crear el onItemClick
    private OnClickItemInterface onClickItemInterface;

    public interface OnClickItemInterface {

        void onClickItem(Country country);

    }

    //cuarto, constructor:
    public CountryListAdapter(OnClickItemInterface onClickItemInterface) {
        this.onClickItemInterface = onClickItemInterface;
    }

    //quinto, crear metodo que trae la info del backend:
    //newCountries es el nombre del parametro.
//    public void updateCountries (List<CountryModel> newCountries){
//        countries.clear();
//        countries.addAll(newCountries);
//        notifyDataSetChanged(); //informa al sistema de todos los cambios
//    }

    //septimo, crear aca el ViewHolder:
    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        return new CountryViewHolder(view);
    }

    //noveno, implementar holder aca:
    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        holder.countryName.setText(countries.get(position).getCountryName());
        holder.capitalName.setText(countries.get(position).getCapital());
        Glide.with(holder.itemView.getContext()).
                load(countries.get(position).getFlag()).into(holder.countryImage);
        //click en cada item del recyclerView
        holder.itemView.getRootView().setOnClickListener(v -> onClickItemInterface.onClickItem(countries.get(position)));

    }

    //sexto, lista.size():
    @Override
    public int getItemCount() {
        return countries.size();
    }

    public void setCountries(List<Country> countries){
        this.countries = countries;
        notifyDataSetChanged();
    }

    //primero, viewHolder:
    static class CountryViewHolder extends RecyclerView.ViewHolder {

        //octavo, traer los elementos del itemView:
        //parecido al viewBinding:
        @BindView(R.id.imageView)
        ImageView countryImage;

        @BindView(R.id.countryName)
        TextView countryName;

        @BindView(R.id.capitalName)
        TextView capitalName;

        //itemView individual(hace parte del primer paso)
        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);//esto pasa todas las variables del itemView al adapter
        }

    }
}
