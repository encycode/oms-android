package com.encycode.sheetalfoods.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.encycode.sheetalfoods.entity.Orders;
import com.encycode.sheetalfoods.helper.request.Order;
import com.encycode.sheetalfoods.repositories.OrdersRepo;

import java.util.List;

public class OrdersViewModel extends AndroidViewModel {

    private OrdersRepo repo;
    private LiveData<List<Orders>> allOrders;

    public OrdersViewModel(@NonNull Application application) {
        super(application);
        repo = new OrdersRepo(application);
        allOrders = repo.getAllOrders();
    }

    public void insert(Orders orders) {
        repo.insert(orders);
    }

    public void delete(Orders orders) {
        repo.delete(orders);
    }

    public void update(Orders orders) { repo.update(orders);}

    public LiveData<List<Orders>> getAllOrders() {
        return allOrders;
    }

    public Orders getSpecificOrder(int orderID) {
        return repo.getSpecificOrder(orderID);
    }
}
