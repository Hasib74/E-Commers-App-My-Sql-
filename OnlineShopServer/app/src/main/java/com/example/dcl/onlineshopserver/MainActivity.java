package com.example.dcl.onlineshopserver;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dcl.onlineshopserver.Model.Admin;
import com.example.dcl.onlineshopserver.Model.AdminResponse;
import com.example.dcl.onlineshopserver.Retrofit.OnlineShopApi;
import com.example.dcl.onlineshopserver.Utils.Common;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText admin_email;
    EditText admin_password;
    Button admin_logIn;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String MyPREFERENCES = "MyPrefs" ;
    OnlineShopApi mServer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        admin_email=findViewById(R.id.admin_email);
        admin_password=findViewById(R.id.admin_password);

        admin_logIn=findViewById(R.id.log_in_admin);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mServer= Common.getApi();



        /*if (sharedPreferences.getString("email","")!=null){
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
        }else*/

        admin_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String email=admin_email.getText().toString();
               final String password=admin_password.getText().toString();

/*
               mServer.checkAdmin(email,password).enqueue(new Callback<com.example.dcl.onlineshopserver.Model.AdminResponse>() {
                   @Override
                   public void onResponse(Call<String> call, AdminResponse<String> response) {

                      Toast.makeText(getApplicationContext(),response.body().toString(),Toast.LENGTH_SHORT).show();
                       if(response.body()!=null){

                         startActivity(new Intent(MainActivity.this,HomeActivity.class));
                     }else {
                         Toast.makeText(getApplicationContext(),"You are not Admin",Toast.LENGTH_SHORT).show();
                     }

                   }

                   @Override
                   public void onFailure(Call<String> call, Throwable t) {
                       Toast.makeText(getApplicationContext(),"Bal"+t.getMessage(),Toast.LENGTH_SHORT).show();

                   }
               });
*/

           mServer.checkAdmin(email,password).enqueue(new Callback<AdminResponse>() {
               @Override
               public void onResponse(Call<AdminResponse> call, Response<AdminResponse> response) {
                   AdminResponse adminResponse=response.body();

                   if(adminResponse.getResponse().equals("success")){
                       SharedPreferences.Editor editor = sharedPreferences.edit();

                       editor.putString("email",email);
                       editor.putString("password",password);
                       editor.commit();



                       startActivity(new Intent(MainActivity.this,HomeActivity.class));
                   }else if(adminResponse.getError_msg().equals("Required parameter (email,password) is missing")){
                       Toast.makeText(getApplicationContext(),"Emty",Toast.LENGTH_SHORT).show();

                   }else if (adminResponse.getResponse().equals("Error")){

                       Toast.makeText(getApplicationContext(),"You are not Admin",Toast.LENGTH_SHORT).show();
                   }

               }

               @Override
               public void onFailure(Call<AdminResponse> call, Throwable t) {

               }
           });
            }
        });


    }

}
