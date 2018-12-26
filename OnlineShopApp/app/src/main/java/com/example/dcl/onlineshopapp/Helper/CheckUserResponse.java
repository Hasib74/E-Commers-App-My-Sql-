package com.example.dcl.onlineshopapp.Helper;

public class CheckUserResponse {

    public CheckUserResponse() {
    }

    public CheckUserResponse(boolean exists, String error_msg) {
        this.exists = exists;
        this.error_msg = error_msg;
    }

    private boolean exists;

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    private String error_msg;


}
