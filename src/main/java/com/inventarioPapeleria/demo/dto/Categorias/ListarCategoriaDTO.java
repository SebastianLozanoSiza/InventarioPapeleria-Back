package com.inventarioPapeleria.demo.dto.Categorias;

import java.util.List;

import com.inventarioPapeleria.demo.Repositories.Entities.Categoria;
import com.inventarioPapeleria.demo.dto.RespuestaDTO;

import lombok.Data;

@Data
public class ListarCategoriaDTO {
    private RespuestaDTO Respuesta;
    private List<Categoria> categoria;
}
