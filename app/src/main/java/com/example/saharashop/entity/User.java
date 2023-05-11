package com.example.saharashop.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("accountId")
    @Expose
    private String accountId;

    @SerializedName("fullname")
    @Expose
    private String fullname;

    @SerializedName("sex")
    @Expose
    private String sex;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    @SerializedName("state")
    @Expose
    private Boolean state;

    public User(String accountId, String fullname, String sex, String phone, String address, String avatar, Boolean state) {
        this.accountId = accountId;
        this.fullname = fullname;
        this.sex = sex;
        this.phone = phone;
        this.address = address;
        this.avatar = avatar;
        this.state = state;
    }
    public User(String id, String accountId, String fullname, String sex, String phone, String address, String avatar, Boolean state) {
        this.id = id;
        this.accountId = accountId;
        this.fullname = fullname;
        this.sex = sex;
        this.phone = phone;
        this.address = address;
        this.avatar = avatar;
        this.state = state;
    }
    public User(String fullname, String phone, String address, String avatar) {
        this.fullname = fullname;
        this.phone = phone;
        this.address = address;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
