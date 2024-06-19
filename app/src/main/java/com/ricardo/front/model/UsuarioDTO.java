package com.ricardo.front.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
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
