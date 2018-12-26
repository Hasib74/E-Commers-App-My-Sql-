
package com.example.dcl.onlineshopserver.Model;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customer implements Serializable, Parcelable
{

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("CustomerAddress")
    @Expose
    private String customerAddress;
    @SerializedName("CustomerNumber")
    @Expose
    private String customerNumber;
    @SerializedName("CustomerImage")
    @Expose
    private String customerImage;
    @SerializedName("MenuId")
    @Expose
    private String menuId;

    public Customer(String iD, String customerName, String customerAddress, String customerNumber, String customerImage, String menuId) {
        this.iD = iD;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerNumber = customerNumber;
        this.customerImage = customerImage;
        this.menuId = menuId;
    }

    public final static Parcelable.Creator<Customer> CREATOR = new Creator<Customer>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        public Customer[] newArray(int size) {
            return (new Customer[size]);
        }

    }
            ;
    private final static long serialVersionUID = -8372281286311478954L;

    protected Customer(Parcel in) {
        this.iD = ((String) in.readValue((String.class.getClassLoader())));
        this.customerName = ((String) in.readValue((String.class.getClassLoader())));
        this.customerAddress = ((String) in.readValue((String.class.getClassLoader())));
        this.customerNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.customerImage = ((String) in.readValue((String.class.getClassLoader())));
        this.menuId = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Customer() {
    }

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(iD);
        dest.writeValue(customerName);
        dest.writeValue(customerAddress);
        dest.writeValue(customerNumber);
        dest.writeValue(customerImage);
        dest.writeValue(menuId);
    }

    public int describeContents() {
        return 0;
    }

}