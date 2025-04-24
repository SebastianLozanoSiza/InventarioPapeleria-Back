package com.inventarioPapeleria.demo.Services;

import com.inventarioPapeleria.demo.dto.Salida.Crear.SalidaDTO;
import com.inventarioPapeleria.demo.dto.Salida.Listar.RespuestaListarSalidaDTO;

public interface ServiceSalida {

    RespuestaListarSalidaDTO findAll();

    SalidaDTO save(SalidaDTO salidaDTO);

    SalidaDTO update(Long id, SalidaDTO salidaDTO);

    void delete(Long id);
}
