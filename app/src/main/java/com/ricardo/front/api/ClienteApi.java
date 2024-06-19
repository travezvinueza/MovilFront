package com.ricardo.front.api;

import com.ricardo.front.util.GenericResponse;
import com.ricardo.front.model.ClienteDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ClienteApi {
    String base = "api/clientes";

    @POST(base + "/register")
    Call<GenericResponse<ClienteDTO>> guardarCliente(@Body ClienteDTO c);

    @GET(base + "/list")
    Call<GenericResponse<List<ClienteDTO>>> getListClient();

    @GET(base + "/getById/{id}")
    Call<GenericResponse<ClienteDTO>> getClienteById(@Path("id") Long  id);
}
