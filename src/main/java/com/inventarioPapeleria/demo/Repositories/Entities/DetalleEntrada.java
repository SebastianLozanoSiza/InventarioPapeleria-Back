package com.inventarioPapeleria.demo.Repositories.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "detalle_entradas")
public class DetalleEntrada {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetalleEntrada;

    private int cantidad;

    private Double precioUnitario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entrada", nullable = false)
    private Entrada entrada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;
}
