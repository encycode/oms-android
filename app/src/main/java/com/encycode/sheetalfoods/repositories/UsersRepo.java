package com.encycode.sheetalfoods.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import com.encycode.sheetalfoods.OMSDatabase;
import com.encycode.sheetalfoods.dao.UsersDao;
import com.encycode.sheetalfoods.entity.Users;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UsersRepo {

    private UsersDao dao;
    private LiveData<List<Users>> allUsers;

    public UsersRepo(Application application) {
        OMSDatabase db = OMSDatabase.getInstance(application);
        dao = db.usersDao();
        allUsers = dao.getAllUsers();
    }

    public void insert(Users users) {
        new InsertAsyncTask(dao).execute(users);
    }

    public LiveData<List<Users>> getAllUsers() {
        return allUsers;
    }

    public LiveData<List<Users>> getAllUsersByRole(String role) {
        LiveData<List<Users>> users = null;
        try {
            users = new GetAllUsersByRole(dao).execute(role).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return users;
    }

    public LiveData<List<Users>> getUserById(int id) {
        LiveData<List<Users>> users = null;
        try {
            users = new GetUserByIdAsyncTask(dao).execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return users;
    }

    public class InsertAsyncTask extends AsyncTask<Users, Void, Void> {

        private UsersDao dao;

        public InsertAsyncTask(UsersDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Users... users) {
            dao.insert(users[0]);
            return null;
        }
    }

    public class GetAllUsersByRole extends AsyncTask<String, Void, LiveData<List<Users>>> {

        private UsersDao dao;

        public GetAllUsersByRole(UsersDao dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<List<Users>> doInBackground(String... strings) {
            return dao.getAllUsersByRole(strings[0]);
        }
    }

    public class GetUserByIdAsyncTask extends AsyncTask<Integer, Void, LiveData<List<Users>>> {

        private UsersDao dao;

        public GetUserByIdAsyncTask(UsersDao dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<List<Users>> doInBackground(Integer... integers) {
            return dao.getUsersById(integers[0]);
        }
    }
}
