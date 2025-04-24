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

import com.inventarioPapeleria.demo.Services.ServiceProveedor;
import com.inventarioPapeleria.demo.dto.RespuestaDTO;
import com.inventarioPapeleria.demo.dto.Proveedor.ListarProveedorDTO;
import com.inventarioPapeleria.demo.dto.Proveedor.ProveedorDTO;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/proveedores")
public class ProveedorController {

    private ServiceProveedor serviceProveedor;

    @GetMapping("/")
    public ResponseEntity<ListarProveedorDTO> findAll() {
        ListarProveedorDTO response = serviceProveedor.findAll();
        if (response.getProveedor() == null || response.getProveedor().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/")
    public ResponseEntity<RespuestaDTO> save(@Valid @RequestBody ProveedorDTO proveedorDTO,
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
            serviceProveedor.save(proveedorDTO);
            return ResponseEntity.ok(
                    new RespuestaDTO(false, "200", "Proveedor creado exitosamente"));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(
                    new RespuestaDTO(true, "400", "Error de integridad en la base de datos"));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new RespuestaDTO(true, "500",
                            "Error en la base de datos: " + e.getMostSpecificCause().getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaDTO> update(@Valid @RequestBody ProveedorDTO proveedorDTO,
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
            serviceProveedor.update(id, proveedorDTO);
            return ResponseEntity.ok(
                    new RespuestaDTO(false, "200", "Proveedor actualizado exitosamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new RespuestaDTO(true, "400", "Error: " + e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(
                    new RespuestaDTO(true, "400", "Error de integridad en la base de datos"));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new RespuestaDTO(true, "500",
                            "Error en la base de datos: " + e.getMostSpecificCause().getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaDTO> delete(@PathVariable Long id) {
        try {
            serviceProveedor.delete(id);
            return ResponseEntity.ok(
                    new RespuestaDTO(false, "200", "Proveedor eliminado exitosamente"));
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