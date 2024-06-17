package com.ricardo.front.entity.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioClienteDTO {
    private String nombres;
    private String apellidos;
    private String telefono;
    private String tipoDoc;
    private String numDoc;
    private String direccion;
    private String provincia;
    private String capital;
    private LocalDate fecha;
}

