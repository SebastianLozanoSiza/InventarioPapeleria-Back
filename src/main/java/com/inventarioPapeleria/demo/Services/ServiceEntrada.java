package com.inventarioPapeleria.demo.Services;

import com.inventarioPapeleria.demo.dto.Entrada.Crear.EntradaDTO;
import com.inventarioPapeleria.demo.dto.Entrada.Listar.RepuestaListarEntradaDTO;

public interface ServiceEntrada {

    RepuestaListarEntradaDTO findAll();

    EntradaDTO save(EntradaDTO entradaDTO);

    EntradaDTO update(Long id, EntradaDTO entradaDTO);

    void delete(Long id);
}
