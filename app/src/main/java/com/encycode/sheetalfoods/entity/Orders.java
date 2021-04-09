package com.encycode.sheetalfoods.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders")
public class Orders {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "sender_client_id")
    private int senderClientID;

    @ColumnInfo(name = "receiver_client_id")
    private int receiverClientID;

    @ColumnInfo(name = "shop_name")
    private String shopName;

    private String address;

    private String mobile;

    @ColumnInfo(name = "orderby")
    private String orderBy;

    @ColumnInfo(name = "category_id")
    private int categoryID;

    private String status;

    @ColumnInfo(name = "order_number")
    private String orderNumber;

    @ColumnInfo(name = "user_id")
    private int userID;

    @ColumnInfo(name = "created_at")
    private String createdAt;

    @ColumnInfo(name = "updated_at")
    private String updatedAt;

    @ColumnInfo(name = "deleted_at")
    private String deletedAt;



    public Orders(int id, int senderClientID, int receiverClientID, String shopName, String address, String mobile, String orderBy, int categoryID, String status, String orderNumber, int userID, String createdAt, String updatedAt, String deletedAt) {
        this.id = id;
        this.senderClientID = senderClientID;
        this.receiverClientID = receiverClientID;
        this.shopName = shopName;
        this.address = address;
        this.mobile = mobile;
        this.orderBy = orderBy;
        this.categoryID = categoryID;
        this.status = status;
        this.orderNumber = orderNumber;
        this.userID = userID;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getSenderClientID() {
        return senderClientID;
    }

    public int getReceiverClientID() {
        return receiverClientID;
    }

    public String getShopName() {
        return shopName;
    }

    public String getAddress() {
        return address;
    }

    public String getMobile() {
        return mobile;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getStatus() {
        return status;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public int getUserID() {
        return userID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

}
