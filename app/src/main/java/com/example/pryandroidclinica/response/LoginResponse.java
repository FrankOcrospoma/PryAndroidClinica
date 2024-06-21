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

    public String obtenerFoto() {
        return data != null ? data.getFoto() : null;
    }

    public static class Data {
        private String direccion;
        private String documento;
        private String email;
        private String estado_cliente;
        private String foto;
        private int id;
        private String nombre_usuario;
        private String nombre;
        private String ape_completo;
        private String telefono;
        private String tipo_documento_identidad_id;
        private String token;
        private String fecha_nac;

        public String getFechaNacimiento() {
            return fecha_nac;
        }

        public void setFechaNacimiento(String fechaNacimiento) {
            this.fecha_nac = fechaNacimiento;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

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

        public String getNombre_usuario() {
            return nombre_usuario;
        }

        public void setNombre_usuario(String nombre_usuario) {
            this.nombre_usuario = nombre_usuario;
        }

        public String getApe_completo() {
            return ape_completo;
        }

        public void setApe_completo(String ape_completo) {
            this.ape_completo = ape_completo;
        }

        public String getFecha_nac() {
            return fecha_nac;
        }

        public void setFecha_nac(String fecha_nac) {
            this.fecha_nac = fecha_nac;
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
