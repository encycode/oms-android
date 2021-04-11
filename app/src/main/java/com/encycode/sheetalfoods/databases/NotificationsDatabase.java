package com.encycode.sheetalfoods.databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.encycode.sheetalfoods.dao.NotificationsDao;
import com.encycode.sheetalfoods.entity.Notifications;

@Database(entities = Notifications.class, version = 2)
public abstract class NotificationsDatabase extends RoomDatabase {

    private static NotificationsDatabase instance;

    public abstract NotificationsDao notificationsDao();

    public static synchronized NotificationsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NotificationsDatabase.class,
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

    private static class PopulateAsyncTask extends AsyncTask<Void, Void, Void> {

        private NotificationsDao dao;

        private PopulateAsyncTask(NotificationsDatabase db) {
            dao = db.notificationsDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
