package com.example.dcl.onlineshopapp.Database.DataSource;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Query;

import com.example.dcl.onlineshopapp.Database.ModelDB.Favorite;

import java.util.List;

import io.reactivex.Flowable;

public interface IFavoriteDataSource {


    Flowable<List<Favorite>> getFavItems();


    int isFavourite(int itemId);

    void  delete(Favorite favorite);

    void  insertFaav(Favorite...favorites);

}
