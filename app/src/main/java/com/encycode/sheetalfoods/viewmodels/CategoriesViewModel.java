package com.encycode.sheetalfoods.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.encycode.sheetalfoods.entity.Categories;
import com.encycode.sheetalfoods.repositories.CategoriesRepo;

import java.util.List;

public class CategoriesViewModel extends AndroidViewModel {

    private CategoriesRepo repo;
    private LiveData<List<Categories>> allCategories;
    public CategoriesViewModel(@NonNull Application application) {
        super(application);
        repo = new CategoriesRepo(application);
        allCategories = repo.getAllCategories();
    }

    public LiveData<List<Categories>> getAllCategories() {
        return allCategories;
    }

    public void insert(Categories categories) {repo.insert(categories);}
}
