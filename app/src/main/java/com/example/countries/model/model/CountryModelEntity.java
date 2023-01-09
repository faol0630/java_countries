package com.example.countries.model.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "countryModelEntity")
public class CountryModelEntity {

    @PrimaryKey
    @NotNull
    @SerializedName("name")
    @ColumnInfo(name = "countryName")
    public final String countryName;

    @SerializedName("capital")
    @ColumnInfo(name = "capital")
    public final String capital;

    @SerializedName("flagPNG")
    @ColumnInfo(name = "flag")
    public final String flag;

    @SerializedName("region")
    @ColumnInfo(name = "region")
    public String region;

    @SerializedName("subregion")
    @ColumnInfo(name = "subregion")
    public String subregion;

    @SerializedName("demonym")
    @ColumnInfo(name = "demonym")
    public String demonym;

    @SerializedName("numericCode")
    @ColumnInfo(name = "numericCode")
    public String numericCode;

    public CountryModelEntity(@NonNull String countryName, String capital, String flag, String region, String subregion, String demonym, String numericCode) {
        this.countryName = countryName;
        this.capital = capital;
        this.flag = flag;
        this.region = region;
        this.subregion = subregion;
        this.demonym = demonym;
        this.numericCode = numericCode;
    }

    @NonNull
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
