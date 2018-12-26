package com.example.dcl.onlineshopapp.Database.ModelDB;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "Cart")
public class Cart {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public  int id;

    @ColumnInfo(name = "name")
    public  String name;

    @ColumnInfo(name = "link")
    public  String link;

    @ColumnInfo(name = "price")
    public  Double price ;

    @ColumnInfo(name = "amount")
    public  int amount ;

    @ColumnInfo(name = "cotton_quality")
    public  String cotton_quality;

    @ColumnInfo(name = "size")
    public  String size;

    @ColumnInfo(name = "topping_extra")
    public  String topping_extra;


}
