package com.example.dcl.onlineshopapp.Database.DataSource;

import com.example.dcl.onlineshopapp.Database.Local.CartDAO;
import com.example.dcl.onlineshopapp.Database.ModelDB.Cart;

import java.util.List;

import io.reactivex.Flowable;

public class CartRepository  implements  ICartDataSource{

    CartDAO cartDAO;

    public CartRepository(ICartDataSource iCartDataSource) {

        this.iCartDataSource = iCartDataSource;
    }

    private  ICartDataSource iCartDataSource;


    private  static  CartRepository instance;

    public static  CartRepository getInstance(ICartDataSource iCartDataSource){
        if (instance==null){

            instance=new CartRepository(iCartDataSource);
        }


        return instance;
    }


    @Override
    public Flowable<List<Cart>> getCurrentItem() {

        return iCartDataSource.getCurrentItem();
    }

    @Override
    public Flowable<List<Cart>> getChartItemBuId(int cartItemId) {
        return iCartDataSource.getChartItemBuId(cartItemId);
    }

    @Override
    public int countCartItems() {
        return iCartDataSource.countCartItems();
    }

    @Override
    public void emptyCart() {
       iCartDataSource.emptyCart();
    }

    @Override
    public void insurtToCart(Cart... carts) {
        iCartDataSource.insurtToCart(carts);
    }

    @Override
    public void updateCart(Cart... carts) {
        iCartDataSource.updateCart(carts);
    }

    @Override
    public void deleteCartItem(Cart cart) {
       iCartDataSource.deleteCartItem(cart);
    }
}
