package com.inventarioPapeleria.demo.Config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inventarioPapeleria.demo.Repositories.Entities.Categoria;
import com.inventarioPapeleria.demo.dto.Categorias.CategoriaDTO;
import com.inventarioPapeleria.demo.dto.Categorias.ListarCategoriaDTO;

@Component
public class CategoriaDTOConverter {
    
    @Autowired
    private ModelMapper dbm;

    public CategoriaDTOConverter(ModelMapper modelMapper){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.dbm = modelMapper;
    }

    public CategoriaDTO convertToDTO(Categoria categoria){
        CategoriaDTO categoriaDTO = dbm.map(categoria, CategoriaDTO.class);
        return categoriaDTO;
    }

    public Categoria convertToEntity(CategoriaDTO categoriaDTO){
        Categoria categoria = dbm.map(categoriaDTO, Categoria.class);
        return categoria;
    }

    public ListarCategoriaDTO convertListarToDTO(Categoria categoria){
        ListarCategoriaDTO listarCategoriaDTO = dbm.map(categoria, ListarCategoriaDTO.class);
        return listarCategoriaDTO;
    }
}
