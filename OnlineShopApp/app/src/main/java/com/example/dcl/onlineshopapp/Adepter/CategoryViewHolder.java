package com.example.dcl.onlineshopapp.Adepter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dcl.onlineshopapp.Interface.ItemClickListener;
import com.example.dcl.onlineshopapp.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder  implements  View.OnClickListener{


    ImageView img_product;
    TextView  txt_product;
    private  ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public CategoryViewHolder(View itemView) {
        super(itemView);
        img_product=itemView.findViewById(R.id.product_image);
        txt_product=itemView.findViewById(R.id.product_name);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v);
    }
}
