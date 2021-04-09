package com.encycode.sheetalfoods.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.encycode.sheetalfoods.entity.OrderDetails;
import com.encycode.sheetalfoods.entity.Orders;

import java.util.List;

@Dao
public interface OrderDetailsDao {

    @Insert
    void insert(OrderDetails orderDetails);

    @Delete
    void delete(OrderDetails orderDetails);

    @Query("SELECT * FROM order_details")
    LiveData<List<OrderDetails>> getAllOrderDetails();

    @Query("SELECT * FROM order_details WHERE order_id = :orderID")
    OrderDetails getSpecificOrderDetails(int orderID);
}
