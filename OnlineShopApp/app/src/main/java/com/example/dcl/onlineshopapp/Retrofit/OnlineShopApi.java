package com.example.dcl.onlineshopapp.Retrofit;
import com.example.dcl.onlineshopapp.Helper.Banner;
import com.example.dcl.onlineshopapp.Helper.Category;
import com.example.dcl.onlineshopapp.Helper.CheckUserResponse;
import com.example.dcl.onlineshopapp.Helper.ItemProduct;
import com.example.dcl.onlineshopapp.Helper.User;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface OnlineShopApi {
    @FormUrlEncoded
    @POST("checkUser.php")
    Call<CheckUserResponse> checkUserExists(@Field("phone")String phone);

    @FormUrlEncoded
    @POST("register.php")
    Call<User> registerNewUser (@Field("phone")String phone,
                               @Field("name")String name,
                               @Field("address")String address,
                               @Field("birthday")String birthdate);
    @FormUrlEncoded
    @POST("getmenudata.php")
    Observable<List<ItemProduct>> getItemProduct (@Field("Menuid")String menuId);


    @FormUrlEncoded
    @POST("getuser.php")
    Call<User>getUserInformation (@Field("phone")String phone);


    @GET("getbanner.php")
    io.reactivex.Observable<List<Banner>> getBanner();


    @GET("getmenu.php")
    io.reactivex.Observable<List<Category>> getCategory();

    @Multipart
    @POST("upload.php")
    Call<String> uploadFile(@Part MultipartBody.Part phone, @Part MultipartBody.Part file);

    @GET("getAllItem.php")
    Call<List<ItemProduct>> getAllItemData();

    @FormUrlEncoded
    @POST("OrderSubmit.php")
    Call<String>submitOrder(@Field("id") String id,
                @Field("orderName") String orderName,
                              @Field("orderPrice")String orderPrice,
                             @Field("orderSize")String orderSize,
                              @Field("orderQuantity")String orderQuantity);


    @FormUrlEncoded
    @POST("submitCustomerInfo.php")
    Call<String>submitCustomerInfo(@Field("customerName") String name,
                            @Field("customerAddress") String address,
                            @Field("cutomerNumber")String number,
                            @Field("customerImage")String image,
                            @Field("menuId")String menuId);

    @FormUrlEncoded
    @POST("insertToken.php")
    Call<String>updateToken(@Field("phone") String name,
                                   @Field("token") String token,
                                    @Field("isServerToken") String isServerToken
                                  );


}
