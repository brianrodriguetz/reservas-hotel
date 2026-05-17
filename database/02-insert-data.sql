-- ============================================================
-- 02-insert-data.sql
-- Datos de prueba para BD reservas_hotel
-- Proyecto Final BD1 - Brian Steban Rodriguez Ortiz
-- Fecha de referencia: 2026-05-16 (hoy)
-- ============================================================
--
-- Volumen total: ~270 registros distribuidos en 19 tablas
-- Cobertura:
--   - Reservas en los 6 estados (Pendiente, Confirmada, En_Curso,
--     Finalizada, Cancelada, No_Show)
--   - Cancelaciones con las 3 políticas (0%, 50%, 100%)
--   - Pagos en los 4 estados (Pendiente, Aprobado, Rechazado, Reversado)
--   - Solicitudes de reembolso en 3 estados (Pendiente, Aprobado, Procesado)
--   - Habitaciones en los 5 estados operacionales
--   - Empleados con jerarquía recursiva (supervisores)
--   - Clientes Persona y Empresa (jerarquía)
--   - Huéspedes asignados como titular y acompañante
-- ============================================================

USE reservas_hotel;

-- ============================================================
-- Limpieza previa (idempotencia)
-- ============================================================
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE solicitud_reembolso;
TRUNCATE TABLE cancelacion;
TRUNCATE TABLE check_out;
TRUNCATE TABLE check_in;
TRUNCATE TABLE evento_reserva;
TRUNCATE TABLE asignacion_huesped;
TRUNCATE TABLE pago;
TRUNCATE TABLE reserva_habitacion;
TRUNCATE TABLE reserva;
TRUNCATE TABLE direccion;
TRUNCATE TABLE contacto;
TRUNCATE TABLE empresa;
TRUNCATE TABLE persona;
TRUNCATE TABLE cliente;
TRUNCATE TABLE habitacion;
TRUNCATE TABLE empleado;
TRUNCATE TABLE huesped;
TRUNCATE TABLE rol;
TRUNCATE TABLE tipo_habitacion;
SET FOREIGN_KEY_CHECKS = 1;


-- ============================================================
-- OLA 1: Catálogos sin FKs
-- ============================================================

-- ROL (3)
INSERT INTO rol (id_Rol, nombre, estado) VALUES
(1, 'Administrador',     'Activo'),
(2, 'Recepcionista',     'Activo'),
(3, 'Personal_Limpieza', 'Activo');

-- TIPO_HABITACION (4)
INSERT INTO tipo_habitacion (id_Tipo, nombre, capacidad_Max, numero_Camas, precioBaseNoche) VALUES
(1, 'Sencilla', 1, 1, 150000.00),
(2, 'Doble',    2, 2, 250000.00),
(3, 'Triple',   3, 3, 350000.00),
(4, 'Suite',    4, 2, 500000.00);

-- HUESPED (20)
INSERT INTO huesped (id_Huesped, nombre, apellido, tipo_Documento, numero_Documento, nacionalidad, fecha_Nacimiento) VALUES
(1,  'Huesped1',  'Apellido1',  'CC', '2010001', 'Colombiana',  '1980-01-15'),
(2,  'Huesped2',  'Apellido2',  'CC', '2010002', 'Colombiana',  '1985-06-22'),
(3,  'Huesped3',  'Apellido3',  'CC', '2010003', 'Colombiana',  '1990-09-10'),
(4,  'Huesped4',  'Apellido4',  'CE', '2020001', 'Venezolana',  '1982-04-18'),
(5,  'Huesped5',  'Apellido5',  'CC', '2010005', 'Colombiana',  '1995-11-30'),
(6,  'Huesped6',  'Apellido6',  'CC', '2010006', 'Colombiana',  '1978-03-25'),
(7,  'Huesped7',  'Apellido7',  'PA', '2030001', 'Argentina',   '1988-07-12'),
(8,  'Huesped8',  'Apellido8',  'CC', '2010008', 'Colombiana',  '1992-12-05'),
(9,  'Huesped9',  'Apellido9',  'CC', '2010009', 'Colombiana',  '1986-05-20'),
(10, 'Huesped10', 'Apellido10', 'CE', '2020002', 'Mexicana',    '1991-08-08'),
(11, 'Huesped11', 'Apellido11', 'CC', '2010011', 'Colombiana',  '1983-02-14'),
(12, 'Huesped12', 'Apellido12', 'CC', '2010012', 'Colombiana',  '1989-10-03'),
(13, 'Huesped13', 'Apellido13', 'CC', '2010013', 'Colombiana',  '1994-06-17'),
(14, 'Huesped14', 'Apellido14', 'PA', '2030002', 'Espanola',    '1981-11-28'),
(15, 'Huesped15', 'Apellido15', 'CC', '2010015', 'Colombiana',  '1987-01-09'),
(16, 'Huesped16', 'Apellido16', 'CC', '2010016', 'Colombiana',  '1993-04-26'),
(17, 'Huesped17', 'Apellido17', 'CC', '2010017', 'Colombiana',  '1979-09-15'),
(18, 'Huesped18', 'Apellido18', 'CE', '2020003', 'Peruana',     '1996-07-21'),
(19, 'Huesped19', 'Apellido19', 'CC', '2010019', 'Colombiana',  '1984-12-11'),
(20, 'Huesped20', 'Apellido20', 'CC', '2010020', 'Colombiana',  '1990-03-30');


-- ============================================================
-- OLA 2: Empleados y habitaciones
-- ============================================================

-- EMPLEADO (8) - 1 admin + 4 recepcionistas + 3 limpieza
-- El admin no tiene supervisor (NULL). Todos los demás reportan al admin.
INSERT INTO empleado (id_Empleado, numero_documento, usuario, nombre, apellido, estado, id_Rol, id_Supervisor) VALUES
(1, '1000001', 'admin1', 'Admin1', 'Admin',    'Activo', 1, NULL),
(2, '1000002', 'recep1', 'Recep1', 'Apellido', 'Activo', 2, 1),
(3, '1000003', 'recep2', 'Recep2', 'Apellido', 'Activo', 2, 1),
(4, '1000004', 'recep3', 'Recep3', 'Apellido', 'Activo', 2, 1),
(5, '1000005', 'recep4', 'Recep4', 'Apellido', 'Activo', 2, 1),
(6, '1000006', 'limp1',  'Limp1',  'Apellido', 'Activo', 3, 1),
(7, '1000007', 'limp2',  'Limp2',  'Apellido', 'Activo', 3, 1),
(8, '1000008', 'limp3',  'Limp3',  'Apellido', 'Activo', 3, 1);

-- HABITACION (20)
-- Distribución por estado "ahora mismo":
--   Disponible (11): mayoría
--   Ocupada (4): asignadas a reservas En_Curso (R10, R11x2, R12 walk-in)
--   Reservada (3): R7 cercana (Empresa con 3 habitaciones, check-in 10 jun)
--   En_Limpieza (1): R16 recién finalizada (3 abr) -- esto es ficticio para tener variedad
--   En_Mantenimiento (1): habitación 103 fuera de servicio
INSERT INTO habitacion (id_Habitacion, codigo, piso, estado, id_Tipo) VALUES
(1,  '101', 1, 'Disponible',       1),
(2,  '102', 1, 'Ocupada',          1),  -- R12 walk-in
(3,  '103', 1, 'En_Mantenimiento', 1),
(4,  '104', 1, 'Disponible',       2),
(5,  '105', 1, 'Disponible',       2),
(6,  '106', 1, 'Disponible',       2),
(7,  '107', 1, 'Disponible',       3),
(8,  '201', 2, 'Disponible',       1),
(9,  '202', 2, 'Ocupada',          1),  -- R10
(10, '203', 2, 'Reservada',        2),  -- R7
(11, '204', 2, 'Reservada',        2),  -- R7
(12, '205', 2, 'Reservada',        2),  -- R7
(13, '206', 2, 'Disponible',       3),
(14, '207', 2, 'Ocupada',          3),  -- R11
(15, '301', 3, 'Disponible',       3),
(16, '302', 3, 'En_Limpieza',      3),  -- R16 finalizada hace poco
(17, '303', 3, 'Disponible',       4),
(18, '304', 3, 'Disponible',       4),
(19, '305', 3, 'Ocupada',          4),  -- R11
(20, '306', 3, 'Disponible',       4);


-- ============================================================
-- OLA 3: Jerarquía CLIENTE -> PERSONA / EMPRESA
-- ============================================================

-- CLIENTE (15): 1-10 son personas, 11-15 son empresas
INSERT INTO cliente (id_Cliente, fecha_Registro, estado) VALUES
(1,  '2024-01-15 10:30:00', 'Activo'),
(2,  '2024-03-20 14:15:00', 'Activo'),
(3,  '2024-06-10 09:00:00', 'Activo'),
(4,  '2024-09-05 16:45:00', 'Activo'),
(5,  '2024-11-12 11:20:00', 'Activo'),
(6,  '2025-02-18 13:00:00', 'Activo'),
(7,  '2025-05-22 10:00:00', 'Activo'),
(8,  '2025-08-30 15:30:00', 'Activo'),
(9,  '2025-11-15 12:00:00', 'Activo'),
(10, '2026-01-20 09:45:00', 'Inactivo'),
(11, '2024-02-10 11:00:00', 'Activo'),
(12, '2024-07-25 14:30:00', 'Activo'),
(13, '2025-04-15 10:15:00', 'Activo'),
(14, '2025-10-08 16:00:00', 'Activo'),
(15, '2026-02-14 11:30:00', 'Activo');

-- PERSONA (10) - clientes 1 a 10
INSERT INTO persona (id_Cliente, tipo_Documento, numero_Documento, nombre, apellido, fecha_Nacimiento, nacionalidad) VALUES
(1,  'CC', '1010001', 'Cliente1',  'Apellido1',  '1985-05-10', 'Colombiana'),
(2,  'CC', '1010002', 'Cliente2',  'Apellido2',  '1990-08-22', 'Colombiana'),
(3,  'CC', '1010003', 'Cliente3',  'Apellido3',  '1978-03-15', 'Colombiana'),
(4,  'CE', '1020001', 'Cliente4',  'Apellido4',  '1992-11-30', 'Venezolana'),
(5,  'CC', '1010005', 'Cliente5',  'Apellido5',  '1988-07-12', 'Colombiana'),
(6,  'PA', '1030001', 'Cliente6',  'Apellido6',  '1995-01-25', 'Argentina'),
(7,  'CC', '1010007', 'Cliente7',  'Apellido7',  '1982-04-18', 'Colombiana'),
(8,  'CC', '1010008', 'Cliente8',  'Apellido8',  '1975-12-05', 'Colombiana'),
(9,  'CE', '1020002', 'Cliente9',  'Apellido9',  '1991-09-09', 'Mexicana'),
(10, 'CC', '1010010', 'Cliente10', 'Apellido10', '1987-06-28', 'Colombiana');

-- EMPRESA (5) - clientes 11 a 15
INSERT INTO empresa (id_Cliente, nit, razon_Social, representante_Legal, sector_Economico) VALUES
(11, '900100001-1', 'Empresa1 SAS',     'RepLegal1', 'Turismo'),
(12, '900100002-2', 'Empresa2 SAS',     'RepLegal2', 'Tecnologia'),
(13, '900100003-3', 'Empresa3 LTDA',    'RepLegal3', 'Salud'),
(14, '900100004-4', 'Empresa4 SAS',     'RepLegal4', 'Educacion'),
(15, '900100005-5', 'Empresa5 SAS',     'RepLegal5', 'Manufactura');


-- ============================================================
-- OLA 4: Multivaluados de CLIENTE
-- ============================================================

-- CONTACTO (25)
-- Cada cliente tiene EXACTAMENTE un contacto principal (regla del supuesto B)
INSERT INTO contacto (id_Contacto, tipo_Contacto, valor, es_Principal, id_Cliente) VALUES
-- Personas
(1,  'Telefono_Movil',     '3001000001',          1, 1),
(2,  'Correo_Electronico', 'cliente1@email.com',  0, 1),
(3,  'Telefono_Movil',     '3001000002',          1, 2),
(4,  'Telefono_Movil',     '3001000003',          1, 3),
(5,  'Correo_Electronico', 'cliente3@email.com',  0, 3),
(6,  'Telefono_Movil',     '3001000004',          1, 4),
(7,  'Telefono_Movil',     '3001000005',          1, 5),
(8,  'Telefono_Fijo',      '6011000005',          0, 5),
(9,  'Telefono_Movil',     '3001000006',          1, 6),
(10, 'Telefono_Movil',     '3001000007',          1, 7),
(11, 'Telefono_Movil',     '3001000008',          1, 8),
(12, 'Telefono_Movil',     '3001000009',          1, 9),
(13, 'Correo_Electronico', 'cliente9@email.com',  0, 9),
(14, 'Telefono_Movil',     '3001000010',          1, 10),
(15, 'Correo_Electronico', 'cliente10@email.com', 0, 10),
-- Empresas (cada una con principal movil + secundario email)
(16, 'Telefono_Movil',     '3201100001',          1, 11),
(17, 'Correo_Electronico', 'contacto@empresa1.com', 0, 11),
(18, 'Telefono_Movil',     '3201100002',          1, 12),
(19, 'Correo_Electronico', 'contacto@empresa2.com', 0, 12),
(20, 'Telefono_Movil',     '3201100003',          1, 13),
(21, 'Correo_Electronico', 'contacto@empresa3.com', 0, 13),
(22, 'Telefono_Movil',     '3201100004',          1, 14),
(23, 'Correo_Electronico', 'contacto@empresa4.com', 0, 14),
(24, 'Telefono_Movil',     '3201100005',          1, 15),
(25, 'Correo_Electronico', 'contacto@empresa5.com', 0, 15);

-- DIRECCION (23)
-- Cada cliente: 1 dirección Residencia principal.
-- Empresas y 3 personas: además 1 dirección Facturacion principal.
INSERT INTO direccion (id_Direccion, tipo_Direccion, calle, numero, ciudad, departamento, codigo_Postal, pais, es_Principal, id_Cliente) VALUES
-- Personas (10 residencias)
(1,  'Residencia', 'Calle 100', '15-30', 'Bogota',     'Cundinamarca', '110111', 'Colombia', 1, 1),
(2,  'Residencia', 'Carrera 7', '20-45', 'Bogota',     'Cundinamarca', '110221', 'Colombia', 1, 2),
(3,  'Residencia', 'Calle 85',  '12-50', 'Bogota',     'Cundinamarca', '110221', 'Colombia', 1, 3),
(4,  'Residencia', 'Carrera 15','30-20', 'Medellin',   'Antioquia',    '050021', 'Colombia', 1, 4),
(5,  'Residencia', 'Calle 50',  '40-15', 'Bogota',     'Cundinamarca', '110311', 'Colombia', 1, 5),
(6,  'Residencia', 'Avenida 9', '125-10','Bogota',     'Cundinamarca', '110441', 'Colombia', 1, 6),
(7,  'Residencia', 'Calle 72',  '8-12',  'Bogota',     'Cundinamarca', '110221', 'Colombia', 1, 7),
(8,  'Residencia', 'Carrera 19','45-22', 'Cali',       'Valle',        '760042', 'Colombia', 1, 8),
(9,  'Residencia', 'Calle 26',  '60-30', 'Bogota',     'Cundinamarca', '110931', 'Colombia', 1, 9),
(10, 'Residencia', 'Carrera 30','55-40', 'Barranquilla','Atlantico',   '080001', 'Colombia', 1, 10),
-- 3 personas con facturación adicional (típico para freelancers/contratistas)
(11, 'Facturacion','Calle 116', '7-50',  'Bogota',     'Cundinamarca', '110111', 'Colombia', 1, 1),
(12, 'Facturacion','Carrera 15','80-15', 'Bogota',     'Cundinamarca', '110221', 'Colombia', 1, 5),
(13, 'Facturacion','Calle 26',  '60-30', 'Bogota',     'Cundinamarca', '110931', 'Colombia', 1, 9),
-- Empresas (5 residencias + 5 facturaciones)
(14, 'Residencia', 'Avenida 19','100-50','Bogota',     'Cundinamarca', '110221', 'Colombia', 1, 11),
(15, 'Facturacion','Avenida 19','100-50','Bogota',     'Cundinamarca', '110221', 'Colombia', 1, 11),
(16, 'Residencia', 'Calle 92',  '15-20', 'Bogota',     'Cundinamarca', '110221', 'Colombia', 1, 12),
(17, 'Facturacion','Carrera 11','93-50', 'Bogota',     'Cundinamarca', '110221', 'Colombia', 1, 12),
(18, 'Residencia', 'Calle 67',  '10-30', 'Medellin',   'Antioquia',    '050021', 'Colombia', 1, 13),
(19, 'Facturacion','Calle 67',  '10-30', 'Medellin',   'Antioquia',    '050021', 'Colombia', 1, 13),
(20, 'Residencia', 'Carrera 50','25-15', 'Bogota',     'Cundinamarca', '110441', 'Colombia', 1, 14),
(21, 'Facturacion','Carrera 50','25-15', 'Bogota',     'Cundinamarca', '110441', 'Colombia', 1, 14),
(22, 'Residencia', 'Calle 100', '8-60',  'Cali',       'Valle',        '760042', 'Colombia', 1, 15),
(23, 'Facturacion','Calle 100', '8-60',  'Cali',       'Valle',        '760042', 'Colombia', 1, 15);


-- ============================================================
-- OLA 5: Reservas
-- ============================================================

-- RESERVA (21)
-- Distribución de estados:
--   R1-R4   Pendientes  (futuras, sin garantía)
--   R5-R9   Confirmadas (con 30% pagado)
--   R10-R12 En_Curso    (check-in hecho, fechas incluyen 2026-05-16)
--   R13-R16 Finalizadas (pasadas con check-out)
--   R17-R20 Canceladas  (con cancelación registrada)
--   R21     No_Show     (no se presentó)
INSERT INTO reserva (id_Reserva, canal, fecha_Creacion, fechaCheckInPrevista, fechaCheckOutPrevista, estado, precio_Total, id_Cliente) VALUES
-- PENDIENTES (sin pagos todavía)
(1,  'Telefonica', '2026-05-10 11:00:00', '2026-06-15 14:00:00', '2026-06-17 11:00:00', 'Pendiente',   500000.00,   1),
(2,  'Presencial', '2026-05-12 15:30:00', '2026-07-05 14:00:00', '2026-07-08 11:00:00', 'Pendiente',   450000.00,   6),
(3,  'Telefonica', '2026-05-13 09:00:00', '2026-07-20 14:00:00', '2026-07-24 11:00:00', 'Pendiente',   2000000.00, 11),
(4,  'Telefonica', '2026-05-14 14:00:00', '2026-08-10 14:00:00', '2026-08-12 11:00:00', 'Pendiente',   700000.00,   7),
-- CONFIRMADAS (30% pagado)
(5,  'Telefonica', '2026-05-01 10:00:00', '2026-05-25 14:00:00', '2026-05-27 11:00:00', 'Confirmada',  300000.00,   2),
(6,  'Telefonica', '2026-04-20 13:00:00', '2026-06-01 14:00:00', '2026-06-04 11:00:00', 'Confirmada',  750000.00,   3),
(7,  'Telefonica', '2026-04-15 11:30:00', '2026-06-10 14:00:00', '2026-06-15 11:00:00', 'Confirmada',  3750000.00, 12),
(8,  'Presencial', '2026-05-05 16:00:00', '2026-07-15 14:00:00', '2026-07-18 11:00:00', 'Confirmada',  1500000.00,  5),
(9,  'Telefonica', '2026-05-08 09:30:00', '2026-07-20 14:00:00', '2026-07-24 11:00:00', 'Confirmada',  1400000.00,  8),
-- EN CURSO (check-in hecho)
(10, 'Presencial', '2026-05-10 12:00:00', '2026-05-14 14:00:00', '2026-05-18 11:00:00', 'En_Curso',    600000.00,   9),
(11, 'Telefonica', '2026-04-25 14:00:00', '2026-05-15 14:00:00', '2026-05-20 11:00:00', 'En_Curso',    4250000.00, 13),
(12, 'Presencial', '2026-05-16 13:30:00', '2026-05-16 14:00:00', '2026-05-17 11:00:00', 'En_Curso',    150000.00,  10),
-- FINALIZADAS (pasadas)
(13, 'Telefonica', '2025-12-20 10:00:00', '2026-01-05 14:00:00', '2026-01-10 11:00:00', 'Finalizada',  750000.00,   1),
(14, 'Presencial', '2026-02-18 11:00:00', '2026-02-20 14:00:00', '2026-02-23 11:00:00', 'Finalizada',  750000.00,   4),
(15, 'Telefonica', '2026-02-10 14:00:00', '2026-03-01 14:00:00', '2026-03-05 11:00:00', 'Finalizada',  2000000.00, 14),
(16, 'Telefonica', '2026-03-20 09:00:00', '2026-04-01 14:00:00', '2026-04-03 11:00:00', 'Finalizada',  700000.00,   7),
-- CANCELADAS
(17, 'Telefonica', '2025-12-15 10:00:00', '2026-01-20 14:00:00', '2026-01-22 11:00:00', 'Cancelada',   300000.00,   2),
(18, 'Telefonica', '2026-01-25 11:00:00', '2026-02-15 14:00:00', '2026-02-19 11:00:00', 'Cancelada',   1000000.00,  5),
(19, 'Telefonica', '2026-02-15 13:00:00', '2026-03-01 14:00:00', '2026-03-04 11:00:00', 'Cancelada',   1500000.00,  3),
(20, 'Telefonica', '2026-04-20 09:00:00', '2026-05-10 14:00:00', '2026-05-15 11:00:00', 'Cancelada',   5000000.00, 15),
-- NO_SHOW
(21, 'Telefonica', '2026-03-10 10:00:00', '2026-04-15 14:00:00', '2026-04-18 11:00:00', 'No_Show',     1050000.00,  8);


-- ============================================================
-- OLA 6: RESERVA_HABITACION (N:M con número de huéspedes)
-- ============================================================
-- numero_huespedes NO excede capacidad_Max del tipo (regla del supuesto D)
INSERT INTO reserva_habitacion (id_Reserva, id_Habitacion, numero_huespedes) VALUES
-- R1 (Pendiente): 1 Doble, sin huéspedes aún
(1, 4, 2),
-- R2 (Pendiente): 1 Sencilla
(2, 1, 1),
-- R3 (Pendiente, empresa): 2 Dobles
(3, 5, 2),
(3, 6, 2),
-- R4 (Pendiente): 1 Triple
(4, 7, 3),
-- R5 (Confirmada): 1 Sencilla
(5, 2, 1),
-- R6 (Confirmada): 1 Doble
(6, 5, 2),
-- R7 (Confirmada, empresa): 3 Dobles (las que están en estado Reservada)
(7, 10, 2),
(7, 11, 2),
(7, 12, 1),
-- R8 (Confirmada): 1 Suite
(8, 17, 3),
-- R9 (Confirmada): 1 Triple
(9, 15, 2),
-- R10 (En_Curso): 1 Sencilla (la 202)
(10, 9, 1),
-- R11 (En_Curso, empresa): 1 Triple + 1 Suite
(11, 14, 3),
(11, 19, 4),
-- R12 (En_Curso walk-in): 1 Sencilla
(12, 2, 1),
-- R13 (Finalizada): 1 Sencilla
(13, 1, 1),
-- R14 (Finalizada): 1 Doble
(14, 5, 2),
-- R15 (Finalizada, empresa): 1 Sencilla + 1 Triple
(15, 8, 1),
(15, 13, 3),
-- R16 (Finalizada): 1 Triple
(16, 16, 2),
-- R17 (Cancelada): 1 Sencilla
(17, 8, 1),
-- R18 (Cancelada): 1 Doble
(18, 4, 2),
-- R19 (Cancelada): 1 Suite
(19, 17, 3),
-- R20 (Cancelada): 2 Suites
(20, 18, 4),
(20, 20, 4),
-- R21 (No_Show): 1 Triple
(21, 7, 3);


-- ============================================================
-- OLA 7: ASIGNACION_HUESPED (titulares y acompañantes)
-- ============================================================
-- Solo reservas En_Curso y Finalizadas tienen huéspedes asignados.
-- Las Pendientes/Canceladas/No_Show no llegaron a registrar huéspedes.
-- Cada habitación tiene exactamente 1 titular (regla supuesto M).
INSERT INTO asignacion_huesped (id_Huesped, id_Reserva, id_Habitacion, es_Titular) VALUES
-- R10 hab 202: 1 huésped (Sencilla cap 1)
(1, 10, 9, 1),
-- R11 hab 207: 3 huéspedes (Triple cap 3)
(2, 11, 14, 1),
(3, 11, 14, 0),
(4, 11, 14, 0),
-- R11 hab 305: 4 huéspedes (Suite cap 4)
(5, 11, 19, 1),
(6, 11, 19, 0),
(7, 11, 19, 0),
(8, 11, 19, 0),
-- R12 hab 102: 1 huésped (walk-in)
(9, 12, 2, 1),
-- R13 hab 101: 1 huésped (Sencilla)
(10, 13, 1, 1),
-- R14 hab 105: 2 huéspedes (Doble cap 2)
(11, 14, 5, 1),
(12, 14, 5, 0),
-- R15 hab 201: 1 huésped (Sencilla)
(13, 15, 8, 1),
-- R15 hab 206: 3 huéspedes (Triple cap 3)
(14, 15, 13, 1),
(15, 15, 13, 0),
(16, 15, 13, 0),
-- R16 hab 302: 2 huéspedes (Triple cap 3)
(17, 16, 16, 1),
(18, 16, 16, 0);


-- ============================================================
-- OLA 8: PAGOS
-- ============================================================
-- Lógica:
--   Pendientes (R1-R4): sin pagos
--   Confirmadas (R5-R9): 30% Aprobado (algunas con pagos adicionales)
--   En_Curso (R10-R12): 100% Aprobado
--   Finalizadas (R13-R16): 100% Aprobado (en 2 pagos)
--   Canceladas (R17-R20): pagos como estaban al cancelar (algunos Reversados)
--   No_Show (R21): solo 30% Aprobado (se queda como penalización)
INSERT INTO pago (id_Pago, medio, monto, estado, fecha, id_Reserva) VALUES
-- R5 Confirmada (300k): 30%=90k
(1,  'Tarjeta_Credito',        90000.00,   'Aprobado',  '2026-05-02 10:30:00', 5),
-- R6 Confirmada (750k): 30%=225k
(2,  'Tarjeta_Debito',         225000.00,  'Aprobado',  '2026-04-21 11:00:00', 6),
-- R7 Confirmada (3.75M): 30%=1.125M
(3,  'Transferencia_Bancaria', 1125000.00, 'Aprobado',  '2026-04-16 14:00:00', 7),
-- R8 Confirmada (1.5M): 30%=450k Aprobado + intento Rechazado + 350k Pendiente
(4,  'Tarjeta_Credito',        450000.00,  'Aprobado',  '2026-05-06 09:00:00', 8),
(5,  'Tarjeta_Credito',        1050000.00, 'Rechazado', '2026-05-10 15:30:00', 8),
(6,  'Tarjeta_Credito',        350000.00,  'Pendiente', '2026-05-15 10:00:00', 8),
-- R9 Confirmada (1.4M): 30%=420k
(7,  'Tarjeta_Debito',         420000.00,  'Aprobado',  '2026-05-09 13:00:00', 9),
-- R10 En_Curso (600k): 30% + 70% = 100%
(8,  'Tarjeta_Credito',        180000.00,  'Aprobado',  '2026-05-10 14:00:00', 10),
(9,  'Tarjeta_Credito',        420000.00,  'Aprobado',  '2026-05-14 13:30:00', 10),
-- R11 En_Curso (4.25M): 30% + 70% = 100% (con intento Rechazado en medio)
(10, 'Transferencia_Bancaria', 1275000.00, 'Aprobado',  '2026-04-26 10:00:00', 11),
(11, 'Tarjeta_Credito',        2975000.00, 'Rechazado', '2026-05-12 11:00:00', 11),
(12, 'Transferencia_Bancaria', 2975000.00, 'Aprobado',  '2026-05-15 09:30:00', 11),
-- R12 walk-in (150k): 100% al ingreso
(13, 'Efectivo',               150000.00,  'Aprobado',  '2026-05-16 13:45:00', 12),
-- R13 Finalizada (750k): 30% + 70%
(14, 'Tarjeta_Credito',        225000.00,  'Aprobado',  '2025-12-21 10:00:00', 13),
(15, 'Tarjeta_Credito',        525000.00,  'Aprobado',  '2026-01-05 13:30:00', 13),
-- R14 Finalizada (750k): 30% + 70%
(16, 'Tarjeta_Debito',         225000.00,  'Aprobado',  '2026-02-19 12:00:00', 14),
(17, 'Tarjeta_Debito',         525000.00,  'Aprobado',  '2026-02-20 14:00:00', 14),
-- R15 Finalizada (2M): 30% + 70%
(18, 'Transferencia_Bancaria', 600000.00,  'Aprobado',  '2026-02-11 10:00:00', 15),
(19, 'Transferencia_Bancaria', 1400000.00, 'Aprobado',  '2026-03-01 14:00:00', 15),
-- R16 Finalizada (700k): 30% + 70%
(20, 'Tarjeta_Credito',        210000.00,  'Aprobado',  '2026-03-22 11:00:00', 16),
(21, 'Tarjeta_Credito',        490000.00,  'Aprobado',  '2026-04-01 14:00:00', 16),
-- R17 Cancelada 0% penalización: pago original Reversado (reembolso ya procesado)
(22, 'Tarjeta_Credito',        90000.00,   'Reversado', '2025-12-16 09:00:00', 17),
-- R18 Cancelada 50%: pagó 30% + 40% adicional = 700k Aprobado (reembolso parcial)
(23, 'Tarjeta_Credito',        300000.00,  'Aprobado',  '2026-01-26 10:00:00', 18),
(24, 'Tarjeta_Credito',        400000.00,  'Aprobado',  '2026-02-05 11:30:00', 18),
-- R19 Cancelada 100%: pagó 30%, no genera reembolso (queda Aprobado)
(25, 'Tarjeta_Debito',         450000.00,  'Aprobado',  '2026-02-16 14:00:00', 19),
-- R20 Cancelada 0%: pagó 30%, reembolso pendiente (solicitud aún Pendiente)
(26, 'Transferencia_Bancaria', 1500000.00, 'Aprobado',  '2026-04-21 10:00:00', 20),
-- R21 No_Show: 30% Aprobado (se queda como penalización del 100%)
(27, 'Tarjeta_Credito',        315000.00,  'Aprobado',  '2026-03-11 11:00:00', 21);


-- ============================================================
-- OLA 9: EVENTOS DE RESERVA (jerarquía con CHECK_IN/CHECK_OUT/CANCELACION)
-- ============================================================
-- Total: 15 eventos
--   7 check-ins  (4 de Finalizadas + 3 de En_Curso)
--   4 check-outs (de las 4 Finalizadas)
--   4 cancelaciones (de las 4 Canceladas)
INSERT INTO evento_reserva (id_Evento, fecha_Hora, id_Reserva, id_Empleado) VALUES
-- Check-ins de Finalizadas
(1,  '2026-01-05 14:00:00', 13, 2),
(2,  '2026-02-20 14:00:00', 14, 4),
(3,  '2026-03-01 14:30:00', 15, 5),
(4,  '2026-04-01 15:00:00', 16, 2),
-- Check-outs de Finalizadas
(5,  '2026-01-10 11:00:00', 13, 3),
(6,  '2026-02-23 11:00:00', 14, 2),
(7,  '2026-03-05 10:45:00', 15, 3),
(8,  '2026-04-03 11:00:00', 16, 4),
-- Check-ins de En_Curso
(9,  '2026-05-14 14:15:00', 10, 3),
(10, '2026-05-15 14:00:00', 11, 2),
(11, '2026-05-16 14:00:00', 12, 4),
-- Cancelaciones
(12, '2026-01-10 10:30:00', 17, 2),
(13, '2026-02-13 16:00:00', 18, 4),
(14, '2026-02-28 23:00:00', 19, 3),
(15, '2026-05-05 09:00:00', 20, 2);

-- CHECK_IN (7)
INSERT INTO check_in (id_Evento) VALUES (1), (2), (3), (4), (9), (10), (11);

-- CHECK_OUT (4)
INSERT INTO check_out (id_Evento) VALUES (5), (6), (7), (8);

-- CANCELACION (4)
-- Penalización calculada según tiempo entre cancelación y check-in previsto:
--   E12: 10 ene 10:30 vs 20 ene 14:00 -> >72h -> 0%
--   E13: 13 feb 16:00 vs 15 feb 14:00 -> 46h -> 50%
--   E14: 28 feb 23:00 vs 1 mar 14:00 -> 15h -> 100%
--   E15: 5 may 09:00 vs 10 may 14:00 -> >72h -> 0%
INSERT INTO cancelacion (id_Evento, motivo, penalizacion) VALUES
(12, 'Cambio de planes del cliente',           0.00),
(13, 'Emergencia familiar',                    50.00),
(14, 'Vuelo cancelado, no llega a tiempo',     100.00),
(15, 'Cliente solicita reagendar para julio',  0.00);


-- ============================================================
-- OLA 10: SOLICITUDES DE REEMBOLSO
-- ============================================================
-- Fórmula (supuesto J): reembolso = pagos_aprobados − (precio_total × penalización)
-- Si <= 0, no se genera solicitud.
--
-- R17 (0%): 90k − 0 = 90k -> S1 Procesada
-- R18 (50%): 700k − 500k = 200k -> S2 Aprobada (autorizada, no procesada)
-- R19 (100%): 450k − 1.5M = -1.05M -> NO genera
-- R20 (0%): 1.5M − 0 = 1.5M -> S3 Pendiente (sin autorización aún)
INSERT INTO solicitud_reembolso (id_Solicitud, motivo, estado, medio, monto, fecha, fecha_Procesamiento, id_Cancelacion, id_Empleado) VALUES
(1, 'Reembolso por cancelacion sin penalizacion (>72h)', 'Procesado', 'Tarjeta_Credito',        90000.00,   '2026-01-10 11:00:00', '2026-01-12 10:00:00', 12, 1),
(2, 'Reembolso parcial por cancelacion con 50% penalizacion', 'Aprobado',  'Tarjeta_Credito',        200000.00,  '2026-02-13 16:30:00', NULL,                  13, 1),
(3, 'Reembolso completo por cancelacion sin penalizacion', 'Pendiente', 'Transferencia_Bancaria', 1500000.00, '2026-05-05 09:30:00', NULL,                  15, NULL);


-- ============================================================
-- VERIFICACIONES FINALES
-- ============================================================
SELECT 'rol' AS tabla, COUNT(*) AS filas FROM rol UNION ALL
SELECT 'tipo_habitacion',     COUNT(*) FROM tipo_habitacion     UNION ALL
SELECT 'huesped',             COUNT(*) FROM huesped             UNION ALL
SELECT 'empleado',            COUNT(*) FROM empleado            UNION ALL
SELECT 'habitacion',          COUNT(*) FROM habitacion          UNION ALL
SELECT 'cliente',             COUNT(*) FROM cliente             UNION ALL
SELECT 'persona',             COUNT(*) FROM persona             UNION ALL
SELECT 'empresa',             COUNT(*) FROM empresa             UNION ALL
SELECT 'contacto',            COUNT(*) FROM contacto            UNION ALL
SELECT 'direccion',           COUNT(*) FROM direccion           UNION ALL
SELECT 'reserva',             COUNT(*) FROM reserva             UNION ALL
SELECT 'reserva_habitacion',  COUNT(*) FROM reserva_habitacion  UNION ALL
SELECT 'asignacion_huesped',  COUNT(*) FROM asignacion_huesped  UNION ALL
SELECT 'pago',                COUNT(*) FROM pago                UNION ALL
SELECT 'evento_reserva',      COUNT(*) FROM evento_reserva      UNION ALL
SELECT 'check_in',            COUNT(*) FROM check_in            UNION ALL
SELECT 'check_out',           COUNT(*) FROM check_out           UNION ALL
SELECT 'cancelacion',         COUNT(*) FROM cancelacion         UNION ALL
SELECT 'solicitud_reembolso', COUNT(*) FROM solicitud_reembolso;
