package com.encycode.sheetalfoods.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "order_details")
public class OrderDetails {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "order_id")
    private int orderID;

    @ColumnInfo(name = "caret_order")
    private int caretOrder;

    @ColumnInfo(name = "caret_price")
    private int caretPrice;

    @ColumnInfo(name = "product_id")
    private int productId;

    public int getProductId() {
        return productId;
    }

    public OrderDetails(int id, int orderID, int caretOrder, int caretPrice, int productId) {
        this.id = id;
        this.orderID = orderID;
        this.caretOrder = caretOrder;
        this.caretPrice = caretPrice;
        this.productId = productId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getCaretOrder() {
        return caretOrder;
    }

    public int getCaretPrice() {
        return caretPrice;
    }
}
