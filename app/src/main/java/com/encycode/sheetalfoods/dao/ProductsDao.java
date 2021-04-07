package com.encycode.sheetalfoods.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.encycode.sheetalfoods.entity.Products;

import java.util.List;

public interface ProductsDao {

    @Query("SELECT * FROM products")
    LiveData<List<Products>> getAllProducts();
}
