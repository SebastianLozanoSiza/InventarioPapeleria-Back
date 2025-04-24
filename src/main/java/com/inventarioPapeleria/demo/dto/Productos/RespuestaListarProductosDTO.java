package com.inventarioPapeleria.demo.dto.Productos;

import java.util.List;

import com.inventarioPapeleria.demo.dto.RespuestaDTO;

import lombok.Data;

@Data
public class RespuestaListarProductosDTO {
    private RespuestaDTO Respuesta;
    private List<ListarProductosDTO> Productos;
}
