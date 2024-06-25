package com.ricardo.front.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private String role;
    private Long clienteId;
    @SerializedName("usuarioClienteDto")
    @Expose
    private UsuarioClienteDTO usuarioClienteDto;
}
