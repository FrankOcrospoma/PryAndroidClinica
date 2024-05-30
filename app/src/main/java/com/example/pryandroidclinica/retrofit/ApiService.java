package com.example.pryandroidclinica.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

import com.example.pryandroidclinica.response.LoginResponse;
//import com.example.pryandroidclinica.response.ProductoCatalogoResponse;

public interface ApiService {
    @FormUrlEncoded
    @POST("/login")
    Call<LoginResponse> login(@Field("email") String email, @Field("clave") String clave);

   // @GET("/producto/catalogo/{id}/{almacen_id}")
   // Call<ProductoCatalogoResponse> catalogo(@Path("id") int id, @Path("almacen_id") int almacen_id);

}
