// Datos de prueba en memoria

let CLIENTES = [
  { idPersona: 1, tipoDocumento: 'CC', numeroDocumento: '1023456789', nombre: 'Maria', apellido: 'Garcia',
    email: 'maria.garcia@email.com', telefono: '3105551234', direccion: 'Cra 7 # 45-21, Bogota',
    fechaNacimiento: '1990-05-12', nacionalidad: 'Colombiana' },
  { idPersona: 2, tipoDocumento: 'CC', numeroDocumento: '1098765432', nombre: 'Carlos', apellido: 'Rodriguez',
    email: 'carlos.r@email.com', telefono: '3209876543', direccion: 'Calle 100 # 15-30, Bogota',
    fechaNacimiento: '1985-09-28', nacionalidad: 'Colombiana' },
  { idPersona: 3, tipoDocumento: 'PAS', numeroDocumento: 'AB123456', nombre: 'Sophie', apellido: 'Martin',
    email: 'sophie.martin@email.com', telefono: '+33612345678', direccion: 'Paris, Francia',
    fechaNacimiento: '1988-03-15', nacionalidad: 'Francesa' },
  { idPersona: 4, tipoDocumento: 'CE', numeroDocumento: '550199', nombre: 'Juan', apellido: 'Perez',
    email: 'juan.perez@email.com', telefono: '3001112233', direccion: 'Medellin',
    fechaNacimiento: '1992-11-08', nacionalidad: 'Venezolana' },
  { idPersona: 5, tipoDocumento: 'CC', numeroDocumento: '1011223344', nombre: 'Laura', apellido: 'Martinez',
    email: 'laura.m@email.com', telefono: '3155556677', direccion: 'Cali',
    fechaNacimiento: '1995-07-22', nacionalidad: 'Colombiana' },
  { idPersona: 6, tipoDocumento: 'CC', numeroDocumento: '1077889900', nombre: 'Andres', apellido: 'Lopez',
    email: 'andres.l@email.com', telefono: '3187778899', direccion: 'Barranquilla',
    fechaNacimiento: '1987-02-14', nacionalidad: 'Colombiana' }
];

let EMPLEADOS = [
  { idPersona: 101, tipoDocumento: 'CC', numeroDocumento: '79123456', nombre: 'Brian', apellido: 'Rodriguez',
    email: 'brodriguez@hotel.com', telefono: '3001234567', fechaContratacion: '2023-01-15',
    salario: 4500000, usuario: 'admin', estado: 'Activo', cargo: 'Administrador' },
  { idPersona: 102, tipoDocumento: 'CC', numeroDocumento: '52987654', nombre: 'Jose', apellido: 'Espin',
    email: 'jespin@hotel.com', telefono: '3019876543', fechaContratacion: '2023-06-01',
    salario: 2200000, usuario: 'empleado', estado: 'Activo', cargo: 'Recepcionista' },
  { idPersona: 103, tipoDocumento: 'CC', numeroDocumento: '80445566', nombre: 'Daniel', apellido: 'Agudelo',
    email: 'dagudelo@hotel.com', telefono: '3024567890', fechaContratacion: '2024-02-10',
    salario: 2200000, usuario: 'dagudelo', estado: 'Activo', cargo: 'Recepcionista' },
  { idPersona: 104, tipoDocumento: 'CC', numeroDocumento: '40112233', nombre: 'Lucia', apellido: 'Torres',
    email: 'ltorres@hotel.com', telefono: '3157891234', fechaContratacion: '2023-08-20',
    salario: 1500000, usuario: 'ltorres', estado: 'Activo', cargo: 'Limpieza' },
  { idPersona: 105, tipoDocumento: 'CC', numeroDocumento: '11223344', nombre: 'Pedro', apellido: 'Sanchez',
    email: 'psanchez@hotel.com', telefono: '3209998877', fechaContratacion: '2024-04-05',
    salario: 1800000, usuario: 'psanchez', estado: 'Activo', cargo: 'Mantenimiento' }
];

const HABITACIONES = [
  { idHabitacion: 1, numero: '101', piso: 1, tipo: 'Estandar', precioBase: 180000, capacidadMaxima: 2, estado: 'Disponible' },
  { idHabitacion: 2, numero: '102', piso: 1, tipo: 'Estandar', precioBase: 180000, capacidadMaxima: 2, estado: 'Ocupada' },
  { idHabitacion: 3, numero: '103', piso: 1, tipo: 'Estandar', precioBase: 180000, capacidadMaxima: 2, estado: 'Disponible' },
  { idHabitacion: 4, numero: '201', piso: 2, tipo: 'Deluxe', precioBase: 280000, capacidadMaxima: 3, estado: 'Disponible' },
  { idHabitacion: 5, numero: '202', piso: 2, tipo: 'Deluxe', precioBase: 280000, capacidadMaxima: 3, estado: 'Limpieza' },
  { idHabitacion: 6, numero: '203', piso: 2, tipo: 'Deluxe', precioBase: 280000, capacidadMaxima: 3, estado: 'Ocupada' },
  { idHabitacion: 7, numero: '301', piso: 3, tipo: 'Suite', precioBase: 450000, capacidadMaxima: 4, estado: 'Disponible' },
  { idHabitacion: 8, numero: '302', piso: 3, tipo: 'Suite', precioBase: 450000, capacidadMaxima: 4, estado: 'En Mantenimiento' },
  { idHabitacion: 9, numero: '401', piso: 4, tipo: 'Familiar', precioBase: 380000, capacidadMaxima: 6, estado: 'Disponible' },
  { idHabitacion: 10, numero: '402', piso: 4, tipo: 'Familiar', precioBase: 380000, capacidadMaxima: 6, estado: 'Ocupada' },
  { idHabitacion: 11, numero: '501', piso: 5, tipo: 'Presidencial', precioBase: 850000, capacidadMaxima: 4, estado: 'Disponible' },
  { idHabitacion: 12, numero: '502', piso: 5, tipo: 'Presidencial', precioBase: 850000, capacidadMaxima: 4, estado: 'Disponible' }
];

let RESERVAS = [
  { idReserva: 1001, fechaReserva: '2026-05-01T10:30:00', fechaEntrada: '2026-05-12', fechaSalida: '2026-05-15',
    estado: 'Confirmada', numeroHuespedes: 2, total: 540000, idCliente: 1, nombreCliente: 'Maria Garcia',
    idEmpleado: 102, idHabitacion: 1, numeroHabitacion: '101' },
  { idReserva: 1002, fechaReserva: '2026-05-03T14:15:00', fechaEntrada: '2026-05-10', fechaSalida: '2026-05-14',
    estado: 'En Curso', numeroHuespedes: 3, total: 1120000, idCliente: 2, nombreCliente: 'Carlos Rodriguez',
    idEmpleado: 102, idHabitacion: 4, numeroHabitacion: '201' },
  { idReserva: 1003, fechaReserva: '2026-04-28T09:00:00', fechaEntrada: '2026-05-08', fechaSalida: '2026-05-11',
    estado: 'Finalizada', numeroHuespedes: 1, total: 540000, idCliente: 3, nombreCliente: 'Sophie Martin',
    idEmpleado: 103, idHabitacion: 6, numeroHabitacion: '203' },
  { idReserva: 1004, fechaReserva: '2026-05-05T16:45:00', fechaEntrada: '2026-05-20', fechaSalida: '2026-05-25',
    estado: 'Pendiente', numeroHuespedes: 4, total: 1900000, idCliente: 4, nombreCliente: 'Juan Perez',
    idEmpleado: 102, idHabitacion: 9, numeroHabitacion: '401' },
  { idReserva: 1005, fechaReserva: '2026-04-15T11:00:00', fechaEntrada: '2026-04-22', fechaSalida: '2026-04-24',
    estado: 'Cancelada', numeroHuespedes: 2, total: 360000, idCliente: 5, nombreCliente: 'Laura Martinez',
    idEmpleado: 103, idHabitacion: 3, numeroHabitacion: '103' },
  { idReserva: 1006, fechaReserva: '2026-05-07T13:30:00', fechaEntrada: '2026-05-15', fechaSalida: '2026-05-18',
    estado: 'Confirmada', numeroHuespedes: 2, total: 1350000, idCliente: 6, nombreCliente: 'Andres Lopez',
    idEmpleado: 102, idHabitacion: 11, numeroHabitacion: '501' }
];

const PAGOS = [
  { idPago: 5001, fechaPago: '2026-05-01T10:35:00', monto: 540000, metodo: 'Tarjeta Credito', estado: 'Aprobado',
    numeroTransaccion: 'TRX-87654321', idReserva: 1001, nombreCliente: 'Maria Garcia' },
  { idPago: 5002, fechaPago: '2026-05-03T14:20:00', monto: 500000, metodo: 'Transferencia', estado: 'Aprobado',
    numeroTransaccion: 'TRX-11223344', idReserva: 1002, nombreCliente: 'Carlos Rodriguez' },
  { idPago: 5003, fechaPago: '2026-05-08T12:00:00', monto: 540000, metodo: 'Efectivo', estado: 'Aprobado',
    numeroTransaccion: '', idReserva: 1003, nombreCliente: 'Sophie Martin' },
  { idPago: 5004, fechaPago: '2026-05-07T13:35:00', monto: 700000, metodo: 'PSE', estado: 'Aprobado',
    numeroTransaccion: 'TRX-99887766', idReserva: 1006, nombreCliente: 'Andres Lopez' }
];
