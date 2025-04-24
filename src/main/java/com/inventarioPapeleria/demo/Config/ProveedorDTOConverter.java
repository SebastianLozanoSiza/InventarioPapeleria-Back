package com.inventarioPapeleria.demo.Config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inventarioPapeleria.demo.Repositories.Entities.Proveedor;
import com.inventarioPapeleria.demo.dto.Proveedor.ListarProveedorDTO;
import com.inventarioPapeleria.demo.dto.Proveedor.ProveedorDTO;

@Component
public class ProveedorDTOConverter {
    
    @Autowired
    private ModelMapper dbm;

    public ProveedorDTOConverter(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.dbm = modelMapper;
    }

    public ProveedorDTO converToDTO(Proveedor proveedor) {
        ProveedorDTO proveedorDTO = dbm.map(proveedor, ProveedorDTO.class);
        return proveedorDTO;
    }

    public Proveedor convertToEntity(ProveedorDTO proveedorDTO) {
        Proveedor proveedor = dbm.map(proveedorDTO, Proveedor.class);
        return proveedor;
    }

    public ListarProveedorDTO convertListarToDTO(Proveedor proveedor) {
        ListarProveedorDTO listarProveedorDTO = dbm.map(proveedor, ListarProveedorDTO.class);
        return listarProveedorDTO;
    }
}
