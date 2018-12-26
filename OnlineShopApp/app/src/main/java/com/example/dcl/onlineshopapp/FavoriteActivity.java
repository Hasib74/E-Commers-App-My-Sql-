package com.example.dcl.onlineshopapp;

import android.database.Cursor;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dcl.onlineshopapp.Adepter.FavoriteAdepter;
import com.example.dcl.onlineshopapp.Adepter.FavoriteViewHolder;
import com.example.dcl.onlineshopapp.Database.SqliteDatabase.FavoriteDatabaseHelper;
import com.example.dcl.onlineshopapp.Helper.FavoriteDB;
import com.example.dcl.onlineshopapp.Utils.RecycularItemTouchHelperListener;
import com.example.dcl.onlineshopapp.Utils.RecycularTouchHelper;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity implements RecycularItemTouchHelperListener {

    List<FavoriteDB> favoriteDBList=new ArrayList<>();
    FavoriteAdepter adepter;
    public static  RecyclerView  recyclerView;

    FavoriteDatabaseHelper databaseHelper;

    RecyclerView.LayoutManager layoutManager;
    public static RelativeLayout relativeLayout;
   // FavoriteAdepter favoriteAdepter;
     String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        recyclerView=findViewById(R.id.favorite_recycular);
        relativeLayout=findViewById(R.id.favorite_activity);
       // layoutManager=new LinearLayoutManager(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adepter=new FavoriteAdepter(this,favoriteDBList);

        databaseHelper=new FavoriteDatabaseHelper(this);
        ItemTouchHelper.SimpleCallback simpleCallback=new RecycularTouchHelper(0,ItemTouchHelper.LEFT,this);

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);




        Cursor cursor=databaseHelper.retiveAllData();
        //FavoriteDB favoriteDB=new FavoriteDB();

        if (cursor!=null){
            while (cursor.moveToNext()){

                String name=cursor.getString(cursor.getColumnIndex(databaseHelper.FAVORITE_NAME));
                String link=cursor.getString(cursor.getColumnIndex(databaseHelper.FAVORITE_LINK));
                String price=cursor.getString(cursor.getColumnIndex(databaseHelper.FAVORITE_PRICE));
                String id=cursor.getString(cursor.getColumnIndex(databaseHelper.FAVORITE_ID));



                FavoriteDB favoriteDB=new FavoriteDB(name,link,price,id);

              /*  favoriteDB.setName(name);
                favoriteDB.setLink(link);
                favoriteDB.setPrice(price);
                favoriteDB.setId(id);*/





                favoriteDBList.add(favoriteDB);
            }


        }else {
            Toast.makeText(getApplicationContext(),"Data not Found",Toast.LENGTH_SHORT).show();
        }
        adepter.notifyDataSetChanged();
        //adepter.notify();



        recyclerView.setAdapter(adepter);

    }

    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof FavoriteViewHolder){
            String name=favoriteDBList.get(position).getName();
            final FavoriteDB deleteItem=favoriteDBList.get(viewHolder.getAdapterPosition());


            final int deleteIndex=viewHolder.getAdapterPosition();
            id=favoriteDBList.get(viewHolder.getAdapterPosition()).getId();
            Toast.makeText(getApplicationContext(),"ID"+id,Toast.LENGTH_SHORT).show();
            removeItem(deleteIndex);
            databaseHelper.deleteFavoriteTable(id);

            Snackbar snackbar=Snackbar.make(FavoriteActivity.relativeLayout,
                    new StringBuilder(name).append("Remove from Favorite List"),Snackbar.LENGTH_SHORT);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    restoreItem(deleteItem,deleteIndex);
                    databaseHelper.InsetIntoDatabase(deleteItem);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
    private void removeItem(int deleteIndex) {
        favoriteDBList.remove(deleteIndex);
        adepter.notifyItemRemoved(deleteIndex);
    }

    public void restoreItem(FavoriteDB item,int position){
        favoriteDBList.add(position,item);
        adepter.notifyItemInserted(position);
    }
}
