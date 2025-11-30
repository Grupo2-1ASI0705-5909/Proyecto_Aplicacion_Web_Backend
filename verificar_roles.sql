-- ============================================
-- SCRIPT DE VERIFICACIÓN Y CORRECCIÓN DE ROLES
-- PulsePay - PostgreSQL
-- ============================================
-- Ejecuta este script para asegurar que todos los roles necesarios existan
-- y para verificar la configuración actual de la base de datos

-- ============================================
-- PARTE 1: VERIFICACIÓN ACTUAL
-- ============================================

-- Mostrar roles existentes
SELECT '=== ROLES ACTUALES ===' as info;
SELECT rol_id, nombre, descripcion 
FROM rol 
ORDER BY rol_id;

-- Mostrar usuarios y sus roles
SELECT '=== USUARIOS Y SUS ROLES ===' as info;
SELECT 
    u.usuario_id,
    u.nombre,
    u.apellido,
    u.email,
    r.nombre as rol
FROM usuario u
LEFT JOIN usuario_rol ur ON u.usuario_id = ur.usuario_id
LEFT JOIN rol r ON ur.rol_id = r.rol_id
ORDER BY u.usuario_id;

-- ============================================
-- PARTE 2: CORRECCIÓN - CREAR ROLES FALTANTES
-- ============================================

-- Crear los 3 roles necesarios si no existen
INSERT INTO rol (nombre, descripcion) VALUES 
('ADMINISTRADOR', 'Administrador del sistema con acceso completo a todas las funcionalidades'),
('USUARIO', 'Usuario cliente que puede realizar transacciones y gestionar sus wallets'),
('COMERCIO', 'Comercio que puede recibir pagos y gestionar sus ventas')
ON CONFLICT (nombre) DO NOTHING;

-- Verificar que se crearon correctamente
SELECT '=== ROLES DESPUÉS DE LA CORRECCIÓN ===' as info;
SELECT rol_id, nombre, descripcion 
FROM rol 
ORDER BY nombre;

-- ============================================
-- PARTE 3: VERIFICACIÓN DE USUARIOS SIN ROL
-- ============================================

-- Encontrar usuarios sin rol asignado
SELECT '=== USUARIOS SIN ROL ASIGNADO ===' as info;
SELECT 
    u.usuario_id,
    u.nombre,
    u.apellido,
    u.email
FROM usuario u
LEFT JOIN usuario_rol ur ON u.usuario_id = ur.usuario_id
WHERE ur.rol_id IS NULL;

-- ============================================
-- PARTE 4: ASIGNAR ROL USUARIO POR DEFECTO
-- ============================================
-- Si hay usuarios sin rol, asignarles el rol USUARIO por defecto
-- (Puedes comentar esto si prefieres asignar roles manualmente)

/*
INSERT INTO usuario_rol (usuario_id, rol_id)
SELECT 
    u.usuario_id,
    (SELECT rol_id FROM rol WHERE nombre = 'USUARIO' LIMIT 1)
FROM usuario u
LEFT JOIN usuario_rol ur ON u.usuario_id = ur.usuario_id
WHERE ur.rol_id IS NULL
ON CONFLICT DO NOTHING;
*/

-- ============================================
-- PARTE 5: VERIFICACIÓN DE DATOS RELACIONADOS
-- ============================================

-- Verificar criptomonedas
SELECT '=== CRIPTOMONEDAS ACTIVAS ===' as info;
SELECT cripto_id, codigo, nombre, decimales, activo
FROM criptomoneda
WHERE activo = true
ORDER BY codigo;

-- Verificar tipos de cambio recientes
SELECT '=== TIPOS DE CAMBIO RECIENTES ===' as info;
SELECT tipo_cambio_id, desde_codigo, hasta_codigo, tasa, fecha_hora, fuente
FROM tipo_cambio
ORDER BY fecha_hora DESC
LIMIT 10;

-- Verificar comercios
SELECT '=== COMERCIOS ACTIVOS ===' as info;
SELECT comercio_id, nombre_comercial, ruc, categoria, activo
FROM comercio
WHERE activo = true
ORDER BY nombre_comercial;

-- Verificar métodos de pago
SELECT '=== MÉTODOS DE PAGO ACTIVOS ===' as info;
SELECT metodo_pago_id, usuario_id, tipo, detalles, activo
FROM metodo_pago
WHERE activo = true
ORDER BY metodo_pago_id;

-- ============================================
-- PARTE 6: ESTADÍSTICAS GENERALES
-- ============================================

SELECT '=== ESTADÍSTICAS GENERALES ===' as info;

SELECT 
    'Total Usuarios' as metrica,
    COUNT(*) as cantidad
FROM usuario
UNION ALL
SELECT 
    'Usuarios Activos' as metrica,
    COUNT(*) as cantidad
FROM usuario
WHERE activo = true
UNION ALL
SELECT 
    'Total Comercios' as metrica,
    COUNT(*) as cantidad
FROM comercio
UNION ALL
SELECT 
    'Comercios Activos' as metrica,
    COUNT(*) as cantidad
FROM comercio
WHERE activo = true
UNION ALL
SELECT 
    'Total Transacciones' as metrica,
    COUNT(*) as cantidad
FROM transaccion
UNION ALL
SELECT 
    'Transacciones Completadas' as metrica,
    COUNT(*) as cantidad
FROM transaccion
WHERE estado = 'COMPLETADA'
UNION ALL
SELECT 
    'Transacciones Pendientes' as metrica,
    COUNT(*) as cantidad
FROM transaccion
WHERE estado = 'PENDIENTE';

-- ============================================
-- PARTE 7: VERIFICACIÓN DE INTEGRIDAD
-- ============================================

-- Verificar transacciones sin usuario
SELECT '=== PROBLEMAS DE INTEGRIDAD ===' as info;

SELECT 'Transacciones sin usuario válido' as problema, COUNT(*) as cantidad
FROM transaccion t
LEFT JOIN usuario u ON t.usuario_id = u.usuario_id
WHERE u.usuario_id IS NULL
UNION ALL
SELECT 'Transacciones sin comercio válido' as problema, COUNT(*) as cantidad
FROM transaccion t
LEFT JOIN comercio c ON t.comercio_id = c.comercio_id
WHERE c.comercio_id IS NULL
UNION ALL
SELECT 'Wallets sin usuario válido' as problema, COUNT(*) as cantidad
FROM wallet w
LEFT JOIN usuario u ON w.usuario_id = u.usuario_id
WHERE u.usuario_id IS NULL
UNION ALL
SELECT 'Wallets sin criptomoneda válida' as problema, COUNT(*) as cantidad
FROM wallet w
LEFT JOIN criptomoneda c ON w.cripto_id = c.cripto_id
WHERE c.cripto_id IS NULL;

-- ============================================
-- RESUMEN FINAL
-- ============================================

SELECT '=== RESUMEN FINAL ===' as info;
SELECT 
    'Roles configurados correctamente' as estado,
    CASE 
        WHEN COUNT(*) >= 3 THEN '✅ OK'
        ELSE '❌ FALTAN ROLES'
    END as resultado
FROM rol
WHERE nombre IN ('ADMINISTRADOR', 'USUARIO', 'COMERCIO');

-- ============================================
-- NOTAS IMPORTANTES
-- ============================================
/*
DESPUÉS DE EJECUTAR ESTE SCRIPT:

1. Verifica que los 3 roles existan (ADMINISTRADOR, USUARIO, COMERCIO)
2. Asegúrate de que todos los usuarios tengan un rol asignado
3. Si hay usuarios sin rol, decide si asignarles USUARIO por defecto o hacerlo manualmente
4. Reinicia el backend de Spring Boot para que tome los cambios
5. Prueba el login con diferentes usuarios para verificar que los roles funcionen

CREDENCIALES DE PRUEBA (si usaste el script crear_admin.sql):
- Admin: admin@pulsepay.com / password123
- Usuario: juan.perez@gmail.com / password123
- Comercio: carlos.lopez@comercio.com / password123

ROLES ESPERADOS EN JWT:
- El backend agrega el prefijo "ROLE_" automáticamente
- En el token JWT verás: ROLE_ADMINISTRADOR, ROLE_USUARIO, ROLE_COMERCIO
- Los controladores usan @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO', ...)")
*/
