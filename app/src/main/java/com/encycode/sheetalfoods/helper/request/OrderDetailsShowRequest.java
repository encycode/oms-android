package com.encycode.sheetalfoods.helper.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsShowRequest {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("order-detail")
    @Expose
    private List<OrderDetailsShow> orderDetail = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<OrderDetailsShow> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetailsShow> orderDetail) {
        this.orderDetail = orderDetail;
    }
}
