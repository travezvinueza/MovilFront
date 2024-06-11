package com.ricardo.front.api;

import com.ricardo.front.entity.GenericResponse;
import com.ricardo.front.entity.service.Cliente;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ClienteApi {
    String base = "api/clientes/register";
    @POST(base)
    Call<GenericResponse<Cliente>> guardarCliente(@Body Cliente c);
}
