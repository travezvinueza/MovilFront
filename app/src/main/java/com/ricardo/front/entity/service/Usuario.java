package com.ricardo.front.entity.service;

import com.google.gson.annotations.SerializedName;

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
    @SerializedName("id")
    private Long id;
    @SerializedName("email")
    private String email;
    @SerializedName("contrasena")
    private String contrasena;
    @SerializedName("vigencia")
    private boolean vigencia;
    @SerializedName("role")
    private String role;
//    private Cliente cliente;

}
