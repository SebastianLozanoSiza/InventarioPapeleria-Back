package com.inventarioPapeleria.demo.dto.Salida.Listar;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ListarSalidaDTO {
    private Long id;
    private String motivo;
    private Date fecha;
    private List<ListarDetalleSalidaDTO> detalleSalida;
}
