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

    @SerializedName("region")
    String region;

    @SerializedName("subregion")
    String subregion;

    @SerializedName("demonym")
    String demonym;

    @SerializedName("numericCode")
    String numericCode;

    //constructor
    public CountryModel(String countryName, String capital, String flag, String region, String subregion, String demonym, String numericCode) {
        this.countryName = countryName;
        this.capital = capital;
        this.flag = flag;
        this.region = region;
        this.subregion = subregion;
        this.demonym = demonym;
        this.numericCode = numericCode;
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

    public String getRegion() {
        return region;
    }

    public String getSubregion() {
        return subregion;
    }

    public String getDemonym() {
        return demonym;
    }

    public String getNumericCode() {
        return numericCode;
    }
}
