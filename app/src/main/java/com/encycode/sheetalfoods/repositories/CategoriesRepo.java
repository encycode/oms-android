package com.encycode.sheetalfoods.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.encycode.sheetalfoods.OMSDatabase;
import com.encycode.sheetalfoods.dao.CategoriesDao;
import com.encycode.sheetalfoods.entity.Categories;

import java.util.List;

public class CategoriesRepo {

    private CategoriesDao dao;
    private LiveData<List<Categories>> allCategories;

    public CategoriesRepo(Application application) {
        OMSDatabase database = OMSDatabase.getInstance(application);
        dao = database.categoriesDao();
        allCategories = dao.getAllCategories();
    }

    public LiveData<List<Categories>> getAllCategories() {
        return allCategories;
    }

    public void insert(Categories categories) {
        new InsertAsyncTask(dao).execute(categories);
    }

    public class InsertAsyncTask extends AsyncTask<Categories,Void,Void> {

        private CategoriesDao dao;

        public InsertAsyncTask(CategoriesDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Categories... categories) {
            dao.insert(categories[0]);
            return null;
        }
    }
}
