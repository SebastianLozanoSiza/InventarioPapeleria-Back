package com.inventarioPapeleria.demo.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventarioPapeleria.demo.Services.ServiceCategoria;
import com.inventarioPapeleria.demo.dto.RespuestaDTO;
import com.inventarioPapeleria.demo.dto.Categorias.CategoriaDTO;
import com.inventarioPapeleria.demo.dto.Categorias.ListarCategoriaDTO;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@AllArgsConstructor
@RequestMapping("/categorias")
public class CategoriaController {

    private ServiceCategoria serviceCategoria;


    @GetMapping("/")
    public ResponseEntity<ListarCategoriaDTO> findAll(){
        ListarCategoriaDTO listarCategoriaDTO = serviceCategoria.findAll();
        if (listarCategoriaDTO.getCategoria() == null || listarCategoriaDTO.getCategoria().isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listarCategoriaDTO);
        }
    }
    

    @PostMapping("/")
    public ResponseEntity<RespuestaDTO> save(@Valid @RequestBody CategoriaDTO categoriaDTO, BindingResult result) {
        if (result.hasErrors()) {
            String errores = result.getFieldErrors().stream().map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(","));
            return ResponseEntity.badRequest().body(new RespuestaDTO(true, "400", "Erroe: " + errores));
        }

        try {
            serviceCategoria.save(categoriaDTO);
            return ResponseEntity.ok(
                    new RespuestaDTO(false, "200", "Categoria creada exitosamente"));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body(new RespuestaDTO(true, "400", "Error: El nombre de la categoria ya esta en uso"));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespuestaDTO(true, "500",
                    "Error en la base de datos: " + e.getMostSpecificCause().getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaDTO> update(@Valid @RequestBody CategoriaDTO categoriaDTO, BindingResult result,
            @PathVariable Long id) {
        if (result.hasErrors()) {
            String errores = result.getFieldErrors().stream().map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(","));
            return ResponseEntity.badRequest().body(new RespuestaDTO(true, "400", "Erroe: " + errores));
        }
        try {
            serviceCategoria.update(id, categoriaDTO);
            return ResponseEntity.ok(
                    new RespuestaDTO(false, "200", "Categoria actualizada exitosamente"));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body(new RespuestaDTO(true, "400", "Error: El nombre de la categoria ya esta en uso"));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespuestaDTO(true, "500",
                    "Error en la base de datos: " + e.getMostSpecificCause().getMessage()));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<RespuestaDTO> delete(@PathVariable Long id) {
        try {
            serviceCategoria.delete(id);
            return ResponseEntity.ok(
                    new RespuestaDTO(false, "200", "Categoria eliminada exitosamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespuestaDTO(true, "404",
                    "Error en la base de datos: " + e.getMessage()));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespuestaDTO(true, "500",
                    "Error en la base de datos: " + e.getMostSpecificCause().getMessage()));
        }
    }

}
