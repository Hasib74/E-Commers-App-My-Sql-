package com.example.dcl.onlineshopapp.Database.Local;

import com.example.dcl.onlineshopapp.Database.DataSource.IFavoriteDataSource;
import com.example.dcl.onlineshopapp.Database.ModelDB.Favorite;

import java.util.List;

import io.reactivex.Flowable;

public class FavoriteDataSource implements IFavoriteDataSource{

    private  FavoriteDAO favoriteDAO;
    private  static  FavoriteDataSource instance;

    public FavoriteDataSource(FavoriteDAO favoriteDAO) {
        this.favoriteDAO = favoriteDAO;
    }

    public static FavoriteDataSource getInstance(FavoriteDAO favoriteDAO){
        if (instance==null){
            instance=new FavoriteDataSource(favoriteDAO);
        }
        return instance;
    }


    @Override
    public Flowable<List<Favorite>> getFavItems() {
        return favoriteDAO.getFavItems();
    }

    @Override
    public int isFavourite(int itemId) {
        return favoriteDAO.isFavourite(itemId);
    }

   /* @Override
    public int isFavourite(int itemId) {
        return favoriteDAO.isFavourite(itemId);
    }*/

    @Override
    public void delete(Favorite favorite) {
          favoriteDAO.delete(favorite);
    }

    @Override
    public void insertFaav(Favorite... favorites) {
        favoriteDAO.insertFaav(favorites);
    }
}
