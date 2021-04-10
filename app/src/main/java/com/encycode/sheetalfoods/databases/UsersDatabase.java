package com.encycode.sheetalfoods.databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.encycode.sheetalfoods.dao.CategoriesDao;
import com.encycode.sheetalfoods.dao.UsersDao;
import com.encycode.sheetalfoods.entity.Users;

@Database(entities = Users.class,version = 1)
public abstract class UsersDatabase extends RoomDatabase {

    private static UsersDatabase instance;

    public abstract UsersDao usersDao();

    public static synchronized UsersDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    UsersDatabase.class,
                    "Users Database").fallbackToDestructiveMigration()
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

        private UsersDao dao;
        private PopulateAsyncTask(UsersDatabase db) {
            dao = db.usersDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}


