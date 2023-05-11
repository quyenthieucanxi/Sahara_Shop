package com.example.saharashop.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Promo {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("expirationDate")
    @Expose
    private String expirationDate;
    @SerializedName("state")
    @Expose
    private Boolean status;

    public Promo(String id, String productId, String type, String expirationDate, Boolean status) {
        this.id = id;
        this.productId = productId;
        this.type = type;
        this.expirationDate = expirationDate;
        this.status = status;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
