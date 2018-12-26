package com.example.dcl.onlineshopapp.Adepter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dcl.onlineshopapp.Database.ModelDB.Cart;
import com.example.dcl.onlineshopapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartAdepter extends RecyclerView.Adapter<CartViewHolder> {
    Context context;
    public List<Cart> cart_list=new ArrayList<>();

    public CartAdepter(Context context, List<Cart> cart_list) {
        this.context = context;
        this.cart_list = cart_list;
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_view_style,null);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Picasso.with(context).load(cart_list.get(position).link).into(holder.cart_image);
        holder.cart_name.setText(new StringBuilder("Name :").append(cart_list.get(position).name));
        holder.cart_size.setText(new StringBuilder("Size :").append(cart_list.get(position).size));
        holder.cart_cotton_quality.setText(new StringBuilder("Cotton Quality :").append(cart_list.get(position).cotton_quality));

        String price=String.valueOf( cart_list.get(position).price * cart_list.get(position).amount);
        holder.cart_price.setText(new StringBuilder("$").append(price));

    }

    @Override
    public int getItemCount() {
        return cart_list.size();
    }
}
