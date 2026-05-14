// Funciones de autenticacion

function login(usuario, password) {
  const credenciales = { admin: 'admin', empleado: 'empleado' };
  if (!credenciales[usuario] || credenciales[usuario] !== password) {
    // Busca en lista de empleados (contrasena = usuario por defecto en mock)
    const emp = EMPLEADOS.find(function(e) { return e.usuario === usuario && e.estado === 'Activo'; });
    if (!emp) return null;
  }
  const emp = EMPLEADOS.find(function(e) { return e.usuario === usuario; });
  if (!emp) return null;
  const sesion = {
    idEmpleado: emp.idEmpleado,
    nombreCompleto: emp.nombreCompleto,
    usuario: emp.usuario,
    rol: emp.rol,
    idRol: emp.idRol,
    esAdmin: emp.idRol === 1
  };
  localStorage.setItem('hotel_sesion', JSON.stringify(sesion));
  return sesion;
}

function obtenerSesion() {
  const s = localStorage.getItem('hotel_sesion');
  return s ? JSON.parse(s) : null;
}

function cerrarSesion() {
  localStorage.removeItem('hotel_sesion');
  window.location.href = '../index.html';
}

function verificarSesion() {
  const s = obtenerSesion();
  if (!s) { window.location.href = '../index.html'; return null; }
  return s;
}

function verificarAdmin() {
  const s = verificarSesion();
  if (!s) return null;
  if (!s.esAdmin) { window.location.href = 'dashboard.html'; return null; }
  return s;
}
