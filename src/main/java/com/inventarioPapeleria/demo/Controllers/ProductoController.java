package com.inventarioPapeleria.demo.Controllers;

import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventarioPapeleria.demo.Services.ServiceProducto;
import com.inventarioPapeleria.demo.dto.RespuestaDTO;
import com.inventarioPapeleria.demo.dto.Productos.ProductoDTO;
import com.inventarioPapeleria.demo.dto.Productos.RespuestaListarProductosDTO;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/productos")
public class ProductoController {

    private ServiceProducto serviceProductos;

    @GetMapping("/")
    public ResponseEntity<RespuestaListarProductosDTO> findAll() {
        RespuestaListarProductosDTO respuesta = serviceProductos.findAll();
        if (respuesta.getProductos() == null || respuesta.getProductos().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/")
    public ResponseEntity<RespuestaDTO> save(@Valid @RequestBody ProductoDTO productosDTO,
            BindingResult result) {
        if (result.hasErrors()) {
            String errores = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            return ResponseEntity.badRequest().body(
                    new RespuestaDTO(true, "400", "Error: " + errores));
        }

        try {
            serviceProductos.save(productosDTO);
            return ResponseEntity.ok(
                    new RespuestaDTO(false, "200", "Producto creado exitosamente"));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(
                    new RespuestaDTO(true, "400", "Error: El nombre del producto ya está en uso"));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new RespuestaDTO(true, "500",
                            "Error en la base de datos: " + e.getMostSpecificCause().getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaDTO> update(@Valid @RequestBody ProductoDTO productosDTO,
            BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            String errores = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            return ResponseEntity.badRequest().body(
                    new RespuestaDTO(true, "400", "Error: " + errores));
        }

        try {
            serviceProductos.update(id, productosDTO);
            return ResponseEntity.ok(
                    new RespuestaDTO(false, "200", "Producto actualizado exitosamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new RespuestaDTO(true, "400", "Error: " + e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(
                    new RespuestaDTO(true, "400", "Error: El nombre del producto ya está en uso"));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new RespuestaDTO(true, "500",
                            "Error en la base de datos: " + e.getMostSpecificCause().getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaDTO> delete(@PathVariable Long id) {
        try {
            serviceProductos.delete(id);
            return ResponseEntity.ok(
                    new RespuestaDTO(false, "200", "Producto eliminado exitosamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new RespuestaDTO(true, "404", e.getMessage()));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new RespuestaDTO(true, "500",
                            "Error en la base de datos: " + e.getMostSpecificCause().getMessage()));
        }
    }
}
