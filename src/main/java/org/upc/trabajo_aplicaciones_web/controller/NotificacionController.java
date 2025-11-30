package org.upc.trabajo_aplicaciones_web.controller;

import org.upc.trabajo_aplicaciones_web.model.Notificacion;
import org.upc.trabajo_aplicaciones_web.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@CrossOrigin(origins = "http://localhost:4200")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    // Crear notificación
    @PostMapping
    public ResponseEntity<Notificacion> crear(@RequestBody Notificacion notificacion) {
        return new ResponseEntity<>(notificacionService.crear(notificacion), HttpStatus.CREATED);
    }

    // Obtener TODAS las notificaciones (para administradores)
    @GetMapping
    public ResponseEntity<List<Notificacion>> obtenerTodas() {
        return new ResponseEntity<>(notificacionService.obtenerTodas(), HttpStatus.OK);
    }

    // Obtener notificaciones por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Notificacion>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return new ResponseEntity<>(notificacionService.obtenerPorUsuario(usuarioId), HttpStatus.OK);
    }

    // Obtener notificaciones no leídas por usuario
    @GetMapping("/usuario/{usuarioId}/no-leidas")
    public ResponseEntity<List<Notificacion>> obtenerNoLeidas(@PathVariable Long usuarioId) {
        return new ResponseEntity<>(notificacionService.obtenerNoLeidas(usuarioId), HttpStatus.OK);
    }

    // Contar notificaciones no leídas por usuario
    @GetMapping("/usuario/{usuarioId}/contar-no-leidas")
    public ResponseEntity<Long> contarNoLeidas(@PathVariable Long usuarioId) {
        return new ResponseEntity<>(notificacionService.contarNoLeidas(usuarioId), HttpStatus.OK);
    }

    // Marcar una notificación como leída
    @PatchMapping("/{id}/leer")
    public ResponseEntity<Notificacion> marcarComoLeida(@PathVariable Long id) {
        Notificacion notificacion = notificacionService.marcarComoLeida(id);
        if (notificacion != null) {
            return new ResponseEntity<>(notificacion, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Marcar todas las notificaciones de un usuario como leídas
    @PatchMapping("/usuario/{usuarioId}/marcar-todas-leidas")
    public ResponseEntity<Void> marcarTodasComoLeidas(@PathVariable Long usuarioId) {
        notificacionService.marcarTodasComoLeidas(usuarioId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Eliminar notificación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        notificacionService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}