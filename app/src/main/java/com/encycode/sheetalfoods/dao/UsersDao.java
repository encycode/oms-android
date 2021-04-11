package com.encycode.sheetalfoods.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.encycode.sheetalfoods.entity.Users;

import java.util.List;

@Dao
public interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Users users);

    @Query("SELECT * FROM users")
    LiveData<List<Users>> getAllUsers();

    @Query("SELECT * FROM users WHERE role = :role")
    LiveData<List<Users>> getAllUsersByRole(String role);

    @Query("SELECT * FROM users WHERE id = :id")
    LiveData<List<Users>> getUsersById(int id);

}
