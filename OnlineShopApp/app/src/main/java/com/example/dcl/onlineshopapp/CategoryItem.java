package com.example.dcl.onlineshopapp;

import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dcl.onlineshopapp.Adepter.CategoryItemAdepter;
import com.example.dcl.onlineshopapp.Helper.ItemProduct;
import com.example.dcl.onlineshopapp.Retrofit.OnlineShopApi;
import com.example.dcl.onlineshopapp.Utils.Common;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CategoryItem extends AppCompatActivity {
    OnlineShopApi mService;
    RecyclerView recyclerView;


    List<ItemProduct> product_list;

    RecyclerView.LayoutManager layoutManager;

    TextView title;

    CompositeDisposable compositeDisposable=new CompositeDisposable();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item);
        recyclerView=findViewById(R.id.recycular_menu);
        //layoutManager=new LinearLayoutManager(new Gid);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        recyclerView.setHasFixedSize(true);
        mService=Common.getApi();


        title=findViewById(R.id.txt_menu);
        title.setText(Common.currentCategory.getName());

        loadList(Common.currentCategory.getID());

    }

    private void loadList(String id) {
        compositeDisposable.add(mService.getItemProduct(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<ItemProduct>>() {
            @Override
            public void accept(List<ItemProduct> itemProducts) throws Exception {
                displayMenu(itemProducts);
            }
        }));
    }

    private void displayMenu(List<ItemProduct> itemProducts) {
        CategoryItemAdepter categoryItemAdepter=new CategoryItemAdepter(itemProducts,this);

        recyclerView.setAdapter(categoryItemAdepter);
    }


}
