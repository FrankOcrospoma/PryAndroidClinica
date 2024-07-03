package com.example.pryandroidclinica.response;

import java.util.List;

public class CitasResponse {
    private boolean status;
    private List<Cita> data;
    private Cita singleData;  // Añadir esta línea
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Cita> getData() {
        return data;
    }

    public void setData(List<Cita> data) {
        this.data = data;
    }

    public Cita getSingleData() {  // Añadir este getter
        return singleData;
    }

    public void setSingleData(Cita singleData) {  // Añadir este setter
        this.singleData = singleData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Cita {
        private int cita_id;
        private String nombre_paciente;
        private String nombre_odontologo;
        private String fecha;
        private String hora;
        private String motivo_consulta;
        private String diagnostico;
        private String anotacion;
        private String costo;
        private String estado;
        public Cita(int cita_id, String nombre_paciente, String nombre_odontologo, String fecha, String hora, String motivo_consulta, String diagnostico, String anotacion, String costo, String estado) {
            this.cita_id = cita_id;
            this.nombre_paciente = nombre_paciente;
            this.nombre_odontologo = nombre_odontologo;
            this.fecha = fecha;
            this.hora = hora;
            this.motivo_consulta = motivo_consulta;
            this.diagnostico = diagnostico;
            this.anotacion = anotacion;
            this.costo = costo;
            this.estado = estado;
        }


        // Getters
        public int getCita_id() {
            return cita_id;
        }

        public String getNombre_paciente() {
            return nombre_paciente;
        }

        public String getNombre_odontologo() {
            return nombre_odontologo;
        }

        public String getFecha() {
            return fecha;
        }

        public String getHora() {
            return hora;
        }

        public String getMotivo_consulta() {
            return motivo_consulta;
        }

        public String getDiagnostico() {
            return diagnostico;
        }

        public String getAnotacion() {
            return anotacion;
        }

        public String getCosto() {
            return costo;
        }

        public String getEstado() {
            return estado;
        }
    }
}
