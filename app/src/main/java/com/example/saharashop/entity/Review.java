package com.example.saharashop.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("time")
    @Expose
    private String time;

    public Review(String id, String userId, String fullname, String productId, String content, String time) {
        this.id = id;
        this.userId = userId;
        this.fullname = fullname;
        this.productId = productId;
        this.content = content;
        this.time = time;
    }
    public Review(String userId, String productId, String content) {
        this.userId = userId;
        this.productId = productId;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
