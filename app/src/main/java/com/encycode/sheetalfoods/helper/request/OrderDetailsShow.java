package com.encycode.sheetalfoods.helper.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetailsShow {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("order_id")
    @Expose
    private Long orderId;
    @SerializedName("product_id")
    @Expose
    private Long productId;
    @SerializedName("carat_order")
    @Expose
    private Long caratOrder;
    @SerializedName("carat_price")
    @Expose
    private Long caratPrice;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCaratOrder() {
        return caratOrder;
    }

    public void setCaratOrder(Long caratOrder) {
        this.caratOrder = caratOrder;
    }

    public Long getCaratPrice() {
        return caratPrice;
    }

    public void setCaratPrice(Long caratPrice) {
        this.caratPrice = caratPrice;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

}
