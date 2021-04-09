package com.encycode.sheetalfoods.helper.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryRequest {
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("product_types")
    @Expose
    private List<ProductType> productTypes = null;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("users")
    @Expose
    private List<UsersResponse> users = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Category> getCategories() {
        return categories;
    }

    public List<UsersResponse> getUsers() {
        return users;
    }

    public void setUsers(List<UsersResponse> users) {
        this.users = users;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<ProductType> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(List<ProductType> productTypes) {
        this.productTypes = productTypes;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
