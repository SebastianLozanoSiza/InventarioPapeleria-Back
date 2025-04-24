package com.inventarioPapeleria.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.inventarioPapeleria.demo.Repositories.Entities.Producto;

public interface RepositoryProducto extends CrudRepository<Producto, Long> {

    @Query("SELECT p FROM Producto p ORDER BY p.idProducto ASC")
    List<Producto> findAllOrdered();

    boolean existsByNombre(String nombre);
}
