package com.example.dcl.onlineshopserver.Model;

import com.google.gson.annotations.SerializedName;

public class AdminMenu {



    private String ID;
    private String Name;
    private String Link;

    public AdminMenu(String ID, String name, String link) {
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
