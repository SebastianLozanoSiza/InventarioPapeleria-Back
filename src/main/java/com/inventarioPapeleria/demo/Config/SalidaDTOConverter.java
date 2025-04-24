package com.inventarioPapeleria.demo.Config;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inventarioPapeleria.demo.Repositories.RepositoryProducto;
import com.inventarioPapeleria.demo.Repositories.Entities.DetalleSalida;
import com.inventarioPapeleria.demo.Repositories.Entities.Producto;
import com.inventarioPapeleria.demo.Repositories.Entities.Salida;
import com.inventarioPapeleria.demo.dto.Salida.Crear.SalidaDTO;
import com.inventarioPapeleria.demo.dto.Salida.Listar.ListarDetalleSalidaDTO;
import com.inventarioPapeleria.demo.dto.Salida.Listar.ListarSalidaDTO;

@Component
public class SalidaDTOConverter {

    @Autowired
    private ModelMapper dbm;

    @Autowired 
    private RepositoryProducto repositoryProducto;

    
    public SalidaDTOConverter(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.dbm = modelMapper;
    }

    public SalidaDTO convertToDto(Salida salida){
        SalidaDTO salidaDTO = dbm.map(salida, SalidaDTO.class);
        return salidaDTO;
    }

    public Salida convertToEntity(SalidaDTO salidaDTO){
        Salida salida = new Salida();
        salida.setMotivoSalida(salidaDTO.getMotivoSalida());
        List<DetalleSalida> detalleSalidas = salidaDTO.getDetalleSalida().stream().map(detDTO ->{
            Producto producto = repositoryProducto.findById(detDTO.getIdProducto())
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            DetalleSalida detalleSalida = new DetalleSalida();
            detalleSalida.setCantidad(detDTO.getCantidad());
            detalleSalida.setPrecioUnitario(detDTO.getPrecioUnitario());
            detalleSalida.setProducto(producto);
            detalleSalida.setSalida(salida);
            return detalleSalida;
        }).collect(Collectors.toList());

        salida.setDetalleSalidas(detalleSalidas);
        return salida;
    }

    public ListarSalidaDTO convertToListarSalidaDTO(Salida salida) {
        ListarSalidaDTO listarSalidaDTO = dbm.map(salida, ListarSalidaDTO.class);
        listarSalidaDTO.setId(salida.getIdSalida().longValue());
        listarSalidaDTO.setMotivo(salida.getMotivoSalida());
        listarSalidaDTO.setDetalleSalida(salida.getDetalleSalidas().stream()
                .map(this::convertListarDetalleDTO)
                .collect(Collectors.toList()));
        return listarSalidaDTO;
    }

    public ListarDetalleSalidaDTO convertListarDetalleDTO(DetalleSalida detalleSalida) {
        ListarDetalleSalidaDTO listarDetalleSalidaDTO = dbm.map(detalleSalida, ListarDetalleSalidaDTO.class);
        listarDetalleSalidaDTO.setId(detalleSalida.getIdDetalleSalida().longValue());
        listarDetalleSalidaDTO.setIdProducto(detalleSalida.getProducto().getIdProducto().longValue());
        listarDetalleSalidaDTO.setProducto(detalleSalida.getProducto().getNombre());
        return listarDetalleSalidaDTO;
    }
}
