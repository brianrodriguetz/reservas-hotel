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

@Controller
@RequestMapping("/reservas")
public class ReservaWebController {

    private final ReservaService reservaService;
    private final ClienteService clienteService;
    private final PagoService pagoService;
    private final EventoReservaService eventoReservaService;

    public ReservaWebController(ReservaService reservaService,
                                ClienteService clienteService,
                                PagoService pagoService,
                                EventoReservaService eventoReservaService) {
        this.reservaService = reservaService;
        this.clienteService = clienteService;
        this.pagoService = pagoService;
        this.eventoReservaService = eventoReservaService;
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
        return "reservas/form";
    }

    // ===== POST CREAR =====
    @PostMapping
    public String crear(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                        @ModelAttribute("reserva") ReservaDTO dto,
                        BindingResult result,
                        Model model,
                        RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/reservas";
        }
        dto.setFechaCreacion(LocalDateTime.now());

        if (result.hasErrors()) {
            model.addAttribute("personas", clienteService.listarPersonas());
            model.addAttribute("empresas", clienteService.listarEmpresas());
            model.addAttribute("estados", EstadoReserva.values());
            model.addAttribute("canales", CanalReserva.values());
            model.addAttribute("mensajeError",
                "Hay errores en el formulario. Revisá los campos marcados.");
            return "reservas/form";
        }
        try {
            Integer idGenerado = reservaService.crear(dto);
            redirect.addFlashAttribute("mensajeExito",
                "Reserva #" + idGenerado + " creada correctamente");
            return "redirect:/reservas/" + idGenerado;
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error al crear reserva: " + e.getMessage());
            return "redirect:/reservas/nueva";
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

            // PagoDTO precargado para el form de "Registrar pago"
            PagoDTO nuevoPago = new PagoDTO();
            nuevoPago.setIdReserva(id);
            nuevoPago.setEstado(EstadoPago.Aprobado);
            nuevoPago.setFechaPago(LocalDateTime.now());

            // Cancelacion precargada con penalizacion sugerida
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
}