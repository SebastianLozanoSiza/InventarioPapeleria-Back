package com.inventarioPapeleria.demo.Services;

import com.inventarioPapeleria.demo.dto.Productos.ProductoDTO;
import com.inventarioPapeleria.demo.dto.Productos.RespuestaListarProductosDTO;

public interface ServiceProducto {

    RespuestaListarProductosDTO findAll();

    ProductoDTO save(ProductoDTO productosDTO);

    ProductoDTO update(Long id, ProductoDTO productosDTO);

    void delete(Long id);
}
