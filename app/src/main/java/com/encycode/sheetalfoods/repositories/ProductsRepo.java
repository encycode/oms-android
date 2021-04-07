package com.encycode.sheetalfoods.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.encycode.sheetalfoods.dao.ProductTypesDao;
import com.encycode.sheetalfoods.dao.ProductsDao;
import com.encycode.sheetalfoods.databases.ProductTypesDatabase;
import com.encycode.sheetalfoods.databases.ProductsDatabase;
import com.encycode.sheetalfoods.entity.ProductTypes;
import com.encycode.sheetalfoods.entity.Products;

import java.util.List;

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
}