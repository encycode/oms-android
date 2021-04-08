package com.encycode.sheetalfoods.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.encycode.sheetalfoods.entity.OrderDetails;
import com.encycode.sheetalfoods.repositories.OrderDetailsRepo;

import java.util.List;

public class OrderDetailsViewModel extends AndroidViewModel {
    private OrderDetailsRepo repo;
    private LiveData<List<OrderDetails>> allOrderDetails;

    public OrderDetailsViewModel(@NonNull Application application) {
        super(application);
        repo = new OrderDetailsRepo(application);
        allOrderDetails = repo.getAllOrderDetails();
    }

    public void insert(OrderDetails orders) {
        repo.insert(orders);
    }

    public void delete(OrderDetails orders) {
        repo.delete(orders);
    }

    public OrderDetails getSpecificOrderDetails(int orderID) {
        return repo.getSpecificOrderDetails(orderID);
    }

    public LiveData<List<OrderDetails>> getAllOrderDetails() {
        return allOrderDetails;
    }
}
