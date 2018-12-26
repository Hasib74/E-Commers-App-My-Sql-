package com.example.dcl.onlineshopapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.dcl.onlineshopapp.Adepter.CategoryAdepter;
import com.example.dcl.onlineshopapp.Database.DataSource.CartRepository;
import com.example.dcl.onlineshopapp.Database.DataSource.FavoriteRespository;
import com.example.dcl.onlineshopapp.Database.Local.CartDataSource;

import com.example.dcl.onlineshopapp.Database.Local.FavoriteDataSource;
import com.example.dcl.onlineshopapp.Database.Local.MonuDatabase;
import com.example.dcl.onlineshopapp.Helper.Banner;
import com.example.dcl.onlineshopapp.Helper.Category;
import com.example.dcl.onlineshopapp.Helper.ItemProduct;
import com.example.dcl.onlineshopapp.Retrofit.OnlineShopApi;
import com.example.dcl.onlineshopapp.Utils.Common;
import com.example.dcl.onlineshopapp.Utils.ProgressRequestBody;
import com.example.dcl.onlineshopapp.Utils.UploadCallBack;
import com.facebook.accountkit.AccountKit;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,UploadCallBack{
    TextView txt_name,txt_phone;
    SliderLayout sliderLayout;
    OnlineShopApi mService;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    NotificationBadge badge;
    CircleImageView circleImageView;
    boolean isBackButtonClick=false;
    private  int PICK_FILE_REQUEST=200;
    Uri selectedFileUri;
    CompositeDisposable compositeDisposable=new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sliderLayout=findViewById(R.id.slider);
        mService=Common.getApi();


        recyclerView=findViewById(R.id.menu_list);

        layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        if (Common.currentUser!=null){
            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    String newToken = instanceIdResult.getToken();
                    mService.updateToken(Common.currentUser.getPhone(),newToken,"0").enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            });


        }





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView=navigationView.getHeaderView(0);
        txt_name=headerView.findViewById(R.id.txt_name);
        txt_phone=headerView.findViewById(R.id.txt_phone);
        circleImageView=(CircleImageView)headerView.findViewById(R.id.imageView);

        txt_name.setText(Common.currentUser.getName());
        txt_phone.setText(Common.currentUser.getPhone());



        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseImage();
            }
        });

        if (!TextUtils.isEmpty(Common.currentUser.getUserImage())){
            Picasso.with(this)
                    .load(new StringBuilder("http://192.168.0.105/shop/user_image/").append(Common.currentUser.getUserImage()).toString())
                    .into(circleImageView);

        }


        getBannerImage();

        load_menu();
        getToppingList();

        initDB();

    }
    private void choseImage() {
        startActivityForResult(Intent.createChooser(FileUtils.createGetContentIntent(),"Select a file")
                ,PICK_FILE_REQUEST);

    }

    private void initDB() {
        Common.monuDatabase= MonuDatabase.getInstance(this);
        Common.cartRepository= CartRepository.getInstance(CartDataSource.getInstance(Common.monuDatabase.cartDAO()));
        Common.favoriteRespository= FavoriteRespository.getInstance(FavoriteDataSource.getInstance(Common.monuDatabase.favoriteDAO()));
    }

    private void getToppingList() {


        compositeDisposable.add(mService.getItemProduct(Common.TOPPING_MENU_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ItemProduct>>() {
                    @Override
                    public void accept(List<ItemProduct> product) throws Exception {

                        Common.topping_list=product;
                    }
                }));

    }

    private void load_menu() {

        compositeDisposable.add(mService.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Category>>() {
                    @Override
                    public void accept(List<Category> categories) throws Exception {

                        displayMenu(categories);

                    }
                }));
        ;

    }

    private void displayMenu(List<Category> categories) {
       Toast.makeText(getApplicationContext(),""+categories.get(0).getLink(),Toast.LENGTH_SHORT).show(); ;
        CategoryAdepter categoryAdepter=new CategoryAdepter(categories,this);
        recyclerView.setAdapter(categoryAdepter);
    }


    private void getBannerImage() {

        compositeDisposable.add(mService.getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Banner>>() {
                    @Override
                    public void accept(List<Banner> banners) throws Exception {
                        displayImage(banners);
                    }
                })
        );

    }

    private void displayImage(List<Banner> banners) {
        HashMap<String,String> bannerMap=new HashMap<>();

        for (Banner item:banners){
            bannerMap.put(item.getName(),item.getLink());
        }
        for (String name:bannerMap.keySet()){
            TextSliderView textSliderView=new TextSliderView(this);
            textSliderView.description(name)
                    .image(bannerMap.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    ;
            sliderLayout.addSlider(textSliderView);
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        if(isBackButtonClick){
            super.onBackPressed();
            Toast.makeText(getApplicationContext(),"Exit successfully.BYE",Toast.LENGTH_SHORT).show();
        }

        isBackButtonClick=true;
        Toast.makeText(getApplicationContext(),"Please press agan to exit this app",Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_action, menu);
        View v=menu.findItem(R.id.cart_menu).getActionView();
         badge=(NotificationBadge)v.findViewById(R.id.badge);


         badge.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(getApplicationContext(),"Click",Toast.LENGTH_LONG).show();
                 startActivity(new Intent(Home.this,AllChart.class));
             }
         });
        updateCartCount();
        return true;
    }

    private void updateCartCount() {

        if (badge==null){
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Common.cartRepository.countCartItems()==0){
                    badge.setVisibility(View.GONE);
                }else {
                    badge.setVisibility(View.VISIBLE);
                    badge.setText(String.valueOf(Common.cartRepository.countCartItems()));
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id==R.id.search_menu){
            startActivity(new Intent(Home.this,SearchActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.favorite)
        {
            // Handle the camera action

            startActivity(new Intent(Home.this,FavoriteActivity.class));

        } /*else if (id == R.id.nav_gallery) {

        } */else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }else if (id==R.id.log_out){
            AlertDialog.Builder alrt=new AlertDialog.Builder(this);
            alrt.setTitle("Do you want to Sing out ?");
            alrt.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AccountKit.logOut();
                    Intent in=new Intent(Home.this,MainActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(in);
                    finish();
                }
            });
            alrt.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alrt.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartCount();
        isBackButtonClick=false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK)
        {
            if (requestCode==PICK_FILE_REQUEST)
            {
                if (data != null){
                     selectedFileUri=data.getData();
                     if (selectedFileUri!=null && !selectedFileUri.getPath().isEmpty()){
                         circleImageView.setImageURI(selectedFileUri);
                         uploadFile();

                     }else {
                         Toast.makeText(getApplicationContext(),"Can not upload file to server",Toast.LENGTH_SHORT).show();
                     }
                }
            }

        }
    }

    private void uploadFile() {
        if (selectedFileUri!=null) {
            File file = FileUtils.getFile(this, selectedFileUri);
            String fileName = new StringBuilder(Common.currentUser.getPhone())
                    .append(FileUtils.getExtension(file.toString()))
                    .toString();
            ProgressRequestBody requestFile = new ProgressRequestBody(this,file);//uploaded_file
            final MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", fileName, requestFile);
            final MultipartBody.Part phone = MultipartBody.Part.createFormData("phone", Common.currentUser.getPhone());



                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mService.uploadFile(phone, body)
                                .enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        Toast.makeText(getApplicationContext(), "Uploaded file successfully", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), "Uploaded file Filed", Toast.LENGTH_SHORT).show();

                                    }
                                });
                    }
                }).start();

            }


    }

    @Override
    public void onProgressUpdate(int pertantage) {

    }
}
