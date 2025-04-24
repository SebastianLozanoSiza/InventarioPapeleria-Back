package com.inventarioPapeleria.demo.dto.Productos;

import java.util.Date;

import lombok.Data;

@Data
public class ListarProductosDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private int stockActual;
    private int stockMinimo;
    private Double precioCompra;
    private Double precioVenta;
    private Date fechaRegistro;
    private Long idCategoria;
    private String categoria;

}
