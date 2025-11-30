-- ============================================
-- SCRIPT DE DATOS DE PRUEBA - PULSEPAY
-- ============================================

-- 1. INSERTAR CRIPTOMONEDAS
-- ============================================
INSERT INTO criptomoneda (codigo, nombre, decimales, simbolo, activo) VALUES
('BTC', 'Bitcoin', 8, '₿', true),
('ETH', 'Ethereum', 18, 'Ξ', true),
('USDT', 'Tether', 6, '₮', true),
('BNB', 'Binance Coin', 8, 'Ⓑ', true),
('SOL', 'Solana', 9, '◎', true),
('ADA', 'Cardano', 6, '₳', true),
('XRP', 'Ripple', 6, 'Ʀ', true),
('DOT', 'Polkadot', 10, '●', true),
('DOGE', 'Dogecoin', 8, 'Ð', true),
('MATIC', 'Polygon', 18, 'Ⓜ', true)
ON CONFLICT (codigo) DO NOTHING;

-- 2. INSERTAR TIPOS DE CAMBIO INICIALES
-- ============================================
-- Tasas USD a Criptomonedas (valores aproximados reales)
INSERT INTO tipo_cambio (desde_codigo, hasta_codigo, tasa, fecha_hora, fuente) VALUES
('USD', 'BTC', 0.000023, NOW(), 'Binance'),
('USD', 'ETH', 0.00042, NOW(), 'Coinbase'),
('USD', 'USDT', 1.0, NOW(), 'Kraken'),
('USD', 'BNB', 0.0025, NOW(), 'Binance'),
('USD', 'SOL', 0.0087, NOW(), 'FTX'),
('USD', 'ADA', 1.85, NOW(), 'Kraken'),
('USD', 'XRP', 1.45, NOW(), 'Binance'),
('USD', 'DOT', 0.125, NOW(), 'Coinbase'),
('USD', 'DOGE', 8.5, NOW(), 'Binance'),
('USD', 'MATIC', 1.15, NOW(), 'Polygon');

-- Tasas PEN (Soles Peruanos) a Criptomonedas
INSERT INTO tipo_cambio (desde_codigo, hasta_codigo, tasa, fecha_hora, fuente) VALUES
('PEN', 'BTC', 0.0000063, NOW(), 'Binance'),
('PEN', 'ETH', 0.000115, NOW(), 'Coinbase'),
('PEN', 'USDT', 0.27, NOW(), 'Kraken'),
('PEN', 'BNB', 0.00068, NOW(), 'Binance'),
('PEN', 'SOL', 0.00238, NOW(), 'FTX');

-- 3. CREAR USUARIOS DE PRUEBA
-- ============================================
-- Nota: Las contraseñas deben estar encriptadas con BCrypt
-- Contraseña para todos: "password123"
-- Hash BCrypt: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy

INSERT INTO usuario (nombre, apellido, email, telefono, password, activo) VALUES
('Admin', 'Sistema', 'admin@pulsepay.com', '999888777', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', true),
('Juan', 'Pérez', 'juan.perez@gmail.com', '987654321', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', true),
('María', 'García', 'maria.garcia@gmail.com', '987654322', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', true),
('Carlos', 'López', 'carlos.lopez@comercio.com', '987654323', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', true),
('Ana', 'Martínez', 'ana.martinez@tienda.com', '987654324', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', true)
ON CONFLICT (email) DO NOTHING;

-- 4. ASIGNAR ROLES A USUARIOS
-- ============================================
-- Asumiendo que los IDs de roles son: 1=ADMINISTRADOR, 2=USUARIO, 3=COMERCIO
INSERT INTO usuario_rol (usuario_id, rol_id) VALUES
(1, 1), -- Admin es ADMINISTRADOR
(2, 2), -- Juan es USUARIO
(3, 2), -- María es USUARIO
(4, 3), -- Carlos es COMERCIO
(5, 3)  -- Ana es COMERCIO
ON CONFLICT DO NOTHING;

-- 5. CREAR WALLETS PARA USUARIOS
-- ============================================
INSERT INTO wallet (usuario_id, cripto_id, direccion, saldo, activo) VALUES
-- Wallets de Juan (usuario_id=2)
(2, 1, '1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa', 0.05, true),  -- BTC
(2, 2, '0x742d35Cc6634C0532925a3b844Bc9e7595f0bEb', 2.5, true),  -- ETH
(2, 3, '0x742d35Cc6634C0532925a3b844Bc9e7595f0bEc', 1000.0, true), -- USDT

-- Wallets de María (usuario_id=3)
(3, 1, '1BvBMSEYstWetqTFn5Au4m4GFg7xJaNVN2', 0.02, true),  -- BTC
(3, 4, 'bnb1grpf0955h0ykzq3ar5nmum7y6gdfl6lxfn46h2', 10.0, true), -- BNB
(3, 5, 'So11111111111111111111111111111111111111112', 50.0, true)  -- SOL
ON CONFLICT DO NOTHING;

-- 6. CREAR COMERCIOS
-- ============================================
INSERT INTO comercio (usuario_id, nombre_comercial, ruc, categoria, direccion, wallet_recepcion, activo) VALUES
(4, 'TechStore Perú', '20123456789', 'Tecnología', 'Av. Arequipa 1234, Lima', '1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa', true),
(5, 'Fashion Boutique', '20987654321', 'Moda', 'Jr. de la Unión 567, Lima', '0x742d35Cc6634C0532925a3b844Bc9e7595f0bEb', true)
ON CONFLICT (ruc) DO NOTHING;

-- 7. CREAR MÉTODOS DE PAGO
-- ============================================
INSERT INTO metodo_pago (usuario_id, tipo, detalles, activo) VALUES
(2, 'WALLET_CRIPTO', 'Bitcoin Wallet Principal', true),
(2, 'WALLET_CRIPTO', 'Ethereum Wallet', true),
(3, 'WALLET_CRIPTO', 'BNB Wallet', true)
ON CONFLICT DO NOTHING;

-- 8. CREAR TRANSACCIONES DE EJEMPLO
-- ============================================
INSERT INTO transaccion (usuario_id, comercio_id, cripto_id, metodo_pago_id, tipo_cambio_id, 
                        monto_total_fiat, codigo_moneda, monto_total_cripto, tasa_aplicada, 
                        estado, tx_hash, fecha_hora) VALUES
(2, 1, 1, 1, 1, 500.00, 'USD', 0.0115, 0.000023, 'COMPLETADA', 'tx_abc123def456', NOW() - INTERVAL '2 days'),
(2, 1, 2, 2, 2, 300.00, 'USD', 0.126, 0.00042, 'COMPLETADA', 'tx_def789ghi012', NOW() - INTERVAL '1 day'),
(3, 2, 4, 3, 4, 200.00, 'USD', 0.5, 0.0025, 'COMPLETADA', 'tx_ghi345jkl678', NOW() - INTERVAL '3 hours'),
(2, 2, 1, 1, 1, 150.00, 'USD', 0.00345, 0.000023, 'PENDIENTE', 'tx_pending001', NOW())
ON CONFLICT DO NOTHING;

-- 9. CREAR PLANES DE PAGO
-- ============================================
INSERT INTO plan_pago (transaccion_id, numero_cuotas, monto_cuota, frecuencia_dias) VALUES
(1, 3, 166.67, 30),  -- 3 cuotas mensuales
(2, 2, 150.00, 15)   -- 2 cuotas quincenales
ON CONFLICT DO NOTHING;

-- 10. CREAR CUOTAS
-- ============================================
INSERT INTO cuota (plan_pago_id, numero_cuota, monto, fecha_vencimiento, estado) VALUES
-- Cuotas del plan 1
(1, 1, 166.67, NOW() + INTERVAL '30 days', 'PENDIENTE'),
(1, 2, 166.67, NOW() + INTERVAL '60 days', 'PENDIENTE'),
(1, 3, 166.67, NOW() + INTERVAL '90 days', 'PENDIENTE'),

-- Cuotas del plan 2
(2, 1, 150.00, NOW() + INTERVAL '15 days', 'PENDIENTE'),
(2, 2, 150.00, NOW() + INTERVAL '30 days', 'PENDIENTE')
ON CONFLICT DO NOTHING;

-- 11. CREAR NOTIFICACIONES
-- ============================================
INSERT INTO notificacion (usuario_id, titulo, mensaje, tipo, leida, fecha_hora) VALUES
(2, 'Pago Exitoso', 'Tu pago de $500 a TechStore Perú fue procesado correctamente', 'TRANSACCION', false, NOW() - INTERVAL '2 days'),
(2, 'Cuota Próxima', 'Tienes una cuota de $166.67 que vence en 30 días', 'CUOTA', false, NOW() - INTERVAL '1 day'),
(4, 'Pago Recibido', 'Has recibido un pago de $500 de Juan Pérez', 'TRANSACCION', false, NOW() - INTERVAL '2 days'),
(3, 'Bienvenido', 'Bienvenido a PulsePay - Tu plataforma de pagos con criptomonedas', 'SISTEMA', true, NOW() - INTERVAL '7 days')
ON CONFLICT DO NOTHING;

-- ============================================
-- VERIFICACIÓN DE DATOS
-- ============================================
SELECT 'Criptomonedas insertadas:' as info, COUNT(*) as total FROM criptomoneda;
SELECT 'Tipos de cambio insertados:' as info, COUNT(*) as total FROM tipo_cambio;
SELECT 'Usuarios insertados:' as info, COUNT(*) as total FROM usuario;
SELECT 'Comercios insertados:' as info, COUNT(*) as total FROM comercio;
SELECT 'Transacciones insertadas:' as info, COUNT(*) as total FROM transaccion;

-- ============================================
-- DATOS DE ACCESO
-- ============================================
/*
USUARIOS DE PRUEBA:
==================

ADMINISTRADOR:
- Email: admin@pulsepay.com
- Password: password123

USUARIO (Cliente):
- Email: juan.perez@gmail.com
- Password: password123

- Email: maria.garcia@gmail.com
- Password: password123

COMERCIO:
- Email: carlos.lopez@comercio.com
- Password: password123

- Email: ana.martinez@tienda.com
- Password: password123
*/
