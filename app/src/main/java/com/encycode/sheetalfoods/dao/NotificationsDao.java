package com.encycode.sheetalfoods.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.encycode.sheetalfoods.entity.Notifications;

import java.util.List;

@Dao
public interface NotificationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Notifications notifications);

    @Delete
    void delete(Notifications notifications);

    @Query("DELETE FROM notifications WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT * FROM notifications")
    LiveData<List<Notifications>> getAllNotifications();
}
