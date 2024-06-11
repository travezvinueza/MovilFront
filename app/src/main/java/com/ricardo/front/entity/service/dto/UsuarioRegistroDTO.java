package com.ricardo.front.entity.service.dto;


import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRegistroDTO {
    private String id;
    private String email;
    private String contrasena;
    private boolean vigencia;
}
