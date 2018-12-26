package com.example.dcl.onlineshopserver.AdminAdepter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dcl.onlineshopserver.Interface.ItemClickListener;
import com.example.dcl.onlineshopserver.Interface.LongClickListener;
import com.example.dcl.onlineshopserver.Model.ItemProduct;
import com.example.dcl.onlineshopserver.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CatagoryItemAdepter extends RecyclerView.Adapter<CatagoryItemAdepter.CategoryViewHolder> {
    List<ItemProduct> menu_list;
    Context context;
    private LongClickListener onLongClick;

    public void setOnLongClick(LongClickListener onLongClick) {
        this.onLongClick = onLongClick;
    }

    public CatagoryItemAdepter(List<ItemProduct> menu_list, Context context) {
        this.menu_list = menu_list;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.catagory_item_desig,null);
        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.product_price.setText(menu_list.get(position).getPrice());
        holder.product_name.setText(menu_list.get(position).getName());
        Picasso.with(context).load(menu_list.get(position).getLink()).into(holder.product_image);

    }

    @Override
    public int getItemCount() {
        return menu_list.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView product_name,product_price;
        ImageView product_image;

        public CategoryViewHolder(View itemView) {
            super(itemView);

            product_name=itemView.findViewById(R.id.text_show);
            product_price=itemView.findViewById(R.id.price_show);
            product_image=itemView.findViewById(R.id.image_show);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public boolean onLongClick(View v) {
            onLongClick.onLongClick(v,getAdapterPosition());
            return true;
        }
    }
}
