package com.example.dcl.onlineshopapp.Adepter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dcl.onlineshopapp.R;

public class CartViewHolder extends RecyclerView.ViewHolder {
    ImageView cart_image;
    TextView cart_name,cart_price,cart_cotton_quality,cart_size;



    public CartViewHolder(View itemView) {
        super(itemView);
        cart_image=itemView.findViewById(R.id.cart_image);
        cart_name=itemView.findViewById(R.id.cart_name);
        cart_price=itemView.findViewById(R.id.cart_price);
        cart_size=itemView.findViewById(R.id.cart_size);
        cart_cotton_quality=itemView.findViewById(R.id.cart_cotton_quality);

    }
}
