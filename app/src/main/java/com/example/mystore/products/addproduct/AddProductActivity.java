package com.example.mystore.products.addproduct;

import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mystore.R;

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        initComponents();
    }

    private void initComponents() {
        Button btn = findViewById(R.id.addProductBtnRegistrar);
        btn.setOnClickListener(this::onBtnRegistrarClick);
    }

    private void onBtnRegistrarClick(View view) {
        finish();
    }
}