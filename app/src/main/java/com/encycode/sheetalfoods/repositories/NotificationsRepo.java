package com.encycode.sheetalfoods.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.encycode.sheetalfoods.OMSDatabase;
import com.encycode.sheetalfoods.dao.NotificationsDao;
import com.encycode.sheetalfoods.entity.Notifications;

import java.util.List;

public class NotificationsRepo {

    private NotificationsDao dao;
    private LiveData<List<Notifications>> allNotifications;

    public NotificationsRepo(Application application) {
        OMSDatabase db = OMSDatabase.getInstance(application);
        dao = db.notificationsDao();
        allNotifications = dao.getAllNotifications();
    }

    public void insert(Notifications notifications) {
        new InsertAsyncTask(dao).execute(notifications);
    }

    public void deleteById(int id) {
        new DeleteByIdAsyncTask(dao).execute(id);
    }

    public void delete(Notifications notifications) {
        new DeleteAsyncTask(dao).execute(notifications);
    }

    public LiveData<List<Notifications>> getAllNotifications() {
        return allNotifications;
    }

    public class InsertAsyncTask extends AsyncTask<Notifications, Void, Void> {

        private NotificationsDao dao;

        public InsertAsyncTask(NotificationsDao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(Notifications... notifications) {
            dao.insert(notifications[0]);
            return null;
        }
    }

    public class DeleteAsyncTask extends AsyncTask<Notifications, Void, Void> {

        private NotificationsDao dao;

        public DeleteAsyncTask(NotificationsDao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(Notifications... notifications) {
            dao.delete(notifications[0]);
            return null;
        }
    }

    public class DeleteByIdAsyncTask extends AsyncTask<Integer, Void, Void> {

        private NotificationsDao dao;

        public DeleteByIdAsyncTask(NotificationsDao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(Integer... integers) {
            dao.deleteById(integers[0]);
            return null;
        }
    }
}
