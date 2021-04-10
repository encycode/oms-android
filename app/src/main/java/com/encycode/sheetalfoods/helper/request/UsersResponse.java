package com.encycode.sheetalfoods.helper.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersResponse {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("role")
    @Expose
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
