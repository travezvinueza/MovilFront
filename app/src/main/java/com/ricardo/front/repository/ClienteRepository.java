package com.ricardo.front.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ricardo.front.api.ClienteApi;
import com.ricardo.front.api.ConfigApi;
import com.ricardo.front.util.GenericResponse;
import com.ricardo.front.model.ClienteDTO;

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

    public LiveData<GenericResponse<ClienteDTO>> guardarCliente(ClienteDTO c) {
        final MutableLiveData<GenericResponse<ClienteDTO>> mld = new MutableLiveData<>();
        api.guardarCliente(c).enqueue(new Callback<GenericResponse<ClienteDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<ClienteDTO>> call, Response<GenericResponse<ClienteDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mld.setValue(response.body());
                } else {
                    GenericResponse<ClienteDTO> errorResponse = new GenericResponse<>();
                    errorResponse.setMessage("Error en la respuesta de la API");
                    mld.setValue(errorResponse);
                    Log.e("ClienteRepository", "Error en la respuesta de la API: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<ClienteDTO>> call, Throwable t) {
                GenericResponse<ClienteDTO> errorResponse = new GenericResponse<>();
                errorResponse.setMessage("Error de red: " + t.getMessage());
                mld.setValue(errorResponse);
                Log.e("ClienteRepository", "Error de red", t);
            }
        });
        return mld;
    }
}
