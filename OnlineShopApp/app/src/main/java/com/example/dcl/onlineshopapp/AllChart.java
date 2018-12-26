package com.example.dcl.onlineshopapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.dcl.onlineshopapp.Adepter.CartAdepter;
import com.example.dcl.onlineshopapp.Adepter.CartViewHolder;
import com.example.dcl.onlineshopapp.Database.DataSource.CartRepository;
import com.example.dcl.onlineshopapp.Database.Local.CartDAO;
import com.example.dcl.onlineshopapp.Database.ModelDB.Cart;
import com.example.dcl.onlineshopapp.Retrofit.OnlineShopApi;
import com.example.dcl.onlineshopapp.Utils.Common;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllChart extends AppCompatActivity {

    RecyclerView recyclerView;
    CompositeDisposable compositeDisposable;
    List<Cart> cartList;
    CartAdepter adepter;
    CartViewHolder viewHolder;

    public  String customerName,customerAddress,customerNumber,customerImage;
    String id;

    OnlineShopApi mServer;





    Button orderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_chart);
        compositeDisposable=new CompositeDisposable();
        orderButton=findViewById(R.id.orderButton);

        recyclerView=findViewById(R.id.recycular_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mServer=Common.getApi();


        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfromSubmitDialog();
            }
        });

        loadCartItem();

        CustomerInfo();


    }

    private void CustomerInfo() {
        customerAddress=Common.currentUser.getAddress();
        customerImage=Common.currentUser.getUserImage();
        customerName=Common.currentUser.getName();
        customerNumber=Common.currentUser.getPhone();
    }

    private void showConfromSubmitDialog() {
        final EditText comment,address;
        RadioButton userAddress,otherAddress;
        RadioGroup tikana;

        AlertDialog.Builder alrt=new AlertDialog.Builder(this);
        View v= LayoutInflater.from(this).inflate(R.layout.order_submit_layout,null);
        alrt.setView(v);

        comment=v.findViewById(R.id.submit_comment);
        address=v.findViewById(R.id.submit_address);


        tikana=v.findViewById(R.id.address);

        tikana.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.userAddress:
                        address.setText(Common.currentUser.getAddress());
                        break;
                    case R.id.otherAddress:
                        address.setText("");

                    default:
                        break;
                }
            }
        });









        alrt.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();



                String number=Common.currentUser.getPhone();
                String addres=address.getText().toString();
                String coment=comment.getText().toString();


                for (int i=0;i<cartList.size();i++){
                     String orderItemName =cartList.get(i).name;
                     Double orderItemPrice=cartList.get(i).price;
                     int orderQuantity=cartList.get(i).amount;
                     String quantity= String.valueOf(orderQuantity);
                     id= String.valueOf(cartList.get(i).id);
                     String orderSize=cartList.get(i).size;
                     String fixedPrice= String.valueOf((float) (orderItemPrice*orderQuantity));



                    submitOrderInfo(id,orderItemName,fixedPrice,orderSize,quantity);


                    mServer.submitCustomerInfo(customerName,customerAddress,customerNumber,customerImage,id).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                            cartList.clear();

                            Toast.makeText(getApplicationContext(),"Successfully save customer info",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Failed to save customerInfo",Toast.LENGTH_SHORT).show();

                        }
                    });

                    //  submitCustomerInfo(customerName,fixedPrice,customerPrice,customerNumber,customerImage);

                }




             /*  String name= cartList.get(0).name;

                Toast.makeText(getApplicationContext(),"Name "+name,Toast.LENGTH_LONG).show();
*/
            }
        });
        alrt.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alrt.show();


    }

    private void submitOrderInfo(String id, String orderItemName, String fixedPrice, String orderSize, String orderQuantity) {

        mServer.submitOrder(id,orderItemName,fixedPrice,orderSize,orderQuantity).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(getApplicationContext(),"Successfully Submit Order"+response.body(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(),""+t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    /*private void loadCartItem(List<Cart> cartList) {
        CartAdepter cartAdepter=new CartAdepter(this,cartList);
        recyclerView.setAdapter(cartAdepter);
    }*/

    private void loadCartItem() {
        compositeDisposable.add(
                Common.cartRepository.getCurrentItem()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Cart>>() {
                    @Override
                    public void accept(List<Cart> carts) throws Exception {
                        displayCart(carts);
                    }
                })

        );

    }
   // List<Cart>
    public void displayCart(List<Cart> carts) {
        CartAdepter cartAdepter=new CartAdepter(this,carts);
        cartList=carts;

        recyclerView.setAdapter(cartAdepter);
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }


/*
    public void submitOrderToTheDatabase(String orderItemName,String number,String addres,String coment,Float fixedPPrice){

        mServer.submitOrder(orderItemName,number,addres,coment,fixedPPrice).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body()!=null){
                    Toast.makeText(getApplicationContext(),"Save into Database Successsfully",Toast.LENGTH_SHORT).show();
                 Common.cartRepository.emptyCart();
                }else {
                    Toast.makeText(getApplicationContext(),"Emty",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Unable to Save into Database",Toast.LENGTH_SHORT).show();
            }
        });

    }
*/
}
