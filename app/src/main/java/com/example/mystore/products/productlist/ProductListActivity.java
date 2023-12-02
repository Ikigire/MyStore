package com.example.mystore.products.productlist;

import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mystore.R;
import com.example.mystore.dbmanager.DBManager;
import com.example.mystore.dbmanager.dao.ProductDao;
import com.example.mystore.dbmanager.entities.Product;
import com.example.mystore.dbmanager.views.UserProducts;
import com.example.mystore.products.addproduct.AddProductActivity;
import com.example.mystore.products.listadapter.ProductListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.ArrayList;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

public class ProductListActivity extends AppCompatActivity {

    private ProductListAdapter adapter;
    private ListView listView;
    private ProductDao productDao;
    private Disposable observer;

    int user_id;

    private Product selectedProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        initComponents();
    }

    private void initComponents() {
        // recibir user_id
        user_id = getIntent().getIntExtra("user_id", -1);
        productDao = DBManager.getDatabase(getApplicationContext()).getProductDao();

        listView = findViewById(R.id.productListListview);
        registerForContextMenu(listView);

        FloatingActionButton fab = findViewById(R.id.productListFabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductListActivity.this, AddProductActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(ProductListActivity.this);
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

    private void getUserProducts() {
        observer = productDao.getUserProducts(user_id)
                .subscribe(
                        this::prepareAdapter,
                        this::onError
                );

    }

    private void prepareAdapter(UserProducts userProducts) {
        if (userProducts == null)
            adapter =
                    new ProductListAdapter(getApplicationContext(), new ArrayList<>());
        else
            adapter =
                    new ProductListAdapter(getApplicationContext(), userProducts.productList);

        listView.setAdapter(adapter);
        if ( observer != null && !observer.isDisposed() )
            observer.dispose();
    }

    private void onError(Throwable e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        prepareAdapter(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserProducts();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.productListListview) {
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            selectedProduct = (Product) listView.getItemAtPosition(acmi.position);

            menu.setHeaderTitle(selectedProduct.getName() + " opciones" );
            menu.setHeaderIcon(R.drawable.ic_product);

            menu.add("Editar").setIcon(R.drawable.ic_edit_24);
            menu.add("Borrar").setIcon(R.drawable.ic_delete_24);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("Editar")){
            //Editar el producto seleccionado
            Toast.makeText(this, "Editando el producto " + selectedProduct.getName(), Toast.LENGTH_SHORT).show();
        }

        if ( item.getTitle().equals("Borrar") ) {

            // Eliminar el producto seleccionado

            // volver a consultar la información de la bd
        }

        return super.onContextItemSelected(item);
    }
}