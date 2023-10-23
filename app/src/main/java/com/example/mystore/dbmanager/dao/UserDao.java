package com.example.mystore.dbmanager.dao;

import androidx.room.*;
import com.example.mystore.dbmanager.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query( "SELECT * FROM users" )
    public List<User> getAllUsers();

    @Query( "SELECT * FROM users WHERE user_id = :id" )
    public User getUserById(int id);

    @Query( "SELECT user_id FROM users WHERE username = :username AND password = :password" )
    public int loginUser( String username, String password );

    @Insert
    public void insertUser(User user);

    @Update
    public void updateUser(User user);

    @Delete
    public void deleteUser(User user);
}
