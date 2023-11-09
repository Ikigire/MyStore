package com.example.mystore.login.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mystore.R;
import com.example.mystore.dbmanager.dao.UserDao;
import com.example.mystore.login.register.RegisterActivity;
import com.example.mystore.products.productlist.ProductListActivity;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout tilUsername;
    private TextInputLayout tilPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();
    }

    private void initComponents() {
        tilUsername = findViewById(R.id.loginTilUsername);
        tilPassword = findViewById(R.id.loginTilPassword);

        TextView tvRegistrateAqui = findViewById(R.id.loginTvRegistrate);
        tvRegistrateAqui.setOnClickListener( (view) -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        } );

        Button btn = findViewById(R.id.loginBtnAcceder);
        btn.setOnClickListener(this::onBtnClick);
    }

    private void onBtnClick(View view) {
        String
                username = tilUsername.getEditText().getText().toString(),
                password = tilPassword.getEditText().getText().toString();
        //UserDao userDao =
        //userDao.login(username, password).subscribe();

        Intent intent = new Intent(this, ProductListActivity.class);
        startActivity(intent);
    }
}