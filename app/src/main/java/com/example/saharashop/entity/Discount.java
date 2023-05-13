package com.example.saharashop.entity;

public class Discount {
    private String id;
    private String productId;
    private String expirationDate;
    private Float value;
    private String status;

    public Discount(String id, String productId, String expirationDate, Float value, String status) {
        this.id = id;
        this.productId = productId;
        this.value = value;
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

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
