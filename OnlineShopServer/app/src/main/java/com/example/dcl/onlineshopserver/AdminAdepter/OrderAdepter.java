package com.example.dcl.onlineshopserver.AdminAdepter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dcl.onlineshopserver.Interface.ItemClickListener;
import com.example.dcl.onlineshopserver.Model.Customer;
import com.example.dcl.onlineshopserver.Model.Order;
import com.example.dcl.onlineshopserver.Model.TotalList;
import com.example.dcl.onlineshopserver.OrderActivity;
import com.example.dcl.onlineshopserver.R;
import com.example.dcl.onlineshopserver.Retrofit.OnlineShopApi;
import com.example.dcl.onlineshopserver.Utils.Common;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdepter extends RecyclerView.Adapter<OrderAdepter.OrderViewHolder> {
    private ItemClickListener itemClickListener;
    public boolean isShow=false;

    OnlineShopApi onlineShopApi;



    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    List<Order> order_list =new ArrayList<>();
    //List<Customer> customer_list=new ArrayList<>();
    Context context;


    public OrderAdepter(List<Order> order_list, Context context) {
        this.order_list = order_list;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          onlineShopApi= Common.getApi();
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_design,null);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder holder, int position) {


        final Order order=order_list.get(position);

        holder.item_name.setText(order.getOrderName());
        holder.item_price.setText(order.getOrderPrice());
        holder.item_quantity.setText(order.getOrderQuantity());
        holder.item_size.setText(order.getOrderSize());






      //  holder.ite


        holder.firstView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onlineShopApi.getCustomerInfo(order.getOrderId()).enqueue(new Callback<Customer>() {
                    @Override                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                        holder.clientName.setText(response.body().getCustomerName());
                        holder.clientAddress.setText(response.body().getCustomerAddress());
                        Picasso.with(context)
                                .load(new StringBuilder("http://192.168.0.106/shop/user_image/").append(response.body().getCustomerImage()).toString()
                                ).into(holder.clientImage);
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {

                    }
                });

               // Toast.makeText(context,holder.item_price.getText().toString(),Toast.LENGTH_LONG).show();


                if (!isShow){
                    holder.secondView.setVisibility(View.VISIBLE);
                    isShow=true;
                }else {
                    holder.secondView.setVisibility(View.GONE);
                    isShow=false;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return order_list.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        ImageView itemImage,clintCall,clientLocation;
        CircleImageView clientImage;
        TextView item_name,item_price,item_size,item_quantity;
        TextView clientName,clientAddress;
        RelativeLayout firstView,secondView;


        public OrderViewHolder(View itemView) {
            super(itemView);
            itemImage=itemView.findViewById(R.id.order_image);
            item_name=itemView.findViewById(R.id.orderName);
            item_size=itemView.findViewById(R.id.order_size);
            item_price=itemView.findViewById(R.id.order_price);
            item_quantity=itemView.findViewById(R.id.order_quantity);

            clientName=itemView.findViewById(R.id.clint_name);
            clientImage=itemView.findViewById(R.id.clint_image);
            clientAddress=itemView.findViewById(R.id.client_address);
            clintCall=itemView.findViewById(R.id.client_phone_call);
            clientLocation=itemView.findViewById(R.id.client_llocation);

            firstView=itemView.findViewById(R.id.first_view);
            secondView=itemView.findViewById(R.id.second_view);

            itemView.setOnClickListener(this);



        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v,getAdapterPosition());
        }
    }
}
