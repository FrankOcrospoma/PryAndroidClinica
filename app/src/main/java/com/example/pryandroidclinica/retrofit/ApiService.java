package com.example.pryandroidclinica.retrofit;

import com.example.pryandroidclinica.response.CitasResponse;
import com.example.pryandroidclinica.response.LoginResponse;
import com.example.pryandroidclinica.response.OdontologosResponse;
import com.example.pryandroidclinica.response.PacientesResponse;
import com.example.pryandroidclinica.response.TratamientoResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    @FormUrlEncoded
    @POST("/loginAdmin")
    Call<LoginResponse> login(@Field("email") String email, @Field("clave") String clave);

    @FormUrlEncoded
    @POST("/usuario/enviarCodigoRecuperacion")
    Call<LoginResponse> enviarCodigoRecuperacion(@Field("email") String email);

    @FormUrlEncoded
    @POST("/usuario/verificarCodigo")
    Call<LoginResponse> verificarCodigo(@Field("email") String email, @Field("codigo") String codigo);

    @FormUrlEncoded
    @POST("/usuario/restablecerContrasena")
    Call<LoginResponse> restablecerContrasena(@Field("email") String email, @Field("nuevaContrasena") String nuevaContrasena);

    @FormUrlEncoded
    @PUT("/usuario/actualizar")
    Call<LoginResponse> actualizarUsuario(
            @Field("id") int id,
            @Field("nombreUsuario") String nombreUsuario,
            @Field("email") String email,
            @Field("estado") int estado,
            @Field("token") String token,
            @Field("estadoToken") int estadoToken,
            @Field("nombre") String nombre,
            @Field("apeCompleto") String apeCompleto,
            @Field("fechaNac") String fechaNacimiento,
            @Field("documento") String documento,
            @Field("tipo_documento_id") int tipo_documento_id,
            @Field("sexo") int sexo,
            @Field("direccion") String direccion,
            @Field("telefono") String telefono,
            @Field("foto") String foto,
            @Field("rolId") int rolId
    );

    @Multipart
    @POST("/usuario/subirFoto")
    Call<LoginResponse> subirFoto(@Part MultipartBody.Part foto);

    @GET("/img/{filename}")
    Call<LoginResponse> obtenerFoto(@Path("filename") String filename);

    @FormUrlEncoded
    @POST("/usuario/cambiarContrasena")
    Call<LoginResponse> cambiarContrasena(
            @Field("id") int id,
            @Field("nuevaContrasena") String nuevaContrasena
    );

    @GET("/atencion/citas/")
    Call<CitasResponse> obtenerCitasPaciente();

    @GET("/atencion/citas/pacientes")
    Call<PacientesResponse> obtenerPacientes();

    @GET("/atencion/citas/odontologos")
    Call<OdontologosResponse> obtenerOdontologos();

    @FormUrlEncoded
    @POST("/atencion/cita/registrar")
    Call<CitasResponse> registrarCita(
            @Field("paciente_id") int pacienteId,
            @Field("odontologo_id") int odontologoId,
            @Field("fecha") String fecha,
            @Field("hora") String hora,
            @Field("motivo_consulta") String motivoConsulta
    );
    @GET("/tratamiento/lista")
    Call<TratamientoResponse> obtenerTratamientos();

    @FormUrlEncoded
    @POST("/usuario/agregar/paciente")
    Call<PacientesResponse> agregarPaciente(
            @Field("nombreUsuario") String nombreUsuario,
            @Field("email") String email,
            @Field("contrasena") String contrasena,
            @Field("estado") int estado,
            @Field("estado_token") int estadoToken,
            @Field("nombre") String nombre,
            @Field("apeCompleto") String apeCompleto,
            @Field("fechaNac") String fechaNac,
            @Field("documento") String documento,
            @Field("tipo_documento_id") int tipoDocumentoId,
            @Field("sexo") int sexo,
            @Field("direccion") String direccion,
            @Field("telefono") String telefono
    );

    @FormUrlEncoded
    @POST("/tratamiento/registrar")
    Call<TratamientoResponse> registrarTratamiento(
            @Field("nombre") String nombre,
            @Field("descripcion") String descripcion,
            @Field("costo") float costo

    );

    @HTTP(method = "PUT", path = "/tratamiento/actualizar", hasBody = true)
    @FormUrlEncoded
    Call<TratamientoResponse> editarTratamiento(
            @Field("nombre") String nombre,
            @Field("descripcion") String descripcion,
            @Field("costo") float costo,
            @Field("id") int id
    );

    @HTTP(method = "DELETE", path = "/tratamiento/eliminar", hasBody = true)
    @FormUrlEncoded
    Call<TratamientoResponse> eliminarTratamiento(
            @Field("id") int id
    );



    @GET("/usuario/lista/pacientes")
    Call<PacientesResponse> listaPacientes();

    @FormUrlEncoded
    @POST("/usuario/agregar/odontologo")
    Call<OdontologosResponse> agregarOdontologo(
            @Field("nombreUsuario") String nombreUsuario,
            @Field("email") String email,
            @Field("contrasena") String contrasena,
            @Field("estado") int estado,
            @Field("estado_token") int estadoToken,
            @Field("nombre") String nombre,
            @Field("apeCompleto") String apeCompleto,
            @Field("fechaNac") String fechaNac,
            @Field("documento") String documento,
            @Field("tipo_documento_id") int tipoDocumentoId,
            @Field("sexo") int sexo,
            @Field("direccion") String direccion,
            @Field("telefono") String telefono
    );

    @GET("/usuario/lista/odontologos")
    Call<OdontologosResponse> listaOdontologos();


    @HTTP(method = "DELETE", path = "/usuario/eliminar", hasBody = true)
    @FormUrlEncoded
    Call<PacientesResponse> eliminarPaciente(
            @Field("id") int id
    );
    @HTTP(method = "DELETE", path = "/usuario/eliminar", hasBody = true)
    @FormUrlEncoded
    Call<OdontologosResponse> eliminarOdontologo(
            @Field("id") int id
    );

    @HTTP(method = "PUT", path = "/atencion/cita/cancelar", hasBody = true)
    @FormUrlEncoded
    Call<CitasResponse> cancelarCita(
            @Field("cita_id") int cita_id
    );

}
