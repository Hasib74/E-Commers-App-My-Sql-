package com.example.dcl.onlineshopserver.Retrofit;



import com.example.dcl.onlineshopserver.Model.AdminMenu;
import com.example.dcl.onlineshopserver.Model.AdminResponse;
import com.example.dcl.onlineshopserver.Model.Customer;
import com.example.dcl.onlineshopserver.Model.ItemProduct;
import com.example.dcl.onlineshopserver.Model.Order;

import java.util.List;
import java.util.Locale;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface OnlineShopApi {
    @FormUrlEncoded
    @POST("checkAdmin.php")
    Call<AdminResponse> checkAdmin (@Field("email") String email, @Field("password") String password);

    @GET("getmenu.php")
    Call<List<AdminMenu>> getAllMenu();

    @Multipart
    @POST("uploadMenuImage.php")
    Call<String> uploadCategoryFile(@Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("insertMenu.php")
    Call<AdminResponse>uploadMenu(@Field("name") String name,@Field("link")String link);

    @FormUrlEncoded
    @POST("deleteMenu.php")
    Call<String>deleteMenu(@Field("id")String id);

    @FormUrlEncoded
    @POST("deleteMenuItem.php")
    Call<String>deleteMenuItem(@Field("id")String id);


    @FormUrlEncoded
    @POST("updateMenu.php")
    Call<String>updateMenu(@Field("id")String id,@Field("name") String name,@Field("link")String link);


    @FormUrlEncoded
    @POST("updateMenuItem.php")
    Call<String>updateMenuItem(@Field("id")String id,@Field("name") String name,@Field("link")String link,@Field("price")String price);


    @FormUrlEncoded
    @POST("getmenudata.php")
    Call<List<ItemProduct>> getItemProduct (@Field("Menuid")String menuId);




    @FormUrlEncoded
    @POST("insertMenuItem.php")
    Call<String>insertMenuItem(@Field("link") String link,@Field("name")String name,@Field("price")String price,@Field("menuId")String menuId);


    @GET("getOrderInfo.php")
    Call<List<Order>> getOrderInfo();

    @FormUrlEncoded
    @POST("getCustomerInfo.php")
    Call<Customer>getCustomerInfo(@Field("id") String id);

    @FormUrlEncoded
    @POST("insertToken.php")
    Call<String>updateToken(@Field("phone") String name,
                            @Field("token") String token,
                            @Field("isServerToken") String isServerToken
    );


}
