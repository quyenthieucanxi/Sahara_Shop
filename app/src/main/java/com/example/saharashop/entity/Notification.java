package com.example.saharashop.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {
    public Notification(String id, String userId, String message, Boolean state) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.state = state;
    }
    public Notification(String userId, String message, Boolean state) {
        this.userId = userId;
        this.message = message;
        this.state = state;
    }

    @SerializedName("_id")
    @Expose
    private String id;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("state")
    @Expose
    private Boolean state;
}
