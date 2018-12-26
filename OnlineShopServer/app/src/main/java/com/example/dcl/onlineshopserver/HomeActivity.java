package com.example.dcl.onlineshopserver;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dcl.onlineshopserver.AdminAdepter.AdminMenuAdepter;
import com.example.dcl.onlineshopserver.Interface.ItemClickListener;
import com.example.dcl.onlineshopserver.Interface.LongClickListener;
import com.example.dcl.onlineshopserver.Model.AdminMenu;
import com.example.dcl.onlineshopserver.Model.AdminResponse;
import com.example.dcl.onlineshopserver.Retrofit.OnlineShopApi;
import com.example.dcl.onlineshopserver.Utils.Common;
import com.example.dcl.onlineshopserver.Utils.ProgressRequestBody;
import com.example.dcl.onlineshopserver.Utils.UploadCallBack;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,UploadCallBack {

    RecyclerView recyclerView;
    List<AdminMenu> menu_list=new ArrayList<>();


    OnlineShopApi mServer;
    ImageView image;
    ImageView updateImage;
    TextView image_title;
    SwipeRefreshLayout myRefresh;


    AdminMenuAdepter adepter;
    private int PICK_FILE_REQUEST=1000;
    private  int PICK_IMAGE_REQUEST=2000;
    Uri selectedFileUri;

    String upload_image_path;


    int signal=0;


    private Bitmap bitmap;

    private Uri filePath;

    private final int REQUEST_PERMISSION_CODE=200;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        recyclerView=findViewById(R.id.recycularview);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);
        mServer= Common.getApi();


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( HomeActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();


            mServer.updateToken("hasibakon1996@gmail.com",newToken,"1").enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });


            }
        });

        subscribeToPushService();







        if(ActivityCompat.checkSelfPermission(this,

                Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            },REQUEST_PERMISSION_CODE);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showItemAddDialog();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        loadMenu();



        adepter=new AdminMenuAdepter(HomeActivity.this,menu_list);





        adepter.setOnLongClick(new LongClickListener() {
            @Override
            public void onLongClick(View v, int possition) {
                showToolsDialog(possition);
            }
        });
        //item click to go ctagery to item activity;
        adepter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int possition) {
                Intent in=new Intent(HomeActivity.this,ItemMenu.class);


                in.putExtra("ID",menu_list.get(possition).getID());
                startActivity(in);

            }
        });


        myRefresh=findViewById(R.id.refresh);
        myRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                menu_list.clear();
                loadMenu();
            }
        });



    }

    private void subscribeToPushService() {
        FirebaseMessaging.getInstance().subscribeToTopic("news");

       /* Log.d("AndroidBash", "Subscribed");
        Toast.makeText(HomeActivity.this, "Subscribed", Toast.LENGTH_SHORT).show();

        String token = FirebaseInstanceId.getInstance().getToken();

        // Log and toast
        Log.d("AndroidBash", token);
        Toast.makeText(HomeActivity.this, token, Toast.LENGTH_SHORT).show();*/
    }

    private void showToolsDialog(final int id) {
        final android.app.AlertDialog.Builder alrt=new android.app.AlertDialog.Builder(this);

        alrt.setTitle("Delete Or Edit");

        alrt.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showEditDialog(id);
                dialog.dismiss();
            }
        });
        alrt.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String ID=menu_list.get(id).getID();


                mServer.deleteMenu(ID).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(HomeActivity.this,"Successfully delete",Toast.LENGTH_SHORT).show();
                        onRestart();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(HomeActivity.this,"Filed",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        alrt.show();


    }

    private void showEditDialog(final int id) {
        AlertDialog.Builder alrt=new AlertDialog.Builder(this);
        View v=LayoutInflater.from(this).inflate(R.layout.add_catagory,null);
        final EditText text=v.findViewById(R.id.item_image_name);
        updateImage=v.findViewById(R.id.item_image);

        alrt.setView(v);

        text.setText(menu_list.get(id).getName());
        Picasso.with(getApplicationContext()).load(menu_list.get(id).getLink()).into(updateImage);

        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signal=1;
                choseImage();
            }
        });

        alrt.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                mServer.updateMenu(menu_list.get(id).getID(),text.getText().toString(),upload_image_path)
                        .enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {

                                upload_image_path="";

                                Toast.makeText(getApplicationContext(),"Successfully Saved To the Database",Toast.LENGTH_SHORT).show();

                                menu_list.clear();
                                loadMenu();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });

            }
        });


       alrt.show();


    }


    public  void loadMenu() {
        menu_list.clear();
        mServer.getAllMenu().enqueue(new Callback<List<AdminMenu>>() {
            @Override
            public void onResponse(Call<List<AdminMenu>> call, Response<List<AdminMenu>> response) {
                List<AdminMenu> list=response.body();

                for (int i=0;i<list.size();i++){
                    String id=list.get(i).getID();
                    String link=list.get(i).getLink();
                    String name=list.get(i).getName();

                    AdminMenu adminMenu=new AdminMenu(id,name,link);

                    menu_list.add(adminMenu);
                }
                recyclerView.setAdapter(adepter);

                if (myRefresh!=null){
                    myRefresh.setRefreshing(false);
                }

            }

            @Override
            public void onFailure(Call<List<AdminMenu>> call, Throwable t) {
                Log.d("ResponseError",t.getMessage());
            }
        });
    }

    private void showItemAddDialog() {


        AlertDialog.Builder alrt=new AlertDialog.Builder(this);
        View v= LayoutInflater.from(this).inflate(R.layout.add_catagory,null);
        alrt.setView(v);
        image=v.findViewById(R.id.item_image);
        image_title=v.findViewById(R.id.item_image_name);



        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //signal=1;
                choseImage();
            }
        });

       alrt.setTitle("Upload Menu Item");
       alrt.setTitle("Please set image and name");

        alrt.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                upload_image_path="";
            }
        });
        alrt.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final String name=image_title.getText().toString();
                if (image_title.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter name",Toast.LENGTH_SHORT).show();
                }if (upload_image_path==null){
                    Toast.makeText(getApplicationContext(),"Please select image",Toast.LENGTH_SHORT).show();
                }

                mServer.uploadMenu(name,upload_image_path).enqueue(new Callback<AdminResponse>() {
                    @Override
                    public void onResponse(Call<AdminResponse> call, Response<AdminResponse> response) {
                    AdminResponse re=  response.body();

                    loadMenu();
                   /* if (re.getResponse().equals("Insert")){
                        Toast.makeText(getApplicationContext(),"Upload successfully",Toast.LENGTH_SHORT).show();
                        upload_image_path="";
                    }else {
                        Toast.makeText(getApplicationContext(),"Upload Faild!!!",Toast.LENGTH_SHORT).show();

                    }*/
                    }

                    @Override
                    public void onFailure(Call<AdminResponse> call, Throwable t) {

                    }
                });

            }
        });
        alrt.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_PERMISSION_CODE:
            {
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(),"Permissson Granted",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Permissson Denied",Toast.LENGTH_SHORT).show();

                }
            }
            break;
              default:
                   break;


        }
    }

    private void choseImage() {
        startActivityForResult(Intent.createChooser(FileUtils.createGetContentIntent(),"Select a file")
                ,PICK_FILE_REQUEST);

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

                        if (signal==1){
                            signal=0;
                            updateImage.setImageURI(selectedFileUri);
                        }else {
                            image.setImageURI(selectedFileUri);

                        }

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
            String fileName = new StringBuilder(UUID.randomUUID().toString())
                    .append(FileUtils.getExtension(file.toString()))
                    .toString();
            ProgressRequestBody requestFile = new ProgressRequestBody(this,file);//uploaded_file
            final MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", fileName, requestFile);




            new Thread(new Runnable() {
                @Override
                public void run() {
                    mServer.uploadCategoryFile(body)
                            .enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    Toast.makeText(getApplicationContext(), "Uploaded file successfully", Toast.LENGTH_SHORT).show();
                                    upload_image_path=new StringBuilder(Common.BASE_URL)
                                            .append("user_image/")
                                            .append(response.body().toString())
                                            .toString();


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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.slider) {

        } else if (id == R.id.orderActivity) {

             startActivity(new Intent(HomeActivity.this,OrderActivity.class));

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onProgressUpdate(int pertantage) {

    }
}
