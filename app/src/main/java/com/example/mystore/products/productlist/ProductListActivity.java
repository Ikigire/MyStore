package com.example.mystore.products.productlist;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mystore.R;
import com.example.mystore.products.addproduct.AddProductActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        initComponents();
    }

    private void initComponents() {
        FloatingActionButton fab = findViewById(R.id.productListFabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductListActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductListActivity.this);
                builder.setTitle(R.string.productListAlertDialogTitle)
                        .setMessage(R.string.productListAlertDialogMessage)
                        .setNegativeButton(R.string.productListAlertDialogNegativeButton,
                                (dialog, which) -> {
                                    finish();
                                }
                            )
                        .setPositiveButton(R.string.productListAlertDialogPositiveButton,
                                (dialog, which) -> {

                                }
                            )
                        .create()
                        .show();
            }
        };

        getOnBackPressedDispatcher().addCallback(callback);
    }
}