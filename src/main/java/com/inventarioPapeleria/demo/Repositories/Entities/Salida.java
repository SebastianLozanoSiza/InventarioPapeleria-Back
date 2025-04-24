package com.inventarioPapeleria.demo.Repositories.Entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "salidas")
public class Salida {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSalida;

    private String motivoSalida;

    private Date fecha;
}
