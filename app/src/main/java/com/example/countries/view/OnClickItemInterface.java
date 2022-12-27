package com.example.countries.view;

import com.example.countries.model.model.CountryModel;

public interface OnClickItemInterface {

    //void onClickItem(CountryModel countryModel, boolean isEdit);
    void onClickItem(CountryModel countryModel, int position);
}

//esto en kotlin va dentro del adapter.
