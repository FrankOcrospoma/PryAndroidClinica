// LoginResponse.java
package com.example.pryandroidclinica.response;

public class LoginResponse {
    private boolean status;
    private String message;
    private Data data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private String direccion;
        private String documento;
        private String email;
        private String estado_cliente;
        private String foto;
        private int id;
        private String nombre;
        private String tipo_documento_identidad_id;
        private String token;

        // Getters y setters para cada campo
        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        public String getDocumento() {
            return documento;
        }

        public void setDocumento(String documento) {
            this.documento = documento;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEstado_cliente() {
            return estado_cliente;
        }

        public void setEstado_cliente(String estado_cliente) {
            this.estado_cliente = estado_cliente;
        }

        public String getFoto() {
            return foto;
        }

        public void setFoto(String foto) {
            this.foto = foto;
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

        public String getTipo_documento_identidad_id() {
            return tipo_documento_identidad_id;
        }

        public void setTipo_documento_identidad_id(String tipo_documento_identidad_id) {
            this.tipo_documento_identidad_id = tipo_documento_identidad_id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
