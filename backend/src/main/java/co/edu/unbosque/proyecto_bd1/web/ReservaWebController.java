package co.edu.unbosque.proyecto_bd1.web;

import co.edu.unbosque.proyecto_bd1.dto.CancelacionDTO;
import co.edu.unbosque.proyecto_bd1.dto.CheckInDTO;
import co.edu.unbosque.proyecto_bd1.dto.CheckOutDTO;
import co.edu.unbosque.proyecto_bd1.dto.EmpresaDTO;
import co.edu.unbosque.proyecto_bd1.dto.PagoDTO;
import co.edu.unbosque.proyecto_bd1.dto.PersonaDTO;
import co.edu.unbosque.proyecto_bd1.dto.ReservaDTO;
import co.edu.unbosque.proyecto_bd1.enums.CanalReserva;
import co.edu.unbosque.proyecto_bd1.enums.EstadoPago;
import co.edu.unbosque.proyecto_bd1.enums.EstadoReserva;
import co.edu.unbosque.proyecto_bd1.enums.MedioPago;
import co.edu.unbosque.proyecto_bd1.service.ClienteService;
import co.edu.unbosque.proyecto_bd1.service.EventoReservaService;
import co.edu.unbosque.proyecto_bd1.service.PagoService;
import co.edu.unbosque.proyecto_bd1.service.ReservaService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.edu.unbosque.proyecto_bd1.dto.HabitacionDTO;
import co.edu.unbosque.proyecto_bd1.dto.ReservaHabitacionDTO;
import co.edu.unbosque.proyecto_bd1.dto.TipoHabitacionDTO;
import co.edu.unbosque.proyecto_bd1.enums.EstadoHabitacion;
import co.edu.unbosque.proyecto_bd1.service.HabitacionService;
import co.edu.unbosque.proyecto_bd1.service.ReservaHabitacionService;
import co.edu.unbosque.proyecto_bd1.service.TipoHabitacionService;
@Controller
@RequestMapping("/reservas")
public class ReservaWebController {

    private final ReservaService reservaService;
    private final ClienteService clienteService;
    private final PagoService pagoService;
    private final EventoReservaService eventoReservaService;
    private final HabitacionService habitacionService;
    private final ReservaHabitacionService reservaHabitacionService;
    private final TipoHabitacionService tipoHabitacionService;

public ReservaWebController(ReservaService reservaService,
                                ClienteService clienteService,
                                PagoService pagoService,
                                EventoReservaService eventoReservaService,
                                HabitacionService habitacionService,
                                ReservaHabitacionService reservaHabitacionService,
                                TipoHabitacionService tipoHabitacionService) {
        this.reservaService = reservaService;
        this.clienteService = clienteService;
        this.pagoService = pagoService;
        this.eventoReservaService = eventoReservaService;
        this.habitacionService = habitacionService;
        this.reservaHabitacionService = reservaHabitacionService;
        this.tipoHabitacionService = tipoHabitacionService;
    }
    private boolean tieneAcceso(UsuarioSesion sesion) {
        return sesion.isAdministrador() || sesion.isRecepcionista();
    }

    /**
     * Construye un map idCliente -> "tipo: identificador - nombre".
     */
    private Map<Integer, String> construirMapClientes() {
        Map<Integer, String> map = new HashMap<>();

        List<PersonaDTO> personas = clienteService.listarPersonas();
        for (int i = 0; i < personas.size(); i++) {
            PersonaDTO p = personas.get(i);
            String etiqueta = "Persona: " + p.getNumeroDocumento()
                + " - " + p.getNombreCompleto();
            map.put(p.getIdCliente(), etiqueta);
        }

        List<EmpresaDTO> empresas = clienteService.listarEmpresas();
        for (int i = 0; i < empresas.size(); i++) {
            EmpresaDTO e = empresas.get(i);
            String etiqueta = "Empresa: " + e.getNit() + " - " + e.getRazonSocial();
            map.put(e.getIdCliente(), etiqueta);
        }
        return map;
    }

    /**
     * Calcula penalizacion automatica segun los supuestos:
     *  - >72h antes del check-in -> 0%
     *  - 24h-72h antes -> 50%
     *  - <24h o ya pasado -> 100%
     */
    private BigDecimal calcularPenalizacionSugerida(LocalDateTime checkInPrevisto,
                                                     LocalDateTime ahora) {
        if (checkInPrevisto == null || ahora == null) {
            return new BigDecimal("100.00");
        }
        long horas = Duration.between(ahora, checkInPrevisto).toHours();
        if (horas > 72) {
            return new BigDecimal("0.00");
        }
        if (horas >= 24) {
            return new BigDecimal("50.00");
        }
        return new BigDecimal("100.00");
    }

    /**
     * Calcula el total pagado en estado Aprobado para una reserva.
     */
    private BigDecimal calcularTotalAprobado(List<PagoDTO> pagos) {
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < pagos.size(); i++) {
            PagoDTO p = pagos.get(i);
            if (p.getEstado() == EstadoPago.Aprobado) {
                total = total.add(p.getMonto());
            }
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    // ===== LISTAR =====
    @GetMapping
    public String listar(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                         @RequestParam(value = "estado", required = false) String estado,
                         @RequestParam(value = "canal", required = false) String canal,
                         Model model,
                         RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/";
        }

        List<ReservaDTO> reservas;
        if (estado != null && !estado.isBlank()) {
            reservas = reservaService.buscarPorEstado(estado);
        } else {
            reservas = reservaService.listarTodos();
        }

        if (canal != null && !canal.isBlank()) {
            List<ReservaDTO> filtradas = new ArrayList<>();
            for (int i = 0; i < reservas.size(); i++) {
                ReservaDTO r = reservas.get(i);
                if (r.getCanal() != null && r.getCanal().name().equals(canal)) {
                    filtradas.add(r);
                }
            }
            reservas = filtradas;
        }

        model.addAttribute("reservas", reservas);
        model.addAttribute("clientesPorId", construirMapClientes());
        model.addAttribute("estados", EstadoReserva.values());
        model.addAttribute("canales", CanalReserva.values());
        model.addAttribute("filtroEstado", estado);
        model.addAttribute("filtroCanal", canal);
        return "reservas/lista";
    }

// ===== FORM CREAR =====
    @GetMapping("/nueva")
    public String formCrear(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                            Model model,
                            RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/reservas";
        }
        ReservaDTO dto = new ReservaDTO();
        dto.setEstado(EstadoReserva.Pendiente);
        dto.setCanal(CanalReserva.Telefonica);

        model.addAttribute("reserva", dto);
        model.addAttribute("personas", clienteService.listarPersonas());
        model.addAttribute("empresas", clienteService.listarEmpresas());
        model.addAttribute("estados", EstadoReserva.values());
        model.addAttribute("canales", CanalReserva.values());
        model.addAttribute("habitacionesDisponibles",
            habitacionService.buscarPorEstado(EstadoHabitacion.Disponible.name()));
        model.addAttribute("tipos", tipoHabitacionService.listarTodos());
        return "reservas/form";
    }
   // ===== POST CREAR (Reserva + asociaciones en una operacion) =====
    @PostMapping
    @Transactional
    public String crear(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                        @ModelAttribute("reserva") ReservaDTO dto,
                        @RequestParam(value = "habitacionId", required = false) List<Integer> habitacionIds,
                        @RequestParam(value = "huespedes", required = false) List<Byte> huespedes,
                        RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/reservas";
        }
        dto.setFechaCreacion(LocalDateTime.now());

        // ====== Validaciones de la lista de habitaciones ======
        if (habitacionIds == null || habitacionIds.isEmpty()) {
            redirect.addFlashAttribute("mensajeError",
                "Debe seleccionar al menos una habitación para la reserva.");
            return "redirect:/reservas/nueva";
        }
        if (huespedes == null || huespedes.size() != habitacionIds.size()) {
            redirect.addFlashAttribute("mensajeError",
                "Los datos de habitaciones y huéspedes no coinciden.");
            return "redirect:/reservas/nueva";
        }
        // Detectar duplicados
        for (int i = 0; i < habitacionIds.size(); i++) {
            for (int j = i + 1; j < habitacionIds.size(); j++) {
                if (habitacionIds.get(i).equals(habitacionIds.get(j))) {
                    redirect.addFlashAttribute("mensajeError",
                        "No podés seleccionar la misma habitación dos veces.");
                    return "redirect:/reservas/nueva";
                }
            }
        }

        // ====== Calcular precio total en el BACKEND ======
        // (No confiamos en lo que mande el JS; recalculamos siempre con BD)
        long horasEstadia = java.time.Duration.between(
            dto.getFechaCheckInPrevista(),
            dto.getFechaCheckOutPrevista()
        ).toHours();
        if (horasEstadia <= 0) {
            redirect.addFlashAttribute("mensajeError",
                "La fecha de check-out debe ser posterior a la de check-in.");
            return "redirect:/reservas/nueva";
        }
        // Numero de noches = redondeo hacia arriba de horas/24, minimo 1
        long noches = horasEstadia / 24;
        if (horasEstadia % 24 != 0) {
            noches = noches + 1;
        }
        if (noches < 1) {
            noches = 1;
        }

        // Cargar habitaciones + tipos para multiplicar
        List<HabitacionDTO> todasHabs = habitacionService.listarTodos();
        Map<Integer, HabitacionDTO> habPorId = new HashMap<>();
        for (int i = 0; i < todasHabs.size(); i++) {
            HabitacionDTO h = todasHabs.get(i);
            habPorId.put(h.getIdHabitacion(), h);
        }
        List<TipoHabitacionDTO> tipos = tipoHabitacionService.listarTodos();
        Map<Integer, TipoHabitacionDTO> tipoPorId = new HashMap<>();
        for (int i = 0; i < tipos.size(); i++) {
            TipoHabitacionDTO t = tipos.get(i);
            tipoPorId.put(t.getIdTipo(), t);
        }

        BigDecimal precioCalculado = BigDecimal.ZERO;
        for (int i = 0; i < habitacionIds.size(); i++) {
            Integer idHab = habitacionIds.get(i);
            HabitacionDTO hab = habPorId.get(idHab);
            if (hab == null) {
                redirect.addFlashAttribute("mensajeError",
                    "La habitación " + idHab + " no existe.");
                return "redirect:/reservas/nueva";
            }
            TipoHabitacionDTO tipo = tipoPorId.get(hab.getIdTipo());
            if (tipo == null) {
                redirect.addFlashAttribute("mensajeError",
                    "El tipo de la habitación " + hab.getCodigo() + " no se encontró.");
                return "redirect:/reservas/nueva";
            }
            // Validar capacidad
            Byte num = huespedes.get(i);
            if (num == null || num < 1) {
                redirect.addFlashAttribute("mensajeError",
                    "El número de huéspedes para " + hab.getCodigo() + " debe ser al menos 1.");
                return "redirect:/reservas/nueva";
            }
            if (num > tipo.getCapacidadMax()) {
                redirect.addFlashAttribute("mensajeError",
                    "La habitación " + hab.getCodigo() + " (" + tipo.getNombre()
                    + ") admite máximo " + tipo.getCapacidadMax() + " huéspedes, recibiste " + num + ".");
                return "redirect:/reservas/nueva";
            }
            BigDecimal subtotal = tipo.getPrecioBaseNoche()
                .multiply(new BigDecimal(noches));
            precioCalculado = precioCalculado.add(subtotal);
        }
        dto.setPrecioTotal(precioCalculado);

        // ====== Crear la reserva y luego cada asociacion ======
        try {
            Integer idGenerado = reservaService.crear(dto);

            for (int i = 0; i < habitacionIds.size(); i++) {
                ReservaHabitacionDTO rh = new ReservaHabitacionDTO();
                rh.setIdReserva(idGenerado);
                rh.setIdHabitacion(habitacionIds.get(i));
                rh.setNumeroHuespedes(huespedes.get(i));
                reservaHabitacionService.crear(rh);
            }

            redirect.addFlashAttribute("mensajeExito",
                "Reserva #" + idGenerado + " creada con "
                + habitacionIds.size() + " habitación(es) por "
                + noches + " noche(s). Precio total: "
                + precioCalculado.toPlainString() + " COP");
            return "redirect:/reservas/" + idGenerado;
        } catch (Exception e) {
            // @Transactional rebobina si lanzamos RuntimeException
            redirect.addFlashAttribute("mensajeError",
                "Error al crear reserva: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
   // ===== DETALLE =====
    @GetMapping("/{id}")
    public String detalle(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                          @PathVariable Integer id,
                          Model model,
                          RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/";
        }
        try {
            ReservaDTO reserva = reservaService.buscarPorId(id);
            List<PagoDTO> pagos = pagoService.buscarPorReserva(id);
            BigDecimal totalAprobado = calcularTotalAprobado(pagos);

            BigDecimal saldoPendiente = BigDecimal.ZERO;
            if (reserva.getPrecioTotal() != null) {
                saldoPendiente = reserva.getPrecioTotal().subtract(totalAprobado);
                if (saldoPendiente.compareTo(BigDecimal.ZERO) < 0) {
                    saldoPendiente = BigDecimal.ZERO;
                }
            }

            // ====== HABITACIONES ASOCIADAS ======
            List<ReservaHabitacionDTO> habitacionesAsociadas =
                reservaHabitacionService.buscarPorReserva(id);

            // Maps para enriquecer la tabla con datos legibles
            List<HabitacionDTO> todasLasHabitaciones = habitacionService.listarTodos();
            Map<Integer, HabitacionDTO> habitacionPorId = new HashMap<>();
            for (int i = 0; i < todasLasHabitaciones.size(); i++) {
                HabitacionDTO h = todasLasHabitaciones.get(i);
                habitacionPorId.put(h.getIdHabitacion(), h);
            }

            List<TipoHabitacionDTO> todosLosTipos = tipoHabitacionService.listarTodos();
            Map<Integer, TipoHabitacionDTO> tipoPorId = new HashMap<>();
            for (int i = 0; i < todosLosTipos.size(); i++) {
                TipoHabitacionDTO t = todosLosTipos.get(i);
                tipoPorId.put(t.getIdTipo(), t);
            }

            // Calcular precio sugerido total segun habitaciones asociadas y noches
            BigDecimal precioSugerido = BigDecimal.ZERO;
            Integer noches = reserva.getNumeroNoches() != null ? reserva.getNumeroNoches() : 0;
            for (int i = 0; i < habitacionesAsociadas.size(); i++) {
                ReservaHabitacionDTO rh = habitacionesAsociadas.get(i);
                HabitacionDTO hab = habitacionPorId.get(rh.getIdHabitacion());
                if (hab != null) {
                    TipoHabitacionDTO tipo = tipoPorId.get(hab.getIdTipo());
                    if (tipo != null && tipo.getPrecioBaseNoche() != null) {
                        BigDecimal subtotal = tipo.getPrecioBaseNoche()
                            .multiply(new BigDecimal(noches));
                        precioSugerido = precioSugerido.add(subtotal);
                    }
                }
            }

            // Listar solo habitaciones Disponibles para el dropdown del modal
            List<HabitacionDTO> habitacionesDisponibles =
                habitacionService.buscarPorEstado(EstadoHabitacion.Disponible.name());

            // Precarga del DTO para el form
            ReservaHabitacionDTO nuevaAsociacion = new ReservaHabitacionDTO();
            nuevaAsociacion.setIdReserva(id);
            nuevaAsociacion.setNumeroHuespedes((byte) 1);

            // Precarga de DTOs y catalogos para el resto del detalle
            PagoDTO nuevoPago = new PagoDTO();
            nuevoPago.setIdReserva(id);
            nuevoPago.setEstado(EstadoPago.Aprobado);
            nuevoPago.setFechaPago(LocalDateTime.now());

            CancelacionDTO nuevaCancelacion = new CancelacionDTO();
            nuevaCancelacion.setIdReserva(id);
            nuevaCancelacion.setIdEmpleado(sesion.getIdEmpleado());
            nuevaCancelacion.setPenalizacion(
                calcularPenalizacionSugerida(reserva.getFechaCheckInPrevista(),
                                              LocalDateTime.now()));

            model.addAttribute("reserva", reserva);
            model.addAttribute("clientesPorId", construirMapClientes());
            model.addAttribute("pagos", pagos);
            model.addAttribute("totalAprobado", totalAprobado);
            model.addAttribute("saldoPendiente", saldoPendiente);
            model.addAttribute("nuevoPago", nuevoPago);
            model.addAttribute("nuevaCancelacion", nuevaCancelacion);
            model.addAttribute("mediosPago", MedioPago.values());
            model.addAttribute("estadosPago", EstadoPago.values());

            // Modelo para habitaciones asociadas
            model.addAttribute("habitacionesAsociadas", habitacionesAsociadas);
            model.addAttribute("habitacionPorId", habitacionPorId);
            model.addAttribute("tipoPorId", tipoPorId);
            model.addAttribute("precioSugerido", precioSugerido);
            model.addAttribute("habitacionesDisponibles", habitacionesDisponibles);
            model.addAttribute("nuevaAsociacion", nuevaAsociacion);

            return "reservas/detalle";
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError", e.getMessage());
            return "redirect:/reservas";
        }
    }
    // ===== CAMBIAR ESTADO (puerta administrativa) =====
    @PostMapping("/{id}/estado")
    public String cambiarEstado(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                                @PathVariable Integer id,
                                @RequestParam("nuevoEstado") EstadoReserva nuevoEstado,
                                RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/reservas";
        }
        try {
            reservaService.cambiarEstado(id, nuevoEstado);
            redirect.addFlashAttribute("mensajeExito",
                "Estado cambiado a " + nuevoEstado.name());
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError", "Error: " + e.getMessage());
        }
        return "redirect:/reservas/" + id;
    }

    // ===== ELIMINAR =====
    @PostMapping("/{id}/eliminar")
    public String eliminar(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                           @PathVariable Integer id,
                           RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/reservas";
        }
        try {
            reservaService.eliminar(id);
            redirect.addFlashAttribute("mensajeExito", "Reserva eliminada");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "No se pudo eliminar (probablemente tiene pagos o eventos asociados)");
        }
        return "redirect:/reservas";
    }

    // ===========================================
    // ============== PAGOS ======================
    // ===========================================

    @PostMapping("/{id}/pagos")
    public String registrarPago(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                                @PathVariable Integer id,
                                @ModelAttribute("nuevoPago") PagoDTO dto,
                                RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/reservas/" + id;
        }
        dto.setIdReserva(id);
        if (dto.getFechaPago() == null) {
            dto.setFechaPago(LocalDateTime.now());
        }
        try {
            pagoService.crear(dto);
            redirect.addFlashAttribute("mensajeExito",
                "Pago registrado correctamente por " + dto.getMonto() + " COP");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error al registrar pago: " + e.getMessage());
        }
        return "redirect:/reservas/" + id;
    }

    // ===========================================
    // ========== CHECK-IN =======================
    // ===========================================

    @PostMapping("/{id}/check-in")
    public String checkIn(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                          @PathVariable Integer id,
                          RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/reservas/" + id;
        }
        try {
            ReservaDTO reserva = reservaService.buscarPorId(id);

            // Validacion segun supuestos: solo se hace check-in si esta Confirmada
            if (reserva.getEstado() != EstadoReserva.Confirmada) {
                redirect.addFlashAttribute("mensajeError",
                    "Solo se puede hacer Check-In a una reserva en estado Confirmada. "
                    + "Estado actual: " + reserva.getEstado().name());
                return "redirect:/reservas/" + id;
            }

            // 1. Crear evento Check-In
            CheckInDTO dto = new CheckInDTO();
            dto.setIdReserva(id);
            dto.setIdEmpleado(sesion.getIdEmpleado());
            dto.setFechaHora(LocalDateTime.now());
            eventoReservaService.crearCheckIn(dto);

            // 2. Cambiar estado de la reserva a En_Curso
            reservaService.cambiarEstado(id, EstadoReserva.En_Curso);

            redirect.addFlashAttribute("mensajeExito",
                "Check-In registrado. La reserva ahora está En Curso.");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error en Check-In: " + e.getMessage());
        }
        return "redirect:/reservas/" + id;
    }

    // ===========================================
    // ========== CHECK-OUT ======================
    // ===========================================

    @PostMapping("/{id}/check-out")
    public String checkOut(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                           @PathVariable Integer id,
                           RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/reservas/" + id;
        }
        try {
            ReservaDTO reserva = reservaService.buscarPorId(id);

            if (reserva.getEstado() != EstadoReserva.En_Curso) {
                redirect.addFlashAttribute("mensajeError",
                    "Solo se puede hacer Check-Out a una reserva En Curso. "
                    + "Estado actual: " + reserva.getEstado().name());
                return "redirect:/reservas/" + id;
            }

            CheckOutDTO dto = new CheckOutDTO();
            dto.setIdReserva(id);
            dto.setIdEmpleado(sesion.getIdEmpleado());
            dto.setFechaHora(LocalDateTime.now());
            eventoReservaService.crearCheckOut(dto);

            reservaService.cambiarEstado(id, EstadoReserva.Finalizada);

            redirect.addFlashAttribute("mensajeExito",
                "Check-Out registrado. La reserva está Finalizada.");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error en Check-Out: " + e.getMessage());
        }
        return "redirect:/reservas/" + id;
    }

    // ===========================================
    // ========== CANCELACION ====================
    // ===========================================

    @PostMapping("/{id}/cancelar")
    public String cancelar(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                           @PathVariable Integer id,
                           @ModelAttribute("nuevaCancelacion") CancelacionDTO dto,
                           RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/reservas/" + id;
        }
        try {
            ReservaDTO reserva = reservaService.buscarPorId(id);

            // Segun supuestos: solo Pendiente o Confirmada se pueden cancelar
            if (reserva.getEstado() != EstadoReserva.Pendiente
                && reserva.getEstado() != EstadoReserva.Confirmada) {
                redirect.addFlashAttribute("mensajeError",
                    "Solo se puede cancelar una reserva Pendiente o Confirmada. "
                    + "Estado actual: " + reserva.getEstado().name());
                return "redirect:/reservas/" + id;
            }

            dto.setIdReserva(id);
            dto.setIdEmpleado(sesion.getIdEmpleado());
            dto.setFechaHora(LocalDateTime.now());

            if (dto.getMotivo() == null || dto.getMotivo().isBlank()) {
                redirect.addFlashAttribute("mensajeError",
                    "El motivo de la cancelación es obligatorio");
                return "redirect:/reservas/" + id;
            }
            if (dto.getPenalizacion() == null) {
                dto.setPenalizacion(
                    calcularPenalizacionSugerida(reserva.getFechaCheckInPrevista(),
                                                  LocalDateTime.now()));
            }

            // 1. Crear evento Cancelacion
            eventoReservaService.crearCancelacion(dto);

            // 2. Cambiar estado de la reserva a Cancelada
            reservaService.cambiarEstado(id, EstadoReserva.Cancelada);

            redirect.addFlashAttribute("mensajeExito",
                "Reserva cancelada con penalización del " + dto.getPenalizacion() + "%");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error al cancelar: " + e.getMessage());
        }
        return "redirect:/reservas/" + id;
    }
// ===========================================
    // ========== HABITACIONES ASOCIADAS =========
    // ===========================================

    @PostMapping("/{id}/habitaciones")
    public String asociarHabitacion(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                                    @PathVariable Integer id,
                                    @ModelAttribute("nuevaAsociacion") ReservaHabitacionDTO dto,
                                    RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/reservas/" + id;
        }
        dto.setIdReserva(id);
        try {
            reservaHabitacionService.crear(dto);
            redirect.addFlashAttribute("mensajeExito",
                "Habitación asociada correctamente con " + dto.getNumeroHuespedes() + " huésped(es)");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error al asociar habitación: " + e.getMessage());
        }
        return "redirect:/reservas/" + id;
    }

    @PostMapping("/{id}/habitaciones/{idHabitacion}/quitar")
    public String quitarHabitacion(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                                    @PathVariable Integer id,
                                    @PathVariable Integer idHabitacion,
                                    RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/reservas/" + id;
        }
        try {
            reservaHabitacionService.eliminar(id, idHabitacion);
            redirect.addFlashAttribute("mensajeExito",
                "Habitación desvinculada de la reserva");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error al quitar habitación: " + e.getMessage());
        }
        return "redirect:/reservas/" + id;
    }

}