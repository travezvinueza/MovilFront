package com.ricardo.front.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ricardo.front.entity.GenericResponse;
import com.ricardo.front.entity.service.Usuario;
import com.ricardo.front.repository.UsuarioRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UsuarioViewModel extends AndroidViewModel {
    private final UsuarioRepository repository;
    public UsuarioViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.repository = UsuarioRepository.getInstance();
    }
    public LiveData<GenericResponse<Usuario>> crearUsuario(Usuario usuario){
        return this.repository.crearUsuario(usuario);
    }
    public LiveData<GenericResponse<Usuario>> actualizarUsuario(long id, Usuario usuario) {
        return this.repository.actualizarUsuario(id, usuario);
    }
    public LiveData<GenericResponse<Usuario>> login(String username, String password){
        return this.repository.login(username, password);
    }
    public LiveData<GenericResponse<Usuario>> toggleVigencia(long id, boolean vigencia) {
        return repository.toggleVigencia(id, vigencia);
    }

    public LiveData<GenericResponse<String>> forgotPassword(String email) {
        return repository.forgotPassword(email);
    }

    public LiveData<GenericResponse<String>> resetPassword(String otp, String newPassword) {
        return repository.resetPassword(otp, newPassword);
    }

    public LiveData<GenericResponse<List<Usuario>>> getUsuariosLista() {
        return repository.getUsuariosLista();
    }
    public LiveData<GenericResponse<Usuario>> eliminarUsuario(long idUsuario) {
        return repository.eliminarUsuario(idUsuario);
    }

}

