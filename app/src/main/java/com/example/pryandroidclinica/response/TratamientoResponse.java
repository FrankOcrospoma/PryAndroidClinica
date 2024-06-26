package com.example.pryandroidclinica.response;

import java.util.List;

public class TratamientoResponse {
    private boolean status;
    private List<Tratamiento> data;
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Tratamiento> getData() {
        return data;
    }

    public void setData(List<Tratamiento> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Tratamiento {
        private int id;
        private String nombre;
        private String descripcion;
        private float costo;

        // Getters
        public int getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public float getCosto() {
            return costo;
        }
    }
}
