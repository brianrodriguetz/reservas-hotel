// Funciones comunes

// Formatea moneda colombiana
function formatearMoneda(valor) {
  if (!valor) return '$ 0';
  return '$ ' + valor.toLocaleString('es-CO');
}

// Formatea fecha
function formatearFecha(fechaIso) {
  if (!fechaIso) return '';
  const fecha = new Date(fechaIso);
  const dia = String(fecha.getDate()).padStart(2, '0');
  const mes = String(fecha.getMonth() + 1).padStart(2, '0');
  const anio = fecha.getFullYear();
  return dia + '/' + mes + '/' + anio;
}

// Formatea fecha con hora
function formatearFechaHora(fechaIso) {
  if (!fechaIso) return '';
  const fecha = new Date(fechaIso);
  const dia = String(fecha.getDate()).padStart(2, '0');
  const mes = String(fecha.getMonth() + 1).padStart(2, '0');
  const anio = fecha.getFullYear();
  const horas = String(fecha.getHours()).padStart(2, '0');
  const mins = String(fecha.getMinutes()).padStart(2, '0');
  return dia + '/' + mes + '/' + anio + ' ' + horas + ':' + mins;
}

// Muestra mensaje en pantalla
function mostrarMensaje(tipo, texto) {
  const cont = document.getElementById('contenedor-mensaje');
  if (!cont) return;
  cont.className = 'mensaje mensaje-' + tipo;
  cont.textContent = texto;
  setTimeout(function() {
    cont.className = 'mensaje oculto';
  }, 4000);
}

// Inicializa sidebar y topbar
function inicializarLayout() {
  const sesion = verificarSesion();
  if (!sesion) return;

  // Topbar
  const elNombre = document.getElementById('topbar-nombre');
  const elRol = document.getElementById('topbar-rol');
  const elAvatar = document.getElementById('topbar-avatar');
  if (elNombre) elNombre.textContent = 'Hola, ' + sesion.nombreCompleto;
  if (elRol) elRol.textContent = sesion.cargo;
  if (elAvatar) elAvatar.textContent = sesion.nombreCompleto.charAt(0).toUpperCase();

  // Sidebar: ocultar opciones de admin si no lo es
  if (!sesion.esAdmin) {
    const itemEmpleados = document.getElementById('menu-empleados');
    const itemReportes = document.getElementById('menu-reportes');
    const sep = document.getElementById('menu-separador');
    if (itemEmpleados) itemEmpleados.classList.add('oculto');
    if (itemReportes) itemReportes.classList.add('oculto');
    if (sep) sep.classList.add('oculto');
  }

  // Marca item activo segun la pagina actual
  const ruta = window.location.pathname;
  const items = document.querySelectorAll('.menu-item');
  items.forEach(function(item) {
    const href = item.getAttribute('href');
    if (href && ruta.indexOf(href) !== -1) {
      item.classList.add('activo');
    }
  });
}
