package com.example.dcl.onlineshopapp.Database.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.dcl.onlineshopapp.Database.ModelDB.Cart;

import java.util.List;
import java.util.Queue;

import io.reactivex.Flowable;
@Dao
public interface CartDAO {
    @Query("SELECT * FROM Cart")
        Flowable<List<Cart>>  getCurrentItem();

    @Query("SELECT * FROM Cart WHERE id=:cartItemId")
        Flowable<List<Cart>> getChartItemBuId(int cartItemId);

    @Query("SELECT COUNT(*) FROM Cart")
        int countCartItems();
    @Query("DELETE FROM  Cart")
       void emptyCart();

    @Insert
    void  insurtToCart(Cart...carts);

    @Update
    void updateCart(Cart...carts);
    @Delete
    void deleteCartItem(Cart cart);

}
