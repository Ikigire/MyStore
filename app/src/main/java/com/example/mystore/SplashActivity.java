package com.example.mystore;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.airbnb.lottie.LottieAnimationView;
import com.example.mystore.dbmanager.DBManager;
import com.example.mystore.dbmanager.dao.UserDao;
import com.example.mystore.dbmanager.entities.User;
import com.example.mystore.login.login.LoginActivity;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.operators.single.SingleSubscribeOn;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {

    private boolean continuar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initComponents();
        verificarUsuarioMaestro();
    }

    private void initComponents() {
        continuar = false;
        LottieAnimationView lav = findViewById(R.id.lavAnimacionStore);

        lav.addAnimatorListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        if ( continuar ) {
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        lav.resumeAnimation();
                    }
                }
        );
        /*verificarUsuarioMaestro();*/
    }

    private void verificarUsuarioMaestro() {
        UserDao userDao = DBManager.getDatabase(getApplicationContext()).getUserDao();

        userDao.loginUser("mamahuevo", "glugluglu")
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        SplashActivity.this.continuar = true;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        User master = new User();
                        master.setFullname("Master Database");
                        master.setUsername("mamahuevo");
                        master.setPassword("glugluglu");

                        userDao.insertUser(master)
                                .observeOn(Schedulers.newThread())
                                .subscribe( () -> {
                                    continuar = true;
                                } );
                        /*userDao.insertUser(master)
                                .observeOn(Schedulers.newThread())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        continuar = true;
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {

                                    }
                                });*/
                    }
                });
    }
}