package com.example.saharashop.entity;

public class Test {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }
    public Test(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
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




}
