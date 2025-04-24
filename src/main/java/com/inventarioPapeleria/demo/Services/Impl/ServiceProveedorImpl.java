package com.inventarioPapeleria.demo.Services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.inventarioPapeleria.demo.Config.ProveedorDTOConverter;
import com.inventarioPapeleria.demo.Repositories.RepositoryProveedor;
import com.inventarioPapeleria.demo.Repositories.Entities.Proveedor;
import com.inventarioPapeleria.demo.Services.ServiceProveedor;
import com.inventarioPapeleria.demo.dto.RespuestaDTO;
import com.inventarioPapeleria.demo.dto.Proveedor.ListarProveedorDTO;
import com.inventarioPapeleria.demo.dto.Proveedor.ProveedorDTO;

@Service
public class ServiceProveedorImpl implements ServiceProveedor {

    @Autowired
    private ProveedorDTOConverter convert;

    @Autowired
    private RepositoryProveedor repositoryProveedor;

    @Override
    public ListarProveedorDTO findAll() {
        ListarProveedorDTO llistarProveedorDTO = new ListarProveedorDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();

        try {
            List<Proveedor> proveedor = repositoryProveedor.findAllOrdered();
            respuestaDTO.setError(false);
            respuestaDTO.setCodigo("200");
            respuestaDTO.setDescripcion("Proveedores encontrados correctamente");
            llistarProveedorDTO.setProveedor(proveedor);

        } catch (Exception e) {
            respuestaDTO.setError(true);
            respuestaDTO.setCodigo("500");
            respuestaDTO.setDescripcion("Error al obtener las categorías: " + e.getMessage());
            llistarProveedorDTO.setProveedor(null);
        }

        llistarProveedorDTO.setRespuesta(respuestaDTO);
        return llistarProveedorDTO;
    }

    @Override
    public ProveedorDTO save(ProveedorDTO proveedorDTO) {
        if (repositoryProveedor.existsByCorreo(proveedorDTO.getCorreo())) {
            throw new IllegalArgumentException("Ya existe un proveedor con el correo " + proveedorDTO.getCorreo());
        }

        Proveedor proveedor = convert.convertToEntity(proveedorDTO);
        Proveedor savedProveedor = repositoryProveedor.save(proveedor);
        return convert.converToDTO(savedProveedor);
    }

    @Override
    public ProveedorDTO update(Long id, ProveedorDTO proveedorDTO) {
        Optional<Proveedor> proveedorCurrentOptional = repositoryProveedor.findById(id);
        if (proveedorCurrentOptional.isPresent()) {
            Proveedor proveedorCurrent = proveedorCurrentOptional.get();

            if (repositoryProveedor.existsByCorreo(proveedorDTO.getCorreo()) &&
                    !proveedorCurrent.getCorreo().equals(proveedorDTO.getCorreo())) {
                throw new DataIntegrityViolationException("El correo del proveedor ya está en uso.");
            }

            proveedorCurrent.setNombre(proveedorDTO.getNombre());
            proveedorCurrent.setCorreo(proveedorDTO.getCorreo());
            proveedorCurrent.setTelefono(proveedorDTO.getTelefono());

            Proveedor updatedProveedor = repositoryProveedor.save(proveedorCurrent);
            return convert.converToDTO(updatedProveedor);
        }
        throw new IllegalArgumentException("El proveedor con ID " + id + " no existe");
    }

    @Override
    public void delete(Long id) {
        Optional<Proveedor> proveedorOptional = repositoryProveedor.findById(id);
        if (proveedorOptional.isPresent()) {
            repositoryProveedor.delete(proveedorOptional.get());
        } else {
            throw new IllegalArgumentException("El proveedor con ID " + id + " no existe.");
        }
    }

}
