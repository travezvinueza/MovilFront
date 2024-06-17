package com.ricardo.front.entity.service;

import com.ricardo.front.entity.service.dto.UsuarioClienteDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario  {
    private Long id;
    private String username;
    private String email;
    private String contrasena;
    private boolean vigencia;
    private LocalDate fecha;
    private String otp;
    private String role;
    private Long clienteId;
    private UsuarioClienteDTO usuarioClienteDTO;
}
