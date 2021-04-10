package com.encycode.sheetalfoods.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class Users {

    @PrimaryKey
    int id;

    String name;

    @ColumnInfo(name = "shop_name")
    String shopName;

    String address;

    String mobile;

    String role;

    public Users(int id, String name, String shopName, String address, String mobile, String role) {
        this.id = id;
        this.name = name;
        this.shopName = shopName;
        this.address = address;
        this.mobile = mobile;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public String getRole() {
        return role;
    }
}
