package com.encycode.sheetalfoods.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.encycode.sheetalfoods.dao.OrderDetailsDao;
import com.encycode.sheetalfoods.dao.OrdersDao;
import com.encycode.sheetalfoods.databases.OrdersDatabase;
import com.encycode.sheetalfoods.entity.Orders;

import java.util.List;

public class OrdersRepo {

    private OrdersDao dao;
    private LiveData<List<Orders>> allOrders;

    public OrdersRepo(Application application) {
        OrdersDatabase database = OrdersDatabase.getInstance(application);
        dao = database.ordersDao();
        allOrders = dao.getAllOrders();
    }

    public void insert(Orders orders) {
        new InsertAsyncTask(dao).execute(orders);
    }

    public void delete(Orders orders) {
        new DeleteAsyncTask(dao).execute(orders);
    }

    public LiveData<List<Orders>> getAllOrders() {
        return allOrders;
    }

    public static class InsertAsyncTask extends AsyncTask<Orders,Void,Void> {

        private OrdersDao dao;

        public InsertAsyncTask(OrdersDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Orders... orders) {
            dao.insert(orders[0]);
            return null;
        }
    }

    public static class DeleteAsyncTask extends AsyncTask<Orders,Void,Void> {

        private OrdersDao dao;

        public DeleteAsyncTask(OrdersDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Orders... orders) {
            dao.insert(orders[0]);
            return null;
        }
    }
}