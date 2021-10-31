package com.pubmanager.pubmanager.models;

public class UserCategory {

    String categoryId;
    double totalTransactionAmount;


    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public double getTotalTransactionAmount() {
        return totalTransactionAmount;
    }

    public void setTotalTransactionAmount(double totalTransactionAmount) {
        this.totalTransactionAmount = totalTransactionAmount;
    }
}
