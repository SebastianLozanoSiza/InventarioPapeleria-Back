package com.inventarioPapeleria.demo.dto.Entrada.Crear;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EntradaDTO {

    @NotNull(message = "El id del proveedor no puede ser nulo")
    private Long idProveedor;

    private List<DetalleEntradaDTO> detalles;

}
