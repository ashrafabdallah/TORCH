package com.example.torch.model.modeladdAddress;

public class ModelAddAddress {
   private String frName,  lsName,  city, flat, region,  phone;
   private int is_default;

    public ModelAddAddress(String frName, String lsName, String city, String flat, String region, String phone, int is_default) {
        this.frName = frName;
        this.lsName = lsName;
        this.city = city;
        this.flat = flat;
        this.region = region;
        this.phone = phone;
        this.is_default = is_default;
    }

    public String getFrName() {
        return frName;
    }

    public void setFrName(String frName) {
        this.frName = frName;
    }

    public String getLsName() {
        return lsName;
    }

    public void setLsName(String lsName) {
        this.lsName = lsName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }
}
