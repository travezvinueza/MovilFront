package com.ricardo.front.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.ricardo.front.util.GenericResponse;
import com.ricardo.front.model.UsuarioDTO;
import com.ricardo.front.repository.UsuarioRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UsuarioViewModel extends AndroidViewModel {

    private final UsuarioRepository repository;
    private final MutableLiveData<GenericResponse<List<UsuarioDTO>>> usuariosLista;

    public UsuarioViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.repository = UsuarioRepository.getInstance();

        this.usuariosLista = new MutableLiveData<>();
        loadUsuarios();
    }

    public LiveData<GenericResponse<List<UsuarioDTO>>> getUsuariosLista() {
        return usuariosLista;
    }
    private void loadUsuarios() {
        repository.getUserLista().observeForever(new Observer<GenericResponse<List<UsuarioDTO>>>() {
            @Override
            public void onChanged(GenericResponse<List<UsuarioDTO>> response) {
                usuariosLista.postValue(response);
            }
        });
    }

    public void refreshUsuarios() {
        loadUsuarios();
    }

//    public LiveData<GenericResponse<List<UsuarioDTO>>> getUsuariosLista() {
//        return repository.getUserLista();
//    }

    public LiveData<GenericResponse<UsuarioDTO>> crearUsuario(UsuarioDTO usuarioDTO){
        return this.repository.crearUsuario(usuarioDTO);
    }

    public LiveData<GenericResponse<UsuarioDTO>> getByIdUsuario(long id) {
        return this.repository.getByIdUsuario(id);
    }

    public LiveData<GenericResponse<UsuarioDTO>> actualizarUsuario(long id, UsuarioDTO usuarioDTO) {
        return this.repository.actualizarUsuario(id, usuarioDTO);
    }

    public LiveData<GenericResponse<UsuarioDTO>> login(String username, String password){
        return this.repository.login(username, password);
    }

    public LiveData<GenericResponse<UsuarioDTO>> toggleVigencia(long id, boolean vigencia) {
        return repository.toggleVigencia(id, vigencia);
    }

    public LiveData<GenericResponse<String>> forgotPassword(String email) {
        return repository.forgotPassword(email);
    }

    public LiveData<GenericResponse<String>> resetPassword(String otp, String newPassword) {
        return repository.resetPassword(otp, newPassword);
    }

    public LiveData<GenericResponse<UsuarioDTO>> eliminarUsuario(long idUsuario) {
        return repository.eliminarUsuario(idUsuario);
    }

}

