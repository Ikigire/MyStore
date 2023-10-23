package com.example.mystore.dbmanager;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.mystore.dbmanager.dao.UserDao;
import com.example.mystore.dbmanager.entities.User;

@Database( entities = {User.class}, version = 1)
public abstract class DBManager extends RoomDatabase {

    public abstract UserDao getUserDao();
}
