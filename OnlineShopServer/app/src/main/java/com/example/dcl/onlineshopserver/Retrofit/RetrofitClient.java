package com.example.dcl.onlineshopserver.Retrofit;

import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {



    public  static Retrofit  retrofit=null;
    public  static  Retrofit getClient(String Baseurl){
        if (retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(Baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
