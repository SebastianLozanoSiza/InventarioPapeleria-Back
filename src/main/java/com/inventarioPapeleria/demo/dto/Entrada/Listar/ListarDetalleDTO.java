package com.inventarioPapeleria.demo.dto.Entrada.Listar;

import lombok.Data;

@Data
public class ListarDetalleDTO {
    private Long id;
    private Integer cantidad;
    private Double precioUnitario;
    private Long idProducto;
    private String producto;
}
