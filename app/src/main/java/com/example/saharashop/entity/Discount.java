package com.example.saharashop.entity;

public class Discount {
    private Integer id;
    private Integer productId;
    private String expirationDate;
    private Float value;
    private String status;

    public Discount(Integer id, Integer productId, String expirationDate, Float value, String status) {
        this.id = id;
        this.productId = productId;
        this.value = value;
        this.expirationDate = expirationDate;
        this.status = status;
    }

    public Discount(Integer id, Integer productId, String expirationDate) {
        this(id, productId, expirationDate, null, null);
    }

    public Discount(Integer productId, String expirationDate) {
        this(-1, productId, expirationDate, null, null);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
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
