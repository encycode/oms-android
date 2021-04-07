package com.encycode.sheetalfoods.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.encycode.sheetalfoods.entity.Products;
import com.encycode.sheetalfoods.repositories.ProductsRepo;

import java.util.List;

public class ProductsViewModel extends AndroidViewModel {

    private ProductsRepo repo;
    protected LiveData<List<Products>> allProducts;
    public ProductsViewModel(@NonNull Application application) {
        super(application);
        repo = new ProductsRepo(application);
        allProducts = repo.getAllProducts();
    }

    public LiveData<List<Products>> getAllProducts() {
        return allProducts;
    }
}