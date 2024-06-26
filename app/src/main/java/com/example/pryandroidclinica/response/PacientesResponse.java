package com.example.pryandroidclinica.response;

import com.example.pryandroidclinica.model.Paciente;
import java.util.List;

public class PacientesResponse {
    private boolean status;
    private List<Paciente> data;
    private String message;

    // Getters y setters

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Paciente> getData() {
        return data;
    }

    public void setData(List<Paciente> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
