# Hotel Diablos Rojos - Frontend HTML/CSS/JS

Frontend del sistema de gestion hotelera construido con HTML, CSS y JavaScript puro.
Sin frameworks. No requiere compilacion ni instalacion de paquetes.

## Como ejecutar

**Opcion 1 (la mas simple):**
Abrir directamente el archivo `index.html` con doble click.

**Opcion 2 (recomendada):**
Si tienes Visual Studio Code, instala la extension "Live Server".
Despues click derecho sobre `index.html` -> "Open with Live Server".

## Credenciales de prueba

| Usuario  | Contrasena | Rol           |
|----------|------------|---------------|
| admin    | admin      | Administrador |
| empleado | empleado   | Recepcionista |

## Estructura

```
hotel-web/
├── index.html              -> Pagina de login
├── css/
│   ├── estilos.css         -> Estilos generales (colores, botones, tablas)
│   ├── layout.css          -> Sidebar y topbar
│   ├── login.css           -> Pantalla de login
│   └── paginas.css         -> Indicadores y formularios
├── js/
│   ├── datos.js            -> Datos de prueba en memoria
│   ├── auth.js             -> Funciones de inicio de sesion
│   └── comunes.js          -> Helpers compartidos
└── paginas/
    ├── dashboard.html      -> Panel principal con indicadores
    ├── clientes.html       -> CRUD de clientes
    ├── reservas.html       -> Lista y cancelacion de reservas
    ├── habitaciones.html   -> Catalogo de habitaciones
    ├── pagos.html          -> Historial de pagos
    ├── empleados.html      -> Gestion de empleados (solo admin)
    └── reportes.html       -> Reportes administrativos (solo admin)
```

## Notas

- Los datos son mock (en memoria). Al recargar la pagina se reinician.
- La sesion se guarda en localStorage del navegador.
- Si el usuario es Recepcionista, las opciones de Empleados y Reportes
  no aparecen en el menu.
