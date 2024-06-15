package com.ricardo.front.repository;

import static com.ricardo.front.utils.Global.RPTA_ERROR;
import static com.ricardo.front.utils.Global.TIPO_RESULT;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ricardo.front.api.ConfigApi;
import com.ricardo.front.api.UsuarioApi;
import com.ricardo.front.entity.GenericResponse;
import com.ricardo.front.entity.service.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class UsuarioRepository {
    private static UsuarioRepository repository;
    private final UsuarioApi api;

    public UsuarioRepository() {
        this.api = ConfigApi.getUsuarioApi();
    }
    public static UsuarioRepository getInstance() {
        if (repository == null) {
            synchronized (UsuarioRepository.class) {
                if (repository == null) {
                    repository = new UsuarioRepository();  // Punto de interrupción aquí
                }
            }
        }
        return repository;
    }


    public LiveData<GenericResponse<List<Usuario>>> getUsuariosLista() {
        final MutableLiveData<GenericResponse<List<Usuario>>> mld = new MutableLiveData<>();
        this.api.getUsuariosLista().enqueue(new Callback<GenericResponse<List<Usuario>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Usuario>>> call, Response<GenericResponse<List<Usuario>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("UsuarioRepository", "Lista de usuarios recibida: " + response.body().getBody());
                } else {
                    Log.e("UsuarioRepository", "Error en la respuesta: " + response.code());
                }
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Usuario>>> call, Throwable t) {
                Log.e("UsuarioRepository", "Fallo en la llamada a la API: " + t.getMessage());
                mld.setValue(new GenericResponse<>());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<GenericResponse<Usuario>> login(String email, String contrasenia){
        final MutableLiveData<GenericResponse<Usuario>> mld = new MutableLiveData<>();
        this.api.login(email, contrasenia).enqueue(new Callback<GenericResponse<Usuario>>() {
            @Override
            public void onResponse(Call<GenericResponse<Usuario>> call, Response<GenericResponse<Usuario>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Usuario>> call, Throwable t) {
                mld.setValue(new GenericResponse());
                System.out.println("Se ha producido un error al iniciar sesión: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    // Método para activar y desactivar un usuario por su ID
    public LiveData<GenericResponse<Usuario>> toggleVigencia(long id, boolean vigencia) {
        final MutableLiveData<GenericResponse<Usuario>> mld = new MutableLiveData<>();
        api.toggleVigencia(id, vigencia).enqueue(new Callback<GenericResponse<Usuario>>() {
            @Override
            public void onResponse(Call<GenericResponse<Usuario>> call, Response<GenericResponse<Usuario>> response) {
                    mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Usuario>> call, Throwable t) {
                mld.setValue(new GenericResponse());
                System.out.println("Se ha producido un error : " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }


    // Método para eliminar un usuario por su ID
    public LiveData<GenericResponse<Usuario>> eliminarUsuario(Long id) {
        final MutableLiveData<GenericResponse<Usuario>> mld = new MutableLiveData<>();
        api.eliminarUsuario(id).enqueue(new Callback<GenericResponse<Usuario>>() {
            @Override
            public void onResponse(Call<GenericResponse<Usuario>> call, Response<GenericResponse<Usuario>> response) {
                    mld.setValue(response.body());
            }
            @Override
            public void onFailure(Call<GenericResponse<Usuario>> call, Throwable t) {
                mld.setValue(new GenericResponse());
                System.out.println("Se ha producido un error : " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<GenericResponse<Usuario>> save (Usuario u){
        final MutableLiveData<GenericResponse<Usuario>> mld = new MutableLiveData<>();
        this.api.save(u).enqueue(new Callback<GenericResponse<Usuario>>() {
            @Override
            public void onResponse(Call<GenericResponse<Usuario>> call, Response<GenericResponse<Usuario>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Usuario>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error : " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

}
