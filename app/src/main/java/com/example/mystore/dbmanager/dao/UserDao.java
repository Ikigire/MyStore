package com.example.mystore.dbmanager.dao;

import androidx.room.*;
import com.example.mystore.dbmanager.entities.User;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

@Dao
public interface UserDao {
    @Query( "SELECT * FROM users" )
    Single<List<User>> getAllUsers();

    @Query( "SELECT * FROM users WHERE user_id = :id" )
    Single<User> getUserById(int id);

    @Query( "SELECT user_id FROM users WHERE username = :username AND password = :password" )
    Single<Integer> loginUser( String username, String password );

    @Insert
    Completable insertUser(User user);

    @Update
    Completable updateUser(User user);

    @Delete
    Completable deleteUser(User user);
}
