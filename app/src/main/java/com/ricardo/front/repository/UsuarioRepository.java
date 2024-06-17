package com.ricardo.front.repository;

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
                    repository = new UsuarioRepository();
                }
            }
        }
        return repository;
    }

    // Método para crear un  usuario
    public LiveData<GenericResponse<Usuario>> crearUsuario(Usuario usuario) {
        final MutableLiveData<GenericResponse<Usuario>> mld = new MutableLiveData<>();
        api.crearUsuario(usuario).enqueue(new Callback<GenericResponse<Usuario>>() {
            @Override
            public void onResponse(Call<GenericResponse<Usuario>> call, Response<GenericResponse<Usuario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mld.setValue(response.body());
                } else {
                    GenericResponse<Usuario> errorResponse = new GenericResponse<>();
                    errorResponse.setMessage("Error en la respuesta de la API");
                    mld.setValue(errorResponse);
                    Log.e("ClienteRepository", "Error en la respuesta de la API: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<Usuario>> call, Throwable t) {
                GenericResponse<Usuario> errorResponse = new GenericResponse<>();
                errorResponse.setMessage("Error de red: " + t.getMessage());
                mld.setValue(errorResponse);
                Log.e("ClienteRepository", "Error de red", t);
            }
        });
        return mld;
    }

    // Método para actualizar un usuario
    public LiveData<GenericResponse<Usuario>> actualizarUsuario(Long id, Usuario usuario) {
        final MutableLiveData<GenericResponse<Usuario>> mld = new MutableLiveData<>();
        api.actualizarUsuario(id, usuario).enqueue(new Callback<GenericResponse<Usuario>>() {
            @Override
            public void onResponse(Call<GenericResponse<Usuario>> call, Response<GenericResponse<Usuario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mld.setValue(response.body());
                } else {
                    GenericResponse<Usuario> errorResponse = new GenericResponse<>();
                    errorResponse.setMessage("Error en la respuesta de la API");
                    mld.setValue(errorResponse);
                    Log.e("UsuarioRepository", "Error en la respuesta de la API: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<Usuario>> call, Throwable t) {
                GenericResponse<Usuario> errorResponse = new GenericResponse<>();
                errorResponse.setMessage("Error de red: " + t.getMessage());
                mld.setValue(errorResponse);
                Log.e("UsuarioRepository", "Error de red", t);
            }
        });
        return mld;
    }

    // Método para traer una lista de los usuarios
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

    // Método para loguearse
    public LiveData<GenericResponse<Usuario>> login(String username, String contrasenia){
        final MutableLiveData<GenericResponse<Usuario>> mld = new MutableLiveData<>();
        this.api.login(username, contrasenia).enqueue(new Callback<GenericResponse<Usuario>>() {
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

    // Método para enviar email y recuoerar la contraseña del usuario
    public LiveData<GenericResponse<String>> forgotPassword(String email) {
        final MutableLiveData<GenericResponse<String>> mld = new MutableLiveData<>();
        api.forgotPassword(email).enqueue(new Callback<GenericResponse<String>>() {
            @Override
            public void onResponse(Call<GenericResponse<String>> call, Response<GenericResponse<String>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<String>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                t.printStackTrace();
            }
        });
        return mld;
    }

    // Método para resetPassword del usuario
    public LiveData<GenericResponse<String>> resetPassword(String otp, String newPassword) {
        final MutableLiveData<GenericResponse<String>> mld = new MutableLiveData<>();
        api.resetPassword(otp, newPassword).enqueue(new Callback<GenericResponse<String>>() {
            @Override
            public void onResponse(Call<GenericResponse<String>> call, Response<GenericResponse<String>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<String>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
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

}
