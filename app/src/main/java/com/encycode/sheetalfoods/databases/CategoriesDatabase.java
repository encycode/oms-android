package com.encycode.sheetalfoods.databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.encycode.sheetalfoods.dao.CategoriesDao;
import com.encycode.sheetalfoods.entity.Categories;

@Database(entities = Categories.class,version = 3)
public abstract class CategoriesDatabase extends RoomDatabase {

    private static CategoriesDatabase instance;

    public abstract CategoriesDao categoriesDao();

    public static synchronized CategoriesDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CategoriesDatabase.class,
                    "Categories Database").fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new  PopulateAsyncTask(instance).execute();
        }
    };

    private static class PopulateAsyncTask extends AsyncTask<Void,Void,Void> {

        private CategoriesDao dao;
        private PopulateAsyncTask(CategoriesDatabase db) {
            dao = db.categoriesDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
