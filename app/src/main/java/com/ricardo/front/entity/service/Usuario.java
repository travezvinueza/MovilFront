package com.ricardo.front.entity.service;

import java.io.Serializable;

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
    private String email;
    private String contrasena;
    private boolean vigencia;
    private String role;
    private Cliente cliente;

}
