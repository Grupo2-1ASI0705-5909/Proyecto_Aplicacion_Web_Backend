# 🔍 Diagnóstico Avanzado - Error 401 Persistente

## Estado Actual
- ✅ Variable `JWT_SECRET` configurada en Render
- ✅ Backend actualizado y pusheado
- ❌ Errores 401 persisten

---

## 📋 Checklist de Diagnóstico

Necesito que verifiques lo siguiente paso a paso:

### 1️⃣ Verificar que Render haya hecho el Deploy

**Pasos:**
1. Ve a Render Dashboard → Tu servicio backend
2. Ve a la pestaña **"Events"** o **"Logs"**
3. Verifica el último deploy:
   - ¿Cuándo fue el último deploy? (debe ser después de las 17:05)
   - ¿El deploy se completó exitosamente? (debe decir "Live" o "Deploy successful")

**¿Qué ves?** (Comparte la fecha/hora del último deploy exitoso)

---

### 2️⃣ Verificar la Variable de Entorno

**Pasos:**
1. En Render Dashboard → Tu servicio → **"Environment"**
2. Busca la variable `JWT_SECRET`
3. Haz clic en el ícono del "ojo" 👁️ para ver el valor

**Preguntas:**
- ¿Está la variable `JWT_SECRET` listada?
- ¿El valor comienza con `95fc8e994d67e99f...`?
- ¿Hay espacios al inicio o al final del valor?

---

### 3️⃣ Limpiar LocalStorage Completamente

**Pasos:**
1. Abre https://frontend-grupo6-final-v2.web.app/
2. Presiona **F12** para abrir DevTools
3. Ve a la pestaña **"Application"**
4. En el menú lateral, expande **"Local Storage"**
5. Haz clic en la URL de tu aplicación
6. Haz clic derecho en el panel → **"Clear"**
7. **Cierra completamente el navegador** (todas las pestañas)
8. Abre nuevamente el navegador

**¿Lo hiciste?** ✅ / ❌

---

### 4️⃣ Verificar los Logs del Backend en Render

**Pasos:**
1. En Render Dashboard → Tu servicio → **"Logs"**
2. Filtra los logs más recientes
3. Busca mensajes relacionados con JWT

**¿Qué errores ves?** (Copia los mensajes exactos)

Posibles mensajes a buscar:
- `"No se puede obtener el token JWT"`
- `"Token JWT ha expirado"`
- `"JWT Token no inicia con Bearer"`
- Excepciones de `io.jsonwebtoken`

---

### 5️⃣ Probar el Login Nuevamente

**Pasos:**
1. Con el localStorage limpio, ve a la página de login
2. Abre la consola del navegador (F12 → "Console")
3. Ingresa tus credenciales y presiona "Iniciar Sesión"

**En la consola, ¿ves?:**
- ✅ "UsuarioService: Token raw: Presente"
- ✅ "UsuarioService: Header Authorization seteado: Bearer eyJ..."
- ❌ Error 401 en las peticiones a `/api/comercios`, `/api/criptomonedas`, etc.

**Exactamente después de qué acción aparece el error 401?**
- ¿Inmediatamente después del login?
- ¿Al navegar a una sección específica?
- ¿En todas las secciones?

---

## 🔬 Causas Posibles y Soluciones

### Causa A: El Deploy No Se Completó

**Síntoma:** El último deploy en Render es anterior a las 17:05

**Solución:**
1. Ve a Render Dashboard → Tu servicio
2. Haz clic en **"Manual Deploy"**
3. Selecciona **"Clear build cache & deploy"**
4. Espera 5 minutos
5. Prueba nuevamente

---

### Causa B: La Variable Tiene un Valor Incorrecto

**Síntoma:** El valor de `JWT_SECRET` es diferente o tiene espacios

**Solución:**
1. Elimina la variable `JWT_SECRET` en Render
2. Agrégala nuevamente con el valor exacto:
   ```
   95fc8e994d67e99f91b18b606a8a2d89e477aa141c598063f0c034201790835c3cc53d6cdcd1393e09d260
   ```
3. Guarda y espera el redeploy automático

---

### Causa C: Token Generado con Clave Antigua

**Síntoma:** El token en localStorage fue generado antes de configurar la variable

**Solución:**
1. Limpia localStorage (paso 3 arriba)
2. Cierra COMPLETAMENTE el navegador
3. Abre nuevamente y haz login

---

### Causa D: Problema con CORS u Otra Configuración

**Síntoma:** Los logs del backend muestran errores diferentes a JWT

**Solución:**
Necesitaré ver los logs específicos para diagnosticar

---

## 🎯 Acción Inmediata Recomendada

Haz lo siguiente en orden:

1. **Fuerza un nuevo deploy con cache limpio:**
   - Render Dashboard → Manual Deploy → Clear build cache & deploy

2. **Mientras esperas el deploy (5 min):**
   - Limpia el localStorage
   - Cierra el navegador completamente

3. **Cuando el deploy termine:**
   - Abre el navegador
   - Ve a la aplicación
   - Haz login
   - Revisa la consola

4. **Si aún falla:**
   - Ve a Render → Logs
   - Copia los últimos 50 líneas de logs
   - Compártelos conmigo

---

## 📸 Capturas de Pantalla Útiles

Si el problema persiste, comparte capturas de:

1. **Render Environment Variables** (mostrando que JWT_SECRET está configurado)
2. **Render Logs** (últimas 50 líneas)
3. **Consola del navegador** (mostrando errores 401)
4. **DevTools → Network** (mostrando la petición fallida con headers)

---

**Última actualización:** 2025-11-30 17:16
