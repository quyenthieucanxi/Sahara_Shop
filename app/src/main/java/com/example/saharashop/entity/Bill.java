package com.example.saharashop.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Bill {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("cartId")
    @Expose
    private String cartId;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("address")
    @Expose
    private String address;


    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("date")
    @Expose
    private Date date;

    @SerializedName("quantity")
    @Expose
    private int quantity;

    @SerializedName("price")
    @Expose
    private long price;

    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String state) {
        this.status = status;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productId) {
        this.productName = productName;
    }


    public Bill(String id, String userId, String cartId, String phone, String address, String productName, Date date, int quantity, long price, String status) {
        this.id = id;
        this.userId = userId;
        this.cartId = cartId;
        this.phone = phone;
        this.address = address;
        this.productName = productName;
        this.date = date;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    public Bill(String userId, String cartId, String phone, String address, String productName, int quantity, long price) {
        this.userId = userId;
        this.cartId = cartId;
        this.phone = phone;
        this.address = address;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
}
