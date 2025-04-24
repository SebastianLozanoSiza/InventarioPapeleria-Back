package com.inventarioPapeleria.demo.dto.Salida.Crear;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SalidaDTO {

    @NotBlank(message = "El motivo no puede estar vacío")
    @Column(nullable = false)
    private String motivoSalida;
    
    private List<DetalleSalidaDTO> detalleSalida;
}
