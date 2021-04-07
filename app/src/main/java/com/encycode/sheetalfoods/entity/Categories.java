package com.encycode.sheetalfoods.entity;

import androidx.core.content.pm.PermissionInfoCompat;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//created_at  updated_at deleted_at
@Entity(tableName = "categories")
public class Categories {

    @PrimaryKey
    private int id;

    private String name;

    private String image;

    private boolean status;

    @ColumnInfo(name = "created_at")
    private String createdAt;

    @ColumnInfo(name = "updated_at")
    private String updatedAt;

    @ColumnInfo(name = "deleted_at")
    private String deletedAt;

    public Categories(String name, String image, boolean status, String createdAt, String updatedAt, String deletedAt) {
        this.name = name;
        this.image = image;
        this.status = status;
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

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public boolean isStatus() {
        return status;
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
