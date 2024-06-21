package com.ricardo.front.model;

import com.google.gson.annotations.Expose;
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
    @Expose
    private Long id;
    @SerializedName("nombres")
    @Expose
    private String nombres;
    @SerializedName("apellidos")
    @Expose
    private String apellidos;
    @SerializedName("telefono")
    @Expose
    private String telefono;
    @SerializedName("tipoDoc")
    @Expose
    private String tipoDoc;
    @SerializedName("numDoc")
    @Expose
    private String numDoc;
    @SerializedName("direccion")
    @Expose
    private String direccion;
    @SerializedName("provincia")
    @Expose
    private String provincia;
    @SerializedName("capital")
    @Expose
    private String capital;
    @SerializedName("fecha")
    @Expose
    private LocalDate fecha;
}

