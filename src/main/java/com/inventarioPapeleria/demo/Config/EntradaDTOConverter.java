package com.inventarioPapeleria.demo.Config;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inventarioPapeleria.demo.Repositories.RepositoryProducto;
import com.inventarioPapeleria.demo.Repositories.RepositoryProveedor;
import com.inventarioPapeleria.demo.Repositories.Entities.DetalleEntrada;
import com.inventarioPapeleria.demo.Repositories.Entities.Entrada;
import com.inventarioPapeleria.demo.Repositories.Entities.Producto;
import com.inventarioPapeleria.demo.Repositories.Entities.Proveedor;
import com.inventarioPapeleria.demo.dto.Entrada.Crear.EntradaDTO;
import com.inventarioPapeleria.demo.dto.Entrada.Listar.ListarDetalleDTO;
import com.inventarioPapeleria.demo.dto.Entrada.Listar.ListarEntradaDTO;

@Component
public class EntradaDTOConverter {

    @Autowired
    private ModelMapper dbm;

    @Autowired
    private RepositoryProveedor repositoryProveedor;

    @Autowired
    private RepositoryProducto repositoryProducto;

    public EntradaDTOConverter(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.dbm = modelMapper;
    }

    public EntradaDTO convertToDTO(Entrada entrada) {
        EntradaDTO entradaDTO = dbm.map(entrada, EntradaDTO.class);
        return entradaDTO;
    }

    public Entrada convertToEntity(EntradaDTO entradaDTO) {
        Proveedor proveedor = repositoryProveedor.findById(entradaDTO.getIdProveedor())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        Entrada entrada = new Entrada();
        entrada.setProveedor(proveedor);

        List<DetalleEntrada> detalles = entradaDTO.getDetalles().stream().map(detDTO -> {
            Producto producto = repositoryProducto.findById(detDTO.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            DetalleEntrada detalle = new DetalleEntrada();
            detalle.setCantidad(detDTO.getCantidad());
            detalle.setPrecioUnitario(detDTO.getPrecioUnitario());
            detalle.setProducto(producto);
            detalle.setEntrada(entrada); 

            return detalle;
        }).collect(Collectors.toList());

        entrada.setDetalles(detalles);
        double total = detalles.stream()
                .mapToDouble(d -> d.getCantidad() * d.getPrecioUnitario())
                .sum();
        entrada.setTotal(total);

        return entrada;
    }

    public ListarEntradaDTO convertToListarEntradaDTO(Entrada entrada) {
        ListarEntradaDTO dto = dbm.map(entrada, ListarEntradaDTO.class);
        dto.setId(entrada.getIdEntrada().longValue());
        dto.setIdProveedor(entrada.getProveedor().getIdProveedor().longValue());
        dto.setProveedor(entrada.getProveedor().getNombre());

        dto.setDetalles(entrada.getDetalles().stream()
                .map(this::convertToListarDetalleDTO)
                .collect(Collectors.toList()));

        return dto;
    }

    public ListarDetalleDTO convertToListarDetalleDTO(DetalleEntrada detalle) {
        ListarDetalleDTO dto = dbm.map(detalle, ListarDetalleDTO.class);
        dto.setId(detalle.getIdDetalleEntrada().longValue());
        dto.setIdProducto(detalle.getProducto().getIdProducto().longValue());
        dto.setProducto(detalle.getProducto().getNombre());
        return dto;
    }
}
