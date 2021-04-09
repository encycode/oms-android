package com.encycode.sheetalfoods.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.encycode.sheetalfoods.entity.ProductTypes;
import com.encycode.sheetalfoods.entity.Products;

import java.util.List;
@Dao
public interface ProductsDao {

    @Query("SELECT * FROM products")
    LiveData<List<Products>> getAllProducts();

    @Query("SELECT * FROM products WHERE product_type_id = :productTypeID")
    LiveData<List<Products>> getAllProductsForSpecificProductType(int productTypeID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Products products);
}
