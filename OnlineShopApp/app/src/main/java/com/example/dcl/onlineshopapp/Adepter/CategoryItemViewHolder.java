package com.example.dcl.onlineshopapp.Adepter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dcl.onlineshopapp.Interface.ItemClickListener;
import com.example.dcl.onlineshopapp.R;

public class CategoryItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView product_image;
    TextView product_name;
    TextView product_price;
    ImageView addToChart,addToFavorite;


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private ItemClickListener itemClickListener;


    public CategoryItemViewHolder(View itemView) {
        super(itemView);

        product_image=itemView.findViewById(R.id.product_image);
        product_name=itemView.findViewById(R.id.product_name);
        product_price=itemView.findViewById(R.id.price);
        addToChart=itemView.findViewById(R.id.addToChart);
        addToFavorite=itemView.findViewById(R.id.addToFavorite);


        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v);
    }
}
