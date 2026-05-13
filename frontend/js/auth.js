// Funciones de autenticacion

// Inicia sesion
function login(usuario, password) {
  // Usuarios de prueba
  if (usuario === 'admin' && password === 'admin') {
    const sesion = {
      idEmpleado: 101,
      nombreCompleto: 'Brian Rodriguez',
      usuario: 'admin',
      cargo: 'Administrador',
      esAdmin: true
    };
    guardarSesion(sesion);
    return sesion;
  }

  if (usuario === 'empleado' && password === 'empleado') {
    const sesion = {
      idEmpleado: 102,
      nombreCompleto: 'Jose Espin',
      usuario: 'empleado',
      cargo: 'Recepcionista',
      esAdmin: false
    };
    guardarSesion(sesion);
    return sesion;
  }

  // Busca en lista de empleados
  let encontrado = null;
  for (let i = 0; i < EMPLEADOS.length; i++) {
    if (EMPLEADOS[i].usuario === usuario && EMPLEADOS[i].estado === 'Activo') {
      encontrado = EMPLEADOS[i];
      break;
    }
  }

  if (!encontrado) {
    return null;
  }

  const sesion = {
    idEmpleado: encontrado.idPersona,
    nombreCompleto: encontrado.nombre + ' ' + encontrado.apellido,
    usuario: encontrado.usuario,
    cargo: encontrado.cargo,
    esAdmin: encontrado.cargo === 'Administrador'
  };
  guardarSesion(sesion);
  return sesion;
}

// Guarda sesion en localStorage
function guardarSesion(sesion) {
  localStorage.setItem('hotel_sesion', JSON.stringify(sesion));
}

// Obtiene sesion actual
function obtenerSesion() {
  const guardada = localStorage.getItem('hotel_sesion');
  if (!guardada) {
    return null;
  }
  return JSON.parse(guardada);
}

// Cierra sesion
function cerrarSesion() {
  localStorage.removeItem('hotel_sesion');
  window.location.href = '../index.html';
}

// Verifica que haya sesion activa
function verificarSesion() {
  const sesion = obtenerSesion();
  if (!sesion) {
    window.location.href = '../index.html';
    return null;
  }
  return sesion;
}

// Verifica acceso de admin
function verificarAdmin() {
  const sesion = verificarSesion();
  if (!sesion) {
    return null;
  }
  if (!sesion.esAdmin) {
    window.location.href = 'dashboard.html';
    return null;
  }
  return sesion;
}
