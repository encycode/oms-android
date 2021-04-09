package com.encycode.sheetalfoods.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.encycode.sheetalfoods.entity.ProductTypes;

import java.util.List;
@Dao
public interface ProductTypesDao {

    @Query("SELECT * FROM product_types")
    LiveData<List<ProductTypes>> getAllProductTypes();

    @Query("SELECT * FROM product_types WHERE category_id = :categoryID")
    LiveData<List<ProductTypes>> getAllProductTypesForSpecificCategory(int categoryID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProductTypes types);
}
