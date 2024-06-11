package com.ricardo.front.entity.service;

import com.google.gson.annotations.SerializedName;
import com.ricardo.front.entity.service.dto.UsuarioRegistroDTO;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    private String nombres;
    private String apellidos;
    private String telefono;
    private String tipoDoc;
    private String numDoc;
    private String direccion;
    private String provincia;
    private String capital;
    @SerializedName("fecha")
    private LocalDate fecha;
    private UsuarioRegistroDTO usuarioRegistroDTO;
}

