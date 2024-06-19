package com.ricardo.front.model;


import java.time.LocalDate;

import lombok.*;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteUsuarioDTO {
    private String id;
    private String username;
    private String email;
    private String contrasena;
    private boolean vigencia;
    private LocalDate fecha;
}
