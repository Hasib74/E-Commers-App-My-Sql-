package com.example.dcl.onlineshopapp.Adepter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dcl.onlineshopapp.Database.SqliteDatabase.FavoriteDatabaseHelper;
import com.example.dcl.onlineshopapp.FavoriteActivity;
import com.example.dcl.onlineshopapp.Helper.FavoriteDB;
import com.example.dcl.onlineshopapp.R;
import com.example.dcl.onlineshopapp.Utils.RecycularItemTouchHelperListener;
import com.example.dcl.onlineshopapp.Utils.RecycularTouchHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdepter extends RecyclerView.Adapter<FavoriteViewHolder> {


    Context context;
    List<FavoriteDB> favoriteDBList;

    FavoriteDatabaseHelper databaseHelper;

    public FavoriteAdepter(Context context, List<FavoriteDB> favoriteDBList) {
        this.context = context;
        this.favoriteDBList = favoriteDBList;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.favoorite_layout_card_design,null);

        return new FavoriteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, final int position) {

        databaseHelper=new FavoriteDatabaseHelper(context);

        holder.favorite_title.setText(favoriteDBList.get(position).getName());
        holder.favorite_price.setText(favoriteDBList.get(position).getPrice());
        Picasso.with(context)
                .load(favoriteDBList.get(position).getLink())
                .into(holder.favorite_IMAGE);

        holder.favorite_IMAGE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,favoriteDBList.get(position).getId(),Toast.LENGTH_SHORT
                ).show();
            }
        });





    }

    @Override
    public int getItemCount() {
        return favoriteDBList.size();
    }




}
