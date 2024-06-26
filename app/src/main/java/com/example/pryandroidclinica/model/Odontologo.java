package com.example.pryandroidclinica.model;

public class Odontologo {
    private int id;
    private String nombre;
    private String ape_completo;

    // Constructor, getters y setters

    public Odontologo(int id, String nombre, String ape_completo) {
        this.id = id;
        this.nombre = nombre;
        this.ape_completo = ape_completo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApe_completo() {
        return ape_completo;
    }

    public void setApe_completo(String ape_completo) {
        this.ape_completo = ape_completo;
    }

    @Override
    public String toString() {
        return nombre + " " + ape_completo;
    }
}
