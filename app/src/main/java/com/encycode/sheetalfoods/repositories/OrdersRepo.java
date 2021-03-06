package com.encycode.sheetalfoods.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.encycode.sheetalfoods.OMSDatabase;
import com.encycode.sheetalfoods.dao.OrdersDao;
import com.encycode.sheetalfoods.entity.Orders;
import com.encycode.sheetalfoods.helper.request.Order;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class OrdersRepo {

    private OrdersDao dao;
    private LiveData<List<Orders>> allOrders;

    public OrdersRepo(Application application) {
        OMSDatabase database = OMSDatabase.getInstance(application);
        dao = database.ordersDao();
        allOrders = dao.getAllOrders();
    }

    public void insert(Orders orders) {
        new InsertAsyncTask(dao).execute(orders);
    }

    public void delete(Orders orders) {
        new DeleteAsyncTask(dao).execute(orders);
    }

    public void update(Orders orders) { new UpdateAsyncTask(dao).execute(orders); }

    public LiveData<List<Orders>> getAllOrders() {
        return allOrders;
    }

    public Orders getSpecificOrder(int orderID) {
        return dao.getSpecificOrder(orderID);
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

    public static class UpdateAsyncTask extends AsyncTask<Orders,Void,Void> {

        private OrdersDao dao;

        public UpdateAsyncTask(OrdersDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Orders... orders) {
            dao.update(orders[0]);
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
            dao.delete(orders[0]);
            return null;
        }
    }

    public static class GetSpecificOrder extends AsyncTask<Integer,Void,Orders> {

        private OrdersDao dao;

        public GetSpecificOrder(OrdersDao dao) {
            this.dao = dao;
        }


        @Override
        protected Orders doInBackground(Integer... integers) {
            return dao.getSpecificOrder(integers[0]);
        }
    }
}
