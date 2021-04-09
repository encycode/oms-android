package com.encycode.sheetalfoods.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.encycode.sheetalfoods.entity.Categories;

import java.util.List;

@Dao
public interface CategoriesDao {

    @Query("SELECT * FROM categories")
    LiveData<List<Categories>> getAllCategories();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Categories categories);

}
