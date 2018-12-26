package com.example.dcl.onlineshopapp.Database.Local;

import java.util.List;

import io.reactivex.Flowable;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.dcl.onlineshopapp.Database.ModelDB.Favorite;

@Dao
public interface FavoriteDAO {
    @Query("SELECT * FROM Favorite")
    Flowable<List<Favorite>> getFavItems();

   @Query("SELECT EXISTS (SELECT 1 FROM Favorite WHERE id=:itemId)")
   int isFavourite(int itemId);
   @Delete
   void  delete(Favorite favorite);
   @Insert
   void  insertFaav(Favorite...favorites);

}
