package com.example.dcl.onlineshopserver;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dcl.onlineshopserver.AdminAdepter.CatagoryItemAdepter;
import com.example.dcl.onlineshopserver.Interface.LongClickListener;
import com.example.dcl.onlineshopserver.Model.ItemProduct;
import com.example.dcl.onlineshopserver.Retrofit.OnlineShopApi;
import com.example.dcl.onlineshopserver.Utils.Common;
import com.example.dcl.onlineshopserver.Utils.ProgressRequestBody;
import com.example.dcl.onlineshopserver.Utils.UploadCallBack;
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

public class ItemMenu extends AppCompatActivity implements UploadCallBack {
    private String id;
    RecyclerView recyclerView;
    List<ItemProduct> item_list=new ArrayList<>();
    OnlineShopApi mServer;
    CatagoryItemAdepter adepter;
    FloatingActionButton floatingActionButton;
    private int PICK_FILE_REQUEST=3000;
    private Uri selectedFileUri=null;
    String upload_image_path;
    ImageView Itemimage;
    ImageView updateImage;

   private  int signal=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_menu);

        id=getIntent().getStringExtra("ID");
        recyclerView=findViewById(R.id.item_recycular);



        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mServer= Common.getApi();

        loaditem();

        adepter=new CatagoryItemAdepter(item_list,ItemMenu.this);
        floatingActionButton =findViewById(R.id.flooting);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItemDialog();
            }
        });

        adepter.setOnLongClick(new LongClickListener() {
            @Override
            public void onLongClick(View v, int possition) {
                //Toast.makeText(getApplicationContext(),""+possition,Toast.LENGTH_SHORT).show();
                showToolsDialog(possition);

            }
        });






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
                String ID=item_list.get(id).getID();

                Toast.makeText(getApplicationContext(),""+ID,Toast.LENGTH_SHORT).show();


                mServer.deleteMenuItem(ID).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(ItemMenu.this,"Successfully delete",Toast.LENGTH_SHORT).show();
                        item_list.clear();
                        loaditem();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(ItemMenu.this,"Filed",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        alrt.show();


    }

    private void showEditDialog(final int id) {
        AlertDialog.Builder alrt=new AlertDialog.Builder(this);
        View v=LayoutInflater.from(this).inflate(R.layout.add_item_dialog,null);
        final EditText text=v.findViewById(R.id.item_name);
        updateImage=v.findViewById(R.id.item_image);
        final EditText price=v.findViewById(R.id.item_price);

        alrt.setView(v);

        text.setText(item_list.get(id).getName());

        Picasso.with(getApplicationContext()).load(item_list.get(id).getLink()).into(updateImage);

        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signal=0;
                choseImage();
            }
        });

        alrt.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();





                mServer.updateMenuItem(item_list.get(id).getID(),text.getText().toString(),upload_image_path,price.getText().toString())
                        .enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {

                                upload_image_path="";

                                Toast.makeText(getApplicationContext(),"Successfully Saved To the Database",Toast.LENGTH_SHORT).show();

                                item_list.clear();
                                loaditem();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });




            }
        });


        alrt.show();


    }

    private void showAddItemDialog() {

        final EditText name,price;
        AlertDialog.Builder alrt=new AlertDialog.Builder(this);
        View v= LayoutInflater.from(this).inflate(R.layout.add_item_dialog,null);
        alrt.setView(v);
        Itemimage=v.findViewById(R.id.item_image);
        name=v.findViewById(R.id.item_name);
        price=v.findViewById(R.id.item_price);

        Itemimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                signal=1;
                choseImage();
            }
        });

        alrt.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();;

                mServer.insertMenuItem(upload_image_path,name.getText().toString(),price.getText().toString(),id).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        item_list.clear();

                        loaditem();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });


            }
        });

alrt.show();


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

                            Itemimage.setImageURI(selectedFileUri);
                        }else {
                            updateImage.setImageURI(selectedFileUri);
                        }


                        uploadFile();

                        }



                    }else {
                        Toast.makeText(getApplicationContext(),"Can not upload file to server",Toast.LENGTH_SHORT).show();
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
            ProgressRequestBody requestFile = new ProgressRequestBody(ItemMenu.this,file);//uploaded_file
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

    private void loaditem() {
        mServer.getItemProduct(id).enqueue(new Callback<List<ItemProduct>>() {
            @Override
            public void onResponse(Call<List<ItemProduct>> call, Response<List<ItemProduct>> response) {
                List<ItemProduct> localList;
                localList=response.body();
               // Toast.makeText(getApplicationContext(),localList.get(0).getName(),Toast.LENGTH_SHORT).show();

                for (int i=0;i<localList.size();i++){
                    String name=localList.get(i).getName();
                    String link=localList.get(i).getLink();
                    String price=localList.get(i).getPrice();
                    String id=localList.get(i).getID();

                    ItemProduct itemProduct=new ItemProduct(name,link,price,id);

                    item_list.add(itemProduct);

                }
                recyclerView.setAdapter(adepter);

            }

            @Override
            public void onFailure(Call<List<ItemProduct>> call, Throwable t) {

            }
        });

    }


    @Override
    public void onProgressUpdate(int pertantage) {

    }
}
