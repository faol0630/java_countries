package com.example.countries.model.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CountryModel implements Serializable {

    @SerializedName("name")
    String countryName;

    @SerializedName("capital")
    String capital;

    @SerializedName("flagPNG")
    String flag;

    @SerializedName("region")
    String region;

    @SerializedName("subregion")
    String subregion;

    @SerializedName("demonym")
    String demonym;

    @SerializedName("numericCode")
    String numericCode;

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

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public void setDemonym(String demonym) {
        this.demonym = demonym;
    }

    public void setNumericCode(String numericCode) {
        this.numericCode = numericCode;
    }
}
