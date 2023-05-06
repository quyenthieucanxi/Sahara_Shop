package com.example.saharashop.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Account1 {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("roleID")
    @Expose
    private String roleID;
    @SerializedName("state")
    @Expose
    private Boolean state;

    public Account1(String id ,String username, String email, String password, String roleID, Boolean state) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roleID = roleID;
        this.state = state;
    }
    public Account1(String username, String email, String password, String roleID, Boolean state) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roleID = roleID;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
