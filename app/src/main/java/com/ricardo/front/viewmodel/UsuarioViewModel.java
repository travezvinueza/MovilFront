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
    public LiveData<GenericResponse<Usuario>> login(String email, String password){
        return this.repository.login(email, password);
    }

    public LiveData<GenericResponse<List<Usuario>>> getUsuariosLista() {
        return repository.getUsuariosLista();
    }



    public LiveData<GenericResponse<Usuario>> save(Usuario u){
        return this.repository.save(u);
    }
}

