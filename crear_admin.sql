-- ============================================
-- SCRIPT MÍNIMO PARA CREAR USUARIO ADMIN
-- ============================================
-- Ejecuta este script desde pgAdmin o cualquier cliente PostgreSQL

-- 1. CREAR TODOS LOS ROLES NECESARIOS (si no existen)
-- Estos roles son requeridos por el sistema de autenticación
INSERT INTO rol (nombre, descripcion) VALUES 
('ADMINISTRADOR', 'Administrador del sistema con acceso completo a todas las funcionalidades'),
('USUARIO', 'Usuario cliente que puede realizar transacciones y gestionar sus wallets'),
('COMERCIO', 'Comercio que puede recibir pagos y gestionar sus ventas')
ON CONFLICT (nombre) DO NOTHING;

-- 2. CREAR USUARIO ADMIN
-- Contraseña: password123
-- Hash BCrypt: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy
INSERT INTO usuario (nombre, apellido, email, telefono, password, activo) 
VALUES ('Admin', 'Sistema', 'admin@pulsepay.com', '999888777', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', true)
ON CONFLICT (email) DO NOTHING;

-- 3. ASIGNAR ROL ADMINISTRADOR AL USUARIO
-- Nota: Ajusta los IDs según tu base de datos
INSERT INTO usuario_rol (usuario_id, rol_id)
SELECT u.usuario_id, r.rol_id
FROM usuario u, rol r
WHERE u.email = 'admin@pulsepay.com' 
AND r.nombre = 'ADMINISTRADOR'
ON CONFLICT DO NOTHING;

-- 4. CREAR ALGUNAS CRIPTOMONEDAS BÁSICAS
INSERT INTO criptomoneda (codigo, nombre, decimales, simbolo, activo) VALUES
('BTC', 'Bitcoin', 8, '₿', true),
('ETH', 'Ethereum', 18, 'Ξ', true),
('USDT', 'Tether', 6, '₮', true)
ON CONFLICT (codigo) DO NOTHING;

-- 5. CREAR TIPOS DE CAMBIO INICIALES
INSERT INTO tipo_cambio (desde_codigo, hasta_codigo, tasa, fecha_hora, fuente) VALUES
('USD', 'BTC', 0.000023, NOW(), 'Binance'),
('USD', 'ETH', 0.00042, NOW(), 'Coinbase'),
('USD', 'USDT', 1.0, NOW(), 'Kraken')
ON CONFLICT DO NOTHING;

-- VERIFICACIÓN
SELECT 'Usuario creado:' as info, email, nombre, apellido FROM usuario WHERE email = 'admin@pulsepay.com';
SELECT 'Criptomonedas:' as info, COUNT(*) as total FROM criptomoneda;
SELECT 'Tipos de cambio:' as info, COUNT(*) as total FROM tipo_cambio;

-- ============================================
-- CREDENCIALES DE ACCESO
-- ============================================
/*
Email: admin@pulsepay.com
Password: password123
*/
