package com.ricardo.front.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ricardo.front.api.ClienteApi;
import com.ricardo.front.api.ConfigApi;
import com.ricardo.front.entity.GenericResponse;
import com.ricardo.front.entity.service.Cliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClienteRepository {
    private static ClienteRepository repository;
    private final ClienteApi api;

    public static ClienteRepository getInstance(){
        if(repository == null){
            repository = new ClienteRepository();
        }
        return repository;
    }
    private ClienteRepository(){
        api = ConfigApi.getClienteApi();
    }

    public LiveData<GenericResponse<Cliente>> guardarCliente(Cliente c) {
        final MutableLiveData<GenericResponse<Cliente>> mld = new MutableLiveData<>();
        api.guardarCliente(c).enqueue(new Callback<GenericResponse<Cliente>>() {
            @Override
            public void onResponse(Call<GenericResponse<Cliente>> call, Response<GenericResponse<Cliente>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mld.setValue(response.body());
                } else {
                    GenericResponse<Cliente> errorResponse = new GenericResponse<>();
                    errorResponse.setMessage("Error en la respuesta de la API");
                    mld.setValue(errorResponse);
                    Log.e("ClienteRepository", "Error en la respuesta de la API: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<Cliente>> call, Throwable t) {
                GenericResponse<Cliente> errorResponse = new GenericResponse<>();
                errorResponse.setMessage("Error de red: " + t.getMessage());
                mld.setValue(errorResponse);
                Log.e("ClienteRepository", "Error de red", t);
            }
        });
        return mld;
    }
}
