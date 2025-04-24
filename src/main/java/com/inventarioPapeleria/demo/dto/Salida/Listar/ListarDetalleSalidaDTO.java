package com.inventarioPapeleria.demo.dto.Salida.Listar;

import lombok.Data;

@Data
public class ListarDetalleSalidaDTO {
    private Long id;
    private int cantidad;
    private Double precioUnitario;
    private Long idProducto;
    private String producto;
}

