package com.example.saharashop.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductType {
    @SerializedName("_id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("state")
    @Expose
    private Boolean state;

    public ProductType(String name, String image, Boolean state) {
        this.name = name;
        this.image = image;
        this.state = state;
    }
}
