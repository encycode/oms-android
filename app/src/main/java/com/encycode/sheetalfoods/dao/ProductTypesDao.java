package com.encycode.sheetalfoods.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.encycode.sheetalfoods.entity.ProductTypes;

import java.util.List;

public interface ProductTypesDao {

    @Query("SELECT * FROM product_types")
    LiveData<List<ProductTypes>> getAllProductTypes();
}
