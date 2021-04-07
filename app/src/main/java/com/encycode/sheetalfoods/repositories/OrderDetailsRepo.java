package com.encycode.sheetalfoods.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.encycode.sheetalfoods.dao.OrderDetailsDao;
import com.encycode.sheetalfoods.dao.OrdersDao;
import com.encycode.sheetalfoods.databases.OrderDetailsDatabase;
import com.encycode.sheetalfoods.databases.OrdersDatabase;
import com.encycode.sheetalfoods.entity.OrderDetails;
import com.encycode.sheetalfoods.entity.Orders;

import java.util.List;

public class OrderDetailsRepo {
    private OrderDetailsDao dao;
    private LiveData<List<OrderDetails>> allOrdersDetails;

    public OrderDetailsRepo(Application application) {
        OrderDetailsDatabase database = OrderDetailsDatabase.getInstance(application);
        dao = database.orderDetailsDao();
        allOrdersDetails = dao.getAllOrderDetails();
    }

    public void insert(OrderDetails details) {
        new InsertAsyncTask(dao).execute(details);
    }

    public void delete(OrderDetails details) {
        new DeleteAsyncTask(dao).execute(details);
    }

    public LiveData<List<OrderDetails>> getAllOrderDetails() {
        return allOrdersDetails;
    }

    public static class InsertAsyncTask extends AsyncTask<OrderDetails,Void,Void> {

        private OrderDetailsDao dao;

        public InsertAsyncTask(OrderDetailsDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(OrderDetails... orderDetails) {
            dao.insert(orderDetails[0]);
            return null;
        }
    }

    public static class DeleteAsyncTask extends AsyncTask<OrderDetails,Void,Void> {

        private OrderDetailsDao dao;

        public DeleteAsyncTask(OrderDetailsDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(OrderDetails... orderDetails) {
            dao.delete(orderDetails[0]);
            return null;
        }
    }
}
