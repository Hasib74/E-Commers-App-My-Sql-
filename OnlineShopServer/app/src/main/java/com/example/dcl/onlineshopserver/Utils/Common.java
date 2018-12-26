package com.example.dcl.onlineshopserver.Utils;

import com.example.dcl.onlineshopserver.Model.Admin;
import com.example.dcl.onlineshopserver.Model.AdminMenu;
import com.example.dcl.onlineshopserver.Retrofit.OnlineShopApi;
import com.example.dcl.onlineshopserver.Retrofit.RetrofitClient;

public class Common {
    public static final  String BASE_URL="http://192.168.0.110/shop/";


   /* public static Admin currentAdmin;
    public  static AdminMenu current_menu;*/

    public static OnlineShopApi getApi(){
        return RetrofitClient.getClient(BASE_URL).create(OnlineShopApi.class);
    }





}
