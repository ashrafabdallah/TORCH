package com.example.torch.model;

public class Ask_Reset {
    private String mobile,code,type;

    public Ask_Reset(String mobile, String code, String type) {
        this.mobile = mobile;
        this.code = code;
        this.type = type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
