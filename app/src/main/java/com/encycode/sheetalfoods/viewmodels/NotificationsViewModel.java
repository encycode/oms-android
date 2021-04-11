package com.encycode.sheetalfoods.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.encycode.sheetalfoods.entity.Notifications;
import com.encycode.sheetalfoods.repositories.NotificationsRepo;

import java.util.List;

public class NotificationsViewModel extends AndroidViewModel {

    private NotificationsRepo repo;
    private LiveData<List<Notifications>> allNotifications;

    public NotificationsViewModel(@NonNull Application application) {
        super(application);
        repo = new NotificationsRepo(application);
        allNotifications = repo.getAllNotifications();
    }

    public void insert(Notifications notifications) {
        repo.insert(notifications);
    }

    public void delete(Notifications notifications) {
        repo.delete(notifications);
    }

    public void deleteById(int id) {repo.deleteById(id);}

    public LiveData<List<Notifications>> getAllNotifications() {
        return allNotifications;
    }
}
