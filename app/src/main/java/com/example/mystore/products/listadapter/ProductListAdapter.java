package com.example.mystore.products.listadapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.mystore.R;
import com.example.mystore.dbmanager.entities.Product;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class ProductListAdapter extends ArrayAdapter<Product> {

    public ProductListAdapter(@NonNull Context context, List<Product> products) {
        super(context, R.layout.list_item_layout, products);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Product product = getItem(position);

        if (view == null){
            view = LayoutInflater
                    .from(getContext() )
                    .inflate(R.layout.list_item_layout, parent, false);
        }

        ShapeableImageView productImg = view.findViewById(R.id.listItemProductImg);
        TextView
                tvProductName       = view.findViewById(R.id.listItemTvProductName),
                tvProductExistences = view.findViewById(R.id.listItemTvProductExistences),
                tvProductPrice      = view.findViewById(R.id.listItemProductPrice);

        try {
            if (product.getPhoto_uri() != null) {

                Uri img = Uri.parse(product.getPhoto_uri());

                getContext().grantUriPermission(
                        "com.example.mystore",
                        img,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                        );

                productImg.setImageURI(img);
            }
        } catch (Exception e){
            productImg.setImageResource(R.drawable.asset_product);
        }

        tvProductName.setText(product.getName());
        tvProductExistences.setText( String.format("Exitencias: %d", product.getExistences()) );
        tvProductPrice.setText( String.format("Precio: $%.2f", product.getPrice()) );

        return view;
    }
}
