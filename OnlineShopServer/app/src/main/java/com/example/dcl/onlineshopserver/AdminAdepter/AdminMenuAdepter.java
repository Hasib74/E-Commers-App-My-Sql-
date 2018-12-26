package com.example.dcl.onlineshopserver.AdminAdepter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dcl.onlineshopserver.HomeActivity;
import com.example.dcl.onlineshopserver.Interface.ItemClickListener;
import com.example.dcl.onlineshopserver.Interface.LongClickListener;
import com.example.dcl.onlineshopserver.Model.AdminMenu;
import com.example.dcl.onlineshopserver.R;
import com.example.dcl.onlineshopserver.Retrofit.OnlineShopApi;
import com.example.dcl.onlineshopserver.Utils.Common;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminMenuAdepter extends RecyclerView.Adapter<AdminMenuAdepter.AdminMenuViewHolder>  {
    OnlineShopApi mServer;
    HomeActivity home=new HomeActivity();
    private Context context;
    private  ItemClickListener itemClickListener;
    private  LongClickListener  onLongClick;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setOnLongClick(LongClickListener onLongClick) {
        this.onLongClick = onLongClick;
    }


    public AdminMenuAdepter(Context context, List<AdminMenu> menu_list) {
        this.context = context;
        this.menu_list = menu_list;
    }

    public static List<AdminMenu> menu_list;


    @NonNull
    @Override
    public AdminMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_menu_design,null);

        return new AdminMenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminMenuViewHolder holder, final int position) {

        mServer=Common.getApi();

        Picasso.with(context)
                .load(menu_list.get(position).getLink())
                .into(holder.img_product);

        holder.txt_product.setText(menu_list.get(position).getName());

       /* holder.setOnLongClick(new LongClickListener() {
            @Override
            public void onLongClick(View v, int possition) {

                showToolsDialog(possition);

                Toast.makeText(context,"Item Clicked :-"+possition,Toast.LENGTH_SHORT).show();
            }
        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int possition) {
                Toast.makeText(context,"Nothing to say",Toast.LENGTH_SHORT).show();
            }
        });*/


    }





    @Override
    public int getItemCount() {
        return menu_list.size();
    }


    public class AdminMenuViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener ,View.OnLongClickListener{
  /*  public AdminMenuViewHolder(View itemView) {
        super(itemView);
    }*/



        ImageView img_product;
        TextView txt_product;




        public AdminMenuViewHolder(View itemView) {
            super(itemView);
            img_product=itemView.findViewById(R.id.product_image);
            txt_product=itemView.findViewById(R.id.product_name);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);


        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v,this.getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {

            onLongClick.onLongClick(v,this.getAdapterPosition());
            return true;
        }
    }

}
