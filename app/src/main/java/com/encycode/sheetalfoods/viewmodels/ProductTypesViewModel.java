package com.encycode.sheetalfoods.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.encycode.sheetalfoods.entity.ProductTypes;
import com.encycode.sheetalfoods.repositories.ProductTypesRepo;

import java.util.List;

public class ProductTypesViewModel extends AndroidViewModel {

    private ProductTypesRepo repo;
    private LiveData<List<ProductTypes>> allProductTypes;
    public ProductTypesViewModel(@NonNull Application application) {
        super(application);
        repo = new ProductTypesRepo(application);
        allProductTypes = repo.getAllProductTypes();
    }

    public LiveData<List<ProductTypes>> getAllProductTypes() {
        return allProductTypes;
    }

    public LiveData<List<ProductTypes>> getAllProductTypesForSpecificCategory(int categoryID) {
        return repo.getAllProductTypesForSpecificCategory(categoryID);
    }
}
