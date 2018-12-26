package com.example.dcl.onlineshopserver.Model;

import java.util.List;

public class Order {


    private String OrderId;
    private String OrderStatus;
    private String OrderName;
    private String OrderPrice;
    private String OrderSize;
    private String OrderQuantity;


 //   "OrderId":"4","OrderStatus":"0","OrderName":"hasib","OrderPrice":"1.2","OrderSize":"Size :Medium","OrderQuantity":"1"

    public Order(String orderId, String orderStatus, String orderName, String orderPrice, String orderSize, String orderQuantity) {
        OrderId = orderId;
        OrderStatus = orderStatus;
        OrderName = orderName;
        OrderPrice = orderPrice;
        OrderSize = orderSize;
        OrderQuantity = orderQuantity;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getOrderName() {
        return OrderName;
    }

    public void setOrderName(String orderName) {
        OrderName = orderName;
    }

    public String getOrderPrice() {
        return OrderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        OrderPrice = orderPrice;
    }

    public String getOrderSize() {
        return OrderSize;
    }

    public void setOrderSize(String orderSize) {
        OrderSize = orderSize;
    }

    public String getOrderQuantity() {
        return OrderQuantity;
    }

    public void setOrderQuantity(String orderQuantity) {
        OrderQuantity = orderQuantity;
    }
}
