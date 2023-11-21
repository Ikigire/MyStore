package com.example.mystore.login.register;

import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mystore.R;
import com.example.mystore.dbmanager.DBManager;
import com.example.mystore.dbmanager.dao.UserDao;
import com.example.mystore.dbmanager.entities.User;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
        view.setEnabled(false);
        String
                fullname = tilFullname.getEditText().getText().toString(),
                username = tilUsername.getEditText().getText().toString(),
                password = tilPassword.getEditText().getText().toString();

        fullname = fullname.trim();
        username = username.trim();
        password = password.trim();

        if ( fullname.isEmpty() || username.isEmpty() || password.isEmpty() ) {
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
            view.setEnabled(true);
            return;
        }

        newUser.setFullname(fullname);
        newUser.setUsername(username);
        newUser.setPassword(password);

        userDao = DBManager.getDatabase( getApplicationContext() ).getUserDao();

        userDao.insertUser(newUser)
                //.observeOn(Schedulers.newThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Toast.makeText(RegisterActivity.this, "Insertando nuevo usuario", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(RegisterActivity.this, "Ahora puedes iniciar sesión", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(RegisterActivity.this, "Mi primera chamba\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        view.setEnabled(true);
                    }
                });
    }
}