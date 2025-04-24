package com.inventarioPapeleria.demo.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.inventarioPapeleria.demo.Repositories.Entities.DetalleEntrada;

public interface RepositoryDetalleEntrada extends CrudRepository<DetalleEntrada,Long>{
    
}
