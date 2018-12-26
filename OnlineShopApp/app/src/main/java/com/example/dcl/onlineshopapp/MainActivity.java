package com.example.dcl.onlineshopapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dcl.onlineshopapp.Helper.CheckUserResponse;
import com.example.dcl.onlineshopapp.Helper.User;
import com.example.dcl.onlineshopapp.Retrofit.OnlineShopApi;
import com.example.dcl.onlineshopapp.Utils.Common;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.security.auth.callback.Callback;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button btnContinue;
    private int REQUEST_CODE=1000;

    OnlineShopApi mService;
    RelativeLayout relativeLayout;
    public String number;
    public  final int REQUEST_PERMISSION=300;




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case  REQUEST_PERMISSION:
            {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(),"Successfully Genareted Permmission",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Filed to Genareted Permmission",Toast.LENGTH_SHORT).show();

                }
            }

            }
        }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE){
            final AccountKitLoginResult result=data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);

            if (result.getError()!=null){
                Toast.makeText(getApplicationContext(),""+result.getError().getErrorType().getMessage(),Toast.LENGTH_LONG).show();
            }
            else if (result.wasCancelled()){
                Toast.makeText(getApplicationContext(),"Cancel",Toast.LENGTH_LONG).show();
            }
            else {
                if (result.getAccessToken()!=null){
                     /*final AlertDialog dialog=new SpotsDialog(MainActivity.this).setMessage("Please Wait...").set;
                    */
                    final ProgressDialog dialog=new ProgressDialog(MainActivity.this);
                    dialog.show();
                    dialog.setMessage("Please Wait...");


                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(final Account account) {
                            mService.checkUserExists(account.getPhoneNumber().toString())
                                    .enqueue(new retrofit2.Callback<CheckUserResponse>() {
                                        CheckUserResponse r=new CheckUserResponse();



                                        @Override
                                        public void onResponse(Call<CheckUserResponse> call, Response<CheckUserResponse> response) {
                                            CheckUserResponse checkUserResponse=response.body();

                                            Toast.makeText(getApplicationContext(),""+r.isExists(),Toast.LENGTH_LONG).show();

                                            if (checkUserResponse.isExists()){

                                                mService.getUserInformation(account.getPhoneNumber().toString())
                                                        .enqueue(new retrofit2.Callback<User>() {
                                                            @Override
                                                            public void onResponse(Call<User> call, Response<User> response) {

                                                                dialog.dismiss();
                                                                Common.currentUser=response.body();
                                                                startActivity(new Intent(MainActivity.this,Home.class));
                                                                finish();
                                                                Toast.makeText(getApplicationContext()," Exists",Toast.LENGTH_LONG).show();

                                                            }

                                                            @Override
                                                            public void onFailure(Call<User> call, Throwable t) {
                                                              Toast.makeText(getApplicationContext(),""+t.getMessage(),Toast.LENGTH_LONG).show();
                                                            }
                                                        });


                                            }else {
                                                dialog.dismiss();
                                                Toast.makeText(getApplicationContext()," Need Registation",Toast.LENGTH_LONG).show();
                                                number=account.getPhoneNumber().toString();

                                                showRegisterDialog();
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<CheckUserResponse> call, Throwable t) {
                                          dialog.dismiss();
                                            Toast.makeText(getApplicationContext()," Faild "+t.getMessage(),Toast.LENGTH_LONG).show();

                                           System.out.print(t.getMessage());
                                        }
                                    });

                        }

                        @Override
                        public void onError(AccountKitError accountKitError) {
                                Log.d("Error",accountKitError.getErrorType().getMessage());
                        }
                    });
                }
            }
        }
    }

    private void showRegisterDialog() {

        Toast.makeText(getApplicationContext(),""+number,Toast.LENGTH_LONG).show();

        final AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
        final EditText registerName,registerAddress,registerBirthday;
        Button SaveRegister;

        dialog.setTitle("Registation");

        dialog.setMessage("Please Registation now to access this Application");

        View v= LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_registation_dialog,null);

        dialog.setView(v);
        registerName=v.findViewById(R.id.registerName);
        registerAddress=v.findViewById(R.id.registerAddress);
        registerBirthday=v.findViewById(R.id.registerbirthdate);


        dialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                final ProgressDialog waithigDialog=new ProgressDialog(MainActivity.this);
                waithigDialog.show();
                waithigDialog.setMessage("Please wait...");

                if (TextUtils.isEmpty(registerName.getText().toString())
                        ||TextUtils.isEmpty(registerAddress.getText().toString())
                        ||TextUtils.isEmpty(registerBirthday.getText().toString())){

                    waithigDialog.dismiss();
                    Snackbar snackbar = Snackbar
                            .make(relativeLayout, "Emty", Snackbar.LENGTH_LONG);
                    snackbar.show();

                }else {
                    mService.registerNewUser(number,registerName.getText().toString(),registerAddress.getText().toString(),registerBirthday.getText().toString())
                            .enqueue(new retrofit2.Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    waithigDialog.dismiss();

                                    User user=response.body();
                                    if (TextUtils.isEmpty(user.getError_meg())){

                                        Common.currentUser=response.body();

                                        startActivity(new Intent(MainActivity.this,Home.class));


                                        /*Snackbar snackbar = Snackbar
                                                .make(relativeLayout, "Registation Successfully", Snackbar.LENGTH_LONG);
                                        snackbar.show();*/
                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    waithigDialog.dismiss();
                                }
                            });
                }

            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });



        dialog.show();






    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prientKeyHash();

        mService= Common.getApi();



        relativeLayout=findViewById(R.id.layout);


        btnContinue=findViewById(R.id.btn_continue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startLogInPage(LoginType.PHONE);

                }catch (Exception x)
                {
                }
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            },REQUEST_PERMISSION);
        }



        if (AccountKit.getCurrentAccessToken()!=null){

            final ProgressDialog dialog=new ProgressDialog(MainActivity.this);
            dialog.setMessage("Please Wait...");
            dialog.show();


            AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {




                @Override
                public void onSuccess(final Account account) {
                    mService.checkUserExists(account.getPhoneNumber().toString())
                            .enqueue(new retrofit2.Callback<CheckUserResponse>() {
                                CheckUserResponse r=new CheckUserResponse();







                                @Override
                                public void onResponse(Call<CheckUserResponse> call, Response<CheckUserResponse> response) {
                                    CheckUserResponse checkUserResponse=response.body();

                                    Toast.makeText(getApplicationContext(),""+r.isExists(),Toast.LENGTH_LONG).show();

                                    if (checkUserResponse.isExists()){

                                        mService.getUserInformation(account.getPhoneNumber().toString())
                                                .enqueue(new retrofit2.Callback<User>() {
                                                    @Override
                                                    public void onResponse(Call<User> call, Response<User> response) {

                                                        dialog.dismiss();

                                                        Common.currentUser=response.body();
                                                        Toast.makeText(getApplicationContext(),""+Common.currentUser.getName()+"   and  "+Common.currentUser.getPhone(),Toast.LENGTH_LONG).show();

                                                        startActivity(new Intent(MainActivity.this,Home.class));
                                                        finish();
                                                        Toast.makeText(getApplicationContext()," Exists",Toast.LENGTH_LONG).show();

                                                    }

                                                    @Override
                                                    public void onFailure(Call<User> call, Throwable t) {
                                                        Toast.makeText(getApplicationContext(),""+t.getMessage(),Toast.LENGTH_LONG).show();
                                                    }
                                                });


                                    }else {
                                        dialog.dismiss();
                                        Toast.makeText(getApplicationContext()," Need Registation",Toast.LENGTH_LONG).show();
                                        number=account.getPhoneNumber().toString();

                                        showRegisterDialog();
                                    }

                                }

                                @Override
                                public void onFailure(Call<CheckUserResponse> call, Throwable t) {
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext()," Faild "+t.getMessage(),Toast.LENGTH_LONG).show();

                                    System.out.print(t.getMessage());
                                }
                            });

                }

                @Override
                public void onError(AccountKitError accountKitError) {
                    Log.d("Error",accountKitError.getErrorType().getMessage());
                }
            });





        }


    }



    private void startLogInPage(LoginType loginType) {

        Intent in=new Intent(MainActivity.this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder builder=new AccountKitConfiguration.AccountKitConfigurationBuilder(loginType,AccountKitActivity.ResponseType.TOKEN);
        in.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,builder.build());
        startActivityForResult(in,REQUEST_CODE);

    }

    private void prientKeyHash() {
        try{
            PackageInfo info=getPackageManager().getPackageInfo("com.example.dcl.onlineshopapp", PackageManager.GET_SIGNATURES);
            for (Signature signature:info.signatures){
                MessageDigest md=MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KEYHASH", Base64.encodeToString(md.digest(),Base64.DEFAULT));

            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    boolean isBackButtonClick=false;

    @Override
    public void onBackPressed() {


        if(isBackButtonClick){
            super.onBackPressed();
            Toast.makeText(getApplicationContext(),"Exit successfully.BYE",Toast.LENGTH_SHORT).show();
        }

        isBackButtonClick=true;
        Toast.makeText(getApplicationContext(),"Please press agan to exit this app",Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onResume() {
        super.onResume();
        isBackButtonClick=false;
    }
}
