package com.example.dcl.onlineshopapp.Database.DataSource;

import com.example.dcl.onlineshopapp.Database.Local.FavoriteDataSource;
import com.example.dcl.onlineshopapp.Database.ModelDB.Favorite;

import java.util.List;

import io.reactivex.Flowable;

public class FavoriteRespository implements IFavoriteDataSource {
    private  IFavoriteDataSource favoriteDataSource;

    public FavoriteRespository(IFavoriteDataSource favoriteDataSource) {
        this.favoriteDataSource = favoriteDataSource;
    }

    private  static  FavoriteRespository instance;

    public  static  FavoriteRespository getInstance(IFavoriteDataSource favoriteDataSource){
        if (instance==null){
            instance=new FavoriteRespository(favoriteDataSource);
        }
        return instance;
    }

    @Override
    public Flowable<List<Favorite>> getFavItems() {
        return favoriteDataSource.getFavItems();
    }

    @Override
    public int isFavourite(int itemId) {
        return favoriteDataSource.isFavourite(itemId);
    }

    @Override
    public void delete(Favorite favorite) {
            favoriteDataSource.delete(favorite);
    }

    @Override
    public void insertFaav(Favorite... favorites) {
        favoriteDataSource.insertFaav(favorites);
    }
}
