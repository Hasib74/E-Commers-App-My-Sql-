package com.example.dcl.onlineshopserver.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TotalList {

    private String OrderId;
    private String OrderStatus;
    private String OrderName;
    private String OrderPrice;
    private String OrderSize;
    private String OrderQuantity;


    private String iD;

    private String customerName;

    private String customerAddress;

    private String customerNumber;

    private String customerImage;

    private String menuId;

    public TotalList(String orderId, String orderStatus, String orderName, String orderPrice, String orderSize, String orderQuantity, String iD, String customerName, String customerAddress, String customerNumber, String customerImage, String menuId) {
        OrderId = orderId;
        OrderStatus = orderStatus;
        OrderName = orderName;
        OrderPrice = orderPrice;
        OrderSize = orderSize;
        OrderQuantity = orderQuantity;
        this.iD = iD;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerNumber = customerNumber;
        this.customerImage = customerImage;
        this.menuId = menuId;
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

    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerImage() {
        return customerImage;
    }

    public void setCustomerImage(String customerImage) {
        this.customerImage = customerImage;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
