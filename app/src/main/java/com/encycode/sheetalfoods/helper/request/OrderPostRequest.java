package com.encycode.sheetalfoods.helper.request;

import com.encycode.sheetalfoods.helper.request.Order;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderPostRequest {

    @SerializedName("orders")
    @Expose
    private Order orders;
    @SerializedName("message")
    @Expose
    private String message;

    public Order getOrders() {
        return orders;
    }

    public void setOrders(Order orders) {
        this.orders = orders;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
