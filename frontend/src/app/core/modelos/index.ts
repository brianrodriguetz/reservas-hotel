export interface Persona {
  idPersona: number;
  tipoDocumento: 'CC' | 'CE' | 'TI' | 'PAS';
  numeroDocumento: string;
  nombre: string;
  apellido: string;
  email?: string;
  telefono?: string;
}

export interface Cliente extends Persona {
  direccion?: string;
  fechaNacimiento?: string;
  nacionalidad?: string;
}

export type CargoEmpleado = 'Recepcionista' | 'Administrador' | 'Mantenimiento' | 'Limpieza';

export interface Empleado extends Persona {
  fechaContratacion: string;
  salario: number;
  usuario: string;
  estado: 'Activo' | 'Inactivo';
  cargo: CargoEmpleado;
}

export interface Habitacion {
  idHabitacion: number;
  numero: string;
  piso: number;
  tipo: 'Estandar' | 'Deluxe' | 'Suite' | 'Familiar' | 'Presidencial';
  precioBase: number;
  capacidadMaxima: number;
  estado: 'Disponible' | 'Ocupada' | 'En Mantenimiento' | 'Limpieza';
}

export interface Reserva {
  idReserva: number;
  fechaReserva: string;
  fechaEntrada: string;
  fechaSalida: string;
  estado: 'Pendiente' | 'Confirmada' | 'En Curso' | 'Finalizada' | 'Cancelada' | 'No Show';
  numeroHuespedes: number;
  total: number;
  idCliente: number;
  nombreCliente?: string;
  idEmpleado: number;
  nombreEmpleado?: string;
  idHabitacion: number;
  numeroHabitacion?: string;
}

export interface Pago {
  idPago: number;
  fechaPago: string;
  monto: number;
  metodo: 'Efectivo' | 'Tarjeta Credito' | 'Tarjeta Debito' | 'Transferencia' | 'PSE';
  estado: 'Aprobado' | 'Rechazado' | 'Pendiente';
  numeroTransaccion?: string;
  idReserva: number;
  nombreCliente?: string;
}

export interface Sesion {
  idEmpleado: number;
  nombreCompleto: string;
  usuario: string;
  cargo: CargoEmpleado;
  esAdmin: boolean;
}
