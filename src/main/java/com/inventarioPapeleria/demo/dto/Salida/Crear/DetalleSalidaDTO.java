package com.inventarioPapeleria.demo.dto.Salida.Crear;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DetalleSalidaDTO {

    @Min(value = 1, message = "La cantidad debe ser mínimo 1")
    @Column(nullable = false)
    private int cantidad;

    @NotNull(message = "El precio unitario no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio unitario debe ser mayor que 0")
    @Column(nullable = false)
    private Double precioUnitario;

    @NotNull(message = "El producto no puede ser nulo")
    private Long idProducto;
}
