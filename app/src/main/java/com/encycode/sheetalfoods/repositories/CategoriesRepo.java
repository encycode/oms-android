package com.encycode.sheetalfoods.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.encycode.sheetalfoods.dao.CategoriesDao;
import com.encycode.sheetalfoods.databases.CategoriesDatabase;
import com.encycode.sheetalfoods.entity.Categories;

import java.util.List;

public class CategoriesRepo {

    private CategoriesDao dao;
    private LiveData<List<Categories>> allCategories;

    public CategoriesRepo(Application application) {
        CategoriesDatabase database = CategoriesDatabase.getInstance(application);
        dao = database.categoriesDao();
        allCategories = dao.getAllCategories();
    }

    public LiveData<List<Categories>> getAllCategories() {
        return allCategories;
    }
}
