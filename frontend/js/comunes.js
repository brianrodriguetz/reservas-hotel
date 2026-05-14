// Helpers compartidos

function formatearMoneda(v) {
  if (!v) return '$ 0';
  return '$ ' + Number(v).toLocaleString('es-CO');
}

function formatearFecha(iso) {
  if (!iso) return '';
  const f = new Date(iso);
  return String(f.getDate()).padStart(2,'0') + '/' +
         String(f.getMonth()+1).padStart(2,'0') + '/' + f.getFullYear();
}

function formatearFechaHora(iso) {
  if (!iso) return '';
  const f = new Date(iso);
  return formatearFecha(iso) + ' ' +
         String(f.getHours()).padStart(2,'0') + ':' +
         String(f.getMinutes()).padStart(2,'0');
}

function mostrarMensaje(tipo, texto) {
  const el = document.getElementById('contenedor-mensaje');
  if (!el) return;
  el.className = 'mensaje mensaje-' + tipo;
  el.textContent = texto;
  setTimeout(function() { el.className = 'mensaje oculto'; }, 4000);
}

function inicializarLayout() {
  const s = verificarSesion();
  if (!s) return;
  const n = document.getElementById('topbar-nombre');
  const r = document.getElementById('topbar-rol');
  const a = document.getElementById('topbar-avatar');
  if (n) n.textContent = 'Hola, ' + s.nombreCompleto;
  if (r) r.textContent = s.rol;
  if (a) a.textContent = s.nombreCompleto.charAt(0).toUpperCase();
  if (!s.esAdmin) {
    ['menu-empleados','menu-reportes','menu-separador'].forEach(function(id) {
      const el = document.getElementById(id);
      if (el) el.classList.add('oculto');
    });
  }
  const ruta = window.location.pathname;
  document.querySelectorAll('.menu-item').forEach(function(item) {
    const href = item.getAttribute('href');
    if (href && ruta.indexOf(href) !== -1) item.classList.add('activo');
  });
}
