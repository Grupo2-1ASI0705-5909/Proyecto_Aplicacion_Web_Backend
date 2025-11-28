package org.upc.trabajo_aplicaciones_web.service;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.upc.trabajo_aplicaciones_web.dto.RolDTO;
import org.upc.trabajo_aplicaciones_web.dto.UsuarioDTO;
import org.upc.trabajo_aplicaciones_web.model.Rol;
import org.upc.trabajo_aplicaciones_web.model.Usuario;
import org.upc.trabajo_aplicaciones_web.repository.RolRepository;
import org.upc.trabajo_aplicaciones_web.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO crear(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setPasswordHash(passwordEncoder.encode(usuarioDTO.getPasswordHash()));
        usuario.setEstado(usuarioDTO.getEstado() != null ? usuarioDTO.getEstado() : true);

        if (usuarioDTO.getRolId() != null) {
            Rol rol = rolRepository.findById(usuarioDTO.getRolId())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            usuario.setRol(rol);
        } else {
            Rol rolDefault = rolRepository.findByNombre("USUARIO")
                    .orElseThrow(() -> new RuntimeException("Rol USUARIO no encontrado en la base de datos"));
            usuario.setRol(rolDefault);
        }

        usuario = usuarioRepository.save(usuario);
        return convertirAUsuarioDTO(usuario);
    }

    public List<UsuarioDTO> obtenerTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuarioDTOs.add(convertirAUsuarioDTO(usuario));
        }
        return usuarioDTOs;
    }

    public UsuarioDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertirAUsuarioDTO(usuario);
    }

    public UsuarioDTO obtenerPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertirAUsuarioDTO(usuario);
    }

    public UsuarioDTO actualizar(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioExistente.setNombre(usuarioDTO.getNombre());
        usuarioExistente.setApellido(usuarioDTO.getApellido());
        usuarioExistente.setTelefono(usuarioDTO.getTelefono());
        usuarioExistente.setEstado(usuarioDTO.getEstado());

        if (usuarioDTO.getRolId() != null) {
            Rol rol = rolRepository.findById(usuarioDTO.getRolId())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            usuarioExistente.setRol(rol);
        }

        usuarioExistente = usuarioRepository.save(usuarioExistente);
        return convertirAUsuarioDTO(usuarioExistente);
    }

    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    public UsuarioDTO cambiarEstado(Long id, Boolean estado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setEstado(estado);
        usuario = usuarioRepository.save(usuario);
        return convertirAUsuarioDTO(usuario);
    }

    public List<UsuarioDTO> buscarPorNombre(String nombre) {
        List<Usuario> usuarios = usuarioRepository.buscarPorNombre(nombre);
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuarioDTOs.add(convertirAUsuarioDTO(usuario));
        }
        return usuarioDTOs;
    }

    public List<UsuarioDTO> obtenerPorEstado(Boolean estado) {
        List<Usuario> usuarios = usuarioRepository.findByEstado(estado);
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuarioDTOs.add(convertirAUsuarioDTO(usuario));
        }
        return usuarioDTOs;
    }

    public long contarUsuariosActivos() {
        return usuarioRepository.countByEstadoTrue();
    }

    public List<UsuarioDTO> obtenerPorRol(Long rolId) {
        List<Usuario> usuarios = usuarioRepository.findByRolRolId(rolId);
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuarioDTOs.add(convertirAUsuarioDTO(usuario));
        }
        return usuarioDTOs;
    }

    private UsuarioDTO convertirAUsuarioDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setUsuarioId(usuario.getUsuarioId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setPasswordHash(usuario.getPasswordHash());
        dto.setEstado(usuario.getEstado());
        dto.setCreatedAt(usuario.getCreatedAt());

        if (usuario.getRol() != null) {
            dto.setRolId(usuario.getRol().getRolId());

            RolDTO rolDTO = new RolDTO();
            rolDTO.setRolId(usuario.getRol().getRolId());
            rolDTO.setNombre(usuario.getRol().getNombre());
            rolDTO.setDescripcion(usuario.getRol().getDescripcion());
            dto.setRol(rolDTO);
        }

        return dto;
    }
}