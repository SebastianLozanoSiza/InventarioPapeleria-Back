package com.inventarioPapeleria.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaDTO {
    private boolean Error;
    private String codigo;
    private String Descripcion;
}
