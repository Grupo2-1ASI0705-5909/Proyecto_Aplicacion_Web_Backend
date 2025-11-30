# 🚀 Guía Paso a Paso: Configurar JWT_SECRET en Render

## ✅ Paso 1: Cambios en el Backend (COMPLETADO)

- ✅ Archivo `application.properties` actualizado
- ✅ Commit realizado: `8002ed9`
- ✅ Push exitoso a GitHub

**Render detectará automáticamente el nuevo push y hará el deploy.**

---

## 🔧 Paso 2: Configurar Variable de Entorno en Render

### Accede a Render Dashboard

1. **Abre tu navegador** y ve a: https://dashboard.render.com/
2. **Inicia sesión** con tu cuenta de Render

### Selecciona tu Servicio Backend

3. En el dashboard, busca tu servicio backend (probablemente se llama algo como `proyecto-aplicacion-web` o similar)
4. **Haz clic** en el nombre del servicio para abrirlo

### Configura la Variable de Entorno

5. En el menú lateral izquierdo, haz clic en **"Environment"**
6. Desplázate hasta la sección **"Environment Variables"**
7. Haz clic en el botón **"Add Environment Variable"**
8. Completa los siguientes campos:

   ```
   Key:   JWT_SECRET
   Value: 95fc8e994d67e99f91b18b606a8a2d89e477aa141c598063f0c034201790835c3cc53d6cdcd1393e09d260
   ```

   ⚠️ **IMPORTANTE**: 
   - Copia y pega el valor EXACTAMENTE como está arriba
   - No agregues espacios adicionales al inicio o al final
   - Asegúrate de que el Key sea exactamente `JWT_SECRET` (en mayúsculas)

9. Haz clic en **"Save Changes"**

### Reinicia el Servicio (Si es necesario)

10. Si Render no reinicia automáticamente después de guardar:
    - Haz clic en **"Manual Deploy"** → **"Deploy latest commit"**
    - O espera a que el deploy automático del push reciente se complete

---

## ⏳ Paso 3: Espera el Deploy

11. Ve a la pestaña **"Logs"** en el menú lateral
12. Observa el proceso de deploy. Deberías ver mensajes como:
    ```
    ==> Building...
    ==> Deploying...
    ==> Your service is live 🎉
    ```
13. **Tiempo estimado**: 3-5 minutos

---

## ✅ Paso 4: Verificar la Solución

### En el Navegador:

14. Abre la aplicación frontend en: https://frontend-grupo6-final-v2.web.app/
15. Abre las **DevTools** (presiona F12)
16. Ve a la pestaña **"Application"** → **"Local Storage"**
17. Elimina TODOS los items del localStorage (especialmente el token)
18. **Cierra y vuelve a abrir** las DevTools

### Inicia Sesión:

19. Ingresa tus credenciales y haz clic en **"Iniciar Sesión"**
20. Ve a la pestaña **"Console"** de las DevTools
21. **Verifica que NO aparezcan errores 401**
22. Deberías ver mensajes como:
    ```
    UsuarioService: Token raw: Presente
    UsuarioService: Header Authorization seteado: Bearer eyJ...
    ```

### Prueba la Funcionalidad:

23. Navega a diferentes secciones de la aplicación (Comercios, Criptomonedas, Wallets, etc.)
24. Verifica que los datos se carguen correctamente
25. **NO deberías ver más errores 401** ✅

---

## 🐛 Si Aún Ves Errores 401

### Verifica la Variable de Entorno:

1. En Render Dashboard → Tu Servicio → **"Environment"**
2. Confirma que `JWT_SECRET` esté listada con el valor correcto
3. Haz clic en el ícono de "ojo" 👁️ para ver el valor y verificarlo

### Revisa los Logs del Backend:

1. Ve a **"Logs"** en el menú lateral de Render
2. Busca mensajes de error relacionados con JWT
3. Si ves errores como `"No se puede obtener el token JWT"` o `"Token JWT ha expirado"`, significa que:
   - El token antiguo sigue en uso (limpia el localStorage)
   - La variable no se configuró correctamente

### Fuerza un Nuevo Deploy:

1. Ve a **"Manual Deploy"**
2. Haz clic en **"Clear build cache & deploy"**
3. Espera a que se complete el deploy
4. Vuelve a probar

---

## 📝 Valores de las Variables de Entorno

Para referencia futura, estas son las variables que deberías tener configuradas en Render:

| Key | Value | Descripción |
|-----|-------|-------------|
| `JWT_SECRET` | `95fc8e994d67e99f91b18b606a8a2d89e477aa141c598063f0c034201790835c3cc53d6cdcd1393e09d260` | Clave secreta para firmar/validar tokens JWT |
| `DATABASE_URL` | (Configurado por Render automáticamente si conectaste una DB) | URL de conexión a PostgreSQL |
| `DATABASE_USERNAME` | (Configurado por Render automáticamente) | Usuario de la base de datos |
| `DATABASE_PASSWORD` | (Configurado por Render automáticamente) | Contraseña de la base de datos |

---

## 🎯 Checklist Final

Antes de decir que está resuelto, verifica:

- [ ] Variable `JWT_SECRET` configurada en Render
- [ ] Deploy completado sin errores
- [ ] LocalStorage limpiado en el navegador
- [ ] Inicio de sesión exitoso
- [ ] NO hay errores 401 en la consola
- [ ] Los datos se cargan correctamente en todas las secciones

---

## 📞 ¿Necesitas Ayuda?

Si sigues teniendo problemas:

1. **Captura de pantalla** de:
   - La sección "Environment Variables" en Render
   - Los errores en la consola del navegador
   - Los logs del backend en Render

2. **Comparte** esa información para un diagnóstico más específico

---

**Última actualización**: 2025-11-30 17:05 (después del push exitoso)
