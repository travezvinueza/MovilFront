package com.ricardo.front.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ricardo.front.api.ConfigApi;
import com.ricardo.front.api.UsuarioApi;
import com.ricardo.front.util.GenericResponse;
import com.ricardo.front.model.UsuarioDTO;

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
    public LiveData<GenericResponse<UsuarioDTO>> crearUsuario(UsuarioDTO usuarioDTO) {
        final MutableLiveData<GenericResponse<UsuarioDTO>> mld = new MutableLiveData<>();
        api.crearUsuario(usuarioDTO).enqueue(new Callback<GenericResponse<UsuarioDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<UsuarioDTO>> call, Response<GenericResponse<UsuarioDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mld.setValue(response.body());
                } else {
                    GenericResponse<UsuarioDTO> errorResponse = new GenericResponse<>();
                    errorResponse.setMessage("Error en la respuesta de la API");
                    mld.setValue(errorResponse);
                    Log.e("ClienteRepository", "Error en la respuesta de la API: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<UsuarioDTO>> call, Throwable t) {
                GenericResponse<UsuarioDTO> errorResponse = new GenericResponse<>();
                errorResponse.setMessage("Error de red: " + t.getMessage());
                mld.setValue(errorResponse);
                Log.e("ClienteRepository", "Error de red", t);
            }
        });
        return mld;
    }

    public LiveData<GenericResponse<UsuarioDTO>> getByIdUsuario(Long id) {
        final MutableLiveData<GenericResponse<UsuarioDTO>> mld = new MutableLiveData<>();
        api.getByIdUsuario(id).enqueue(new Callback<GenericResponse<UsuarioDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<UsuarioDTO>> call, Response<GenericResponse<UsuarioDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mld.setValue(response.body());
                } else {
                    GenericResponse<UsuarioDTO> errorResponse = new GenericResponse<>();
                    errorResponse.setMessage("Error en la respuesta de la API");
                    mld.setValue(errorResponse);
                    Log.e("UsuarioRepository", "Error en la respuesta de la API: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<UsuarioDTO>> call, Throwable t) {
                GenericResponse<UsuarioDTO> errorResponse = new GenericResponse<>();
                errorResponse.setMessage("Error de red: " + t.getMessage());
                mld.setValue(errorResponse);
                Log.e("UsuarioRepository", "Error de red", t);
            }
        });
        return mld;
    }

    // Método para actualizar un usuario
    public LiveData<GenericResponse<UsuarioDTO>> actualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        final MutableLiveData<GenericResponse<UsuarioDTO>> mld = new MutableLiveData<>();
        api.actualizarUsuario(id, usuarioDTO).enqueue(new Callback<GenericResponse<UsuarioDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<UsuarioDTO>> call, Response<GenericResponse<UsuarioDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mld.setValue(response.body());
                } else {
                    GenericResponse<UsuarioDTO> errorResponse = new GenericResponse<>();
                    errorResponse.setMessage("Error en la respuesta de la API");
                    mld.setValue(errorResponse);
                    Log.e("UsuarioRepository", "Error en la respuesta de la API: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<UsuarioDTO>> call, Throwable t) {
                GenericResponse<UsuarioDTO> errorResponse = new GenericResponse<>();
                errorResponse.setMessage("Error de red: " + t.getMessage());
                mld.setValue(errorResponse);
                Log.e("UsuarioRepository", "Error de red", t);
            }
        });
        return mld;
    }

    // Método para traer una lista de los usuarios
    public LiveData<GenericResponse<List<UsuarioDTO>>> getUsuariosLista() {
        final MutableLiveData<GenericResponse<List<UsuarioDTO>>> mld = new MutableLiveData<>();
        this.api.getUsuariosLista().enqueue(new Callback<GenericResponse<List<UsuarioDTO>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<UsuarioDTO>>> call, Response<GenericResponse<List<UsuarioDTO>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("UsuarioRepository", "Lista de usuarios recibida: " + response.body().getBody());
                } else {
                    Log.e("UsuarioRepository", "Error en la respuesta: " + response.code());
                }
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<UsuarioDTO>>> call, Throwable t) {
                Log.e("UsuarioRepository", "Fallo en la llamada a la API: " + t.getMessage());
                mld.setValue(new GenericResponse<>());
                t.printStackTrace();
            }
        });
        return mld;
    }

    // Método para loguearse
    public LiveData<GenericResponse<UsuarioDTO>> login(String username, String contrasenia){
        final MutableLiveData<GenericResponse<UsuarioDTO>> mld = new MutableLiveData<>();
        this.api.login(username, contrasenia).enqueue(new Callback<GenericResponse<UsuarioDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<UsuarioDTO>> call, Response<GenericResponse<UsuarioDTO>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<UsuarioDTO>> call, Throwable t) {
                mld.setValue(new GenericResponse());
                System.out.println("Se ha producido un error al iniciar sesión: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    // Método para activar y desactivar un usuario por su ID
    public LiveData<GenericResponse<UsuarioDTO>> toggleVigencia(long id, boolean vigencia) {
        final MutableLiveData<GenericResponse<UsuarioDTO>> mld = new MutableLiveData<>();
        api.toggleVigencia(id, vigencia).enqueue(new Callback<GenericResponse<UsuarioDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<UsuarioDTO>> call, Response<GenericResponse<UsuarioDTO>> response) {
                    mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<UsuarioDTO>> call, Throwable t) {
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
    public LiveData<GenericResponse<UsuarioDTO>> eliminarUsuario(Long id) {
        final MutableLiveData<GenericResponse<UsuarioDTO>> mld = new MutableLiveData<>();
        api.eliminarUsuario(id).enqueue(new Callback<GenericResponse<UsuarioDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<UsuarioDTO>> call, Response<GenericResponse<UsuarioDTO>> response) {
                    mld.setValue(response.body());
            }
            @Override
            public void onFailure(Call<GenericResponse<UsuarioDTO>> call, Throwable t) {
                mld.setValue(new GenericResponse());
                System.out.println("Se ha producido un error : " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

}
