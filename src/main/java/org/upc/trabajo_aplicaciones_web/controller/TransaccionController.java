package org.upc.trabajo_aplicaciones_web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.TransaccionDTO;
import org.upc.trabajo_aplicaciones_web.dto.UsuarioDTO;
import org.upc.trabajo_aplicaciones_web.service.TransaccionService;
import org.upc.trabajo_aplicaciones_web.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
@RequiredArgsConstructor
public class TransaccionController {

    // actualizacion
    private final TransaccionService transaccionService;
    private final UsuarioService usuarioService;

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','USUARIO')")
    @GetMapping
    public ResponseEntity<List<TransaccionDTO>> obtenerTodos() {
        List<TransaccionDTO> transacciones = transaccionService.obtenerTodos();
        return ResponseEntity.ok(transacciones);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id, Authentication authentication) {
        // Validar propiedad
        TransaccionDTO transaccion = transaccionService.obtenerPorId(id);
        String currentEmail = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"));

        // Si no es admin, debe ser el dueño (usuario origen)
        if (!isAdmin) {
            UsuarioDTO usuario = usuarioService.obtenerPorEmail(currentEmail);
            if (!transaccion.getUsuarioId().equals(usuario.getUsuarioId())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

        transaccionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','USUARIO')")
    @GetMapping("/cripto")
    public ResponseEntity<List<TransaccionDTO>> obtenerTransaccionesConCripto() {
        List<TransaccionDTO> transacciones = transaccionService.obtenerTransaccionesConCripto();
        return ResponseEntity.ok(transacciones);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','USUARIO')")
    @GetMapping("/recientes")
    public ResponseEntity<List<TransaccionDTO>> obtenerRecientes() {
        List<TransaccionDTO> transacciones = transaccionService.obtenerRecientes();
        return ResponseEntity.ok(transacciones);
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ROLE_ADMINISTRADOR', 'USUARIO', 'ROLE_USUARIO', 'CLIENTE')")
    @PostMapping
    public ResponseEntity<TransaccionDTO> crear(@RequestBody TransaccionDTO transaccionDTO, Authentication authentication) { // <--- Agregamos Authentication

        // Obtenemos el email del usuario logueado (TOKEN)
        String email = authentication.getName();

        // Pasamos el email al servicio en lugar de confiar solo en el DTO
        TransaccionDTO nuevaTransaccion = transaccionService.crear(transaccionDTO, email);

        return new ResponseEntity<>(nuevaTransaccion, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TransaccionDTO>> obtenerPorUsuario(@PathVariable Long usuarioId,
            Authentication authentication) {
        // Validar que el usuario solicite sus propias transacciones
        String currentEmail = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"));

        if (!isAdmin) {
            UsuarioDTO usuario = usuarioService.obtenerPorEmail(currentEmail);
            if (!usuario.getUsuarioId().equals(usuarioId)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

        List<TransaccionDTO> transacciones = transaccionService.obtenerPorUsuario(usuarioId);
        return ResponseEntity.ok(transacciones);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    @GetMapping("/usuario/{usuarioId}/total-fiat")
    public ResponseEntity<Double> calcularTotalFiatPorUsuario(@PathVariable Long usuarioId,
            Authentication authentication) {
        // Validar propiedad
        String currentEmail = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"));

        if (!isAdmin) {
            UsuarioDTO usuario = usuarioService.obtenerPorEmail(currentEmail);
            if (!usuario.getUsuarioId().equals(usuarioId)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

        Double total = transaccionService.calcularTotalFiatPorUsuario(usuarioId);
        return ResponseEntity.ok(total);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    @GetMapping("/usuario/{usuarioId}/total-cripto")
    public ResponseEntity<Double> calcularTotalCriptoPorUsuario(@PathVariable Long usuarioId,
            Authentication authentication) {
        // Validar propiedad
        String currentEmail = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"));

        if (!isAdmin) {
            UsuarioDTO usuario = usuarioService.obtenerPorEmail(currentEmail);
            if (!usuario.getUsuarioId().equals(usuarioId)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

        Double total = transaccionService.calcularTotalCriptoPorUsuario(usuarioId);
        return ResponseEntity.ok(total);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'COMERCIO')")
    @GetMapping("/comercio/{comercioId}")
    public ResponseEntity<List<TransaccionDTO>> obtenerPorComercio(@PathVariable Long comercioId) {
        List<TransaccionDTO> transacciones = transaccionService.obtenerPorComercio(comercioId);
        return ResponseEntity.ok(transacciones);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'COMERCIO')")
    @PutMapping("/{id}")
    public ResponseEntity<TransaccionDTO> actualizar(@PathVariable Long id,
            @RequestBody TransaccionDTO transaccionDTO) {
        TransaccionDTO transaccionActualizada = transaccionService.actualizar(id, transaccionDTO);
        return ResponseEntity.ok(transaccionActualizada);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'COMERCIO')")
    @PatchMapping("/{id}/estado")
    public ResponseEntity<TransaccionDTO> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        TransaccionDTO transaccion = transaccionService.cambiarEstado(id, estado);
        return ResponseEntity.ok(transaccion);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/{id}")
    public ResponseEntity<TransaccionDTO> obtenerPorId(@PathVariable Long id) {
        TransaccionDTO transaccion = transaccionService.obtenerPorId(id);
        return ResponseEntity.ok(transaccion);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<TransaccionDTO>> obtenerPorEstado(@PathVariable String estado) {
        List<TransaccionDTO> transacciones = transaccionService.obtenerPorEstado(estado);
        return ResponseEntity.ok(transacciones);
    }

}
