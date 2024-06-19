package com.ricardo.front.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioClienteDTO {
    @SerializedName("id")
    private Long id;
    @SerializedName("nombres")
    private String nombres;
    @SerializedName("apellidos")
    private String apellidos;
    @SerializedName("telefono")
    private String telefono;
    @SerializedName("tipoDoc")
    private String tipoDoc;
    @SerializedName("numDoc")
    private String numDoc;
    @SerializedName("direccion")
    private String direccion;
    @SerializedName("provincia")
    private String provincia;
    @SerializedName("capital")
    private String capital;
    @SerializedName("fecha")
    private LocalDate fecha;
    @SerializedName("usuarioId")
    private Long usuarioId;
}

