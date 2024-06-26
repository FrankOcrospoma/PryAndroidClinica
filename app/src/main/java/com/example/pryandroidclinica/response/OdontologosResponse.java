package com.example.pryandroidclinica.response;

import com.example.pryandroidclinica.model.Odontologo;
import java.util.List;

public class OdontologosResponse {
    private boolean status;
    private List<Odontologo> data;
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Odontologo> getData() {
        return data;
    }

    public void setData(List<Odontologo> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
