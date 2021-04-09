package com.encycode.sheetalfoods.databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.encycode.sheetalfoods.dao.OrderDetailsDao;
import com.encycode.sheetalfoods.dao.OrdersDao;
import com.encycode.sheetalfoods.entity.Orders;

@Database(entities = Orders.class,version = 2)
public abstract class OrdersDatabase extends RoomDatabase {
    private static OrdersDatabase instance;

    public abstract OrdersDao ordersDao();

    public static synchronized OrdersDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    OrdersDatabase.class,
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

        private OrdersDao dao;
        private PopulateAsyncTask(OrdersDatabase db) {
            dao = db.ordersDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
