package com.inventarioPapeleria.demo.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.inventarioPapeleria.demo.Repositories.Entities.Entrada;

public interface RepositoryEntrada extends CrudRepository<Entrada,Long> {
    
}
