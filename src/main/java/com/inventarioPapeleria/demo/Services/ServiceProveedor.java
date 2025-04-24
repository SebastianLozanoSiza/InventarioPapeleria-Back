package com.inventarioPapeleria.demo.Services;

import com.inventarioPapeleria.demo.dto.Proveedor.ListarProveedorDTO;
import com.inventarioPapeleria.demo.dto.Proveedor.ProveedorDTO;

public interface ServiceProveedor {

    ListarProveedorDTO findAll();

    ProveedorDTO save(ProveedorDTO proveedorDTO);

    ProveedorDTO update(Long id, ProveedorDTO proveedorDTO);

    void delete(Long id);
}
