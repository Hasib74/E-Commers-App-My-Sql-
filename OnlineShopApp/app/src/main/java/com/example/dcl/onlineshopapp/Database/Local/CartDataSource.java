package com.example.dcl.onlineshopapp.Database.Local;

import com.example.dcl.onlineshopapp.Database.DataSource.ICartDataSource;
import com.example.dcl.onlineshopapp.Database.ModelDB.Cart;

import java.util.List;

import io.reactivex.Flowable;

public class CartDataSource implements ICartDataSource {
    private  CartDAO cartDAO;
    private  static  CartDataSource instance;

    public CartDataSource(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }

    public  static  CartDataSource getInstance (CartDAO cartDAO)
    {
        if (instance==null){
            instance=new CartDataSource(cartDAO);
        }
        return instance;
    }

    @Override
    public Flowable<List<Cart>> getCurrentItem() {
        return cartDAO.getCurrentItem();
    }

    @Override
    public Flowable<List<Cart>> getChartItemBuId(int cartItemId) {
        return cartDAO.getChartItemBuId(cartItemId);
    }

    @Override
    public int countCartItems() {
        return cartDAO.countCartItems();
    }

    @Override
    public void emptyCart() {
       cartDAO.emptyCart();
    }

    @Override
    public void insurtToCart(Cart... carts) {
         cartDAO.insurtToCart(carts);
    }

    @Override
    public void updateCart(Cart... carts) {
        cartDAO.updateCart(carts);
    }

    @Override
    public void deleteCartItem(Cart cart) {
        cartDAO.deleteCartItem(cart);
    }
}
