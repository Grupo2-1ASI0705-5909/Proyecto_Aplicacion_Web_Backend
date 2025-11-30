# 📖 USER STORIES - PULSEPAY

## 📋 Índice
1. [Épicas](#épicas)
2. [User Stories por Módulo](#user-stories-por-módulo)
3. [Criterios de Aceptación](#criterios-de-aceptación)
4. [Priorización](#priorización)

---

## 🎯 Épicas

### **ÉPICA 1: Gestión de Usuarios y Autenticación**
Como sistema, necesito gestionar usuarios y su autenticación para controlar el acceso a la plataforma.

### **ÉPICA 2: Gestión de Comercios**
Como plataforma, necesito permitir la gestión de comercios para que puedan recibir pagos.

### **ÉPICA 3: Gestión de Criptomonedas y Wallets**
Como plataforma, necesito gestionar criptomonedas y wallets para permitir transacciones.

### **ÉPICA 4: Gestión de Tipos de Cambio**
Como plataforma, necesito gestionar tipos de cambio para calcular conversiones de monedas.

### **ÉPICA 5: Gestión de Transacciones**
Como plataforma, necesito permitir la realización de transacciones entre usuarios y comercios.

### **ÉPICA 6: Gestión de Planes de Pago**
Como plataforma, necesito permitir pagos en cuotas para facilitar compras grandes.

### **ÉPICA 7: Visualización de Datos en Tiempo Real** ← NUEVO
Como usuario, necesito ver información actualizada en tiempo real para tomar decisiones informadas.

---

## 📝 USER STORIES POR MÓDULO

---

## 🔐 MÓDULO 1: AUTENTICACIÓN Y AUTORIZACIÓN

### **US-001: Registro de Usuario**
**Como** visitante del sitio  
**Quiero** registrarme en la plataforma  
**Para** poder acceder a los servicios de PulsePay

**Criterios de Aceptación**:
- ✅ El formulario solicita: nombre, apellido, email, teléfono y contraseña
- ✅ El email debe ser único en el sistema
- ✅ La contraseña debe tener mínimo 8 caracteres
- ✅ El sistema valida el email en tiempo real (validación asíncrona)
- ✅ Al registrarse, se asigna automáticamente el rol "USUARIO"
- ✅ La contraseña se encripta con BCrypt antes de guardarse
- ✅ Se muestra mensaje de éxito al completar el registro
- ✅ Se redirige automáticamente al login

**Prioridad**: ALTA  
**Estimación**: 5 puntos  
**Épica**: Gestión de Usuarios y Autenticación

---

### **US-002: Inicio de Sesión**
**Como** usuario registrado  
**Quiero** iniciar sesión en la plataforma  
**Para** acceder a mis funcionalidades según mi rol

**Criterios de Aceptación**:
- ✅ El formulario solicita email y contraseña
- ✅ El sistema valida las credenciales contra la base de datos
- ✅ Si las credenciales son correctas, se genera un JWT válido por 7 horas
- ✅ El token se almacena en localStorage
- ✅ Se redirige al dashboard según el rol del usuario
- ✅ Si las credenciales son incorrectas, se muestra mensaje de error
- ✅ El sistema verifica que el usuario esté activo

**Prioridad**: ALTA  
**Estimación**: 3 puntos  
**Épica**: Gestión de Usuarios y Autenticación

---

### **US-003: Recuperación de Contraseña**
**Como** usuario que olvidó su contraseña  
**Quiero** poder recuperarla  
**Para** volver a acceder a mi cuenta

**Criterios de Aceptación**:
- ✅ El formulario solicita el email
- ✅ El sistema verifica que el email exista
- ✅ Se envía un enlace de recuperación al email (simulado)
- ✅ Se muestra mensaje de confirmación
- ✅ El enlace tiene una expiración de 24 horas

**Prioridad**: MEDIA  
**Estimación**: 5 puntos  
**Épica**: Gestión de Usuarios y Autenticación

---

### **US-004: Cierre de Sesión**
**Como** usuario autenticado  
**Quiero** cerrar sesión  
**Para** proteger mi cuenta cuando no esté usando la plataforma

**Criterios de Aceptación**:
- ✅ El botón "Cerrar Sesión" está visible en el menú
- ✅ Al hacer click, se elimina el token de localStorage
- ✅ Se redirige al login
- ✅ No se puede acceder a rutas protegidas después de cerrar sesión

**Prioridad**: ALTA  
**Estimación**: 2 puntos  
**Épica**: Gestión de Usuarios y Autenticación

---

### **US-005: Control de Acceso por Roles**
**Como** sistema  
**Quiero** controlar el acceso a las funcionalidades según el rol del usuario  
**Para** mantener la seguridad y privacidad de los datos

**Criterios de Aceptación**:
- ✅ Los usuarios solo ven las opciones de menú correspondientes a su rol
- ✅ Las rutas están protegidas con guards
- ✅ Los endpoints del backend validan roles con @PreAuthorize
- ✅ Si un usuario intenta acceder a una ruta no autorizada, se redirige al dashboard
- ✅ Se muestra mensaje de "No tiene permisos" cuando corresponde

**Prioridad**: ALTA  
**Estimación**: 8 puntos  
**Épica**: Gestión de Usuarios y Autenticación

---

## 👥 MÓDULO 2: GESTIÓN DE USUARIOS

### **US-006: Crear Usuario (Admin)**
**Como** administrador  
**Quiero** crear nuevos usuarios  
**Para** darles acceso a la plataforma

**Criterios de Aceptación**:
- ✅ Solo el administrador puede acceder a esta funcionalidad
- ✅ El formulario solicita: nombre, apellido, email, teléfono, contraseña y rol
- ✅ Se valida que el email sea único
- ✅ Se puede asignar cualquier rol (ADMINISTRADOR, USUARIO, COMERCIO)
- ✅ La contraseña se encripta automáticamente
- ✅ El usuario se crea como activo por defecto
- ✅ Se muestra mensaje de éxito al crear

**Prioridad**: ALTA  
**Estimación**: 5 puntos  
**Épica**: Gestión de Usuarios y Autenticación

---

### **US-007: Listar Usuarios (Admin)**
**Como** administrador  
**Quiero** ver la lista de todos los usuarios  
**Para** gestionar las cuentas del sistema

**Criterios de Aceptación**:
- ✅ Solo el administrador puede ver todos los usuarios
- ✅ La lista muestra: nombre, email, rol y estado (activo/inactivo)
- ✅ Se puede filtrar por rol
- ✅ Se puede buscar por nombre o email
- ✅ Hay botones para editar y activar/desactivar
- ✅ La tabla es paginada

**Prioridad**: ALTA  
**Estimación**: 5 puntos  
**Épica**: Gestión de Usuarios y Autenticación

---

### **US-008: Editar Usuario (Admin)**
**Como** administrador  
**Quiero** editar la información de un usuario  
**Para** mantener los datos actualizados

**Criterios de Aceptación**:
- ✅ Solo el administrador puede editar usuarios
- ✅ Se puede editar: nombre, apellido, email, teléfono y rol
- ✅ No se puede editar la contraseña desde aquí
- ✅ Se valida que el nuevo email sea único
- ✅ Se muestra mensaje de éxito al guardar

**Prioridad**: MEDIA  
**Estimación**: 3 puntos  
**Épica**: Gestión de Usuarios y Autenticación

---

### **US-009: Ver Perfil Propio**
**Como** usuario autenticado  
**Quiero** ver mi perfil  
**Para** verificar mi información personal

**Criterios de Aceptación**:
- ✅ Todos los usuarios pueden ver su propio perfil
- ✅ Se muestra: nombre, apellido, email, teléfono y rol
- ✅ Se puede editar la información propia
- ✅ No se puede cambiar el rol propio
- ✅ Se muestra mensaje de éxito al actualizar

**Prioridad**: MEDIA  
**Estimación**: 3 puntos  
**Épica**: Gestión de Usuarios y Autenticación

---

## 🏪 MÓDULO 3: GESTIÓN DE COMERCIOS

### **US-010: Crear Comercio**
**Como** administrador o usuario con rol comercio  
**Quiero** crear un comercio  
**Para** poder recibir pagos de clientes

**Criterios de Aceptación**:
- ✅ El formulario solicita: nombre comercial, RUC, categoría, dirección y wallet de recepción
- ✅ El RUC debe ser único en el sistema
- ✅ Se valida el RUC en tiempo real (validación asíncrona)
- ✅ El comercio se asocia al usuario autenticado
- ✅ El comercio se crea como activo por defecto
- ✅ Se muestra mensaje de éxito al crear

**Prioridad**: ALTA  
**Estimación**: 5 puntos  
**Épica**: Gestión de Comercios

---

### **US-011: Listar Comercios (Admin)**
**Como** administrador  
**Quiero** ver todos los comercios  
**Para** gestionar los comercios de la plataforma

**Criterios de Aceptación**:
- ✅ Solo el administrador puede ver todos los comercios
- ✅ La lista muestra: nombre comercial, RUC, categoría y estado
- ✅ Se puede filtrar por categoría
- ✅ Se puede buscar por nombre o RUC
- ✅ Hay botones para editar y activar/desactivar
- ✅ La tabla es paginada

**Prioridad**: MEDIA  
**Estimación**: 5 puntos  
**Épica**: Gestión de Comercios

---

### **US-012: Editar Comercio**
**Como** administrador o comercio  
**Quiero** editar la información de mi comercio  
**Para** mantener los datos actualizados

**Criterios de Aceptación**:
- ✅ El comercio solo puede editar su propio comercio
- ✅ El administrador puede editar cualquier comercio
- ✅ Se puede editar: nombre comercial, categoría, dirección y wallet
- ✅ No se puede editar el RUC
- ✅ Se muestra mensaje de éxito al guardar

**Prioridad**: MEDIA  
**Estimación**: 3 puntos  
**Épica**: Gestión de Comercios

---

## 💰 MÓDULO 4: GESTIÓN DE CRIPTOMONEDAS

### **US-013: Crear Criptomoneda (Admin)**
**Como** administrador  
**Quiero** agregar nuevas criptomonedas al sistema  
**Para** ampliar las opciones de pago

**Criterios de Aceptación**:
- ✅ Solo el administrador puede crear criptomonedas
- ✅ El formulario solicita: código, nombre, decimales y símbolo
- ✅ El código debe ser único (ej: BTC, ETH)
- ✅ Los decimales deben ser un número entre 0 y 18
- ✅ La criptomoneda se crea como activa por defecto
- ✅ Se muestra mensaje de éxito al crear

**Prioridad**: ALTA  
**Estimación**: 3 puntos  
**Épica**: Gestión de Criptomonedas y Wallets

---

### **US-014: Listar Criptomonedas**
**Como** usuario autenticado  
**Quiero** ver las criptomonedas disponibles  
**Para** saber con cuáles puedo operar

**Criterios de Aceptación**:
- ✅ Todos los usuarios pueden ver las criptomonedas
- ✅ La lista muestra: código, nombre, símbolo y estado
- ✅ Se puede filtrar por activas/inactivas
- ✅ Solo el admin ve botones de editar/eliminar
- ✅ La tabla es paginada

**Prioridad**: ALTA  
**Estimación**: 3 puntos  
**Épica**: Gestión de Criptomonedas y Wallets

---

### **US-015: Editar Criptomoneda (Admin)**
**Como** administrador  
**Quiero** editar una criptomoneda  
**Para** corregir información o activar/desactivar

**Criterios de Aceptación**:
- ✅ Solo el administrador puede editar criptomonedas
- ✅ Se puede editar: nombre, decimales, símbolo y estado
- ✅ No se puede editar el código
- ✅ Se muestra mensaje de éxito al guardar

**Prioridad**: BAJA  
**Estimación**: 2 puntos  
**Épica**: Gestión de Criptomonedas y Wallets

---

## 👛 MÓDULO 5: GESTIÓN DE WALLETS

### **US-016: Crear Wallet**
**Como** usuario  
**Quiero** crear una wallet de criptomonedas  
**Para** poder realizar y recibir pagos

**Criterios de Aceptación**:
- ✅ El formulario solicita: criptomoneda y dirección de wallet
- ✅ Se valida que la dirección no esté vacía
- ✅ La wallet se asocia al usuario autenticado
- ✅ El saldo inicial es 0
- ✅ La wallet se crea como activa por defecto
- ✅ Se muestra mensaje de éxito al crear

**Prioridad**: ALTA  
**Estimación**: 3 puntos  
**Épica**: Gestión de Criptomonedas y Wallets

---

### **US-017: Listar Wallets Propias**
**Como** usuario  
**Quiero** ver mis wallets  
**Para** gestionar mis criptomonedas

**Criterios de Aceptación**:
- ✅ El usuario solo ve sus propias wallets
- ✅ La lista muestra: criptomoneda, dirección, saldo y estado
- ✅ Se puede filtrar por criptomoneda
- ✅ Hay botones para editar y ver detalle
- ✅ La tabla es paginada

**Prioridad**: ALTA  
**Estimación**: 3 puntos  
**Épica**: Gestión de Criptomonedas y Wallets

---

### **US-018: Ver Detalle de Wallet**
**Como** usuario  
**Quiero** ver el detalle de una wallet  
**Para** ver las transacciones asociadas

**Criterios de Aceptación**:
- ✅ Se muestra: criptomoneda, dirección, saldo y estado
- ✅ Se muestra la lista de transacciones de esa wallet
- ✅ Las transacciones muestran: fecha, monto, comercio y estado
- ✅ Se puede volver a la lista de wallets

**Prioridad**: MEDIA  
**Estimación**: 5 puntos  
**Épica**: Gestión de Criptomonedas y Wallets

---

## 💱 MÓDULO 6: GESTIÓN DE TIPOS DE CAMBIO

### **US-019: Crear Tipo de Cambio (Admin)**
**Como** administrador  
**Quiero** crear tipos de cambio  
**Para** que el sistema pueda calcular conversiones

**Criterios de Aceptación**:
- ✅ Solo el administrador puede crear tipos de cambio
- ✅ El formulario solicita: moneda origen, moneda destino, tasa y fuente
- ✅ La tasa debe ser un número positivo
- ✅ La fecha/hora se registra automáticamente
- ✅ Se muestra mensaje de éxito al crear

**Prioridad**: ALTA  
**Estimación**: 3 puntos  
**Épica**: Gestión de Tipos de Cambio

---

### **US-020: Listar Tipos de Cambio**
**Como** usuario autenticado  
**Quiero** ver los tipos de cambio  
**Para** conocer las tasas actuales

**Criterios de Aceptación**:
- ✅ Todos los usuarios pueden ver los tipos de cambio
- ✅ La lista muestra: par (desde-hasta), tasa, fecha/hora y fuente
- ✅ Se puede filtrar por par de monedas
- ✅ Los registros están ordenados por fecha (más reciente primero)
- ✅ Solo el admin ve botón de eliminar
- ✅ La tabla es paginada

**Prioridad**: ALTA  
**Estimación**: 3 puntos  
**Épica**: Gestión de Tipos de Cambio

---

### **US-021: Ver Tasas en Tiempo Real** ← NUEVO
**Como** usuario autenticado  
**Quiero** ver las tasas de cambio actualizándose en tiempo real  
**Para** tomar decisiones informadas sobre mis transacciones

**Criterios de Aceptación**:
- ✅ Todos los usuarios autenticados pueden acceder a "Tasas en Vivo"
- ✅ Se muestran las tasas más recientes de todos los pares
- ✅ Las tasas se actualizan automáticamente cada 10 segundos
- ✅ Se muestra un indicador visual cuando una tasa sube (flecha verde ↑)
- ✅ Se muestra un indicador visual cuando una tasa baja (flecha roja ↓)
- ✅ Se muestra el porcentaje de cambio
- ✅ Se muestra la hora de la última actualización
- ✅ Cada criptomoneda tiene su ícono y color característico
- ✅ El diseño es responsive (móvil, tablet, desktop)
- ✅ Las tarjetas tienen animaciones suaves
- ✅ Se muestra la fuente de la tasa (Binance, Coinbase, etc.)
- ✅ Si no hay tasas, se muestra un mensaje informativo

**Prioridad**: ALTA  
**Estimación**: 8 puntos  
**Épica**: Visualización de Datos en Tiempo Real

---

### **US-022: Eliminar Tipo de Cambio (Admin)**
**Como** administrador  
**Quiero** eliminar tipos de cambio obsoletos  
**Para** mantener la base de datos limpia

**Criterios de Aceptación**:
- ✅ Solo el administrador puede eliminar tipos de cambio
- ✅ Se muestra confirmación antes de eliminar
- ✅ Al eliminar, se remueve de la base de datos
- ✅ Se muestra mensaje de éxito al eliminar

**Prioridad**: BAJA  
**Estimación**: 2 puntos  
**Épica**: Gestión de Tipos de Cambio

---

## 💳 MÓDULO 7: GESTIÓN DE TRANSACCIONES

### **US-023: Crear Transacción (Pago)**
**Como** usuario  
**Quiero** realizar un pago a un comercio  
**Para** comprar productos o servicios

**Criterios de Aceptación**:
- ✅ El formulario solicita: comercio, método de pago, monto en fiat, criptomoneda
- ✅ El backend calcula automáticamente el monto en cripto según la tasa actual
- ✅ Se muestra el monto calculado antes de confirmar
- ✅ La transacción se crea con estado "PENDIENTE"
- ✅ Se genera un hash de transacción único
- ✅ Se asocia al usuario autenticado
- ✅ Se muestra mensaje de éxito al crear
- ✅ Se redirige a la lista de transacciones

**Prioridad**: ALTA  
**Estimación**: 8 puntos  
**Épica**: Gestión de Transacciones

---

### **US-024: Listar Transacciones Propias**
**Como** usuario  
**Quiero** ver mis transacciones  
**Para** llevar un control de mis pagos

**Criterios de Aceptación**:
- ✅ El usuario solo ve sus propias transacciones
- ✅ La lista muestra: fecha, comercio, monto fiat, monto cripto, estado
- ✅ Se puede filtrar por estado (PENDIENTE, COMPLETADA, FALLIDA)
- ✅ Se puede filtrar por fecha
- ✅ Hay botón para ver detalle
- ✅ Se puede eliminar solo transacciones PENDIENTES
- ✅ La tabla es paginada

**Prioridad**: ALTA  
**Estimación**: 5 puntos  
**Épica**: Gestión de Transacciones

---

### **US-025: Ver Detalle de Transacción**
**Como** usuario  
**Quiero** ver el detalle de una transacción  
**Para** verificar la información completa

**Criterios de Aceptación**:
- ✅ Se muestra en un dialog/modal
- ✅ Se muestra: fecha, comercio, monto fiat, monto cripto, tasa aplicada, estado, hash
- ✅ Se muestra el método de pago usado
- ✅ Se puede cerrar el dialog
- ✅ No se puede editar desde aquí

**Prioridad**: MEDIA  
**Estimación**: 3 puntos  
**Épica**: Gestión de Transacciones

---

### **US-026: Listar Todas las Transacciones (Admin)**
**Como** administrador  
**Quiero** ver todas las transacciones del sistema  
**Para** monitorear la actividad

**Criterios de Aceptación**:
- ✅ Solo el administrador puede ver todas las transacciones
- ✅ La lista muestra: fecha, usuario, comercio, monto, estado
- ✅ Se puede filtrar por usuario, comercio, estado y fecha
- ✅ Se puede buscar por hash de transacción
- ✅ La tabla es paginada

**Prioridad**: MEDIA  
**Estimación**: 5 puntos  
**Épica**: Gestión de Transacciones

---

### **US-027: Ver Transacciones Recibidas (Comercio)**
**Como** comercio  
**Quiero** ver los pagos que he recibido  
**Para** llevar control de mis ventas

**Criterios de Aceptación**:
- ✅ El comercio solo ve transacciones donde es el receptor
- ✅ La lista muestra: fecha, cliente, monto fiat, monto cripto, estado
- ✅ Se puede filtrar por estado y fecha
- ✅ Se muestra el total de ventas
- ✅ La tabla es paginada

**Prioridad**: ALTA  
**Estimación**: 5 puntos  
**Épica**: Gestión de Transacciones

---

## 📅 MÓDULO 8: GESTIÓN DE PLANES DE PAGO

### **US-028: Crear Plan de Pago**
**Como** usuario  
**Quiero** crear un plan de pago en cuotas  
**Para** pagar una compra grande en partes

**Criterios de Aceptación**:
- ✅ El formulario solicita: transacción, número de cuotas y frecuencia (días)
- ✅ El sistema calcula automáticamente el monto de cada cuota
- ✅ Se generan las cuotas con fechas de vencimiento
- ✅ Todas las cuotas se crean con estado "PENDIENTE"
- ✅ Se muestra mensaje de éxito al crear

**Prioridad**: MEDIA  
**Estimación**: 5 puntos  
**Épica**: Gestión de Planes de Pago

---

### **US-029: Listar Planes de Pago**
**Como** usuario  
**Quiero** ver mis planes de pago  
**Para** gestionar mis cuotas

**Criterios de Aceptación**:
- ✅ El usuario solo ve sus propios planes
- ✅ La lista muestra: transacción, número de cuotas, monto por cuota, frecuencia
- ✅ Hay botón para ver cuotas
- ✅ La tabla es paginada

**Prioridad**: MEDIA  
**Estimación**: 3 puntos  
**Épica**: Gestión de Planes de Pago

---

### **US-030: Ver Cuotas de un Plan**
**Como** usuario  
**Quiero** ver las cuotas de un plan  
**Para** saber cuándo y cuánto debo pagar

**Criterios de Aceptación**:
- ✅ Se muestra en un dialog/modal
- ✅ La lista muestra: número de cuota, monto, fecha de vencimiento, estado
- ✅ Hay botón "Pagar" para cuotas pendientes
- ✅ Las cuotas pagadas muestran la fecha de pago
- ✅ Se puede cerrar el dialog

**Prioridad**: MEDIA  
**Estimación**: 5 puntos  
**Épica**: Gestión de Planes de Pago

---

### **US-031: Pagar Cuota**
**Como** usuario  
**Quiero** pagar una cuota  
**Para** cumplir con mi plan de pago

**Criterios de Aceptación**:
- ✅ Se muestra confirmación antes de pagar
- ✅ Al confirmar, la cuota cambia a estado "PAGADA"
- ✅ Se registra la fecha de pago
- ✅ Se muestra mensaje de éxito
- ✅ La lista de cuotas se actualiza

**Prioridad**: MEDIA  
**Estimación**: 3 puntos  
**Épica**: Gestión de Planes de Pago

---

## 💳 MÓDULO 9: GESTIÓN DE MÉTODOS DE PAGO

### **US-032: Crear Método de Pago**
**Como** usuario  
**Quiero** crear métodos de pago  
**Para** usarlos en mis transacciones

**Criterios de Aceptación**:
- ✅ El formulario solicita: tipo y detalles
- ✅ Los tipos disponibles son: WALLET_CRIPTO, TARJETA_CREDITO, TRANSFERENCIA_BANCARIA
- ✅ El método se asocia al usuario autenticado
- ✅ El método se crea como activo por defecto
- ✅ Se muestra mensaje de éxito al crear

**Prioridad**: MEDIA  
**Estimación**: 3 puntos  
**Épica**: Gestión de Transacciones

---

### **US-033: Listar Métodos de Pago**
**Como** usuario  
**Quiero** ver mis métodos de pago  
**Para** gestionarlos

**Criterios de Aceptación**:
- ✅ El usuario solo ve sus propios métodos
- ✅ La lista muestra: tipo, detalles y estado
- ✅ Hay botones para editar y activar/desactivar
- ✅ La tabla es paginada

**Prioridad**: MEDIA  
**Estimación**: 3 puntos  
**Épica**: Gestión de Transacciones

---

## 🔔 MÓDULO 10: GESTIÓN DE NOTIFICACIONES

### **US-034: Ver Notificaciones**
**Como** usuario  
**Quiero** ver mis notificaciones  
**Para** estar informado de eventos importantes

**Criterios de Aceptación**:
- ✅ El usuario solo ve sus propias notificaciones
- ✅ La lista muestra: título, mensaje, tipo, fecha y estado (leída/no leída)
- ✅ Las notificaciones no leídas se destacan visualmente
- ✅ Se puede filtrar por tipo (TRANSACCION, CUOTA, SISTEMA)
- ✅ Hay botón para marcar como leída
- ✅ La tabla es paginada

**Prioridad**: BAJA  
**Estimación**: 3 puntos  
**Épica**: Gestión de Usuarios y Autenticación

---

### **US-035: Marcar Notificación como Leída**
**Como** usuario  
**Quiero** marcar notificaciones como leídas  
**Para** organizar mi bandeja

**Criterios de Aceptación**:
- ✅ Al hacer click en una notificación, se marca como leída
- ✅ El estado visual cambia
- ✅ Se actualiza el contador de no leídas

**Prioridad**: BAJA  
**Estimación**: 2 puntos  
**Épica**: Gestión de Usuarios y Autenticación

---

## 📊 MÓDULO 11: DASHBOARD

### **US-036: Ver Dashboard de Admin**
**Como** administrador  
**Quiero** ver estadísticas globales del sistema  
**Para** monitorear la plataforma

**Criterios de Aceptación**:
- ✅ Se muestra: total de usuarios, comercios, transacciones y monto transaccionado
- ✅ Se muestran gráficos de tendencias (opcional)
- ✅ Se muestran las últimas transacciones
- ✅ Se actualiza en tiempo real (opcional)

**Prioridad**: MEDIA  
**Estimación**: 8 puntos  
**Épica**: Visualización de Datos en Tiempo Real

---

### **US-037: Ver Dashboard de Comercio**
**Como** comercio  
**Quiero** ver un resumen de mis ventas  
**Para** monitorear mi negocio

**Criterios de Aceptación**:
- ✅ Se muestra: total de ventas (en fiat), total recibido (en cripto)
- ✅ Se muestran las últimas 5 transacciones
- ✅ Se puede filtrar por fecha

**Prioridad**: MEDIA  
**Estimación**: 5 puntos  
**Épica**: Visualización de Datos en Tiempo Real

---

### **US-038: Ver Dashboard de Cliente**
**Como** cliente  
**Quiero** ver un resumen de mi actividad  
**Para** tener control de mis finanzas

**Criterios de Aceptación**:
- ✅ Se muestra: saldo total en wallets, total gastado
- ✅ Se muestran las últimas 5 transacciones
- ✅ Se muestran cuotas próximas a vencer

**Prioridad**: MEDIA  
**Estimación**: 5 puntos  
**Épica**: Visualización de Datos en Tiempo Real

---

## 📈 PRIORIZACIÓN

### **MUST HAVE (Prioridad ALTA)**
1. US-001: Registro de Usuario
2. US-002: Inicio de Sesión
3. US-004: Cierre de Sesión
4. US-005: Control de Acceso por Roles
5. US-006: Crear Usuario (Admin)
6. US-007: Listar Usuarios (Admin)
7. US-010: Crear Comercio
8. US-013: Crear Criptomoneda (Admin)
9. US-014: Listar Criptomonedas
10. US-016: Crear Wallet
11. US-017: Listar Wallets Propias
12. US-019: Crear Tipo de Cambio (Admin)
13. US-020: Listar Tipos de Cambio
14. **US-021: Ver Tasas en Tiempo Real** ← NUEVO
15. US-023: Crear Transacción (Pago)
16. US-024: Listar Transacciones Propias
17. US-027: Ver Transacciones Recibidas (Comercio)

### **SHOULD HAVE (Prioridad MEDIA)**
18. US-003: Recuperación de Contraseña
19. US-008: Editar Usuario (Admin)
20. US-009: Ver Perfil Propio
21. US-011: Listar Comercios (Admin)
22. US-012: Editar Comercio
23. US-018: Ver Detalle de Wallet
24. US-025: Ver Detalle de Transacción
25. US-026: Listar Todas las Transacciones (Admin)
26. US-028: Crear Plan de Pago
27. US-029: Listar Planes de Pago
28. US-030: Ver Cuotas de un Plan
29. US-031: Pagar Cuota
30. US-032: Crear Método de Pago
31. US-033: Listar Métodos de Pago
32. US-036: Ver Dashboard de Admin
33. US-037: Ver Dashboard de Comercio
34. US-038: Ver Dashboard de Cliente

### **COULD HAVE (Prioridad BAJA)**
35. US-015: Editar Criptomoneda (Admin)
36. US-022: Eliminar Tipo de Cambio (Admin)
37. US-034: Ver Notificaciones
38. US-035: Marcar Notificación como Leída

---

## 📊 ESTIMACIÓN TOTAL

- **Total de User Stories**: 38
- **Puntos Totales**: 165 puntos
- **Prioridad ALTA**: 17 stories (85 puntos)
- **Prioridad MEDIA**: 17 stories (70 puntos)
- **Prioridad BAJA**: 4 stories (10 puntos)

---

## 🎯 ROADMAP SUGERIDO

### **Sprint 1 (2 semanas)**: Autenticación y Usuarios
- US-001, US-002, US-004, US-005, US-006, US-007

### **Sprint 2 (2 semanas)**: Criptomonedas y Wallets
- US-013, US-014, US-016, US-017, US-018

### **Sprint 3 (2 semanas)**: Tipos de Cambio y Tasas en Vivo
- US-019, US-020, **US-021** ← NUEVO

### **Sprint 4 (2 semanas)**: Comercios y Transacciones
- US-010, US-023, US-024, US-027

### **Sprint 5 (2 semanas)**: Planes de Pago y Métodos
- US-028, US-029, US-030, US-031, US-032, US-033

### **Sprint 6 (2 semanas)**: Dashboards y Mejoras
- US-036, US-037, US-038, US-034, US-035

---

## ✅ DEFINICIÓN DE HECHO (DoD)

Para considerar una User Story como "Hecho", debe cumplir:

1. ✅ Código implementado y funcionando
2. ✅ Todos los criterios de aceptación cumplidos
3. ✅ Pruebas unitarias escritas y pasando
4. ✅ Código revisado (code review)
5. ✅ Sin errores de compilación ni warnings críticos
6. ✅ Documentación actualizada
7. ✅ Probado en entorno de desarrollo
8. ✅ Aprobado por el Product Owner

---

**Versión**: 1.0  
**Fecha**: Noviembre 2025  
**Autor**: Equipo PulsePay
