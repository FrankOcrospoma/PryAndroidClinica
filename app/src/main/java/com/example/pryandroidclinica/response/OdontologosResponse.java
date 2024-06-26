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

    public static class Odontologo {
        private int id;
        private String nombre;
        private String email;
        private int estado;
        private String apeCompleto;
        private String telefono;
        private String direccion;

        public String getApeCompleto() {
            return apeCompleto;
        }

        public void setApeCompleto(String apeCompleto) {
            this.apeCompleto = apeCompleto;
        }

        public int getEstado() {
            return estado;
        }

        public void setEstado(int estado) {
            this.estado = estado;
        }

        private String especialidad;
        private String foto;

        // Getters y setters
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        public String getEspecialidad() {
            return especialidad;
        }

        public void setEspecialidad(String especialidad) {
            this.especialidad = especialidad;
        }

        public String getFoto() {
            return foto;
        }

        public void setFoto(String foto) {
            this.foto = foto;
        }
    }
}
