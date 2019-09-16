package com.example.torch.model.profile;

import okhttp3.MultipartBody;

public class Profile {
    private String full_name, mobile, email, password;
    private MultipartBody.Part image;

    public Profile(String full_name, String mobile, String email, String password, MultipartBody.Part image) {
        this.full_name = full_name;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    public MultipartBody.Part getImage() {
        return image;
    }

    public void setImage(MultipartBody.Part image) {
        this.image = image;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
