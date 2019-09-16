
package com.example.torch.model.homeModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("offers")
    @Expose
    private List<Offer> offers = null;
    @SerializedName("providers")
    @Expose
    private List<Provider> providers = null;
    @SerializedName("tutorial")
    @Expose
    private List<Tutorial> tutorial = null;

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }

    public List<Tutorial> getTutorial() {
        return tutorial;
    }

    public void setTutorial(List<Tutorial> tutorial) {
        this.tutorial = tutorial;
    }

}
