# Solución al Error 401 en Producción (Render)

## Problema Identificado

Estás recibiendo errores **401 Unauthorized** en producción (Render) que no ocurren en tu entorno local. El problema está relacionado con la **configuración de la clave secreta JWT** entre ambos ambientes.

### Causa Raíz
- **Local**: Usa la clave JWT hardcodeada en `application.properties`
- **Producción**: Probablemente está usando una variable de entorno diferente o no configurada, causando que los tokens JWT sean inválidos

## Solución Completa

### 1. Actualizar el Backend (Ya Completado ✅)

He actualizado `application.properties` para que use variables de entorno con fallback:

```properties
# JWT Secret
jwt.secret=${JWT_SECRET:95fc8e994d67e99f91b18b606a8a2d89e477aa141c598063f0c034201790835c3cc53d6cdcd1393e09d260}

# Database Configuration
spring.datasource.url=${DATABASE_URL:jdbc:postgresql://...}
spring.datasource.username=${DATABASE_USERNAME:db_grupo6_user}
spring.datasource.password=${DATABASE_PASSWORD:PGAkFeO9dLReLMBwPvdUSnxOIHn8pS5Z}
```

### 2. Configurar Variables de Entorno en Render

#### Pasos a seguir:

1. **Accede a Render Dashboard**:
   - Ve a: https://dashboard.render.com/
   - Inicia sesión con tu cuenta

2. **Selecciona tu servicio backend**:
   - Busca el servicio que contiene tu aplicación Java/Spring Boot

3. **Configura las Variables de Entorno**:
   - Ve a la pestaña **"Environment"** en el menú lateral
   - Haz clic en **"Add Environment Variable"**
   - Agrega la siguiente variable:

   ```
   Key: JWT_SECRET
   Value: 95fc8e994d67e99f91b18b606a8a2d89e477aa141c598063f0c034201790835c3cc53d6cdcd1393e09d260
   ```

   **IMPORTANTE**: El valor debe ser EXACTAMENTE el mismo que tienes en tu `application.properties` local.

4. **(Opcional) Verifica otras variables**:
   - Render podría estar configurando automáticamente `DATABASE_URL` si conectaste una base de datos PostgreSQL
   - Si no, agrega también:
     ```
     DATABASE_URL=jdbc:postgresql://dpg-d4ma0ja4d50c73egpcs0-a.oregon-postgres.render.com:5432/db_grupo6
     DATABASE_USERNAME=db_grupo6_user
     DATABASE_PASSWORD=PGAkFeO9dLReLMBwPvdUSnxOIHn8pS5Z
     ```

5. **Guarda los cambios**:
   - Haz clic en **"Save Changes"**
   - Render reiniciará automáticamente tu servicio

### 3. Re-deploy del Backend

Después de configurar las variables de entorno:

1. **Haz commit y push de los cambios** al repositorio:
   ```bash
   cd "c:\Users\USER\Desktop\FRONTEND\intellij 2.0"
   git add src/main/resources/application.properties
   git commit -m "fix: Configure JWT secret via environment variable"
   git push origin main
   ```

2. **Espera a que Render haga el deploy automático**:
   - Render detectará el push y hará el deploy automáticamente
   - Monitorea los logs en el dashboard de Render

### 4. Verificación

Una vez que el deploy esté completo:

1. **Limpia el localStorage** del frontend:
   - Abre las DevTools en el navegador (F12)
   - Ve a la pestaña **"Application"** → **"Local Storage"**
   - Elimina todos los items (especialmente el token)

2. **Inicia sesión nuevamente** en la aplicación

3. **Verifica que no aparezcan errores 401** en la consola

## ¿Por Qué Funciona Esta Solución?

### Antes:
```
[Frontend] Login → [Backend Local] Genera token con secret "ABC123"
                                    ↓
[Frontend] Request → [Backend Render] Valida token con secret "XYZ789" ❌ 401
```

### Después:
```
[Frontend] Login → [Backend Render] Genera token con secret "ABC123"
                                    ↓
[Frontend] Request → [Backend Render] Valida token con secret "ABC123" ✅ 200
```

## Problemas Comunes

### "Sigo recibiendo 401 después de configurar la variable"
1. Verifica que el valor de `JWT_SECRET` sea EXACTAMENTE el mismo (sin espacios adicionales)
2. Asegúrate de que Render haya reiniciado el servicio
3. Limpia el localStorage y vuelve a iniciar sesión para obtener un token nuevo

### "Los logs de Render muestran errores de conexión a la base de datos"
1. Verifica que las variables `DATABASE_URL`, `DATABASE_USERNAME`, y `DATABASE_PASSWORD` estén configuradas
2. Asegúrate de que la base de datos PostgreSQL en Render esté activa

### "El deploy falla en Render"
1. Revisa los logs de deploy en Render
2. Asegúrate de que el `pom.xml` esté configurado correctamente
3. Verifica que la versión de Java en Render coincida con la de tu proyecto

## Seguridad Adicional (Recomendado)

Para mayor seguridad, considera:

1. **Generar una nueva clave JWT más fuerte**:
   ```bash
   # Genera una nueva clave secreta de 64 bytes (128 caracteres hex)
   openssl rand -hex 64
   ```

2. **Actualizar la clave en ambos ambientes**:
   - Local: Actualiza `application.properties`
   - Render: Actualiza la variable de entorno `JWT_SECRET`

3. **Nunca commitear credenciales** al repositorio:
   - El archivo `application.properties` actualizado usa variables de entorno por defecto
   - Los valores hardcodeados son solo fallbacks para desarrollo local

## Contacto y Soporte

Si los errores persisten después de seguir estos pasos:
1. Revisa los logs en Render Dashboard
2. Verifica que el token se esté generando correctamente (revisa los logs del backend)
3. Usa las DevTools del navegador para inspeccionar el token JWT y verificar su estructura
