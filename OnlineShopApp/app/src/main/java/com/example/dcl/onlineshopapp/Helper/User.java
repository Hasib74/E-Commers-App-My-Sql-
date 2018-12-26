package com.example.dcl.onlineshopapp.Helper;

public class User {
    private String phone;
    private String address;
    private String name;

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    private String userImage;

    public User() {
    }

    public User(String phone, String address, String name, String birthDate, String error_meg) {
        this.phone = phone;
        this.address = address;
        this.name = name;
        this.birthday = birthDate;
        this.error_meg = error_meg;
    }

    private String birthday;
    private String error_meg;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthday;
    }

    public void setBirthDate(String birthDate) {
        this.birthday = birthDate;
    }

    public String getError_meg() {
        return error_meg;
    }

    public void setError_meg(String error_meg) {
        this.error_meg = error_meg;
    }



}
