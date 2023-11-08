package com.example.mystore.login.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mystore.R;
import com.example.mystore.login.register.RegisterActivity;
import com.example.mystore.products.productlist.ProductListActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();
    }

    private void initComponents() {
        TextView tvRegistrateAqui = findViewById(R.id.loginTvRegistrate);
        tvRegistrateAqui.setOnClickListener( (view) -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        } );

        Button btn = findViewById(R.id.loginBtnAcceder);
        btn.setOnClickListener(this::onBtnClick);
    }

    private void onBtnClick(View view) {
        Intent intent = new Intent(this, ProductListActivity.class);
        startActivity(intent);
    }
}