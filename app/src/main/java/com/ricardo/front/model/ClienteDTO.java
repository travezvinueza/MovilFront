package com.ricardo.front.model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private Long id;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String tipoDoc;
    private String numDoc;
    private String direccion;
    private String provincia;
    private String capital;
    private LocalDate fecha;
    @SerializedName("clienteUsuarioDTO")
    private ClienteUsuarioDTO clienteUsuarioDTO;
}

