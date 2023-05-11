package com.example.saharashop.entity;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Product {
    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("storeId")
    @Expose
    private String storeId;

    @SerializedName("typeId")
    @Expose
    private String typeId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("price")
    @Expose
    private long price;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("defaultImage")
    @Expose
    private int defaultImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(int defaultImage) {
        this.defaultImage = defaultImage;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    @SerializedName("detail")
    @Expose
    private String detail;

    @SerializedName("star")
    @Expose
    private int star;

    @SerializedName("state")
    @Expose
    private Boolean state;
    public  Product(){

    }
    public Product(String id,String storeId, String typeId, String name, long price, String image, int defaultImage, String detail, int star, Boolean state) {
        this.id = id;
        this.storeId = storeId;
        this.typeId = typeId;
        this.name = name;
        this.price = price;
        this.image = image;
        this.defaultImage = defaultImage;
        this.detail = detail;
        this.star = star;
        this.state = state;
    }

}
