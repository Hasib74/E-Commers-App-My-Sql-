package com.example.dcl.onlineshopapp.Helper;

public class FavoriteDB {

    private String  id;

    private String name;
    private String link;
    private String price;
    private String menuId;

    public FavoriteDB(String name, String link, String price,String id) {
        this.name = name;
        this.link = link;
        this.price = price;
        this.id=id;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public FavoriteDB(String id, String name, String link, String price, String menuId) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.price = price;
        this.menuId = menuId;
    }

    public FavoriteDB() {
    }
}
