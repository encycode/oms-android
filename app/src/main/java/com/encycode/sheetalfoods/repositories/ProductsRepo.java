package com.encycode.sheetalfoods.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.encycode.sheetalfoods.OMSDatabase;
import com.encycode.sheetalfoods.dao.ProductsDao;
import com.encycode.sheetalfoods.entity.Products;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ProductsRepo {
    private ProductsDao dao;
    private LiveData<List<Products>> allProducts;

    public ProductsRepo(Application application) {
        OMSDatabase database = OMSDatabase.getInstance(application);
        dao = database.productsDao();
        allProducts = dao.getAllProducts();
    }

    public LiveData<List<Products>> getAllProducts() {
        return allProducts;
    }

    public void insert(Products products) {
        new InsertAsyncTask(dao).execute(products);
    }

    public LiveData<List<Products>> getAllProductsForSpecificProductType(int productID) {
        LiveData<List<Products>> products = null;
        try {
            products = new GetAllProductsForSpecificProductTypeASYNCTask(dao).execute(productID).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return products;
    }

    public LiveData<List<Products>> getSpecificProduct(int id) {
        LiveData<List<Products>> products = null;
        try {
            products = new GetSpecificProductASYNCTask(dao).execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return products;
    }

    public class InsertAsyncTask extends AsyncTask<Products, Void, Void> {

        ProductsDao dao;

        public InsertAsyncTask(ProductsDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Products... products) {
            dao.insert(products[0]);
            return null;
        }
    }

    public class GetAllProductsForSpecificProductTypeASYNCTask extends AsyncTask<Integer, Void, LiveData<List<Products>>> {

        ProductsDao dao;

        public GetAllProductsForSpecificProductTypeASYNCTask(ProductsDao dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<List<Products>> doInBackground(Integer... integers) {
            return dao.getAllProductsForSpecificProductType(integers[0]);
        }
    }

    public class GetSpecificProductASYNCTask extends AsyncTask<Integer, Void, LiveData<List<Products>>> {

        ProductsDao dao;

        public GetSpecificProductASYNCTask(ProductsDao dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<List<Products>> doInBackground(Integer... integers) {
            return dao.getSpecificProduct(integers[0]);
        }
    }
}