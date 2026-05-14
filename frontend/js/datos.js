// ============================================================
// DATOS MOCK - Consistentes con modelo ER (Diagrama BD)
// Grupo Diablos Rojos - Universidad El Bosque 2026-1
// ============================================================

// ---------- ROL ----------
const ROLES = [
  { idRol: 1, nombre: 'Administrador', estado: 'Activo' },
  { idRol: 2, nombre: 'Recepcionista', estado: 'Activo' },
  { idRol: 3, nombre: 'Mantenimiento', estado: 'Activo' },
  { idRol: 4, nombre: 'Limpieza',      estado: 'Activo' }
];

// ---------- EMPLEADO ----------
let EMPLEADOS = [
  { idEmpleado: 101, numeroDocumento: '79123456', nombreCompleto: 'Brian Rodriguez',
    nombre: 'Brian', apellido: 'Rodriguez',
    email: 'brodriguez@hotel.com', usuario: 'admin', idRol: 1, rol: 'Administrador', estado: 'Activo' },
  { idEmpleado: 102, numeroDocumento: '52987654', nombreCompleto: 'Jose Espin',
    nombre: 'Jose', apellido: 'Espin',
    email: 'jespin@hotel.com', usuario: 'empleado', idRol: 2, rol: 'Recepcionista', estado: 'Activo' },
  { idEmpleado: 103, numeroDocumento: '80445566', nombreCompleto: 'Daniel Agudelo',
    nombre: 'Daniel', apellido: 'Agudelo',
    email: 'dagudelo@hotel.com', usuario: 'dagudelo', idRol: 2, rol: 'Recepcionista', estado: 'Activo' },
  { idEmpleado: 104, numeroDocumento: '40112233', nombreCompleto: 'Lucia Torres',
    nombre: 'Lucia', apellido: 'Torres',
    email: 'ltorres@hotel.com', usuario: 'ltorres', idRol: 4, rol: 'Limpieza', estado: 'Activo' },
  { idEmpleado: 105, numeroDocumento: '11223344', nombreCompleto: 'Pedro Sanchez',
    nombre: 'Pedro', apellido: 'Sanchez',
    email: 'psanchez@hotel.com', usuario: 'psanchez', idRol: 3, rol: 'Mantenimiento', estado: 'Activo' }
];

// ---------- PERSONA / CLIENTE ----------
// Refleja: PERSONA ← CLIENTE, con DIRECCION y CONTACTO
let CLIENTES = [
  { idCliente: 1, idPersona: 1, tipoDocumento: 'CC', numeroDocumento: '1023456789',
    nombre: 'Maria', apellido: 'Garcia', nombreCompleto: 'Maria Garcia',
    fechaNacimiento: '1990-05-12', nacionalidad: 'Colombiana', estado: 'Activo',
    fechaRegistro: '2025-01-10',
    // CONTACTO
    email: 'maria.garcia@email.com', telefono: '3105551234', tipoContacto: 'Personal',
    // DIRECCION
    calle: 'Cra 7 # 45-21', ciudad: 'Bogota', departamento: 'Cundinamarca',
    pais: 'Colombia', codigoPostal: '110111' },
  { idCliente: 2, idPersona: 2, tipoDocumento: 'CC', numeroDocumento: '1098765432',
    nombre: 'Carlos', apellido: 'Rodriguez', nombreCompleto: 'Carlos Rodriguez',
    fechaNacimiento: '1985-09-28', nacionalidad: 'Colombiana', estado: 'Activo',
    fechaRegistro: '2025-02-14',
    email: 'carlos.r@email.com', telefono: '3209876543', tipoContacto: 'Personal',
    calle: 'Calle 100 # 15-30', ciudad: 'Bogota', departamento: 'Cundinamarca',
    pais: 'Colombia', codigoPostal: '110221' },
  { idCliente: 3, idPersona: 3, tipoDocumento: 'PAS', numeroDocumento: 'AB123456',
    nombre: 'Sophie', apellido: 'Martin', nombreCompleto: 'Sophie Martin',
    fechaNacimiento: '1988-03-15', nacionalidad: 'Francesa', estado: 'Activo',
    fechaRegistro: '2025-03-05',
    email: 'sophie.martin@email.com', telefono: '+33612345678', tipoContacto: 'Personal',
    calle: 'Rue de la Paix 12', ciudad: 'Paris', departamento: 'Ile-de-France',
    pais: 'Francia', codigoPostal: '75001' },
  { idCliente: 4, idPersona: 4, tipoDocumento: 'CE', numeroDocumento: '550199',
    nombre: 'Juan', apellido: 'Perez', nombreCompleto: 'Juan Perez',
    fechaNacimiento: '1992-11-08', nacionalidad: 'Venezolana', estado: 'Activo',
    fechaRegistro: '2025-04-20',
    email: 'juan.perez@email.com', telefono: '3001112233', tipoContacto: 'Personal',
    calle: 'Cra 50 # 80-90', ciudad: 'Medellin', departamento: 'Antioquia',
    pais: 'Colombia', codigoPostal: '050021' },
  { idCliente: 5, idPersona: 5, tipoDocumento: 'CC', numeroDocumento: '1011223344',
    nombre: 'Laura', apellido: 'Martinez', nombreCompleto: 'Laura Martinez',
    fechaNacimiento: '1995-07-22', nacionalidad: 'Colombiana', estado: 'Activo',
    fechaRegistro: '2025-06-01',
    email: 'laura.m@email.com', telefono: '3155556677', tipoContacto: 'Personal',
    calle: 'Calle 5 # 20-50', ciudad: 'Cali', departamento: 'Valle del Cauca',
    pais: 'Colombia', codigoPostal: '760001' },
  { idCliente: 6, idPersona: 6, tipoDocumento: 'CC', numeroDocumento: '1077889900',
    nombre: 'Andres', apellido: 'Lopez', nombreCompleto: 'Andres Lopez',
    fechaNacimiento: '1987-02-14', nacionalidad: 'Colombiana', estado: 'Activo',
    fechaRegistro: '2025-07-15',
    email: 'andres.l@email.com', telefono: '3187778899', tipoContacto: 'Personal',
    calle: 'Cra 45 # 70-100', ciudad: 'Barranquilla', departamento: 'Atlantico',
    pais: 'Colombia', codigoPostal: '080010' }
];

// ---------- TIPO_HABITACION ----------
const TIPOS_HABITACION = [
  { idTipo: 1, nombre: 'Estandar',     precioBaseNoche: 180000, numeroInternos: 2 },
  { idTipo: 2, nombre: 'Deluxe',       precioBaseNoche: 280000, numeroInternos: 3 },
  { idTipo: 3, nombre: 'Suite',        precioBaseNoche: 450000, numeroInternos: 4 },
  { idTipo: 4, nombre: 'Familiar',     precioBaseNoche: 380000, numeroInternos: 6 },
  { idTipo: 5, nombre: 'Presidencial', precioBaseNoche: 850000, numeroInternos: 4 }
];

// ---------- HABITACION ----------
// Refleja: HABITACION → TIPO_HABITACION
const HABITACIONES = [
  { idHabitacion: 1, codigo: 'H101', piso: 1, idTipo: 1, tipo: 'Estandar', precioBase: 180000, capacidadMax: 2, estado: 'Disponible' },
  { idHabitacion: 2, codigo: 'H102', piso: 1, idTipo: 1, tipo: 'Estandar', precioBase: 180000, capacidadMax: 2, estado: 'Ocupada' },
  { idHabitacion: 3, codigo: 'H103', piso: 1, idTipo: 1, tipo: 'Estandar', precioBase: 180000, capacidadMax: 2, estado: 'Disponible' },
  { idHabitacion: 4, codigo: 'H201', piso: 2, idTipo: 2, tipo: 'Deluxe',   precioBase: 280000, capacidadMax: 3, estado: 'Disponible' },
  { idHabitacion: 5, codigo: 'H202', piso: 2, idTipo: 2, tipo: 'Deluxe',   precioBase: 280000, capacidadMax: 3, estado: 'Limpieza' },
  { idHabitacion: 6, codigo: 'H203', piso: 2, idTipo: 2, tipo: 'Deluxe',   precioBase: 280000, capacidadMax: 3, estado: 'Ocupada' },
  { idHabitacion: 7, codigo: 'H301', piso: 3, idTipo: 3, tipo: 'Suite',    precioBase: 450000, capacidadMax: 4, estado: 'Disponible' },
  { idHabitacion: 8, codigo: 'H302', piso: 3, idTipo: 3, tipo: 'Suite',    precioBase: 450000, capacidadMax: 4, estado: 'En Mantenimiento' },
  { idHabitacion: 9, codigo: 'H401', piso: 4, idTipo: 4, tipo: 'Familiar', precioBase: 380000, capacidadMax: 6, estado: 'Disponible' },
  { idHabitacion: 10,codigo: 'H402', piso: 4, idTipo: 4, tipo: 'Familiar', precioBase: 380000, capacidadMax: 6, estado: 'Ocupada' },
  { idHabitacion: 11,codigo: 'H501', piso: 5, idTipo: 5, tipo: 'Presidencial', precioBase: 850000, capacidadMax: 4, estado: 'Disponible' },
  { idHabitacion: 12,codigo: 'H502', piso: 5, idTipo: 5, tipo: 'Presidencial', precioBase: 850000, capacidadMax: 4, estado: 'Disponible' }
];

// ---------- HUESPED ----------
// Personas que se alojan en una reserva (distinto del cliente que reserva)
let HUESPEDES = [
  { idHuesped: 1, idReserva: 1001, tipoDocumento: 'CC', numeroDocumento: '1023456789',
    nombre: 'Maria', apellido: 'Garcia', edad: 35, nacionalidad: 'Colombiana', esTitular: true },
  { idHuesped: 2, idReserva: 1001, tipoDocumento: 'CC', numeroDocumento: '7788990011',
    nombre: 'Pedro', apellido: 'Garcia', edad: 38, nacionalidad: 'Colombiana', esTitular: false },
  { idHuesped: 3, idReserva: 1002, tipoDocumento: 'CC', numeroDocumento: '1098765432',
    nombre: 'Carlos', apellido: 'Rodriguez', edad: 40, nacionalidad: 'Colombiana', esTitular: true },
  { idHuesped: 4, idReserva: 1002, tipoDocumento: 'CC', numeroDocumento: '9988776655',
    nombre: 'Ana', apellido: 'Rodriguez', edad: 37, nacionalidad: 'Colombiana', esTitular: false },
  { idHuesped: 5, idReserva: 1002, tipoDocumento: 'PAS', numeroDocumento: 'CD987654',
    nombre: 'Marc', apellido: 'Rodriguez', edad: 12, nacionalidad: 'Colombiana', esTitular: false }
];

// ---------- RESERVA ----------
// Incluye EVENTO_RESERVA (CHECK_IN, CHECK_OUT, CANCELACION)
let RESERVAS = [
  { idReserva: 1001, fechaCreacion: '2026-05-01T10:30:00',
    fechaCheckInPrevista: '2026-05-12', fechaCheckOutPrevista: '2026-05-15',
    estado: 'Confirmada', canal: 'Presencial', numeroNoches: 3,
    precioTotal: 540000, idCliente: 1, nombreCliente: 'Maria Garcia',
    idEmpleado: 102, nombreEmpleado: 'Jose Espin',
    idHabitacion: 1, codigoHabitacion: 'H101',
    eventos: [
      { tipo: 'CHECK_IN', fechaHora: '2026-05-12T14:00:00', idEmpleado: 102 }
    ]
  },
  { idReserva: 1002, fechaCreacion: '2026-05-03T14:15:00',
    fechaCheckInPrevista: '2026-05-10', fechaCheckOutPrevista: '2026-05-14',
    estado: 'En Curso', canal: 'Online', numeroNoches: 4,
    precioTotal: 1120000, idCliente: 2, nombreCliente: 'Carlos Rodriguez',
    idEmpleado: 102, nombreEmpleado: 'Jose Espin',
    idHabitacion: 4, codigoHabitacion: 'H201',
    eventos: [
      { tipo: 'CHECK_IN', fechaHora: '2026-05-10T15:30:00', idEmpleado: 102 }
    ]
  },
  { idReserva: 1003, fechaCreacion: '2026-04-28T09:00:00',
    fechaCheckInPrevista: '2026-05-08', fechaCheckOutPrevista: '2026-05-11',
    estado: 'Finalizada', canal: 'Telefono', numeroNoches: 3,
    precioTotal: 540000, idCliente: 3, nombreCliente: 'Sophie Martin',
    idEmpleado: 103, nombreEmpleado: 'Daniel Agudelo',
    idHabitacion: 6, codigoHabitacion: 'H203',
    eventos: [
      { tipo: 'CHECK_IN',  fechaHora: '2026-05-08T16:00:00', idEmpleado: 103 },
      { tipo: 'CHECK_OUT', fechaHora: '2026-05-11T11:00:00', idEmpleado: 103 }
    ]
  },
  { idReserva: 1004, fechaCreacion: '2026-05-05T16:45:00',
    fechaCheckInPrevista: '2026-05-20', fechaCheckOutPrevista: '2026-05-25',
    estado: 'Pendiente', canal: 'Online', numeroNoches: 5,
    precioTotal: 1900000, idCliente: 4, nombreCliente: 'Juan Perez',
    idEmpleado: 102, nombreEmpleado: 'Jose Espin',
    idHabitacion: 9, codigoHabitacion: 'H401',
    eventos: []
  },
  { idReserva: 1005, fechaCreacion: '2026-04-15T11:00:00',
    fechaCheckInPrevista: '2026-04-22', fechaCheckOutPrevista: '2026-04-24',
    estado: 'Cancelada', canal: 'Presencial', numeroNoches: 2,
    precioTotal: 360000, idCliente: 5, nombreCliente: 'Laura Martinez',
    idEmpleado: 103, nombreEmpleado: 'Daniel Agudelo',
    idHabitacion: 3, codigoHabitacion: 'H103',
    eventos: [
      { tipo: 'CANCELACION', fechaHora: '2026-04-20T10:00:00', idEmpleado: 103, motivo: 'Cliente solicito cancelacion' }
    ]
  },
  { idReserva: 1006, fechaCreacion: '2026-05-07T13:30:00',
    fechaCheckInPrevista: '2026-05-15', fechaCheckOutPrevista: '2026-05-18',
    estado: 'Confirmada', canal: 'Presencial', numeroNoches: 3,
    precioTotal: 1350000, idCliente: 6, nombreCliente: 'Andres Lopez',
    idEmpleado: 102, nombreEmpleado: 'Jose Espin',
    idHabitacion: 11, codigoHabitacion: 'H501',
    eventos: []
  }
];

// ---------- PAGO ----------
let PAGOS = [
  { idPago: 5001, fechaPago: '2026-05-01T10:35:00', monto: 540000,
    medio: 'Tarjeta Credito', estado: 'Aprobado',
    numeroTransaccion: 'TRX-87654321', idReserva: 1001, nombreCliente: 'Maria Garcia' },
  { idPago: 5002, fechaPago: '2026-05-03T14:20:00', monto: 500000,
    medio: 'Transferencia', estado: 'Aprobado',
    numeroTransaccion: 'TRX-11223344', idReserva: 1002, nombreCliente: 'Carlos Rodriguez' },
  { idPago: 5003, fechaPago: '2026-05-08T12:00:00', monto: 540000,
    medio: 'Efectivo', estado: 'Aprobado',
    numeroTransaccion: '', idReserva: 1003, nombreCliente: 'Sophie Martin' },
  { idPago: 5004, fechaPago: '2026-05-07T13:35:00', monto: 700000,
    medio: 'PSE', estado: 'Aprobado',
    numeroTransaccion: 'TRX-99887766', idReserva: 1006, nombreCliente: 'Andres Lopez' }
];
