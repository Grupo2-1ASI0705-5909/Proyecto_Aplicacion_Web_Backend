package org.upc.trabajo_aplicaciones_web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador temporal para diagnosticar la configuración de JWT_SECRET en
 * Render.
 * ESTE ARCHIVO DEBE SER ELIMINADO EN PRODUCCIÓN POR RAZONES DE SEGURIDAD.
 */
@RestController
@RequestMapping("/api/debug")
public class DebugController {

    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * Endpoint para verificar que la variable JWT_SECRET esté configurada.
     * IMPORTANTE: Este endpoint NO muestra el valor completo por seguridad,
     * solo los primeros y últimos caracteres.
     */
    @GetMapping("/jwt-config")
    public Map<String, Object> checkJwtConfig() {
        Map<String, Object> response = new HashMap<>();

        if (jwtSecret != null && !jwtSecret.isEmpty()) {
            int length = jwtSecret.length();
            String prefix = jwtSecret.substring(0, Math.min(10, length));
            String suffix = jwtSecret.substring(Math.max(0, length - 10));

            response.put("configured", true);
            response.put("length", length);
            response.put("prefix", prefix + "...");
            response.put("suffix", "..." + suffix);
            response.put("expectedLength", 96); // La longitud correcta de tu secret
            response.put("isCorrectLength", length == 96);
        } else {
            response.put("configured", false);
            response.put("error", "JWT_SECRET no está configurado");
        }

        return response;
    }

    /**
     * Endpoint para verificar todas las variables de entorno relevantes.
     */
    @GetMapping("/env-check")
    public Map<String, Object> checkEnvironment() {
        Map<String, Object> response = new HashMap<>();

        // Verificar JWT_SECRET
        response.put("JWT_SECRET_configured", jwtSecret != null && !jwtSecret.isEmpty());
        response.put("JWT_SECRET_length", jwtSecret != null ? jwtSecret.length() : 0);

        // Información del sistema
        response.put("java_version", System.getProperty("java.version"));
        response.put("os_name", System.getProperty("os.name"));

        return response;
    }
}
