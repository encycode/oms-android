package com.encycode.sheetalfoods.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.encycode.sheetalfoods.dao.ProductTypesDao;
import com.encycode.sheetalfoods.databases.ProductTypesDatabase;
import com.encycode.sheetalfoods.entity.ProductTypes;

import java.util.List;

public class ProductTypesRepo {

    private ProductTypesDao dao;
    private LiveData<List<ProductTypes>> allProductTypes;

    public ProductTypesRepo(Application application) {
        ProductTypesDatabase database = ProductTypesDatabase.getInstance(application);
        dao = database.productTypesDao();
        allProductTypes = dao.getAllProductTypes();
    }

    public LiveData<List<ProductTypes>> getAllProductTypes() {
        return allProductTypes;
    }
}
