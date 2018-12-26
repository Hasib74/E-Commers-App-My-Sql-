package com.example.dcl.onlineshopapp.Adepter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dcl.onlineshopapp.CategoryItem;
import com.example.dcl.onlineshopapp.Helper.Category;
import com.example.dcl.onlineshopapp.Interface.ItemClickListener;
import com.example.dcl.onlineshopapp.R;
import com.example.dcl.onlineshopapp.Utils.Common;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdepter extends RecyclerView.Adapter<CategoryViewHolder> {
    List<Category> categories_list;

    public CategoryAdepter(List<Category> categories_list, Context context) {
        this.categories_list = categories_list;
        this.context = context;
    }

    Context context;
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_layout,null);

        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position) {

        Picasso.with(context)
                .load(categories_list.get(position).getLink())
                .into(holder.img_product);

        holder.txt_product.setText(categories_list.get(position).getName());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v) {
                Common.currentCategory=categories_list.get(position);
                context.startActivity(new Intent(context, CategoryItem.class));

            }
        });


    }

    @Override
    public int getItemCount() {
        return categories_list.size();
    }
}
