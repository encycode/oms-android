package com.encycode.sheetalfoods.databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.encycode.sheetalfoods.dao.OrderDetailsDao;
import com.encycode.sheetalfoods.entity.OrderDetails;
import com.encycode.sheetalfoods.entity.Orders;
import com.encycode.sheetalfoods.helper.request.Order;

@Database(entities = OrderDetails.class,version = 1)
public abstract class OrderDetailsDatabase extends RoomDatabase {

    private static OrderDetailsDatabase instance;

    public abstract OrderDetailsDao orderDetailsDao();

    public static synchronized OrderDetailsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    OrderDetailsDatabase.class,
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

        private OrderDetailsDao dao;
        private PopulateAsyncTask(OrderDetailsDatabase db) {
            dao = db.orderDetailsDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
