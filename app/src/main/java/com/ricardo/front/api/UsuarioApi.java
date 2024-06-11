package com.ricardo.front.api;

import com.ricardo.front.entity.GenericResponse;
import com.ricardo.front.entity.service.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UsuarioApi {
    String base = "api/usuarios";

    @FormUrlEncoded
    @POST(base + "/login")
    Call<GenericResponse<Usuario>> login(@Field("email") String email, @Field("password") String contrasenia);

    @POST(base)
    Call<GenericResponse<Usuario>> save (@Body Usuario u);

    @GET(base + "/listar")
    Call<GenericResponse<List<Usuario>>> getUsuariosLista();
}
