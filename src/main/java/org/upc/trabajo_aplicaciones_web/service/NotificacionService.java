package org.upc.trabajo_aplicaciones_web.service;

import org.upc.trabajo_aplicaciones_web.model.Notificacion;
import org.upc.trabajo_aplicaciones_web.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    public Notificacion crear(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

    public List<Notificacion> obtenerTodas() {
        return notificacionRepository.findAll();
    }

    public List<Notificacion> obtenerPorUsuario(Long usuarioId) {
        return notificacionRepository.findByUsuarioIdOrderByFechaEnvioDesc(usuarioId);
    }

    public List<Notificacion> obtenerNoLeidas(Long usuarioId) {
        return notificacionRepository.findByUsuarioIdAndLeidoFalseOrderByFechaEnvioDesc(usuarioId);
    }

    public long contarNoLeidas(Long usuarioId) {
        return notificacionRepository.countByUsuarioIdAndLeidoFalse(usuarioId);
    }

    public Notificacion marcarComoLeida(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id).orElse(null);
        if (notificacion != null) {
            notificacion.setLeido(true);
            return notificacionRepository.save(notificacion);
        }
        return null;
    }

    public void marcarTodasComoLeidas(Long usuarioId) {
        notificacionRepository.marcarTodasComoLeidas(usuarioId);
    }

    public void eliminar(Long id) {
        notificacionRepository.deleteById(id);
    }
}