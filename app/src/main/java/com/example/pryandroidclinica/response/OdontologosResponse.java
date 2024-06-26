package com.example.pryandroidclinica.response;

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
        private String nombreUsuario;
        private String email;
        private String contrasena;
        private int estado;
        private int estadoToken;
        private String nombre;
        private String ape_completo;
        private String fechaNac;
        private String documento;
        private int tipoDocumentoId;
        private int sexo;
        private String direccion;
        private String telefono;

        // Getters
        public int getId() {
            return id;
        }

        public String getNombreUsuario() {
            return nombreUsuario;
        }

        public String getEmail() {
            return email;
        }

        public String getContrasena() {
            return contrasena;
        }

        public int getEstado() {
            return estado;
        }

        public int getEstadoToken() {
            return estadoToken;
        }

        public String getNombre() {
            return nombre;
        }

        public String getApeCompleto() {
            return ape_completo;
        }

        public String getFechaNac() {
            return fechaNac;
        }

        public String getDocumento() {
            return documento;
        }

        public int getTipoDocumentoId() {
            return tipoDocumentoId;
        }

        public int getSexo() {
            return sexo;
        }

        public String getDireccion() {
            return direccion;
        }

        public String getTelefono() {
            return telefono;
        }
    }
}
