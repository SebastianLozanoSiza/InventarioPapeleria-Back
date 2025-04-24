package com.inventarioPapeleria.demo.Services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.inventarioPapeleria.demo.Config.CategoriaDTOConverter;
import com.inventarioPapeleria.demo.Repositories.RepositoryCategoria;
import com.inventarioPapeleria.demo.Repositories.Entities.Categoria;
import com.inventarioPapeleria.demo.Services.ServiceCategoria;
import com.inventarioPapeleria.demo.dto.RespuestaDTO;
import com.inventarioPapeleria.demo.dto.Categorias.CategoriaDTO;
import com.inventarioPapeleria.demo.dto.Categorias.ListarCategoriaDTO;

@Service
public class ServiceCategoriaImpl implements ServiceCategoria{

    @Autowired
    private CategoriaDTOConverter convert;

    @Autowired
    private RepositoryCategoria repositoryCategoria;


    @Override
    public ListarCategoriaDTO findAll() {
        ListarCategoriaDTO listarCategoriaDTO = new ListarCategoriaDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();

        try {
            List<Categoria> categorias = repositoryCategoria.findAllOrdered();
            respuestaDTO.setError(false);
            respuestaDTO.setCodigo("200");
            respuestaDTO.setDescripcion("Categorias encontradas correctamente");
            listarCategoriaDTO.setCategoria(categorias);
        } catch (Exception e) {
            respuestaDTO.setError(true);
            respuestaDTO.setCodigo("500");
            respuestaDTO.setDescripcion("Error al obtener las categorias: " + e.getMessage());
            listarCategoriaDTO.setCategoria(null);
        }
        listarCategoriaDTO.setRespuesta(respuestaDTO);
        return listarCategoriaDTO;
    }

    @Override
    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        if (repositoryCategoria.existsByNombre(categoriaDTO.getNombre())) {
            throw new IllegalArgumentException("Ya existe una categoria con el nombre " + categoriaDTO.getNombre());
        }

        Categoria categoria = convert.convertToEntity(categoriaDTO);
        Categoria savedCategoria = repositoryCategoria.save(categoria);
        return convert.convertToDTO(savedCategoria);
    }

    @Override
    public CategoriaDTO update(Long id, CategoriaDTO categoriaDTO) {
        Optional<Categoria> categoriaCurrentOptional = repositoryCategoria.findById(id);
        if (categoriaCurrentOptional.isPresent()) {
            Categoria categoriaCurrent = categoriaCurrentOptional.get();
            if (repositoryCategoria.existsByNombre(categoriaDTO.getNombre()) && !categoriaCurrent.getNombre().equals(categoriaDTO.getNombre())) {
                throw new DataIntegrityViolationException("El nombre de la categoría ya está en uso");
            }
            categoriaCurrent.setNombre(categoriaDTO.getNombre());
            Categoria updatedCategoria = repositoryCategoria.save(categoriaCurrent);
            return convert.convertToDTO(updatedCategoria);
        }
        throw new IllegalArgumentException("La categoria con ID " + id + " no existe");
    }

    @Override
    public void delete(Long id) {
        Optional<Categoria> categoriaOptional = repositoryCategoria.findById(id);
        if (categoriaOptional.isPresent()) {
            repositoryCategoria.delete(categoriaOptional.get());
        }else{
            throw new IllegalArgumentException("La categoria con ID " + id + " no existe");
        }
    }


    
}
