package com.example.countries.model.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "countryModelEntity")
public class CountryModelEntity {

    //@SerializedName es de retrofit para relacionar el Json con este objeto.
    @PrimaryKey
    @NotNull
    @SerializedName("name")//name es el nombre que aparece en el json que viene de la web.
    @ColumnInfo(name = "countryName")
    public final String countryName;

    @SerializedName("capital")//capital es el nombre que aparece en el json que viene de la web.
    @ColumnInfo(name = "capital")
    public final String capital;

    @SerializedName("flagPNG")//flagPNG es el nombre que aparece en el json que viene de la web.
    @ColumnInfo(name = "flag")
    public final String flag;

    //constructor
    public CountryModelEntity(String countryName, String capital, String flag) {
        this.countryName = countryName;
        this.capital = capital;
        this.flag = flag;
    }

    //getters and setters
    public String getCountryName() {
        return countryName;
    }

    public String getCapital() {
        return capital;
    }

    public String getFlag() {
        return flag;
    }

    //al ser final no tiene logica usar set:

//    public void setCountryName(String countryName) {
//        this.countryName = countryName;
//    }

//    public void setCapital(String capital) {
//        this.capital = capital;
//    }

//    public void setFlag(String flag) {
//        this.flag = flag;
//    }
}
