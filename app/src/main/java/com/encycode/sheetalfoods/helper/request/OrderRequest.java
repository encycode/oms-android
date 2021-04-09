package com.encycode.sheetalfoods.helper.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderRequest {

    @SerializedName("orders")
    @Expose
    private List<Order> orders = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
