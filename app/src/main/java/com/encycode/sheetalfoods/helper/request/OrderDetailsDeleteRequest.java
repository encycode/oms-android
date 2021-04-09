package com.encycode.sheetalfoods.helper.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetailsDeleteRequest {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("order-detail")
    @Expose
    private OrderDetailsDelete orderDetail;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OrderDetailsDelete getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetailsDelete orderDetail) {
        this.orderDetail = orderDetail;
    }
}
