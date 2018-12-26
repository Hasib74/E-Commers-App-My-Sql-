package com.example.dcl.onlineshopapp.Database.DataSource;

import com.example.dcl.onlineshopapp.Database.ModelDB.Cart;

import java.util.List;

import io.reactivex.Flowable;

public interface ICartDataSource {

    Flowable<List<Cart>> getCurrentItem();
    Flowable<List<Cart>> getChartItemBuId(int cartItemId);
    int countCartItems();
    void emptyCart();
    void  insurtToCart(Cart...carts);
    void updateCart(Cart...carts);
    void deleteCartItem(Cart cart);
}
