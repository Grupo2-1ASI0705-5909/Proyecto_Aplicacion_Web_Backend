package org.upc.trabajo_aplicaciones_web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.MetodoPagoDTO;
import org.upc.trabajo_aplicaciones_web.service.MetodoPagoService;

import java.util.List;

@RestController
@RequestMapping("/api/metodos-pago")
@RequiredArgsConstructor
public class MetodoPagoController {

    private final MetodoPagoService metodoPagoService;

    //David MetodoPagoController

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<MetodoPagoDTO> crear(@RequestBody MetodoPagoDTO metodoPagoDTO) {
        MetodoPagoDTO nuevoMetodo = metodoPagoService.crear(metodoPagoDTO);
        return new ResponseEntity<>(nuevoMetodo, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<MetodoPagoDTO> actualizar(@PathVariable Long id, @RequestBody MetodoPagoDTO metodoPagoDTO) {
        MetodoPagoDTO metodoActualizado = metodoPagoService.actualizar(id, metodoPagoDTO);
        return ResponseEntity.ok(metodoActualizado);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        metodoPagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PatchMapping("/{id}/estado")
    public ResponseEntity<MetodoPagoDTO> cambiarEstado(@PathVariable Long id, @RequestParam Boolean estado) {
        MetodoPagoDTO metodo = metodoPagoService.cambiarEstado(id, estado);
        return ResponseEntity.ok(metodo);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/contar/activos")
    public ResponseEntity<Long> contarMetodosActivos() {
        long cantidad = metodoPagoService.contarMetodosActivos();
        return ResponseEntity.ok(cantidad);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<MetodoPagoDTO>> obtenerTodos() {
        List<MetodoPagoDTO> metodos = metodoPagoService.obtenerTodos();
        return ResponseEntity.ok(metodos);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/{id}")
    public ResponseEntity<MetodoPagoDTO> obtenerPorId(@PathVariable Long id) {
        MetodoPagoDTO metodo = metodoPagoService.obtenerPorId(id);
        return ResponseEntity.ok(metodo);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','USUARIO')")
    @GetMapping("/activos")
    public ResponseEntity<List<MetodoPagoDTO>> obtenerActivos() {
        List<MetodoPagoDTO> metodos = metodoPagoService.obtenerActivos();
        return ResponseEntity.ok(metodos);
    }

}
