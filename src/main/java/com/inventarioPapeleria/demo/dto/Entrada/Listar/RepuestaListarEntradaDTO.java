package com.inventarioPapeleria.demo.dto.Entrada.Listar;

import java.util.List;

import com.inventarioPapeleria.demo.dto.RespuestaDTO;

import lombok.Data;

@Data
public class RepuestaListarEntradaDTO {
    private RespuestaDTO Repuesta;
    private List<ListarEntradaDTO> entradas;
}
