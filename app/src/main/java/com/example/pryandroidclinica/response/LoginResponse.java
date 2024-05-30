package com.example.pryandroidclinica.response;
import com.example.pryandroidclinica.model.Sesion;
public class LoginResponse {
    private Sesion data;
    private String message;
    private Boolean status;

    public Sesion getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public Boolean isStatus() {
        return status;
    }
}
