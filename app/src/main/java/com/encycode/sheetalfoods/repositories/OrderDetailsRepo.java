package com.encycode.sheetalfoods.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.encycode.sheetalfoods.OMSDatabase;
import com.encycode.sheetalfoods.dao.OrderDetailsDao;
import com.encycode.sheetalfoods.entity.OrderDetails;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class OrderDetailsRepo {
    private OrderDetailsDao dao;
    private LiveData<List<OrderDetails>> allOrdersDetails;

    public OrderDetailsRepo(Application application) {
        OMSDatabase database = OMSDatabase.getInstance(application);
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

    public LiveData<List<OrderDetails>> getSpecificOrderDetails(int orderID) {
        LiveData<List<OrderDetails>> orderDetails = null;
        try {
            orderDetails = new GetSpecificOrderDetailsAsyncTask(dao).execute(orderID).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return orderDetails;
    }

    public static class InsertAsyncTask extends AsyncTask<OrderDetails, Void, Void> {

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

    public static class GetSpecificOrderDetailsAsyncTask extends AsyncTask<Integer, Void, LiveData<List<OrderDetails>>> {

        private OrderDetailsDao dao;

        public GetSpecificOrderDetailsAsyncTask(OrderDetailsDao dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<List<OrderDetails>> doInBackground(Integer... integers) {
            return dao.getSpecificOrderDetails(integers[0]);
        }
    }


    public static class DeleteAsyncTask extends AsyncTask<OrderDetails, Void, Void> {

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
