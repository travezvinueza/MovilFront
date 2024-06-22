package com.ricardo.front.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ricardo.front.api.ClienteApi;
import com.ricardo.front.api.ConfigApi;
import com.ricardo.front.util.GenericResponse;
import com.ricardo.front.model.ClienteDTO;
import com.ricardo.front.util.Global;

import okhttp3.ResponseBody;
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
            public void onResponse(@NonNull Call<GenericResponse<ClienteDTO>> call, @NonNull Response<GenericResponse<ClienteDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mld.setValue(response.body());
                } else {
                    GenericResponse<ClienteDTO> errorResponse = new GenericResponse<>();
                    errorResponse.setType(Global.TIPO_RESULT);
                    errorResponse.setRpta(Global.RPTA_ERROR);
                    errorResponse.setMessage("Error en la respuesta de la API");
                    errorResponse.setBody(null);
                    mld.setValue(errorResponse);
                    // Leer y cerrar el ResponseBody
                    ResponseBody errorBody = response.errorBody();
                    if (errorBody != null) {
                        try {
                            Log.e("ClienteRepository", "Error en la respuesta de la API: " + errorBody.string());
                        } catch (Exception e) {
                            Log.e("ClienteRepository", "Error al leer el cuerpo de la respuesta de error", e);
                        } finally {
                            errorBody.close();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GenericResponse<ClienteDTO>> call,@NonNull Throwable t) {
                GenericResponse<ClienteDTO> errorResponse = GenericResponse.<ClienteDTO>builder()
                        .type(Global.TIPO_AUTH)
                        .rpta(Global.RPTA_ERROR)
                        .message("Error en la llamada a la API: " + t.getMessage())
                        .body(null)
                        .build();
                mld.setValue(errorResponse);
            }
        });
        return mld;
    }
}
