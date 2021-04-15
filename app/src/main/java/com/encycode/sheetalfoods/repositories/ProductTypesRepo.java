package com.encycode.sheetalfoods.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.encycode.sheetalfoods.OMSDatabase;
import com.encycode.sheetalfoods.dao.ProductTypesDao;
import com.encycode.sheetalfoods.entity.ProductTypes;
import com.encycode.sheetalfoods.helper.request.ProductType;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ProductTypesRepo {

    private ProductTypesDao dao;
    private LiveData<List<ProductTypes>> allProductTypes;

    public ProductTypesRepo(Application application) {
        OMSDatabase database = OMSDatabase.getInstance(application);
        dao = database.productTypesDao();
        allProductTypes = dao.getAllProductTypes();
    }

    public LiveData<List<ProductTypes>> getAllProductTypes() {
        return allProductTypes;
    }

    public LiveData<List<ProductTypes>> getAllProductTypesForSpecificCategory(int categoryID) {
        LiveData<List<ProductTypes>> productTypes = null;
        try {
            productTypes = new GetAllProductTypesForSpecificCategoryAsyncTask(dao).execute(categoryID).get(20, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return productTypes;
    }

    public void insert(ProductTypes productTypes) {
        new InsertAsyncTask(dao).execute(productTypes);
    }

    public class InsertAsyncTask extends AsyncTask<ProductTypes,Void,Void> {
        ProductTypesDao dao;

        public InsertAsyncTask(ProductTypesDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(ProductTypes... productTypes) {
            dao.insert(productTypes[0]);
            return null;
        }
    }

    public class GetAllProductTypesForSpecificCategoryAsyncTask extends AsyncTask<Integer, Void, LiveData<List<ProductTypes>>> {

        ProductTypesDao dao;

        public GetAllProductTypesForSpecificCategoryAsyncTask(ProductTypesDao dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<List<ProductTypes>> doInBackground(Integer... integers) {
            return dao.getAllProductTypesForSpecificCategory(integers[0]);
        }
    }
}
