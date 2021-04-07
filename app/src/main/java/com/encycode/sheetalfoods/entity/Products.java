package com.encycode.sheetalfoods.entity;

import androidx.core.content.PermissionChecker;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class Products {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "product_type_id")
    private int productTypeID;

    private String name;

    private String image;

    private boolean status;

    @ColumnInfo(name = "caret_item")
    private int caretItem;

    @ColumnInfo(name = "caret_price")
    private int caretPrice;

    @ColumnInfo(name = "created_at")
    private String createdAt;

    @ColumnInfo(name = "updated_at")
    private String updatedAt;

    @ColumnInfo(name = "deleted_at")
    private String deletedAt;

    public Products(int productTypeID, String name, String image, boolean status, int caretItem, int caretPrice, String createdAt, String updatedAt, String deletedAt) {
        this.productTypeID = productTypeID;
        this.name = name;
        this.image = image;
        this.status = status;
        this.caretItem = caretItem;
        this.caretPrice = caretPrice;
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

    public int getProductTypeID() {
        return productTypeID;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public boolean isStatus() {
        return status;
    }

    public int getCaretItem() {
        return caretItem;
    }

    public int getCaretPrice() {
        return caretPrice;
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
