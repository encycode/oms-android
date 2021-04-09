package com.encycode.sheetalfoods.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.encycode.sheetalfoods.entity.Orders;

import java.util.List;

@Dao
public interface OrdersDao {

    @Insert
    void insert(Orders order);

    @Delete
    void delete(Orders orders);

    @Query("SELECT * FROM orders")
    LiveData<List<Orders>> getAllOrders();

    @Query("SELECT * FROM orders WHERE id = :orderID")
    Orders getSpecificOrder(int orderID);
}
