package com.ricardo.front.util;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse<T> {
    private String type;
    private int rpta;
    private String message;
    private T body;
}