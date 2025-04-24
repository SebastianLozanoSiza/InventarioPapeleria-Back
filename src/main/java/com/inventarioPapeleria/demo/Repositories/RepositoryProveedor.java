package com.inventarioPapeleria.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.inventarioPapeleria.demo.Repositories.Entities.Proveedor;

public interface RepositoryProveedor extends CrudRepository<Proveedor, Long> {

    @Query("SELECT p FROM Proveedor p ORDER BY p.idProveedor ASC")
    List<Proveedor> findAllOrdered();

    boolean existsByNombre(String nombre);

    boolean existsByCorreo(String correo);
}
