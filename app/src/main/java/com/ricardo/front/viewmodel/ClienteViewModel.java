package com.ricardo.front.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ricardo.front.util.GenericResponse;
import com.ricardo.front.model.ClienteDTO;
import com.ricardo.front.repository.ClienteRepository;

public class ClienteViewModel extends AndroidViewModel {
    private final ClienteRepository repository;

    public ClienteViewModel(@NonNull Application application) {
        super(application);
        this.repository = ClienteRepository.getInstance();
    }

    public LiveData<GenericResponse<ClienteDTO>> guardarCliente(ClienteDTO c){
        return  repository.guardarCliente(c);
    }
}

