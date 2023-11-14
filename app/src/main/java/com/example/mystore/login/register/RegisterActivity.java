package com.example.mystore.login.register;

import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mystore.R;
import com.example.mystore.dbmanager.dao.UserDao;
import com.example.mystore.dbmanager.entities.User;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    // Elementos de interacción con la vista
    private TextInputLayout tilFullname;
    private TextInputLayout tilUsername;
    private TextInputLayout tilPassword;
    private ShapeableImageView sivImage;
    private TextView tvImgMessage;

    // Conexión a la base de datos con la tabla users
    private UserDao userDao;

    // Definiendo contratos
    private ActivityResultLauncher getImageContract;

    // Objeto usuario para manipulación
    private User newUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initComponents();
        initContracts();
    }

    private void initContracts() {
        getImageContract = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                this::onImageSelected
        );
    }

    private void onImageSelected(Uri uriToPhoto) {
        if ( uriToPhoto == null ) {
            return;
        }

        newUser.setPhoto_uri(uriToPhoto.toString());
        sivImage.setImageURI(uriToPhoto);
        tvImgMessage.setText(R.string.registerTvImgMessageActivo);
    }

    private void initComponents() {
        newUser = new User();

        tilFullname = findViewById(R.id.registerTilFullname);
        tilUsername = findViewById(R.id.registerTilUsername);
        tilPassword = findViewById(R.id.registerTilPassword);

        sivImage    = findViewById(R.id.registerSivImage);
        tvImgMessage = findViewById(R.id.registerTvImgMessage);

        sivImage.setOnClickListener( view -> getImageContract.launch("image/*") );
        sivImage.setOnLongClickListener(this::resetImageView);

        TextView tvIniciaSesion = findViewById(R.id.registerTvIniciarSesion);
        tvIniciaSesion.setOnClickListener( view -> finish() );

        Button btn = findViewById(R.id.registerBtnRegistrar);
        btn.setOnClickListener(this::onBtnRegistrarClick);
    }

    private boolean resetImageView(View view) {
        newUser.setPhoto_uri(null);

        sivImage.setImageResource(R.drawable.user_asset);
        tvImgMessage.setText(R.string.registerTvImgMessageInactivo);
        return true;
    }

    private void onBtnRegistrarClick(View view) {

        finish();
    }
}