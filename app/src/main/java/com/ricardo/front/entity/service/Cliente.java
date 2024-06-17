package com.ricardo.front.entity.service;

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
    private LocalDate fecha;
    private Long usuarioId;
    private UsuarioRegistroDTO usuarioRegistroDTO;
}

