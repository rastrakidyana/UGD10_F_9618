package com.rastrakidyana.ugd10_f_9618.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.rastrakidyana.ugd10_f_9618.Model.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Insert
    void insert(User user);

}
