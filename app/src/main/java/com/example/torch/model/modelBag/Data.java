
package com.example.torch.model.modelBag;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable
{

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    private final static long serialVersionUID = 9108840645474571326L;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
