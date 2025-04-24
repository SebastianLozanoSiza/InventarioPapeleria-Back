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

import com.inventarioPapeleria.demo.Services.ServiceSalida;
import com.inventarioPapeleria.demo.dto.RespuestaDTO;
import com.inventarioPapeleria.demo.dto.Salida.Crear.SalidaDTO;
import com.inventarioPapeleria.demo.dto.Salida.Listar.RespuestaListarSalidaDTO;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/salidas")
public class SalidaController {

    private ServiceSalida serviceSalida;

    @GetMapping("/")
    public ResponseEntity<RespuestaListarSalidaDTO> findAll() {
        RespuestaListarSalidaDTO respuestaListarSalidaDTO = serviceSalida.findAll();
        return ResponseEntity.ok(respuestaListarSalidaDTO);
    }

    @PostMapping("/")
    public ResponseEntity<RespuestaDTO> save(@Valid @RequestBody SalidaDTO salidaDTO, BindingResult result) {
        if (result.hasErrors()) {
            String errores = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            return ResponseEntity.badRequest().body(
                    new RespuestaDTO(true, "400", "Error: " + errores));
        }

        try {
            serviceSalida.save(salidaDTO);
            return ResponseEntity.ok(
                    new RespuestaDTO(false, "200", "Salida creada exitosamente"));
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
    public ResponseEntity<RespuestaDTO> update(@Valid @RequestBody SalidaDTO salidaDTO,
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
            serviceSalida.update(id, salidaDTO);
            return ResponseEntity.ok(
                    new RespuestaDTO(false, "200", "Salida actualizada exitosamente"));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(
                    new RespuestaDTO(true, "400", "Error: Violación de integridad de datos"));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new RespuestaDTO(true, "500",
                            "Error en la base de datos: " + e.getMostSpecificCause().getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new RespuestaDTO(true, "404", "Error: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaDTO> delete(@PathVariable Long id) {
        try {
            serviceSalida.delete(id);
            return ResponseEntity.ok(new RespuestaDTO(false, "200", "Salida eliminada exitosamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RespuestaDTO(true, "404", "Error: " + e.getMessage()));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RespuestaDTO(true, "500", "Error: " + e.getMostSpecificCause().getMessage()));
        }
    }

}