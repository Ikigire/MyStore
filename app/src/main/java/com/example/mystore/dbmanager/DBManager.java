package com.example.mystore.dbmanager;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.mystore.dbmanager.dao.UserDao;
import com.example.mystore.dbmanager.entities.User;

@Database( entities = {User.class}, version = 1, exportSchema = false)
public abstract class DBManager extends RoomDatabase {

    private static DBManager database = null;
    public abstract UserDao getUserDao();


    public static DBManager getDatabase(Context context) {
        if ( database == null ) {
            synchronized (DBManager.class) {
                database = Room.databaseBuilder(context, DBManager.class, "my-store").build();
            }
        }

        return database;
    }
}
