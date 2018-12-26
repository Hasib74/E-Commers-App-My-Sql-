package com.example.dcl.onlineshopapp.Adepter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.dcl.onlineshopapp.CategoryItem;
import com.example.dcl.onlineshopapp.Database.ModelDB.Cart;
import com.example.dcl.onlineshopapp.Database.ModelDB.Favorite;
import com.example.dcl.onlineshopapp.Database.SqliteDatabase.FavoriteDatabaseHelper;
import com.example.dcl.onlineshopapp.Helper.FavoriteDB;
import com.example.dcl.onlineshopapp.Helper.ItemProduct;
import com.example.dcl.onlineshopapp.Interface.ItemClickListener;
import com.example.dcl.onlineshopapp.R;
import com.example.dcl.onlineshopapp.Utils.Common;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

import static com.example.dcl.onlineshopapp.Utils.Common.*;

public class CategoryItemAdepter extends RecyclerView.Adapter<CategoryItemViewHolder> {




    FavoriteDatabaseHelper helper;
    public CategoryItemAdepter(List<ItemProduct> categoryItems_list, Context context) {
        this.categoryItems_list = categoryItems_list;
        this.context = context;
    }



    List<ItemProduct> categoryItems_list;
    Context context;

    public  boolean isFavorite;



    @NonNull
    @Override
    public CategoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_design,null);
        return new CategoryItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryItemViewHolder holder, final int position) {

        helper=new FavoriteDatabaseHelper(context);
        Picasso.with(context)
                .load(categoryItems_list.get(position).getLink())
                .into(holder.product_image);
        holder.product_name.setText(categoryItems_list.get(position).getName());
        holder.product_price.setText(new StringBuilder("$").append(categoryItems_list.get(position).getPrice()));
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,"You select "+categoryItems_list.get(position).getID(),Toast.LENGTH_LONG).show();
            }
        });
        holder.addToChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChartDialog(position);
            }
        });
        /*holder.addToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        /*if (favoriteRespository.isFavourite(Integer.parseInt(categoryItems_list.get(position).getID()))==1){
            holder.addToFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
        }else {
            holder.addToFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }*/

       /*  if (isFavorite==true){
             holder.addToFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
         }else {
             holder.addToFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
         }*/



        final Cursor cursor=helper.retivePossirion(categoryItems_list.get(position).getID());

               if (cursor.getCount()>0){
                   holder.addToFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);


               }else {


                   holder.addToFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
               }

        holder.addToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((helper.retivePossirion(categoryItems_list.get(position).getID())).getCount()>0)
                {

                    //int id= favoriteRespository.isFavourite(Integer.parseInt(categoryItems_list.get(position).getID()));
                    //Toast.makeText(context,"Fav id"+id,Toast.LENGTH_SHORT).show();

                   // addOrRemoveFavorite(categoryItems_list.get(position),true);

                    helper.deleteFavoriteTable(categoryItems_list.get(position).getID());
                    holder.addToFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);

                    //holder.addToFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
                }else {

                    FavoriteDB favoriteDB=new FavoriteDB();

                    favoriteDB.setId(categoryItems_list.get(position).getID());
                    favoriteDB.setName(categoryItems_list.get(position).getName());
                    favoriteDB.setPrice(categoryItems_list.get(position).getPrice());
                    favoriteDB.setLink(categoryItems_list.get(position).getLink());
                    favoriteDB.setMenuId(categoryItems_list.get(position).getMenuid());




                    helper.InsetIntoDatabase(favoriteDB);



                    holder.addToFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);



                    //  addOrRemoveFavorite(categoryItems_list.get(position),false);

                   // holder.addToFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                }
            }
        });


    }

    private void addOrRemoveFavorite(ItemProduct itemProduct, boolean isAdd) {


       // Favorite favorite=new Favorite();
        FavoriteDB favoriteDB=new FavoriteDB();

        favoriteDB.setId(itemProduct.getID());
        favoriteDB.setName(itemProduct.getName());
        favoriteDB.setPrice(itemProduct.getPrice());
        favoriteDB.setLink(itemProduct.getLink());
        favoriteDB.setMenuId(itemProduct.getMenuid());

     /*   favoriteDB.id=itemProduct.getID();
        favorite.link=itemProduct.getLink();
        favorite.menuId=itemProduct.getMenuid();
        favorite.name=itemProduct.getName();
        favorite.price=itemProduct.getPrice();
*/


        if (!isAdd){
            //favoriteRespository.insertFaav(favorite);

            helper.InsetIntoDatabase(favoriteDB);

            Toast.makeText(context,"Saved",Toast.LENGTH_SHORT).show();

            isFavorite=true;


        }else {
            helper.deleteFavoriteTable(itemProduct.getID());
            Toast.makeText(context,"Delete",Toast.LENGTH_SHORT).show();

            isFavorite=false;
        }


    }


    private void showChartDialog(final int position) {
        AlertDialog.Builder alrt=new AlertDialog.Builder(context);
        View v=LayoutInflater.from(context).inflate(R.layout.add_to_chart_dialog_layout,null);
        alrt.setView(v);

        ImageView  chart_image=v.findViewById(R.id.chart_image);
        TextView chart_title=v.findViewById(R.id.t_shirt_name);
        final ElegantNumberButton txt_count=v.findViewById(R.id.coantity);


        RadioButton small_small=v.findViewById(R.id.s_size);
        RadioButton medium_size=v.findViewById(R.id.m_size);
        RadioButton large_size=v.findViewById(R.id.l_size);
        RadioButton extraLarge_size=v.findViewById(R.id.xl_size);

        small_small.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sizeOf_t_shirt=0;

            }
        });
        medium_size.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sizeOf_t_shirt=1;
            }
        });
        large_size.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sizeOf_t_shirt=2;
            }
        });
        extraLarge_size.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sizeOf_t_shirt=3;
            }
        });



        RadioButton q1=v.findViewById(R.id.q1);
        RadioButton q2=v.findViewById(R.id.q2);
        RadioButton q3=v.findViewById(R.id.q3);
        RadioButton q4=v.findViewById(R.id.q4);
        RadioButton q5=v.findViewById(R.id.q5);
        RadioButton q6=v.findViewById(R.id.q6);

        q1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cotton_qulity=10;
            }
        });
        q2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cotton_qulity=30;
            }
        });
        q3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cotton_qulity=20;
            }
        });
        q4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cotton_qulity=60;
            }
        });
        q5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cotton_qulity=70;
            }
        });



        RecyclerView tooopin_recycular=(RecyclerView)v.findViewById(R.id.tooping_recycular);

        tooopin_recycular.setHasFixedSize(true);
        tooopin_recycular.setLayoutManager(new LinearLayoutManager(context));
        MultiiChoiceAdepter adepter=new MultiiChoiceAdepter(context,categoryItems_list);

        tooopin_recycular.setAdapter(adepter);


        Picasso.with(context).load(categoryItems_list.get(position).getLink()).into(chart_image);
        chart_title.setText(categoryItems_list.get(position).getName());

        alrt.setPositiveButton("ADD TO CART", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (sizeOf_t_shirt==-1){
                    Toast.makeText(context,"Please insure the T-Shirt Size",Toast.LENGTH_SHORT).show();
                }
                if (ice==-1){
                    Toast.makeText(context,"Please insure the Quality Size",Toast.LENGTH_SHORT).show();
                }
                if (cotton_qulity==-1){
                    Toast.makeText(context,"Please insure the Quality Size",Toast.LENGTH_SHORT).show();
                }

                if(txt_count.getNumber()!="0") {
                    showTheConfromDialog(position, txt_count.getNumber(), cotton_qulity, sizeOf_t_shirt);
                    dialog.dismiss();
                }else {
                    Toast.makeText(context,"Order Emety ",Toast.LENGTH_SHORT).show();
                }



            }
        });


        alrt.show();

    }

    private void showTheConfromDialog(final int position, final String number, final int cotton_qulity, final int sizeOf_t_shirt) {
        AlertDialog.Builder alert=new AlertDialog.Builder(context);
        View v=LayoutInflater.from(context).inflate(R.layout.confrom_dialog,null);
        alert.setView(v);
        final TextView controm_title=v.findViewById(R.id.confrom_t_shirt);
        final TextView confrom_price=v.findViewById(R.id.confrom_price);
        TextView confrom_quality=v.findViewById(R.id.conftom_cotton_quality);
        final TextView cnfrom_size=v.findViewById(R.id.conftom_size);
        ImageView cnfrom_image=v.findViewById(R.id.confrom_image);
        final TextView toooping_extra=v.findViewById(R.id.tooping_extra);

        controm_title.setText(categoryItems_list.get(position).getName());

        confrom_price.setText(String.valueOf(Double.parseDouble(categoryItems_list.get(position).getPrice()) * Double.parseDouble(number)));

     //   confrom_price.setText(Double.parseDouble(String.valueOf(Double.parseDouble(categoryItems_list.get(position).getPrice())*9Double.parseDouble(number)));
        confrom_quality.setText(new StringBuilder("Cotton Quality :")
                .append(Common.cotton_qulity).append("%"));
        cnfrom_size.setText(new StringBuilder("Size :").append(get_t_shirt_size()));

        Picasso.with(context).load(categoryItems_list.get(position).getLink()).into(cnfrom_image);

        StringBuilder stringBuilder=new StringBuilder();

        for (String line: toppingAdded){
            stringBuilder.append(line).append("\n");
        }

        toooping_extra.setText(stringBuilder);


      alert.setPositiveButton("Confrom", new DialogInterface.OnClickListener() {


          @Override
          public void onClick(DialogInterface dialog, int which) {
              toppingAdded.clear();
              dialog.dismiss();


              try {


                  Cart cartItem = new Cart();
                  cartItem.name = controm_title.getText().toString();
                  cartItem.amount = Integer.parseInt(number);
                  cartItem.price = Double.valueOf(confrom_price.getText().toString());
                  cartItem.cotton_quality = String.valueOf(cotton_qulity);
                  cartItem.topping_extra = toooping_extra.toString();
                  cartItem.link=categoryItems_list.get(position).getLink();
                  cartItem.size=cnfrom_size.getText().toString();

                  cartRepository.insurtToCart(cartItem);

                  Log.d("DATA_INSURT", new Gson().toJson(cartItem));

                  Toast.makeText(context, "Save data Successfully", Toast.LENGTH_LONG).show();

              }catch (Exception ex){
                  Toast.makeText(context,ex.getMessage(),Toast.LENGTH_LONG).show();
              }
          }


      });

      alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
              dialog.dismiss();

          }
      });


        alert.show();



    }


    @Override
    public int getItemCount() {
        return categoryItems_list.size();
    }
}
