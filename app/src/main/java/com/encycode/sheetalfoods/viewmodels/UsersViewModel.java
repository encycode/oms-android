package com.encycode.sheetalfoods.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.encycode.sheetalfoods.entity.Users;
import com.encycode.sheetalfoods.repositories.UsersRepo;

import java.util.List;

public class UsersViewModel extends AndroidViewModel {

    private UsersRepo repo;
    private LiveData<List<Users>> allUsers;

    public UsersViewModel(@NonNull Application application) {
        super(application);
        repo = new UsersRepo(application);
        allUsers = repo.getAllUsers();
    }

    public LiveData<List<Users>> getAllUsers() {
        return allUsers;
    }

    public void insert(Users users) {
        repo.insert(users);
    }

    public LiveData<List<Users>> getAllUsersByRole(String role) {
        return repo.getAllUsersByRole(role);
    }

}
