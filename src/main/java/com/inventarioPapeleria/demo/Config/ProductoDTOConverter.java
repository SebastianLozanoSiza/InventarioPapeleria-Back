package com.inventarioPapeleria.demo.Config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inventarioPapeleria.demo.Repositories.RepositoryCategoria;
import com.inventarioPapeleria.demo.Repositories.Entities.Categoria;
import com.inventarioPapeleria.demo.Repositories.Entities.Producto;
import com.inventarioPapeleria.demo.dto.Productos.ListarProductosDTO;
import com.inventarioPapeleria.demo.dto.Productos.ProductoDTO;

@Component
public class ProductoDTOConverter {

    @Autowired
    private ModelMapper dbm;

    @Autowired
    private RepositoryCategoria repositoryCategoria;

    public ProductoDTOConverter(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.dbm = modelMapper;
    }

    public ProductoDTO convertToDTO(Producto producto) {
        ProductoDTO productosDTO = dbm.map(producto, ProductoDTO.class);
        return productosDTO;
    }

    public Producto convertToEntity(ProductoDTO productosDTO) {
        Producto productos = dbm.map(productosDTO, Producto.class);
        Categoria categoria = repositoryCategoria.findById(productosDTO.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        productos.setCategoria(categoria);
        return productos;
    }

    public ListarProductosDTO convertToListarProductosDTO(Producto producto) {
        ListarProductosDTO listarProductosDTO = dbm.map(producto, ListarProductosDTO.class);
        listarProductosDTO.setId(producto.getIdProducto().longValue());
        listarProductosDTO.setIdCategoria(producto.getCategoria().getIdCategoria().longValue());
        listarProductosDTO.setCategoria(producto.getCategoria().getNombre());
        return listarProductosDTO;
    }
}
