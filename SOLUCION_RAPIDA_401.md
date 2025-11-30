# 🚀 GUÍA RÁPIDA: Solucionar Errores 401

## Problema
Estás recibiendo errores **401 (Unauthorized)** porque **no has iniciado sesión**.

## Solución en 3 Pasos

### Paso 1: Crear Usuario Admin en la Base de Datos

#### Opción A: Usar pgAdmin (Recomendado)
1. Abre **pgAdmin**
2. Conéctate a tu base de datos `prueba1`
3. Click derecho en `prueba1` → **Query Tool**
4. Abre el archivo `crear_admin.sql` que acabo de crear
5. Copia y pega el contenido en Query Tool
6. Click en el botón **Execute** (▶️)
7. Verifica que no haya errores

#### Opción B: Usar DBeaver u otro cliente SQL
1. Abre tu cliente SQL favorito
2. Conéctate a `localhost:5432/prueba1` (usuario: postgres, password: 1735)
3. Abre el archivo `crear_admin.sql`
4. Ejecuta el script
5. Verifica que no haya errores

#### Opción C: Usar línea de comandos (si tienes psql en PATH)
```bash
psql -U postgres -d prueba1 -f "c:\Users\USER\Desktop\intellij 2.0\crear_admin.sql"
```

---

### Paso 2: Verificar que Backend esté corriendo

1. Abre una terminal en `c:\Users\USER\Desktop\intellij 2.0`
2. Ejecuta:
```bash
./mvnw spring-boot:run
```
3. Espera a que veas: `Started Trabajo_Aplicaciones_Web`
4. Verifica que esté en `http://localhost:8080`

---

### Paso 3: Hacer Login en el Frontend

1. Abre tu navegador en `http://localhost:4200`
2. Deberías ver la pantalla de login
3. Ingresa las credenciales:
   - **Email**: `admin@pulsepay.com`
   - **Password**: `password123`
4. Click en **Iniciar Sesión**

---

## ✅ Verificación

Si todo funcionó correctamente:

1. ✅ No deberías ver más errores 401 en la consola
2. ✅ Deberías estar en el Dashboard
3. ✅ Puedes navegar por todas las opciones del menú
4. ✅ "Tasas en Vivo" debería mostrar las tasas actualizándose cada 10 segundos

---

## 🔍 Verificar Token en el Navegador

1. Abre **DevTools** (F12)
2. Ve a la pestaña **Application**
3. En el menú izquierdo, expande **Session Storage**
4. Click en `http://localhost:4200`
5. Deberías ver una key llamada `token` con un valor largo (JWT)

---

## ❌ Si Aún Tienes Errores

### Error: "CREDENCIALES_INVALIDAS"
**Causa**: La contraseña no coincide  
**Solución**: Verifica que el hash BCrypt en la BD sea exactamente:
```
$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy
```

### Error: "Usuario no encontrado"
**Causa**: El script SQL no se ejecutó correctamente  
**Solución**: Verifica en pgAdmin que exista el usuario:
```sql
SELECT * FROM usuario WHERE email = 'admin@pulsepay.com';
```

### Error: Errores 401 después de login
**Causa**: El token no se está guardando  
**Solución**: Verifica en DevTools → Application → Session Storage que exista el token

---

## 📊 Después de Login Exitoso

Una vez que hayas iniciado sesión como Admin, puedes:

1. **Crear más usuarios** desde el menú "Usuarios"
2. **Crear criptomonedas** desde el menú "Criptomonedas"
3. **Crear tipos de cambio** desde el menú "Tipos de Cambio"
4. **Ver tasas en vivo** desde el menú "Tasas en Vivo"

---

## 🎯 Resumen

1. Ejecuta `crear_admin.sql` en pgAdmin
2. Inicia el backend (`./mvnw spring-boot:run`)
3. Abre `http://localhost:4200`
4. Login con `admin@pulsepay.com / password123`
5. ¡Listo! Ya no habrá errores 401

---

**¿Necesitas ayuda?** Avísame si algo no funciona.
