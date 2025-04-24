package com.inventarioPapeleria.demo.dto.Productos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Column(nullable = false)
    private String descripcion;

    @Min(value = 1, message = "El stock actual debe ser mínimo 1")
    @Column(nullable = false)
    private int stockActual;    

    @Min(value = 1, message = "El stock minimo debe ser mínimo 1")
    @Column(nullable = false)
    private int stockMinimo;

    @NotNull(message = "El precio de compra no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de compra debe ser mayor que 0")
    @Column(nullable = false)
    private Double precioCompra;
    
    @NotNull(message = "El precio de venta no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de venta debe ser mayor que 0")
    @Column(nullable = false)
    private Double precioVenta;

    @NotNull(message = "La categoría no puede ser nula")
    private Long idCategoria; 
}