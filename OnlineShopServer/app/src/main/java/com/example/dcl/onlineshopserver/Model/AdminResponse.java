package com.example.dcl.onlineshopserver.Model;

import com.google.gson.annotations.SerializedName;

public class AdminResponse {
    public AdminResponse(String response, String error_msg) {
        this.response = response;
        this.error_msg = error_msg;
    }
    @SerializedName("response")
    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public AdminResponse() {

    }
    @SerializedName("error_msg")
    private String error_msg;
}
