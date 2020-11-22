package com.rastrakidyana.ugd10_f_9618.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.rastrakidyana.ugd10_f_9618.Model.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
}
