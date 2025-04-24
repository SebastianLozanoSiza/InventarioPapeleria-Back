package com.inventarioPapeleria.demo.Services;

import com.inventarioPapeleria.demo.dto.Categorias.CategoriaDTO;
import com.inventarioPapeleria.demo.dto.Categorias.ListarCategoriaDTO;

public interface ServiceCategoria {

    ListarCategoriaDTO findAll();

    CategoriaDTO save(CategoriaDTO categoriaDTO);

    CategoriaDTO update(Long id, CategoriaDTO categoriaDTO);

    void delete(Long id);
}
