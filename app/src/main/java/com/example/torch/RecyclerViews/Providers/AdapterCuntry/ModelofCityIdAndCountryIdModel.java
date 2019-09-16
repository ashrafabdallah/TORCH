package com.example.torch.RecyclerViews.Providers.AdapterCuntry;

import java.io.Serializable;

public class ModelofCityIdAndCountryIdModel implements Serializable {
    private String CityId;
    private int countryId;

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public ModelofCityIdAndCountryIdModel() {
    }
}
