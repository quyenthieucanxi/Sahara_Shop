package com.example.saharashop.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart {
    public static final String CART_UNPAID = "Unpaid";
    public static final String CART_PAID = "Paid";
    public static final String CART_ORDERED = "Ordered";
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("state")
    @Expose
    private String status;
    public Cart()
    {

    }

    public Cart(String id, String userId, String productId, Integer quantity, String status) {
        this.setId(id);
        this.setUserId(userId);
        this.setProductId(productId);
        this.setQuantity(quantity);
        this.setStatus(status);
    }
    public Cart(String userId,String productId,Integer quantity)
    {
        this.userId= userId;
        this.productId= productId;
        this.quantity= quantity;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        if (quantity >= 0)
            this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }





    public void setCartOrdered() {
        this.setStatus(CART_ORDERED);
    }

    public void setCartPaid() {
        this.setStatus(CART_PAID);
    }
}
