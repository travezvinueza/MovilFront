package com.ricardo.front.model;

import java.time.LocalDate;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private Long id;
    private String role;
    private LocalDate fecha;
}

