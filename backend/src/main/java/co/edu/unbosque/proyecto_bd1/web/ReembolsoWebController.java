package co.edu.unbosque.proyecto_bd1.web;

import co.edu.unbosque.proyecto_bd1.dto.SolicitudReembolsoDTO;
import co.edu.unbosque.proyecto_bd1.enums.EstadoSolicitudReembolso;
import co.edu.unbosque.proyecto_bd1.enums.MedioPago;
import co.edu.unbosque.proyecto_bd1.service.SolicitudReembolsoService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reembolsos")
public class ReembolsoWebController {

    private final SolicitudReembolsoService solicitudReembolsoService;

    public ReembolsoWebController(SolicitudReembolsoService solicitudReembolsoService) {
        this.solicitudReembolsoService = solicitudReembolsoService;
    }

    // ===== LISTAR con filtro por estado =====
    @GetMapping
    public String listar(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                         @RequestParam(value = "estado", required = false) String estado,
                         Model model,
                         RedirectAttributes redirect) {
        if (!sesion.isAdministrador()) {
            redirect.addFlashAttribute("mensajeError",
                "Solo el Administrador puede acceder al módulo de reembolsos");
            return "redirect:/";
        }

        List<SolicitudReembolsoDTO> solicitudes;
        if (estado != null && !estado.isBlank()) {
            solicitudes = solicitudReembolsoService.buscarPorEstado(estado);
        } else {
            solicitudes = solicitudReembolsoService.listarTodos();
        }

        model.addAttribute("solicitudes", solicitudes);
        model.addAttribute("estados", EstadoSolicitudReembolso.values());
        model.addAttribute("medios", MedioPago.values());
        model.addAttribute("filtroEstado", estado);
        return "reembolsos/lista";
    }

    // ===== FORM CREAR =====
    @GetMapping("/nueva")
    public String formCrear(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                            Model model,
                            RedirectAttributes redirect) {
        if (!sesion.isAdministrador()) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/reembolsos";
        }

        SolicitudReembolsoDTO dto = new SolicitudReembolsoDTO();
        dto.setEstado(EstadoSolicitudReembolso.Pendiente);
        dto.setFecha(LocalDateTime.now());

        model.addAttribute("solicitud", dto);
        model.addAttribute("estados", EstadoSolicitudReembolso.values());
        model.addAttribute("medios", MedioPago.values());
        return "reembolsos/form";
    }

    // ===== POST CREAR =====
    @PostMapping
    public String crear(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                        @ModelAttribute("solicitud") SolicitudReembolsoDTO dto,
                        RedirectAttributes redirect) {
        if (!sesion.isAdministrador()) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/reembolsos";
        }

        // Defaults
        if (dto.getFecha() == null) {
            dto.setFecha(LocalDateTime.now());
        }
        if (dto.getEstado() == null) {
            dto.setEstado(EstadoSolicitudReembolso.Pendiente);
        }

        try {
            Integer idGenerado = solicitudReembolsoService.crear(dto);
            redirect.addFlashAttribute("mensajeExito",
                "Solicitud de reembolso #" + idGenerado + " creada");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error al crear solicitud: " + e.getMessage());
        }
        return "redirect:/reembolsos";
    }

    // ===== APROBAR (Pendiente -> Aprobado, asigna al admin actual) =====
    @PostMapping("/{id}/aprobar")
    public String aprobar(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                          @PathVariable Integer id,
                          RedirectAttributes redirect) {
        if (!sesion.isAdministrador()) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/reembolsos";
        }
        try {
            SolicitudReembolsoDTO solicitud = solicitudReembolsoService.buscarPorId(id);
            if (solicitud.getEstado() != EstadoSolicitudReembolso.Pendiente) {
                redirect.addFlashAttribute("mensajeError",
                    "Solo se pueden aprobar solicitudes en estado Pendiente. Estado actual: "
                    + solicitud.getEstado().name());
                return "redirect:/reembolsos";
            }

            // Asignar al admin actual si no tenia empleado asignado
            if (solicitud.getIdEmpleado() == null) {
                solicitudReembolsoService.asignarEmpleado(id, sesion.getIdEmpleado());
            }
            solicitudReembolsoService.procesar(id, EstadoSolicitudReembolso.Aprobado);
            redirect.addFlashAttribute("mensajeExito",
                "Solicitud #" + id + " aprobada");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error al aprobar: " + e.getMessage());
        }
        return "redirect:/reembolsos";
    }

    // ===== PROCESAR (Aprobado -> Procesado) =====
    @PostMapping("/{id}/procesar")
    public String procesar(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                           @PathVariable Integer id,
                           RedirectAttributes redirect) {
        if (!sesion.isAdministrador()) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/reembolsos";
        }
        try {
            SolicitudReembolsoDTO solicitud = solicitudReembolsoService.buscarPorId(id);
            if (solicitud.getEstado() != EstadoSolicitudReembolso.Aprobado) {
                redirect.addFlashAttribute("mensajeError",
                    "Solo se pueden procesar solicitudes Aprobadas. Estado actual: "
                    + solicitud.getEstado().name());
                return "redirect:/reembolsos";
            }
            solicitudReembolsoService.procesar(id, EstadoSolicitudReembolso.Procesado);
            redirect.addFlashAttribute("mensajeExito",
                "Solicitud #" + id + " procesada (reembolso ejecutado)");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error al procesar: " + e.getMessage());
        }
        return "redirect:/reembolsos";
    }

    // ===== RECHAZAR (Pendiente -> Rechazado) =====
    @PostMapping("/{id}/rechazar")
    public String rechazar(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                           @PathVariable Integer id,
                           RedirectAttributes redirect) {
        if (!sesion.isAdministrador()) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/reembolsos";
        }
        try {
            SolicitudReembolsoDTO solicitud = solicitudReembolsoService.buscarPorId(id);
            if (solicitud.getEstado() == EstadoSolicitudReembolso.Procesado) {
                redirect.addFlashAttribute("mensajeError",
                    "No se puede rechazar una solicitud ya procesada");
                return "redirect:/reembolsos";
            }

            // Asignar al admin actual si no tenia empleado asignado
            if (solicitud.getIdEmpleado() == null) {
                solicitudReembolsoService.asignarEmpleado(id, sesion.getIdEmpleado());
            }
            solicitudReembolsoService.procesar(id, EstadoSolicitudReembolso.Rechazado);
            redirect.addFlashAttribute("mensajeExito",
                "Solicitud #" + id + " rechazada");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error al rechazar: " + e.getMessage());
        }
        return "redirect:/reembolsos";
    }

    // ===== ELIMINAR =====
    @PostMapping("/{id}/eliminar")
    public String eliminar(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                           @PathVariable Integer id,
                           RedirectAttributes redirect) {
        if (!sesion.isAdministrador()) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/reembolsos";
        }
        try {
            solicitudReembolsoService.eliminar(id);
            redirect.addFlashAttribute("mensajeExito", "Solicitud eliminada");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/reembolsos";
    }
}