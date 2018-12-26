package com.example.dcl.onlineshopserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dcl.onlineshopserver.AdminAdepter.OrderAdepter;
import com.example.dcl.onlineshopserver.Interface.ItemClickListener;
import com.example.dcl.onlineshopserver.Model.Customer;
import com.example.dcl.onlineshopserver.Model.Order;
import com.example.dcl.onlineshopserver.Model.TotalList;
import com.example.dcl.onlineshopserver.Retrofit.OnlineShopApi;
import com.example.dcl.onlineshopserver.Utils.Common;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {

    List<Order> order_list=new ArrayList<>();
    List<Customer> customerList=new ArrayList<>();
    List<TotalList>totalLists=new ArrayList<>();



    RecyclerView recyclerView;
    JsonReader reader;
    OnlineShopApi mServer;
  //  String orderId;
    OrderAdepter adepter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recyclerView=findViewById(R.id.recycularview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mServer= Common.getApi();

        loadItem();
       adepter=new OrderAdepter(order_list,this);


    }

    private void loadItem() {


        mServer.getOrderInfo().enqueue(new Callback<List<Order>>() {

            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                List<Order> tamp_list=response.body();




                for (int i=0;i<tamp_list.size();i++){



                   /* String  orderId=tamp_list.get(i).getOrderId();

                    final String name=tamp_list.get(i).getOrderName();
                    final String status=tamp_list.get(i).getOrderStatus();
                    final String price=tamp_list.get(i).getOrderPrice();
                    final String size=tamp_list.get(i).getOrderSize();
                    final String quantity=tamp_list.get(i).getOrderQuantity();



                    Order order=tamp_list.get(i);

                    Order order=new Order(orderId,status,name,price,size,quantity);*/
                    order_list.add(tamp_list.get(i));

                }

                recyclerView.setAdapter(adepter);



            }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast.makeText(getApplicationContext()," "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }


        });


      //  Toast.makeText(getApplicationContext(),"Customer List "+customerList.size(),Toast.LENGTH_SHORT).show();


    }



   /* public void getCutomer(){


        Toast.makeText(getApplicationContext(),"Tota IS="+orderIdList.size(),Toast.LENGTH_LONG).show();;

    }*/



}
