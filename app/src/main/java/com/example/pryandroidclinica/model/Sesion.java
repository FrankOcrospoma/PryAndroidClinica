package com.example.pryandroidclinica.model;

public class Sesion {
    private int almacen_id;
    private String email;
    private String estado_usuario;
    private int id;
    private String nombre;
    private String token;
    private String foto;


    /*Declarar un atributo para almacenar los datos de la sesión iniciada por el usuario*/
    public static Sesion DATOS_SESION;
    /*Declarar un atributo para almacenar los datos de la sesión iniciada por el usuario*/

    public int getAlmacen_id() {
        return almacen_id;
    }

    public void setAlmacen_id(int almacen_id) {
        this.almacen_id = almacen_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado_usuario() {
        return estado_usuario;
    }

    public void setEstado_usuario(String estado_usuario) {
        this.estado_usuario = estado_usuario;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
