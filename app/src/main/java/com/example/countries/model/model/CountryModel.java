package com.example.countries.model.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

//implements Serializable permite que este objeto sea enviado entre activities.
public class CountryModel implements Serializable {
    //si no se les pone public al comienzo, no seran visibles desde afuera
    //(por defecto son private?????)
    //esa es la razon por la que se hacen setters y getters

    //@SerializedName es de retrofit para relacionar el Json con este objeto
    @SerializedName("name") //name es el nombre que aparece en el json que viene de la web
    String countryName;

    @SerializedName("capital")//capital es el nombre que aparece en el json que viene de la web.
    String capital;

    @SerializedName("flagPNG")//flagPNG es el nombre que aparece en el json que viene de la web.
    String flag;

    //constructor
    public CountryModel(String countryName, String capital, String flag) {
        this.countryName = countryName;
        this.capital = capital;
        this.flag = flag;
    }

    //getters
    public String getCountryName() {
        return countryName;
    }

    public String getCapital() {
        return capital;
    }

    public String getFlag() {
        return flag;
    }

}
