package com.encycode.sheetalfoods.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.encycode.sheetalfoods.dao.ProductTypesDao;
import com.encycode.sheetalfoods.dao.ProductsDao;
import com.encycode.sheetalfoods.databases.ProductTypesDatabase;
import com.encycode.sheetalfoods.databases.ProductsDatabase;
import com.encycode.sheetalfoods.entity.ProductTypes;
import com.encycode.sheetalfoods.entity.Products;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ProductsRepo {
    private ProductsDao dao;
    private LiveData<List<Products>> allProducts;

    public ProductsRepo(Application application) {
        ProductsDatabase database = ProductsDatabase.getInstance(application);
        dao = database.productsDao();
        allProducts = dao.getAllProducts();
    }

    public LiveData<List<Products>> getAllProducts() {
        return allProducts;
    }

     public LiveData<List<Products>> getAllProductsForSpecificProductType(int productID) {
        LiveData<List<Products>> products = null;
        try {
            products = new GetAllProductsForSpecificProductTypeASYNCTask(dao).execute(productID).get(20, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return products;
    }

    public class GetAllProductsForSpecificProductTypeASYNCTask extends AsyncTask<Integer,Void,LiveData<List<Products>>> {

        ProductsDao dao;

        public GetAllProductsForSpecificProductTypeASYNCTask(ProductsDao dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<List<Products>> doInBackground(Integer... integers) {
            return dao.getAllProductsForSpecificProductType(integers[0]);
        }
    }
}