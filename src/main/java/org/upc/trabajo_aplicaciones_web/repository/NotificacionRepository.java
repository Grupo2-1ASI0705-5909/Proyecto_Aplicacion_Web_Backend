package org.upc.trabajo_aplicaciones_web.repository;

import org.upc.trabajo_aplicaciones_web.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    List<Notificacion> findByUsuarioIdOrderByFechaEnvioDesc(Long usuarioId);

    List<Notificacion> findByUsuarioIdAndLeidoFalseOrderByFechaEnvioDesc(Long usuarioId);

    long countByUsuarioIdAndLeidoFalse(Long usuarioId);

    @Modifying
    @Transactional
    @Query("UPDATE Notificacion n SET n.leido = true WHERE n.usuarioId = :usuarioId")
    void marcarTodasComoLeidas(Long usuarioId);
}