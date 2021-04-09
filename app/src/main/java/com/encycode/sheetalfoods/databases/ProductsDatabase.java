package com.encycode.sheetalfoods.databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.encycode.sheetalfoods.dao.ProductsDao;
import com.encycode.sheetalfoods.entity.ProductTypes;
import com.encycode.sheetalfoods.entity.Products;

@Database(entities = Products.class,version = 4)
public abstract class ProductsDatabase extends RoomDatabase {
    private static ProductsDatabase instance;

    public abstract ProductsDao productsDao();

    public static synchronized ProductsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ProductsDatabase.class,
                    "Categories Database").fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsyncTask(instance).execute();
        }
    };

    private static class PopulateAsyncTask extends AsyncTask<Void,Void,Void> {

        private ProductsDao dao;
        private PopulateAsyncTask(ProductsDatabase db) {
            dao = db.productsDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
