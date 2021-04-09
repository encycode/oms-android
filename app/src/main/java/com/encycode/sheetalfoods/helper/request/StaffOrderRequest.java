package com.encycode.sheetalfoods.helper.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StaffOrderRequest {

    @SerializedName("orders")
    @Expose
    private StaffOrder orders = null;
    @SerializedName("message")
    @Expose
    private String message;

    public StaffOrder getOrders() {
        return orders;
    }

    public void setOrders(StaffOrder orders) {
        this.orders = orders;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
