package com.example.mystore.dbmanager.views;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.example.mystore.dbmanager.entities.Product;
import com.example.mystore.dbmanager.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserProducts {
    @Embedded
    public User user = null;
    @Relation(parentColumn = "user_id", entityColumn = "user_id")
    public List<Product> productList = new ArrayList<Product>();
}
