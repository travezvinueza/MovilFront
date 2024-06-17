package com.ricardo.front.entity.service.dto;


import java.time.LocalDate;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRegistroDTO {
    private String id;
    private String username;
    private String email;
    private String contrasena;
    private boolean vigencia;
    private LocalDate fecha;
}
