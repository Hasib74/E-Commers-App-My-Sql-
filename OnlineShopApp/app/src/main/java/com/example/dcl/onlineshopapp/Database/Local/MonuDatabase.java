package com.example.dcl.onlineshopapp.Database.Local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.dcl.onlineshopapp.Database.ModelDB.Cart;
import com.example.dcl.onlineshopapp.Database.ModelDB.Favorite;

@Database(entities = {Cart.class,Favorite.class},version =4)
public abstract class MonuDatabase extends RoomDatabase{

    public  abstract  CartDAO cartDAO();
    public  abstract  FavoriteDAO favoriteDAO();
    private  static  MonuDatabase instance;

    public  static  MonuDatabase getInstance(Context context){
        if (instance==null){
            instance= Room.databaseBuilder(context,MonuDatabase.class,"OnlineShop")
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

}
