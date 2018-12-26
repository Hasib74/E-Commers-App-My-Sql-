package com.example.dcl.onlineshopapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.example.dcl.onlineshopapp.Adepter.CategoryItemAdepter;
import com.example.dcl.onlineshopapp.Helper.ItemProduct;
import com.example.dcl.onlineshopapp.Retrofit.OnlineShopApi;
import com.example.dcl.onlineshopapp.Utils.Common;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    List<String> suggestList=new ArrayList<>();

    List<ItemProduct> item_list=new ArrayList<>();
    MaterialSearchBar searchBar;

    RecyclerView recyclerView;
    CategoryItemAdepter categoryItemAdepter;


    OnlineShopApi mService;

    List<ItemProduct> localDataSource=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mService= Common.getApi();

        recyclerView=findViewById(R.id.item_search_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        searchBar=findViewById(R.id.searchBar);

        searchBar.setHint("Product Name");

        loadAllItem();
        suggetList();




        if (item_list!=null) {

            categoryItemAdepter = new CategoryItemAdepter(item_list, this);
        }else {
           Toast.makeText(getApplicationContext(),"not Found",Toast.LENGTH_LONG).show();
        }
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                       List<String> suggest=new ArrayList<>();
                       for (String search:suggestList){
                           if (search.toLowerCase().contains(searchBar.getText().toLowerCase())){
                               suggest.add(search);
                           }
                       }
                       searchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled){
                    recyclerView.setAdapter(categoryItemAdepter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                 startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        recyclerView.setAdapter(categoryItemAdepter);
    }

    private void startSearch(CharSequence text) {
        List<ItemProduct> result=new ArrayList<>();
        for (ItemProduct itemProduct:localDataSource){
            if (itemProduct.getName().contains(text)){
                result.add(itemProduct);
            }
            categoryItemAdepter=new CategoryItemAdepter(result,this);

            recyclerView.setAdapter(categoryItemAdepter);
        }
    }

    private void loadAllItem() {


      mService.getAllItemData().enqueue(new Callback<List<ItemProduct>>() {

          @Override
          public void onResponse(Call<List<ItemProduct>> call, Response<List<ItemProduct>> response) {

              List<ItemProduct> item_prooduct=response.body();

              for (int i=0;i<item_prooduct.size();i++){
                  String name=item_prooduct.get(i).getName();
                  String id=item_prooduct.get(i).getID();
                  String link=item_prooduct.get(i).getLink();
                  String price=item_prooduct.get(i).getPrice();
                  String menuId=item_prooduct.get(i).getMenuid();

                  ItemProduct itemProduct=new ItemProduct(id,name,link,price,menuId);

                  item_list.add(itemProduct);
                  localDataSource.add(itemProduct);

              }

          }

          @Override
          public void onFailure(Call<List<ItemProduct>> call, Throwable t) {

          }
      });

    }

    public void  suggetList(){
        mService.getAllItemData().enqueue(new Callback<List<ItemProduct>>() {
            @Override
            public void onResponse(Call<List<ItemProduct>> call, Response<List<ItemProduct>> response) {

                for (int i=0;i<response.body().size();i++) {
                    suggestList.add(response.body().get(i).getName());
                }
                searchBar.setLastSuggestions(suggestList);
            }

            @Override
            public void onFailure(Call<List<ItemProduct>> call, Throwable t) {

            }
        });
    }
}
