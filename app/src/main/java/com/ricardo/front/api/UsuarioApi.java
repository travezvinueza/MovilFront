package com.ricardo.front.api;

import com.ricardo.front.util.GenericResponse;
import com.ricardo.front.model.UsuarioDTO;

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
    Call<GenericResponse<UsuarioDTO>> crearUsuario(@Body UsuarioDTO usuarioDTO);

    @GET(base + "/getById/{id}")
    Call<GenericResponse<UsuarioDTO>> getByIdUsuario(@Path("id") Long  id);

    @PUT(base + "/update/{id}")
    Call<GenericResponse<UsuarioDTO>> actualizarUsuario(@Path("id") Long  id, @Body UsuarioDTO usuarioDTO);

    @FormUrlEncoded
    @POST(base + "/login")
    Call<GenericResponse<UsuarioDTO>> login(@Field("username") String username, @Field("password") String contrasenia);

    @GET(base + "/listar")
    Call<GenericResponse<List<UsuarioDTO>>> getUsuariosLista();

    @DELETE(base + "/delete/{id}")
    Call<GenericResponse<UsuarioDTO>> eliminarUsuario(@Path("id") Long  id);

    @POST(base + "/toggle-vigencia/{id}")
    Call<GenericResponse<UsuarioDTO>> toggleVigencia(@Path("id") Long id, @Query("vigencia") boolean vigencia);

    @FormUrlEncoded
    @POST(base + "/forgot-password")
    Call<GenericResponse<String>> forgotPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST(base + "/reset-password")
    Call<GenericResponse<String>> resetPassword(@Field("otp") String otp, @Field("newPassword") String newPassword);

}