package com.inventarioPapeleria.demo.Services.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.inventarioPapeleria.demo.Config.ProductoDTOConverter;
import com.inventarioPapeleria.demo.Repositories.RepositoryCategoria;
import com.inventarioPapeleria.demo.Repositories.RepositoryProducto;
import com.inventarioPapeleria.demo.Repositories.Entities.Categoria;
import com.inventarioPapeleria.demo.Repositories.Entities.Producto;
import com.inventarioPapeleria.demo.Services.ServiceProducto;
import com.inventarioPapeleria.demo.dto.RespuestaDTO;
import com.inventarioPapeleria.demo.dto.Productos.ListarProductosDTO;
import com.inventarioPapeleria.demo.dto.Productos.ProductoDTO;
import com.inventarioPapeleria.demo.dto.Productos.RespuestaListarProductosDTO;

@Service
public class ServiceProductoImpl implements ServiceProducto {

    @Autowired
    private RepositoryProducto repositoryProducto;

    @Autowired
    private RepositoryCategoria repositoryCategoria;

    @Autowired
    private ProductoDTOConverter convert;

    @Override
    public RespuestaListarProductosDTO findAll() {
        RespuestaListarProductosDTO respuesta = new RespuestaListarProductosDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();

        try {
            Iterable<Producto> productosIterable = repositoryProducto.findAll();

            List<ListarProductosDTO> productosDTO = StreamSupport.stream(productosIterable.spliterator(), false)
                    .map(convert::convertToListarProductosDTO)
                    .collect(Collectors.toList());

            respuestaDTO.setError(false);
            respuestaDTO.setCodigo("200");
            respuestaDTO.setDescripcion("Productos encontrados correctamente");

            respuesta.setProductos(productosDTO);
        } catch (Exception e) {
            respuestaDTO.setError(true);
            respuestaDTO.setCodigo("500");
            respuestaDTO.setDescripcion("Error al obtener los productos: " + e.getMessage());
            respuesta.setProductos(null);
        }

        respuesta.setRespuesta(respuestaDTO);
        return respuesta;
    }

    @Override
    public ProductoDTO save(ProductoDTO productosDTO) {
        boolean exists = repositoryProducto.existsByNombre(productosDTO.getNombre());
        System.out.println("¿Existe el producto con nombre '" + productosDTO.getNombre() + "'? " + exists);

        if (exists) {
            throw new IllegalArgumentException("Ya existe un producto con el nombre de: " + productosDTO.getNombre());
        }

        Producto producto = convert.convertToEntity(productosDTO);
        Producto savedProducto = repositoryProducto.save(producto);
        return convert.convertToDTO(savedProducto);
    }

    @Override
    public ProductoDTO update(Long id, ProductoDTO productosDTO) {
        Optional<Producto> productoCurrentOptional = repositoryProducto.findById(id);
        if (productoCurrentOptional.isPresent()) {
            Producto productosCurrent = productoCurrentOptional.get();

            if (repositoryProducto.existsByNombre(productosDTO.getNombre()) &&
                    !productosCurrent.getNombre().equals(productosDTO.getNombre())) {
                throw new DataIntegrityViolationException("El nombre del producto ya está en uso.");
            }
            productosCurrent.setNombre(productosDTO.getNombre());
            productosCurrent.setDescripcion(productosDTO.getDescripcion());
            productosCurrent.setStockActual(productosDTO.getStockActual());
            productosCurrent.setStockMinimo(productosDTO.getStockMinimo());
            productosCurrent.setPrecioCompra(productosDTO.getPrecioCompra());
            productosCurrent.setPrecioVenta(productosDTO.getPrecioVenta());
            Categoria nuevCategoria = repositoryCategoria.findById(productosDTO.getIdCategoria())
            .orElseThrow(() -> new IllegalArgumentException("La categoria con ID " + productosDTO.getIdCategoria() + " no existe"));
            productosCurrent.setCategoria(nuevCategoria);
            Producto updatedProductos = repositoryProducto.save(productosCurrent);
            return convert.convertToDTO(updatedProductos);
        }
        throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
    }

    @Override
    public void delete(Long id) {
        Optional<Producto> productoOptional = repositoryProducto.findById(id);
        if (productoOptional.isPresent()) {
            repositoryProducto.delete(productoOptional.get());
        } else {
            throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
        }
    }

}
