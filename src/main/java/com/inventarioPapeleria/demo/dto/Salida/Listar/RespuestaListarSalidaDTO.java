package com.inventarioPapeleria.demo.dto.Salida.Listar;

import java.util.List;

import com.inventarioPapeleria.demo.dto.RespuestaDTO;

import lombok.Data;

@Data
public class RespuestaListarSalidaDTO {
    private RespuestaDTO Respuesta;
    private List<ListarSalidaDTO> detalle;
}
