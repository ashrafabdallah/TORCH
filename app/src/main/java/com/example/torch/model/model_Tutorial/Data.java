
package com.example.torch.model.model_Tutorial;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("rates")
    @Expose
    private Integer rates;
    @SerializedName("is_fav")
    @Expose
    private Integer isFav;
    @SerializedName("tutorials")
    @Expose
    private List<Tutorial> tutorials = null;
    private final static long serialVersionUID = -4754282645000906992L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getRates() {
        return rates;
    }

    public void setRates(Integer rates) {
        this.rates = rates;
    }

    public Integer getIsFav() {
        return isFav;
    }

    public void setIsFav(Integer isFav) {
        this.isFav = isFav;
    }

    public List<Tutorial> getTutorials() {
        return tutorials;
    }

    public void setTutorials(List<Tutorial> tutorials) {
        this.tutorials = tutorials;
    }

}
