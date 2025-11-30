# 📊 ANÁLISIS DE FUNCIONALIDADES - PULSEPAY

## 🎯 Resumen Ejecutivo

PulsePay es una plataforma de pagos con criptomonedas que permite a usuarios realizar transacciones, gestionar wallets digitales y realizar pagos a comercios. El sistema cuenta con tres roles principales: Administrador, Comercio y Cliente (Usuario).

---

## 🏗️ Arquitectura del Sistema

### **Backend**
- **Framework**: Spring Boot 3.x
- **Lenguaje**: Java 17+
- **Base de Datos**: PostgreSQL/MySQL
- **Seguridad**: Spring Security + JWT
- **ORM**: JPA/Hibernate

### **Frontend**
- **Framework**: Angular 19
- **UI Library**: Angular Material
- **Autenticación**: JWT con @auth0/angular-jwt
- **Estado**: RxJS Observables

---

## 📦 Módulos del Sistema

### 1. **Módulo de Autenticación y Autorización**
**Componentes**:
- Login
- Registro
- Recuperación de contraseña
- Guards de seguridad (seguridadGuard, roleGuard)
- Interceptor HTTP para manejo de errores

**Funcionalidades**:
- ✅ Login con email y contraseña
- ✅ Registro de nuevos usuarios
- ✅ Recuperación de contraseña
- ✅ Gestión de sesiones con JWT (7 horas de expiración)
- ✅ Control de acceso basado en roles
- ✅ Redirección automática en errores 401/403

---

### 2. **Módulo de Usuarios**
**Componentes**:
- UsuarioListarComponent
- UsuarioCrearComponent
- PerfilComponent

**Funcionalidades**:
- ✅ Crear usuarios (solo Admin)
- ✅ Editar usuarios (solo Admin)
- ✅ Activar/Desactivar usuarios
- ✅ Asignar roles (ADMINISTRADOR, USUARIO, COMERCIO)
- ✅ Ver perfil propio
- ✅ Validación asíncrona de email único

**Entidades**:
- Usuario (id, nombre, apellido, email, telefono, password, activo)
- Rol (id, nombre)
- UsuarioRol (relación many-to-many)

---

### 3. **Módulo de Comercios**
**Componentes**:
- ComercioListarComponent
- ComercioCrearComponent

**Funcionalidades**:
- ✅ Crear comercio (Admin y Comercio)
- ✅ Editar comercio
- ✅ Ver todos los comercios (solo Admin)
- ✅ Gestionar información del comercio
- ✅ Validación asíncrona de RUC único

**Entidades**:
- Comercio (id, usuarioId, nombreComercial, ruc, categoria, direccion, walletRecepcion, activo)

---

### 4. **Módulo de Criptomonedas**
**Componentes**:
- CriptoListarComponent
- CriptoCrearComponent

**Funcionalidades**:
- ✅ Listar criptomonedas (todos los usuarios)
- ✅ Crear criptomoneda (solo Admin)
- ✅ Editar criptomoneda (solo Admin)
- ✅ Activar/Desactivar criptomoneda

**Entidades**:
- Criptomoneda (id, codigo, nombre, decimales, simbolo, activo)

**Criptomonedas Soportadas**:
- BTC (Bitcoin)
- ETH (Ethereum)
- USDT (Tether)
- BNB (Binance Coin)
- SOL (Solana)
- ADA (Cardano)
- XRP (Ripple)
- DOT (Polkadot)
- DOGE (Dogecoin)
- MATIC (Polygon)

---

### 5. **Módulo de Wallets**
**Componentes**:
- WalletListarComponent
- WalletCrearComponent
- WalletDetalleComponent

**Funcionalidades**:
- ✅ Crear wallet (todos los usuarios)
- ✅ Editar wallet
- ✅ Ver wallets propias
- ✅ Ver detalle de wallet con transacciones
- ✅ Activar/Desactivar wallet

**Entidades**:
- Wallet (id, usuarioId, criptoId, direccion, saldo, activo)

---

### 6. **Módulo de Tipos de Cambio**
**Componentes**:
- TipoCambioListarComponent
- TipoCambioCrearComponent
- **CriptoTasasLiveComponent** ← NUEVO

**Funcionalidades**:
- ✅ Listar tipos de cambio (todos los usuarios)
- ✅ Crear tipo de cambio (solo Admin)
- ✅ Eliminar tipo de cambio (solo Admin)
- ✅ **Ver tasas en tiempo real con actualización automática** ← NUEVO
- ✅ **Indicadores visuales de tendencia (subida/bajada)** ← NUEVO
- ✅ Obtener tasa más reciente por par
- ✅ Obtener historial de tasas
- ✅ Calcular promedio de tasas

**Entidades**:
- TipoCambio (id, desdeCodigo, hastaCodigo, tasa, fechaHora, fuente)

**Endpoints**:
- GET `/api/tipos-cambio` - Listar todos
- POST `/api/tipos-cambio` - Crear (Admin)
- DELETE `/api/tipos-cambio/{id}` - Eliminar (Admin)
- GET `/api/tipos-cambio/tasas-recientes` - Tasas más recientes de todos los pares
- GET `/api/tipos-cambio/tasa-actual` - Tasa actual de un par específico
- GET `/api/tipos-cambio/historial` - Historial de un par
- GET `/api/tipos-cambio/promedio` - Promedio en un período

---

### 7. **Módulo de Transacciones**
**Componentes**:
- TransaccionListarComponent
- TransaccionCrearComponent
- TransaccionDetalleDialogComponent

**Funcionalidades**:
- ✅ Crear transacción (pago)
- ✅ Ver transacciones propias
- ✅ Ver todas las transacciones (solo Admin)
- ✅ Eliminar transacciones PENDIENTES
- ✅ Cálculo automático de monto en cripto (backend)
- ✅ Ver detalle de transacción

**Entidades**:
- Transaccion (id, usuarioId, comercioId, criptoId, metodoPagoId, tipoCambioId, montoTotalFiat, codigoMoneda, montoTotalCripto, tasaAplicada, estado, txHash, fechaHora)

**Estados**:
- PENDIENTE
- COMPLETADA
- FALLIDA

---

### 8. **Módulo de Planes de Pago**
**Componentes**:
- PlanPagoListarComponent
- PlanPagoCrearComponent
- CuotaDialogComponent

**Funcionalidades**:
- ✅ Crear plan de pago (cuotas)
- ✅ Ver planes de pago
- ✅ Ver cuotas de un plan
- ✅ Pagar cuotas
- ✅ Gestionar frecuencia de pago

**Entidades**:
- PlanPago (id, transaccionId, numeroCuotas, montoCuota, frecuenciaDias)
- Cuota (id, planPagoId, numeroCuota, monto, fechaVencimiento, estado, fechaPago)

---

### 9. **Módulo de Métodos de Pago**
**Componentes**:
- MetodoPagoListarComponent
- MetodoPagoCrearComponent

**Funcionalidades**:
- ✅ Crear método de pago (Admin y Usuario)
- ✅ Editar método de pago
- ✅ Ver métodos de pago propios
- ✅ Activar/Desactivar método de pago

**Entidades**:
- MetodoPago (id, usuarioId, tipo, detalles, activo)

**Tipos**:
- WALLET_CRIPTO
- TARJETA_CREDITO
- TRANSFERENCIA_BANCARIA

---

### 10. **Módulo de Notificaciones**
**Componentes**:
- NotificacionListarComponent

**Funcionalidades**:
- ✅ Ver notificaciones propias
- ✅ Marcar como leída
- ✅ Filtrar por tipo

**Entidades**:
- Notificacion (id, usuarioId, titulo, mensaje, tipo, leida, fechaHora)

**Tipos**:
- TRANSACCION
- CUOTA
- SISTEMA

---

### 11. **Módulo de Dashboard**
**Componentes**:
- DashboardComponent

**Funcionalidades**:
- ✅ Ver estadísticas según rol
- ✅ Admin: Estadísticas globales del sistema
- ✅ Comercio: Resumen de ventas
- ✅ Cliente: Saldo total y últimas transacciones

---

## 🔒 Seguridad Implementada

### **Autenticación**
- ✅ JWT con expiración de 7 horas
- ✅ Contraseñas encriptadas con BCrypt
- ✅ Interceptor HTTP para manejo de errores 401/403

### **Autorización**
- ✅ Guards en rutas frontend
- ✅ `@PreAuthorize` en endpoints backend
- ✅ Validación de propiedad de recursos (IDOR protegido)

### **Validaciones**
- ✅ Email único (validación asíncrona)
- ✅ RUC único para comercios
- ✅ Solo se pueden eliminar transacciones PENDIENTES
- ✅ Usuarios solo ven sus propios datos

---

## 📊 Flujos Principales

### **Flujo 1: Registro y Login**
1. Usuario se registra en `/registro`
2. Sistema asigna rol "USUARIO"
3. Usuario inicia sesión
4. Sistema genera JWT
5. Usuario es redirigido al dashboard

### **Flujo 2: Crear Wallet**
1. Usuario autenticado va a "Mis Wallets"
2. Click en "Nueva Wallet"
3. Selecciona criptomoneda
4. Ingresa dirección de wallet
5. Sistema guarda wallet

### **Flujo 3: Realizar Pago**
1. Usuario va a "Mis Pagos" → "Nueva Transacción"
2. Selecciona comercio destino
3. Selecciona método de pago
4. Ingresa monto en fiat (USD, PEN, etc.)
5. Selecciona criptomoneda
6. **Backend calcula automáticamente monto en cripto**
7. Usuario confirma transacción
8. Sistema procesa pago

### **Flujo 4: Ver Tasas en Tiempo Real** ← NUEVO
1. Usuario autenticado va a "Tasas en Vivo"
2. Sistema muestra tasas más recientes
3. **Cada 10 segundos, sistema actualiza automáticamente**
4. **Indicadores visuales muestran si precio subió o bajó**
5. Usuario ve cambios en tiempo real sin recargar

---

## 🎨 Características de UX/UI

### **Diseño**
- ✅ Material Design
- ✅ Responsive (móvil, tablet, desktop)
- ✅ Tema oscuro/claro (configurable)
- ✅ Animaciones suaves
- ✅ Feedback visual en acciones

### **Navegación**
- ✅ Sidebar con menú según rol
- ✅ Breadcrumbs
- ✅ Rutas protegidas
- ✅ Redirección automática

### **Formularios**
- ✅ Validación en tiempo real
- ✅ Mensajes de error claros
- ✅ Validación asíncrona
- ✅ Autocomplete donde aplica

---

## 📈 Métricas y Reportes

### **Disponibles**:
- ✅ Total de transacciones
- ✅ Monto total transaccionado
- ✅ Promedio de tasas
- ✅ Historial de tasas

### **Por Implementar**:
- ⏳ Gráficos de tendencias
- ⏳ Reportes exportables (PDF, Excel)
- ⏳ Dashboard con gráficos interactivos

---

## 🚀 Tecnologías y Herramientas

### **Backend**
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- JWT (io.jsonwebtoken)
- BCrypt
- Lombok
- ModelMapper

### **Frontend**
- Angular 19
- Angular Material 19
- RxJS 7.8
- @auth0/angular-jwt
- TypeScript 5.7

### **Base de Datos**
- PostgreSQL / MySQL
- Flyway (migraciones)

### **DevOps**
- Docker
- Maven
- Git

---

## 📊 Estadísticas del Proyecto

### **Backend**
- **Controladores**: 12
- **Servicios**: 12
- **Repositorios**: 12
- **Entidades**: 12
- **DTOs**: 12+
- **Endpoints**: ~60

### **Frontend**
- **Componentes**: 22+
- **Servicios**: 12
- **Guards**: 2
- **Interceptores**: 1
- **Rutas**: 30+

---

## 🎯 Casos de Uso Principales

### **UC-01: Gestión de Usuarios**
**Actor**: Administrador
**Flujo**:
1. Admin accede a "Usuarios"
2. Crea nuevo usuario
3. Asigna rol
4. Activa/Desactiva según necesidad

### **UC-02: Gestión de Comercios**
**Actor**: Administrador, Comercio
**Flujo**:
1. Usuario accede a "Comercios"
2. Crea/Edita información del comercio
3. Configura wallet de recepción
4. Guarda cambios

### **UC-03: Gestión de Wallets**
**Actor**: Usuario
**Flujo**:
1. Usuario accede a "Mis Wallets"
2. Crea nueva wallet
3. Selecciona criptomoneda
4. Ingresa dirección
5. Sistema guarda wallet

### **UC-04: Realizar Transacción**
**Actor**: Usuario
**Flujo**:
1. Usuario accede a "Mis Pagos"
2. Click en "Nueva Transacción"
3. Selecciona comercio, método de pago, monto y cripto
4. Backend calcula conversión
5. Usuario confirma
6. Sistema procesa pago

### **UC-05: Gestión de Tipos de Cambio**
**Actor**: Administrador
**Flujo**:
1. Admin accede a "Tipos de Cambio"
2. Crea nuevo tipo de cambio
3. Ingresa par (desde-hasta), tasa y fuente
4. Sistema guarda

### **UC-06: Ver Tasas en Tiempo Real** ← NUEVO
**Actor**: Todos los usuarios autenticados
**Flujo**:
1. Usuario accede a "Tasas en Vivo"
2. Sistema muestra tasas más recientes
3. Sistema actualiza automáticamente cada 10 segundos
4. Usuario ve cambios con indicadores visuales

### **UC-07: Crear Plan de Pago**
**Actor**: Usuario
**Flujo**:
1. Usuario accede a "Planes de Pago"
2. Crea nuevo plan
3. Selecciona transacción
4. Define número de cuotas y frecuencia
5. Sistema genera cuotas

### **UC-08: Pagar Cuota**
**Actor**: Usuario
**Flujo**:
1. Usuario ve plan de pago
2. Click en "Pagar Cuota"
3. Confirma pago
4. Sistema actualiza estado de cuota

---

## 🔄 Integraciones

### **Actuales**:
- ✅ Ninguna (sistema standalone)

### **Futuras**:
- ⏳ APIs de exchanges (Binance, Coinbase, Kraken)
- ⏳ Pasarelas de pago tradicionales
- ⏳ Servicios de KYC/AML
- ⏳ Notificaciones push
- ⏳ Webhooks para comercios

---

## 📝 Conclusiones

PulsePay es una plataforma completa y funcional que permite:
1. ✅ Gestión de usuarios con roles
2. ✅ Gestión de comercios
3. ✅ Gestión de wallets de criptomonedas
4. ✅ Realización de pagos con conversión automática
5. ✅ Planes de pago con cuotas
6. ✅ **Visualización de tasas en tiempo real** ← NUEVO
7. ✅ Sistema de notificaciones
8. ✅ Seguridad robusta con JWT y roles

El sistema está listo para ser usado con 10 usuarios concurrentes y puede escalar según necesidad.
