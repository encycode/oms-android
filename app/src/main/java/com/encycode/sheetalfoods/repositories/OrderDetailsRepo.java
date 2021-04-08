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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

    public OrderDetails getSpecificOrderDetails(int orderID){
        OrderDetails orderDetails = null;
        try {
            orderDetails = new GetSpecificOrderDetailsAsyncTask(dao).execute(orderID).get(20, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return orderDetails;
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

    public static class GetSpecificOrderDetailsAsyncTask extends AsyncTask<Integer,Void,OrderDetails> {

        private OrderDetailsDao dao;

        public GetSpecificOrderDetailsAsyncTask(OrderDetailsDao dao) {
            this.dao = dao;
        }

        @Override
        protected OrderDetails doInBackground(Integer... integers) {
            OrderDetails orderDetails = dao.getSpecificOrderDetails(integers[0]);
            return orderDetails;
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
