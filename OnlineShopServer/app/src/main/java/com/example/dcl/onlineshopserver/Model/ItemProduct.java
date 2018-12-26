package com.example.dcl.onlineshopserver.Model;

public class ItemProduct {
    private  String ID;
    private String Name;
    private String Link;
    private String Price;
    private  String Menuid;

    public ItemProduct(String name, String link, String price,String id) {
        Name = name;
        Link = link;
        Price = price;
        ID=id;
    }

    public ItemProduct(String ID, String name, String link, String price, String menuid) {
        this.ID = ID;
        Name = name;
        Link = link;
        Price = price;
        Menuid = menuid;
    }

    public ItemProduct() {
    }



    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getMenuid() {
        return Menuid;
    }

    public void setMenuid(String menuid) {
        Menuid = menuid;
    }


}
