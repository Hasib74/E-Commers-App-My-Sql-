package com.example.dcl.onlineshopapp.Utils;

import com.example.dcl.onlineshopapp.Database.DataSource.CartRepository;

import com.example.dcl.onlineshopapp.Database.DataSource.FavoriteRespository;
import com.example.dcl.onlineshopapp.Database.Local.MonuDatabase;
import com.example.dcl.onlineshopapp.Helper.Category;
import com.example.dcl.onlineshopapp.Helper.ItemProduct;
import com.example.dcl.onlineshopapp.Helper.User;
import com.example.dcl.onlineshopapp.Retrofit.OnlineShopApi;
import com.example.dcl.onlineshopapp.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

public class Common {


    public static final  String BASE_URL="http://192.168.0.110/shop/";

    public static final  String TOPPING_MENU_ID="7";

    public static User currentUser=null;
    public static  Category currentCategory=null;
    public   static List<ItemProduct> topping_list=new ArrayList<>();
    public static  double toppingPrice=0.0;
    public static int sizeOf_t_shirt=-1;
    public static  int cotton_qulity=-1;
    public static  int ice=-1;

    public  static CartRepository cartRepository;
    public static FavoriteRespository favoriteRespository;
    public  static MonuDatabase monuDatabase;




    public static  List<String> toppingAdded=new ArrayList<>();
    public static OnlineShopApi getApi(){
        return RetrofitClient.getClient(BASE_URL).create(OnlineShopApi.class);
    }

    public static String get_t_shirt_size(){

        String size=null;

        if (sizeOf_t_shirt==0){

            size="Small";

        }else if (sizeOf_t_shirt==1){
           size="Medium";
        }
        else if (sizeOf_t_shirt==2){
            size="Large";
        }else if (sizeOf_t_shirt==3){

            size="Extra Large";
        }
        return size;
    }
}
