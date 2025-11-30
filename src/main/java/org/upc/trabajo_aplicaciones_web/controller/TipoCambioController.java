package org.upc.trabajo_aplicaciones_web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.TipoCambioDTO;
import org.upc.trabajo_aplicaciones_web.service.TipoCambioService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-cambio")
@RequiredArgsConstructor
// actualizado
public class TipoCambioController {

    private final TipoCambioService tipoCambioService;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<TipoCambioDTO> crear(@RequestBody TipoCambioDTO tipoCambioDTO) {
        TipoCambioDTO nuevoTipoCambio = tipoCambioService.crear(tipoCambioDTO);
        return new ResponseEntity<>(nuevoTipoCambio, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tipoCambioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO', 'COMERCIO')")
    @GetMapping("/promedio")
    public ResponseEntity<Double> calcularPromedioTasas(
            @RequestParam String desde,
            @RequestParam String hasta,
            @RequestParam LocalDateTime fechaInicio,
            @RequestParam LocalDateTime fechaFin) {
        Double promedio = tipoCambioService.calcularPromedioTasas(desde, hasta, fechaInicio, fechaFin);
        return ResponseEntity.ok(promedio);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO', 'COMERCIO')")
    @GetMapping("/historial")
    public ResponseEntity<List<TipoCambioDTO>> obtenerHistorialTasas(@RequestParam String desde,
            @RequestParam String hasta) {
        List<TipoCambioDTO> tiposCambio = tipoCambioService.obtenerHistorialTasas(desde, hasta);
        return ResponseEntity.ok(tiposCambio);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO', 'COMERCIO')")
    @GetMapping
    public ResponseEntity<List<TipoCambioDTO>> obtenerTodos() {
        List<TipoCambioDTO> tiposCambio = tipoCambioService.obtenerTodos();
        return ResponseEntity.ok(tiposCambio);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO', 'COMERCIO')")
    @GetMapping("/{id}")
    public ResponseEntity<TipoCambioDTO> obtenerPorId(@PathVariable Long id) {
        TipoCambioDTO tipoCambio = tipoCambioService.obtenerPorId(id);
        return ResponseEntity.ok(tipoCambio);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO', 'COMERCIO')")
    @GetMapping("/tasa-actual")
    public ResponseEntity<TipoCambioDTO> obtenerTasaMasReciente(@RequestParam String desde,
            @RequestParam String hasta) {
        TipoCambioDTO tipoCambio = tipoCambioService.obtenerTasaMasReciente(desde, hasta);
        return ResponseEntity.ok(tipoCambio);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO', 'COMERCIO')")
    @GetMapping("/tasas-recientes")
    public ResponseEntity<List<TipoCambioDTO>> obtenerTasasMasRecientes() {
        List<TipoCambioDTO> tiposCambio = tipoCambioService.obtenerTasasMasRecientes();
        return ResponseEntity.ok(tiposCambio);
    }
}
