package com.inventarioPapeleria.demo.Services.Impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventarioPapeleria.demo.Config.SalidaDTOConverter;
import com.inventarioPapeleria.demo.Repositories.RepositoryProducto;
import com.inventarioPapeleria.demo.Repositories.RepositorySalida;
import com.inventarioPapeleria.demo.Repositories.Entities.DetalleSalida;
import com.inventarioPapeleria.demo.Repositories.Entities.Producto;
import com.inventarioPapeleria.demo.Repositories.Entities.Salida;
import com.inventarioPapeleria.demo.Services.ServiceSalida;
import com.inventarioPapeleria.demo.dto.RespuestaDTO;
import com.inventarioPapeleria.demo.dto.Salida.Crear.SalidaDTO;
import com.inventarioPapeleria.demo.dto.Salida.Listar.ListarSalidaDTO;
import com.inventarioPapeleria.demo.dto.Salida.Listar.RespuestaListarSalidaDTO;

@Service
public class ServiceSalidaImpl implements ServiceSalida {

    @Autowired
    private RepositorySalida repositorySalida;

    @Autowired
    private RepositoryProducto repositoryProducto;

    @Autowired
    private SalidaDTOConverter convert;

    @Override
    public RespuestaListarSalidaDTO findAll() {
        List<ListarSalidaDTO> salidaDTOs = StreamSupport.stream(repositorySalida.findAll().spliterator(), false)
                .map(convert::convertToListarSalidaDTO)
                .collect(Collectors.toList());

        RespuestaListarSalidaDTO respuestaListarSalidaDTO = new RespuestaListarSalidaDTO();
        respuestaListarSalidaDTO.setDetalle(salidaDTOs);
        respuestaListarSalidaDTO.setRespuesta(new RespuestaDTO(false, "200", "Salidas encontradas exitosamente"));
        return respuestaListarSalidaDTO;
    }

    @Override
    public SalidaDTO save(SalidaDTO salidaDTO) {
        Salida salida = convert.convertToEntity(salidaDTO);
        for (DetalleSalida detalleSalida : salida.getDetalleSalidas()) {
            Producto producto = detalleSalida.getProducto();
            int cantidadSalio = detalleSalida.getCantidad();
            if (producto.getStockActual() < cantidadSalio) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }
            producto.setStockActual(producto.getStockActual() - cantidadSalio);
        }
        Salida savedSalida = repositorySalida.save(salida);
        return convert.convertToDto(savedSalida);
    }

    @Override
    public SalidaDTO update(Long id, SalidaDTO salidaDTO) {
        Salida salidaCurrent = repositorySalida.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la salida con ID: " + id));

        salidaCurrent.setMotivoSalida(salidaDTO.getMotivoSalida());

        salidaCurrent.getDetalleSalidas().clear();

        // Mapear los nuevos detalles desde el DTO
        List<DetalleSalida> nuevosDetalles = salidaDTO.getDetalleSalida().stream().map(detalleDTO -> {
            Producto producto = repositoryProducto.findById(detalleDTO.getIdProducto())
                    .orElseThrow(() -> new RuntimeException(
                            "Producto no encontrado con ID: " + detalleDTO.getIdProducto()));

            DetalleSalida nuevoDetalle = new DetalleSalida();
            nuevoDetalle.setCantidad(detalleDTO.getCantidad());
            nuevoDetalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());
            nuevoDetalle.setProducto(producto);
            nuevoDetalle.setSalida(salidaCurrent);
            return nuevoDetalle;
        }).collect(Collectors.toList());

        salidaCurrent.setDetalleSalidas(nuevosDetalles);
        Salida updatedSalida = repositorySalida.save(salidaCurrent);
        return convert.convertToDto(updatedSalida);
    }

    @Override
    public void delete(Long id) {
        Salida salida = repositorySalida.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Salida no encontrada"));
        repositorySalida.delete(salida);
    }

}
