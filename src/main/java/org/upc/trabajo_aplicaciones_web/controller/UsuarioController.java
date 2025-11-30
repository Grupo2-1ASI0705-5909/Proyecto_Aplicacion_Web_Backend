package org.upc.trabajo_aplicaciones_web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.UsuarioDTO;
import org.upc.trabajo_aplicaciones_web.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioController {
    // actualizacion
    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO nuevoUsuario = usuarioService.crear(usuarioDTO);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerTodos() {
        List<UsuarioDTO> usuarios = usuarioService.obtenerTodos();
        return ResponseEntity.ok(usuarios);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PatchMapping("/{id}/estado")
    public ResponseEntity<UsuarioDTO> cambiarEstado(@PathVariable Long id, @RequestParam Boolean estado) {
        UsuarioDTO usuario = usuarioService.cambiarEstado(id, estado);
        return ResponseEntity.ok(usuario);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<UsuarioDTO>> obtenerPorEstado(@PathVariable Boolean estado) {
        List<UsuarioDTO> usuarios = usuarioService.obtenerPorEstado(estado);
        return ResponseEntity.ok(usuarios);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/contar/activos")
    public ResponseEntity<Long> contarUsuariosActivos() {
        long cantidad = usuarioService.contarUsuariosActivos();
        return ResponseEntity.ok(cantidad);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/rol/{rolId}")
    public ResponseEntity<List<UsuarioDTO>> obtenerPorRol(@PathVariable Long rolId) {
        List<UsuarioDTO> usuarios = usuarioService.obtenerPorRol(rolId);
        return ResponseEntity.ok(usuarios);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO', 'COMERCIO')")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Long id, Authentication authentication) {
        UsuarioDTO usuario = usuarioService.obtenerPorId(id);

        // Validar que sea el propio usuario o un admin
        String currentEmail = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"));

        if (!isAdmin && !currentEmail.equals(usuario.getEmail())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(usuario);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO', 'COMERCIO')")
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDTO> obtenerPorEmail(@PathVariable String email, Authentication authentication) {
        // Validacion eliminada para permitir busqueda P2P
        // if (!isAdmin && !currentEmail.equals(email)) {
        // return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        // }

        UsuarioDTO usuario = usuarioService.obtenerPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO', 'COMERCIO')")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO,
            Authentication authentication) {
        // Validar que sea el propio usuario o un admin (Primero obtenemos el usuario
        // actual para ver su email)
        UsuarioDTO usuarioExistente = usuarioService.obtenerPorId(id);

        String currentEmail = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"));

        if (!isAdmin && !currentEmail.equals(usuarioExistente.getEmail())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        UsuarioDTO usuarioActualizado = usuarioService.actualizar(id, usuarioDTO);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO', 'COMERCIO')")
    @GetMapping("/buscar")
    public ResponseEntity<List<UsuarioDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<UsuarioDTO> usuarios = usuarioService.buscarPorNombre(nombre);
        return ResponseEntity.ok(usuarios);
    }
}
