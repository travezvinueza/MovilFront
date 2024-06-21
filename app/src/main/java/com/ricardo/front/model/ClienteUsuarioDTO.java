package com.ricardo.front.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

import lombok.*;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteUsuarioDTO {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("contrasena")
    @Expose
    private String contrasena;
    @SerializedName("vigencia")
    @Expose
    private boolean vigencia;
    @SerializedName("fecha")
    @Expose
    private LocalDate fecha;
    @SerializedName("clienteId")
    @Expose
    private Long clienteId;
}
