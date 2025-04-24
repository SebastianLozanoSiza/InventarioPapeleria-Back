package com.inventarioPapeleria.demo.dto.Proveedor;

import java.util.List;

import com.inventarioPapeleria.demo.Repositories.Entities.Proveedor;
import com.inventarioPapeleria.demo.dto.RespuestaDTO;

import lombok.Data;

@Data
public class ListarProveedorDTO {
    private RespuestaDTO Respuesta;
    private List<Proveedor> proveedor;
}
