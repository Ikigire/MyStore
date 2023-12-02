package com.example.mystore.dbmanager;

import android.content.Context;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.mystore.dbmanager.dao.ProductDao;
import com.example.mystore.dbmanager.dao.UserDao;
import com.example.mystore.dbmanager.entities.Product;
import com.example.mystore.dbmanager.entities.User;

@Database(
        entities = {User.class, Product.class},
        version = 2,
        exportSchema = false
        //autoMigrations = { @AutoMigration( from = 1, to = 2)}
)
public abstract class DBManager extends RoomDatabase {

    private static DBManager database = null;
    public abstract UserDao getUserDao();
    public abstract ProductDao getProductDao();


    public static DBManager getDatabase(Context context) {
        if ( database == null ) {
            synchronized (DBManager.class) {
                database = Room.databaseBuilder(context, DBManager.class, "my-store")
                        .allowMainThreadQueries()
                        .build();
            }
        }

        return database;
    }
}
