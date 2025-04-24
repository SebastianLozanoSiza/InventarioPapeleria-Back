package com.inventarioPapeleria.demo.Services.Impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventarioPapeleria.demo.Config.EntradaDTOConverter;
import com.inventarioPapeleria.demo.Repositories.RepositoryEntrada;
import com.inventarioPapeleria.demo.Repositories.RepositoryProducto;
import com.inventarioPapeleria.demo.Repositories.RepositoryProveedor;
import com.inventarioPapeleria.demo.Repositories.Entities.DetalleEntrada;
import com.inventarioPapeleria.demo.Repositories.Entities.Entrada;
import com.inventarioPapeleria.demo.Repositories.Entities.Producto;
import com.inventarioPapeleria.demo.Repositories.Entities.Proveedor;
import com.inventarioPapeleria.demo.Services.ServiceEntrada;
import com.inventarioPapeleria.demo.dto.RespuestaDTO;
import com.inventarioPapeleria.demo.dto.Entrada.Crear.EntradaDTO;
import com.inventarioPapeleria.demo.dto.Entrada.Listar.ListarEntradaDTO;
import com.inventarioPapeleria.demo.dto.Entrada.Listar.RepuestaListarEntradaDTO;

@Service
public class ServiceEntradaImpl implements ServiceEntrada {
    @Autowired
    private RepositoryEntrada repositoryEntrada;

    @Autowired
    private EntradaDTOConverter convert;

    @Autowired
    private RepositoryProducto repositoryProducto;

    @Autowired
    private RepositoryProveedor repositoryProveedor;

    @Override
    public RepuestaListarEntradaDTO findAll() {
        List<ListarEntradaDTO> entradasDTO = StreamSupport
                .stream(repositoryEntrada.findAll().spliterator(), false)
                .map(convert::convertToListarEntradaDTO)
                .collect(Collectors.toList());

        RepuestaListarEntradaDTO respuesta = new RepuestaListarEntradaDTO();
        respuesta.setEntradas(entradasDTO);
        respuesta.setRepuesta(new RespuestaDTO(false, "200", "Entradas encontradas exitosamente"));
        return respuesta;
    }

    @Override
    public EntradaDTO save(EntradaDTO entradaDTO) {
        Entrada entrada = convert.convertToEntity(entradaDTO);
        for (DetalleEntrada detalleEntrada : entrada.getDetalles()) {
            Producto producto = detalleEntrada.getProducto();
            int cantidadEntra = detalleEntrada.getCantidad();
            producto.setStockActual(producto.getStockActual() + cantidadEntra);
        }
        Entrada savedEntrada = repositoryEntrada.save(entrada);
        return convert.convertToDTO(savedEntrada);
    }

    @Override
    public EntradaDTO update(Long id, EntradaDTO entradaDTO) {
        Entrada entrada = repositoryEntrada.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La entrada con ID " + id + " no existe."));

        Proveedor proveedor = repositoryProveedor.findById(entradaDTO.getIdProveedor())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        entrada.setProveedor(proveedor);

        List<DetalleEntrada> detallesActuales = entrada.getDetalles();

        List<DetalleEntrada> nuevosDetalles = entradaDTO.getDetalles().stream().map(detDTO -> {
            Producto producto = repositoryProducto.findById(detDTO.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            DetalleEntrada detalle;
            if (detDTO.getIdProducto() != null) {
                detalle = detallesActuales.stream()
                        .filter(d -> d.getIdDetalleEntrada().equals(detDTO.getIdProducto()))
                        .findFirst()
                        .orElse(new DetalleEntrada()); 
            } else {
                detalle = new DetalleEntrada();
            }

            detalle.setProducto(producto);
            detalle.setCantidad(detDTO.getCantidad());
            detalle.setPrecioUnitario(detDTO.getPrecioUnitario());
            detalle.setEntrada(entrada);
            return detalle;
        }).collect(Collectors.toList());

        entrada.getDetalles().clear(); 
        entrada.getDetalles().addAll(nuevosDetalles);

        // Recalcular total
        double total = nuevosDetalles.stream()
                .mapToDouble(d -> d.getCantidad() * d.getPrecioUnitario())
                .sum();
        entrada.setTotal(total);

        // Guardar
        Entrada updatedEntrada = repositoryEntrada.save(entrada);
        return convert.convertToDTO(updatedEntrada);
    }

    @Override
    public void delete(Long id) {
        Entrada entrada = repositoryEntrada.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entrada no encontrada"));
        repositoryEntrada.delete(entrada);
    }
}
