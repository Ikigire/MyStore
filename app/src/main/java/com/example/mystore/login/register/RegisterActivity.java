package com.example.mystore.login.register;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mystore.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initComponents();
    }

    private void initComponents() {
        TextView tvIniciaSesion = findViewById(R.id.registerTvIniciarSesion);
        tvIniciaSesion.setOnClickListener( view -> finish() );

        Button btn = findViewById(R.id.registerBtnRegistrar);
        btn.setOnClickListener(this::onBtnRegistrarClick);
    }

    private void onBtnRegistrarClick(View view) {
        finish();
    }
}