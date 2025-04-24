package com.inventarioPapeleria.demo.dto.Proveedor;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProveedorDTO {
    
    @Column(nullable = false)
    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;

    @Column(nullable = false)
    @Email(message = "El correo no tiene un formato valido")
    @NotBlank(message = "El correo no puede estar vacio")
    private String correo;

    @Column(nullable = false)
    @NotBlank(message = "El telefono no puede estar vacio")
    private String telefono;
}
