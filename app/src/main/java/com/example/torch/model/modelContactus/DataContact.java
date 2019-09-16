package com.example.torch.model.modelContactus;

public class DataContact {
    private String name,email,mobile,message;

    public DataContact(String name, String email, String mobile, String message) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
