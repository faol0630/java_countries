package com.example.countries.model.model;


import java.io.Serializable;

public class Country implements Serializable {

    private String countryName;

    private final String capital;

    private final String flag;

    private String region;

    private String subregion;

    private final String demonym;

    private final String numericCode;

    public Country(String countryName, String capital, String flag, String region, String subregion, String demonym, String numericCode) {
        this.countryName = countryName;
        this.capital = capital;
        this.flag = flag;
        this.region = region;
        this.subregion = subregion;
        this.demonym = demonym;
        this.numericCode = numericCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public String getDemonym() {
        return demonym;
    }

    public String getNumericCode() {
        return numericCode;
    }

}
