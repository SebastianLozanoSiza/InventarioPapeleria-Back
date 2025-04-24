package com.inventarioPapeleria.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.inventarioPapeleria.demo.Repositories.Entities.Categoria;

public interface RepositoryCategoria extends CrudRepository<Categoria,Long>{
    
    @Query("SELECT c FROM Categoria c ORDER BY c.idCategoria ASC")
    List<Categoria> findAllOrdered();

    boolean existsByNombre(String nombre);
}
