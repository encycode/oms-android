package com.encycode.sheetalfoods.databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.encycode.sheetalfoods.dao.ProductTypesDao;
import com.encycode.sheetalfoods.entity.ProductTypes;

public abstract class ProductTypesDatabase extends RoomDatabase {
    private static ProductTypesDatabase instance;

    public abstract ProductTypesDao productTypesDao();

    public static synchronized ProductTypesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ProductTypesDatabase.class,
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

        private ProductTypesDao dao;
        private PopulateAsyncTask(ProductTypesDatabase db) {
            dao = db.productTypesDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
