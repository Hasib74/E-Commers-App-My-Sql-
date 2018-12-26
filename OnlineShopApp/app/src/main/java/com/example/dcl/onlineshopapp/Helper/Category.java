package com.example.dcl.onlineshopapp.Helper;

public class Category {
    private String ID;
    private String Name;
    private String Link;

    public Category(String ID, String name, String link) {
        this.ID = ID;
        Name = name;
        Link = link;
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

}
