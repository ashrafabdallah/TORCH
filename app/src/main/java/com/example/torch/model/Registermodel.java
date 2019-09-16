package com.example.torch.model;

public class Registermodel {
    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Registermodel(String full_name, String mobile, String email, String gender, String password, String address, String fcm_token, String type) {
        this.full_name = full_name;
        this.mobile = mobile;
        this.email = email;
        this.gender = gender;
        this.password = password;
        this.address = address;
        this.fcm_token = fcm_token;
        this.type = type;
    }

    private String full_name,mobile,email,gender,password,address,fcm_token,type;

}