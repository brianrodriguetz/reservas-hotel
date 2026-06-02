// ============================================================
//  Stay Flow — Sistema de Gestión Hotelera
//  frontend/js/main.js
// ============================================================

// ── DATA ──────────────────────────────────────────────────
const reservas = [
  {id:'R001',canal:'Telefónica',  cliente:'Cliente1 Apellido1',    ci:'15 jun 2026',co:'17 jun 2026',habs:'104',         total:'$500,000',   estado:'Pendiente'},
  {id:'R002',canal:'Presencial',  cliente:'Cliente6 Apellido6',    ci:'05 jul 2026',co:'08 jul 2026',habs:'101',         total:'$450,000',   estado:'Pendiente'},
  {id:'R003',canal:'Telefónica',  cliente:'Empresa1 SAS',          ci:'20 jul 2026',co:'24 jul 2026',habs:'105,106',     total:'$2,000,000', estado:'Pendiente'},
  {id:'R004',canal:'Telefónica',  cliente:'Cliente7 Apellido7',    ci:'10 ago 2026',co:'12 ago 2026',habs:'107',         total:'$700,000',   estado:'Pendiente'},
  {id:'R005',canal:'Telefónica',  cliente:'Cliente2 Apellido2',    ci:'25 may 2026',co:'27 may 2026',habs:'102',         total:'$300,000',   estado:'Confirmada'},
  {id:'R006',canal:'Telefónica',  cliente:'Cliente3 Apellido3',    ci:'01 jun 2026',co:'04 jun 2026',habs:'105',         total:'$750,000',   estado:'Confirmada'},
  {id:'R007',canal:'Telefónica',  cliente:'Empresa2 SAS',          ci:'10 jun 2026',co:'15 jun 2026',habs:'203,204,205', total:'$3,750,000', estado:'Confirmada'},
  {id:'R008',canal:'Presencial',  cliente:'Cliente5 Apellido5',    ci:'15 jul 2026',co:'18 jul 2026',habs:'303',         total:'$1,500,000', estado:'Confirmada'},
  {id:'R009',canal:'Telefónica',  cliente:'Cliente8 Apellido8',    ci:'20 jul 2026',co:'24 jul 2026',habs:'301',         total:'$1,400,000', estado:'Confirmada'},
  {id:'R010',canal:'Presencial',  cliente:'Cliente9 Apellido9',    ci:'14 may 2026',co:'18 may 2026',habs:'202',         total:'$600,000',   estado:'En_Curso'},
  {id:'R011',canal:'Telefónica',  cliente:'Empresa3 LTDA',         ci:'15 may 2026',co:'20 may 2026',habs:'207,305',     total:'$4,250,000', estado:'En_Curso'},
  {id:'R012',canal:'Presencial',  cliente:'Cliente10 Apellido10',  ci:'16 may 2026',co:'17 may 2026',habs:'102',         total:'$150,000',   estado:'En_Curso'},
  {id:'R013',canal:'Telefónica',  cliente:'Cliente1 Apellido1',    ci:'05 ene 2026',co:'10 ene 2026',habs:'101',         total:'$750,000',   estado:'Finalizada'},
  {id:'R014',canal:'Presencial',  cliente:'Cliente4 Apellido4',    ci:'20 feb 2026',co:'23 feb 2026',habs:'105',         total:'$750,000',   estado:'Finalizada'},
  {id:'R015',canal:'Telefónica',  cliente:'Empresa4 SAS',          ci:'01 mar 2026',co:'05 mar 2026',habs:'201,206',     total:'$2,000,000', estado:'Finalizada'},
  {id:'R016',canal:'Telefónica',  cliente:'Cliente7 Apellido7',    ci:'01 abr 2026',co:'03 abr 2026',habs:'302',         total:'$700,000',   estado:'Finalizada'},
  {id:'R017',canal:'Telefónica',  cliente:'Cliente2 Apellido2',    ci:'20 ene 2026',co:'22 ene 2026',habs:'201',         total:'$300,000',   estado:'Cancelada'},
  {id:'R018',canal:'Telefónica',  cliente:'Cliente5 Apellido5',    ci:'15 feb 2026',co:'19 feb 2026',habs:'104',         total:'$1,000,000', estado:'Cancelada'},
  {id:'R019',canal:'Telefónica',  cliente:'Cliente3 Apellido3',    ci:'01 mar 2026',co:'04 mar 2026',habs:'303',         total:'$1,500,000', estado:'Cancelada'},
  {id:'R020',canal:'Telefónica',  cliente:'Empresa5 SAS',          ci:'10 may 2026',co:'15 may 2026',habs:'304,306',     total:'$5,000,000', estado:'Cancelada'},
  {id:'R021',canal:'Telefónica',  cliente:'Cliente8 Apellido8',    ci:'15 abr 2026',co:'18 abr 2026',habs:'107',         total:'$1,050,000', estado:'No_Show'},
];

const habitaciones = [
  {cod:'101',piso:1,estado:'Disponible',       tipo:'Sencilla',precio:'$150,000'},
  {cod:'102',piso:1,estado:'Ocupada',          tipo:'Sencilla',precio:'$150,000'},
  {cod:'103',piso:1,estado:'En_Mantenimiento', tipo:'Sencilla',precio:'$150,000'},
  {cod:'104',piso:1,estado:'Disponible',       tipo:'Doble',   precio:'$250,000'},
  {cod:'105',piso:1,estado:'Disponible',       tipo:'Doble',   precio:'$250,000'},
  {cod:'106',piso:1,estado:'Disponible',       tipo:'Doble',   precio:'$250,000'},
  {cod:'107',piso:1,estado:'Disponible',       tipo:'Triple',  precio:'$350,000'},
  {cod:'201',piso:2,estado:'Disponible',       tipo:'Sencilla',precio:'$150,000'},
  {cod:'202',piso:2,estado:'Ocupada',          tipo:'Sencilla',precio:'$150,000'},
  {cod:'203',piso:2,estado:'Reservada',        tipo:'Doble',   precio:'$250,000'},
  {cod:'204',piso:2,estado:'Reservada',        tipo:'Doble',   precio:'$250,000'},
  {cod:'205',piso:2,estado:'Reservada',        tipo:'Doble',   precio:'$250,000'},
  {cod:'206',piso:2,estado:'Disponible',       tipo:'Triple',  precio:'$350,000'},
  {cod:'207',piso:2,estado:'Ocupada',          tipo:'Triple',  precio:'$350,000'},
  {cod:'301',piso:3,estado:'Disponible',       tipo:'Triple',  precio:'$350,000'},
  {cod:'302',piso:3,estado:'En_Limpieza',      tipo:'Triple',  precio:'$350,000'},
  {cod:'303',piso:3,estado:'Disponible',       tipo:'Suite',   precio:'$500,000'},
  {cod:'304',piso:3,estado:'Disponible',       tipo:'Suite',   precio:'$500,000'},
  {cod:'305',piso:3,estado:'Ocupada',          tipo:'Suite',   precio:'$500,000'},
  {cod:'306',piso:3,estado:'Disponible',       tipo:'Suite',   precio:'$500,000'},
];

const clientes = [
  {id:'C001',tipo:'Persona', nombre:'Cliente1 Apellido1',   doc:'CC 1010001',      contacto:'3001000001', reg:'15 ene 2024', estado:'Activo'},
  {id:'C002',tipo:'Persona', nombre:'Cliente2 Apellido2',   doc:'CC 1010002',      contacto:'3001000002', reg:'20 mar 2024', estado:'Activo'},
  {id:'C003',tipo:'Persona', nombre:'Cliente3 Apellido3',   doc:'CC 1010003',      contacto:'3001000003', reg:'10 jun 2024', estado:'Activo'},
  {id:'C004',tipo:'Persona', nombre:'Cliente4 Apellido4',   doc:'CE 1020001',      contacto:'3001000004', reg:'05 sep 2024', estado:'Activo'},
  {id:'C005',tipo:'Persona', nombre:'Cliente5 Apellido5',   doc:'CC 1010005',      contacto:'3001000005', reg:'12 nov 2024', estado:'Activo'},
  {id:'C006',tipo:'Persona', nombre:'Cliente6 Apellido6',   doc:'PA 1030001',      contacto:'3001000006', reg:'18 feb 2025', estado:'Activo'},
  {id:'C007',tipo:'Persona', nombre:'Cliente7 Apellido7',   doc:'CC 1010007',      contacto:'3001000007', reg:'22 may 2025', estado:'Activo'},
  {id:'C008',tipo:'Persona', nombre:'Cliente8 Apellido8',   doc:'CC 1010008',      contacto:'3001000008', reg:'30 ago 2025', estado:'Activo'},
  {id:'C009',tipo:'Persona', nombre:'Cliente9 Apellido9',   doc:'CE 1020002',      contacto:'3001000009', reg:'15 nov 2025', estado:'Activo'},
  {id:'C010',tipo:'Persona', nombre:'Cliente10 Apellido10', doc:'CC 1010010',      contacto:'3001000010', reg:'20 ene 2026', estado:'Inactivo'},
  {id:'C011',tipo:'Empresa', nombre:'Empresa1 SAS',         doc:'NIT 900100001-1', contacto:'3201100001', reg:'10 feb 2024', estado:'Activo'},
  {id:'C012',tipo:'Empresa', nombre:'Empresa2 SAS',         doc:'NIT 900100002-2', contacto:'3201100002', reg:'25 jul 2024', estado:'Activo'},
  {id:'C013',tipo:'Empresa', nombre:'Empresa3 LTDA',        doc:'NIT 900100003-3', contacto:'3201100003', reg:'15 abr 2025', estado:'Activo'},
  {id:'C014',tipo:'Empresa', nombre:'Empresa4 SAS',         doc:'NIT 900100004-4', contacto:'3201100004', reg:'08 oct 2025', estado:'Activo'},
  {id:'C015',tipo:'Empresa', nombre:'Empresa5 SAS',         doc:'NIT 900100005-5', contacto:'3201100005', reg:'14 feb 2026', estado:'Activo'},
];

const huespedes = [
  {id:'H01',nombre:'Huesped1', apellido:'Apellido1', doc:'CC 2010001', nac:'Colombiana', fecha:'1980-01-15'},
  {id:'H02',nombre:'Huesped2', apellido:'Apellido2', doc:'CC 2010002', nac:'Colombiana', fecha:'1985-06-22'},
  {id:'H03',nombre:'Huesped3', apellido:'Apellido3', doc:'CC 2010003', nac:'Colombiana', fecha:'1990-09-10'},
  {id:'H04',nombre:'Huesped4', apellido:'Apellido4', doc:'CE 2020001', nac:'Venezolana', fecha:'1982-04-18'},
  {id:'H05',nombre:'Huesped5', apellido:'Apellido5', doc:'CC 2010005', nac:'Colombiana', fecha:'1995-11-30'},
  {id:'H06',nombre:'Huesped6', apellido:'Apellido6', doc:'CC 2010006', nac:'Colombiana', fecha:'1978-03-25'},
  {id:'H07',nombre:'Huesped7', apellido:'Apellido7', doc:'PA 2030001', nac:'Argentina',  fecha:'1988-07-12'},
  {id:'H08',nombre:'Huesped8', apellido:'Apellido8', doc:'CC 2010008', nac:'Colombiana', fecha:'1992-12-05'},
  {id:'H09',nombre:'Huesped9', apellido:'Apellido9', doc:'CC 2010009', nac:'Colombiana', fecha:'1986-05-20'},
  {id:'H10',nombre:'Huesped10',apellido:'Apellido10',doc:'CE 2020002', nac:'Mexicana',   fecha:'1991-08-08'},
  {id:'H11',nombre:'Huesped11',apellido:'Apellido11',doc:'CC 2010011', nac:'Colombiana', fecha:'1983-02-14'},
  {id:'H12',nombre:'Huesped12',apellido:'Apellido12',doc:'CC 2010012', nac:'Colombiana', fecha:'1989-10-03'},
  {id:'H13',nombre:'Huesped13',apellido:'Apellido13',doc:'CC 2010013', nac:'Colombiana', fecha:'1994-06-17'},
  {id:'H14',nombre:'Huesped14',apellido:'Apellido14',doc:'PA 2030002', nac:'Española',   fecha:'1981-11-28'},
  {id:'H15',nombre:'Huesped15',apellido:'Apellido15',doc:'CC 2010015', nac:'Colombiana', fecha:'1987-01-09'},
  {id:'H16',nombre:'Huesped16',apellido:'Apellido16',doc:'CC 2010016', nac:'Colombiana', fecha:'1993-04-26'},
  {id:'H17',nombre:'Huesped17',apellido:'Apellido17',doc:'CC 2010017', nac:'Colombiana', fecha:'1979-09-15'},
  {id:'H18',nombre:'Huesped18',apellido:'Apellido18',doc:'CE 2020003', nac:'Peruana',    fecha:'1996-07-21'},
  {id:'H19',nombre:'Huesped19',apellido:'Apellido19',doc:'CC 2010019', nac:'Colombiana', fecha:'1984-12-11'},
  {id:'H20',nombre:'Huesped20',apellido:'Apellido20',doc:'CC 2010020', nac:'Colombiana', fecha:'1990-03-30'},
];

const pagos = [
  {id:'P01',res:'R005',medio:'Tarjeta Crédito', monto:'$90,000',     fecha:'02 may 2026', estado:'Aprobado'},
  {id:'P02',res:'R006',medio:'Tarjeta Débito',  monto:'$225,000',    fecha:'21 abr 2026', estado:'Aprobado'},
  {id:'P03',res:'R007',medio:'Transferencia',   monto:'$1,125,000',  fecha:'16 abr 2026', estado:'Aprobado'},
  {id:'P04',res:'R008',medio:'Tarjeta Crédito', monto:'$450,000',    fecha:'06 may 2026', estado:'Aprobado'},
  {id:'P05',res:'R008',medio:'Tarjeta Crédito', monto:'$1,050,000',  fecha:'10 may 2026', estado:'Rechazado'},
  {id:'P06',res:'R008',medio:'Tarjeta Crédito', monto:'$350,000',    fecha:'15 may 2026', estado:'Pendiente'},
  {id:'P07',res:'R009',medio:'Tarjeta Débito',  monto:'$420,000',    fecha:'09 may 2026', estado:'Aprobado'},
  {id:'P08',res:'R010',medio:'Tarjeta Crédito', monto:'$180,000',    fecha:'10 may 2026', estado:'Aprobado'},
  {id:'P09',res:'R010',medio:'Tarjeta Crédito', monto:'$420,000',    fecha:'14 may 2026', estado:'Aprobado'},
  {id:'P10',res:'R011',medio:'Transferencia',   monto:'$1,275,000',  fecha:'26 abr 2026', estado:'Aprobado'},
  {id:'P11',res:'R011',medio:'Tarjeta Crédito', monto:'$2,975,000',  fecha:'12 may 2026', estado:'Rechazado'},
  {id:'P12',res:'R011',medio:'Transferencia',   monto:'$2,975,000',  fecha:'15 may 2026', estado:'Aprobado'},
  {id:'P13',res:'R012',medio:'Efectivo',        monto:'$150,000',    fecha:'16 may 2026', estado:'Aprobado'},
  {id:'P22',res:'R017',medio:'Tarjeta Crédito', monto:'$90,000',     fecha:'16 dic 2025', estado:'Reversado'},
  {id:'P23',res:'R018',medio:'Tarjeta Crédito', monto:'$300,000',    fecha:'26 ene 2026', estado:'Aprobado'},
  {id:'P26',res:'R020',medio:'Transferencia',   monto:'$1,500,000',  fecha:'21 abr 2026', estado:'Aprobado'},
  {id:'P27',res:'R021',medio:'Tarjeta Crédito', monto:'$315,000',    fecha:'11 mar 2026', estado:'Aprobado'},
];

// ── BADGE MAP ──────────────────────────────────────────────
const estadoBadge = {
  'Pendiente' : 'badge-yellow',
  'Confirmada': 'badge-blue',
  'En_Curso'  : 'badge-accent',
  'Finalizada': 'badge-green',
  'Cancelada' : 'badge-red',
  'No_Show'   : 'badge-muted',
  'Aprobado'  : 'badge-green',
  'Rechazado' : 'badge-red',
  'Reversado' : 'badge-purple',
  'Activo'    : 'badge-green',
  'Inactivo'  : 'badge-muted',
};

// ── RENDER FUNCTIONS ───────────────────────────────────────
function renderReservas(list) {
  document.getElementById('bodyReservas').innerHTML = list.map(r => `
    <tr>
      <td class="mono">#${r.id}</td>
      <td class="muted">${r.canal}</td>
      <td>${r.cliente}</td>
      <td class="muted">${r.ci}</td>
      <td class="muted">${r.co}</td>
      <td class="muted font-mono" style="font-size:11px">${r.habs}</td>
      <td class="text-accent font-mono">${r.total}</td>
      <td><span class="badge ${estadoBadge[r.estado] || 'badge-muted'}">${r.estado.replace('_',' ')}</span></td>
      <td><button class="btn btn-ghost btn-sm" onclick="showToast('Reserva #${r.id} seleccionada')">Ver</button></td>
    </tr>`).join('');
}

function renderRooms(list) {
  const estadoClass = {
    'Disponible'      : 'disponible',
    'Reservada'       : 'reservada',
    'Ocupada'         : 'ocupada',
    'En_Limpieza'     : 'limpieza',
    'En_Mantenimiento': 'mantenimiento',
  };
  const estadoBadgeRoom = {
    'Disponible'      : 'badge-green',
    'Reservada'       : 'badge-blue',
    'Ocupada'         : 'badge-accent',
    'En_Limpieza'     : 'badge-yellow',
    'En_Mantenimiento': 'badge-red',
  };
  document.getElementById('roomsGrid').innerHTML = list.map(h => `
    <div class="room-card ${estadoClass[h.estado] || ''}" onclick="showToast('Hab. ${h.cod} — ${h.estado}')">
      <div class="room-code">${h.cod}</div>
      <div class="room-floor">Piso ${h.piso}</div>
      <div class="room-type">${h.tipo}</div>
      <div class="flex justify-between items-center">
        <div class="room-price">${h.precio}</div>
        <span class="badge ${estadoBadgeRoom[h.estado]}" style="font-size:9px;padding:2px 6px">${h.estado.replace('_',' ')}</span>
      </div>
    </div>`).join('');
}

function renderClientes(list) {
  document.getElementById('bodyClientes').innerHTML = list.map(c => `
    <tr>
      <td class="mono">${c.id}</td>
      <td><span class="badge ${c.tipo === 'Empresa' ? 'badge-purple' : 'badge-blue'}">${c.tipo}</span></td>
      <td>${c.nombre}</td>
      <td class="muted font-mono" style="font-size:11px">${c.doc}</td>
      <td class="muted font-mono" style="font-size:12px">${c.contacto}</td>
      <td class="muted">${c.reg}</td>
      <td><span class="badge ${estadoBadge[c.estado] || 'badge-muted'}">${c.estado}</span></td>
    </tr>`).join('');
}

function renderHuespedes() {
  document.getElementById('bodyHuespedes').innerHTML = huespedes.map(h => `
    <tr>
      <td class="mono">${h.id}</td>
      <td>${h.nombre}</td>
      <td>${h.apellido}</td>
      <td class="muted font-mono" style="font-size:11px">${h.doc}</td>
      <td class="muted">${h.nac}</td>
      <td class="muted font-mono" style="font-size:11px">${h.fecha}</td>
    </tr>`).join('');
}

function renderPagos() {
  document.getElementById('bodyPagos').innerHTML = pagos.map(p => `
    <tr>
      <td class="mono">${p.id}</td>
      <td class="mono">#${p.res}</td>
      <td class="muted">${p.medio}</td>
      <td class="text-accent font-mono">${p.monto}</td>
      <td class="muted">${p.fecha}</td>
      <td><span class="badge ${estadoBadge[p.estado] || 'badge-muted'}">${p.estado}</span></td>
    </tr>`).join('');
}

// ── FILTERS ────────────────────────────────────────────────
function filterReservas(estado, btn) {
  document.querySelectorAll('#reservaChips .chip').forEach(c => c.classList.remove('active'));
  if (btn) btn.classList.add('active');
  const filtered = estado === 'all' ? reservas : reservas.filter(r => r.estado === estado);
  renderReservas(filtered);
}

function filterRooms(estado, btn) {
  document.querySelectorAll('.chips .chip').forEach(c => c.classList.remove('active'));
  if (btn) btn.classList.add('active');
  const filtered = estado === 'all' ? habitaciones : habitaciones.filter(h => h.estado === estado);
  renderRooms(filtered);
}

function filterClientes(tipo, btn) {
  document.querySelectorAll('.chips .chip').forEach(c => c.classList.remove('active'));
  if (btn) btn.classList.add('active');
  const filtered = tipo === 'all' ? clientes : clientes.filter(c => c.tipo.toLowerCase() === tipo);
  renderClientes(filtered);
}

// ── NAVIGATION ─────────────────────────────────────────────
const titles = {
  dashboard   : 'Dashboard',
  reservas    : 'Reservas',
  habitaciones: 'Habitaciones',
  clientes    : 'Clientes',
  huespedes   : 'Huéspedes',
  pagos       : 'Pagos',
  checkin     : 'Check-in / Check-out',
  reembolsos  : 'Reembolsos',
  empleados   : 'Empleados',
};

function nav(view, el) {
  document.querySelectorAll('.view').forEach(v => v.classList.remove('active'));
  document.querySelectorAll('.nav-item').forEach(n => n.classList.remove('active'));
  document.getElementById('view-' + view).classList.add('active');
  document.getElementById('pageTitle').textContent = titles[view] || view;
  if (el) el.classList.add('active');
}

// ── MODALS ─────────────────────────────────────────────────
function openModal(id)  { document.getElementById(id).classList.add('open'); }
function closeModal(id) { document.getElementById(id).classList.remove('open'); }

document.querySelectorAll('.modal-overlay').forEach(m => {
  m.addEventListener('click', e => { if (e.target === m) m.classList.remove('open'); });
});

// ── TOAST ──────────────────────────────────────────────────
let toastTimer;
function showToast(msg) {
  const t = document.getElementById('toast');
  t.textContent = msg;
  t.classList.add('show');
  clearTimeout(toastTimer);
  toastTimer = setTimeout(() => t.classList.remove('show'), 2800);
}

// ── ACTIONS ────────────────────────────────────────────────
function crearReserva()  { closeModal('modalReserva'); showToast('Reserva creada en estado Pendiente ✓'); }
function doCheckin()     { showToast('Check-in registrado ✓ — Habitación → Ocupada'); }
function doCheckout()    { showToast('Check-out registrado ✓ — Habitación → En Limpieza'); }

// ── INIT ───────────────────────────────────────────────────
renderReservas(reservas);
renderRooms(habitaciones);
renderClientes(clientes);
renderHuespedes();
renderPagos();
