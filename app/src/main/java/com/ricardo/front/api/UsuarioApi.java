package com.ricardo.front.api;

import com.ricardo.front.entity.GenericResponse;
import com.ricardo.front.entity.service.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UsuarioApi {
    String base = "api/usuarios";

    @POST(base + "/create")
    Call<GenericResponse<Usuario>> crearUsuario(@Body Usuario usuario);

    @PUT(base + "/update/{id}")
    Call<GenericResponse<Usuario>> actualizarUsuario(@Path("id") Long  id, @Body Usuario usuario);

    @FormUrlEncoded
    @POST(base + "/login")
    Call<GenericResponse<Usuario>> login(@Field("username") String username, @Field("password") String contrasenia);

    @GET(base + "/listar")
    Call<GenericResponse<List<Usuario>>> getUsuariosLista();

    @DELETE(base + "/delete/{id}")
    Call<GenericResponse<Usuario>> eliminarUsuario(@Path("id") Long  id);

    @POST(base + "/toggle-vigencia/{id}")
    Call<GenericResponse<Usuario>> toggleVigencia(@Path("id") Long id, @Query("vigencia") boolean vigencia);

    @FormUrlEncoded
    @POST(base + "/forgot-password")
    Call<GenericResponse<String>> forgotPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST(base + "/reset-password")
    Call<GenericResponse<String>> resetPassword(@Field("otp") String otp, @Field("newPassword") String newPassword);


}
