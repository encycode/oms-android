package com.encycode.sheetalfoods.helper.request;

import com.encycode.sheetalfoods.entity.Orders;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetailsEditRequest {

    @SerializedName("orders")
    @Expose
    private OrdersEdit orders;
    @SerializedName("message")
    @Expose
    private String message;

    public OrdersEdit getOrders() {
        return orders;
    }

    public void setOrders(OrdersEdit orders) {
        this.orders = orders;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
