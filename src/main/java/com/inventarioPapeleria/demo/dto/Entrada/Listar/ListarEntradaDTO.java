package com.inventarioPapeleria.demo.dto.Entrada.Listar;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ListarEntradaDTO {
    
    private Long id;
    private Date fecha;
    private Double total;
    private Long idProveedor;
    private String proveedor;
    private List<ListarDetalleDTO> detalles;
}
