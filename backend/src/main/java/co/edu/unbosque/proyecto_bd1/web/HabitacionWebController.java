package co.edu.unbosque.proyecto_bd1.web;

import co.edu.unbosque.proyecto_bd1.dto.HabitacionDTO;
import co.edu.unbosque.proyecto_bd1.dto.TipoHabitacionDTO;
import co.edu.unbosque.proyecto_bd1.enums.EstadoHabitacion;
import co.edu.unbosque.proyecto_bd1.service.HabitacionService;
import co.edu.unbosque.proyecto_bd1.service.TipoHabitacionService;
import jakarta.validation.Valid;
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
@RequestMapping("/habitaciones")
public class HabitacionWebController {

    private final HabitacionService habitacionService;
    private final TipoHabitacionService tipoHabitacionService;

    public HabitacionWebController(HabitacionService habitacionService,
                                   TipoHabitacionService tipoHabitacionService) {
        this.habitacionService = habitacionService;
        this.tipoHabitacionService = tipoHabitacionService;
    }

    // ===== LISTAR con filtros opcionales =====
    @GetMapping
    public String listar(@RequestParam(value = "estado", required = false) String estado,
                         @RequestParam(value = "idTipo", required = false) Integer idTipo,
                         Model model) {
        List<HabitacionDTO> habitaciones = habitacionService.listarTodos();

        // Filtrar por estado (en memoria, sin streams)
        if (estado != null && !estado.isBlank()) {
            List<HabitacionDTO> filtradas = new ArrayList<>();
            for (int i = 0; i < habitaciones.size(); i++) {
                HabitacionDTO h = habitaciones.get(i);
                if (h.getEstado() != null && h.getEstado().name().equals(estado)) {
                    filtradas.add(h);
                }
            }
            habitaciones = filtradas;
        }

        // Filtrar por tipo
        if (idTipo != null) {
            List<HabitacionDTO> filtradas = new ArrayList<>();
            for (int i = 0; i < habitaciones.size(); i++) {
                HabitacionDTO h = habitaciones.get(i);
                if (idTipo.equals(h.getIdTipo())) {
                    filtradas.add(h);
                }
            }
            habitaciones = filtradas;
        }

        // Cargar tipos y armar map idTipo -> nombre, para mostrar en la tabla
        List<TipoHabitacionDTO> tipos = tipoHabitacionService.listarTodos();
        Map<Integer, String> tiposPorId = new HashMap<>();
        for (int i = 0; i < tipos.size(); i++) {
            TipoHabitacionDTO t = tipos.get(i);
            tiposPorId.put(t.getIdTipo(), t.getNombre().name());
        }

        model.addAttribute("habitaciones", habitaciones);
        model.addAttribute("tipos", tipos);
        model.addAttribute("tiposPorId", tiposPorId);
        model.addAttribute("estados", EstadoHabitacion.values());
        model.addAttribute("filtroEstado", estado);
        model.addAttribute("filtroIdTipo", idTipo);
        return "habitaciones/lista";
    }

    // ===== FORM CREAR =====
    @GetMapping("/nueva")
    public String formCrear(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                            Model model,
                            RedirectAttributes redirect) {
        if (!sesion.isAdministrador()) {
            redirect.addFlashAttribute("mensajeError",
                "Solo el Administrador puede crear habitaciones");
            return "redirect:/habitaciones";
        }
        model.addAttribute("habitacion", new HabitacionDTO());
        model.addAttribute("tipos", tipoHabitacionService.listarTodos());
        model.addAttribute("estados", EstadoHabitacion.values());
        model.addAttribute("modo", "crear");
        return "habitaciones/form";
    }

    // ===== POST CREAR =====
    @PostMapping
    public String crear(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                        @Valid @ModelAttribute("habitacion") HabitacionDTO dto,
                        BindingResult result,
                        Model model,
                        RedirectAttributes redirect) {
        if (!sesion.isAdministrador()) {
            redirect.addFlashAttribute("mensajeError",
                "Solo el Administrador puede crear habitaciones");
            return "redirect:/habitaciones";
        }
        if (result.hasErrors()) {
            model.addAttribute("tipos", tipoHabitacionService.listarTodos());
            model.addAttribute("estados", EstadoHabitacion.values());
            model.addAttribute("modo", "crear");
            return "habitaciones/form";
        }
        try {
            habitacionService.crear(dto);
            redirect.addFlashAttribute("mensajeExito",
                "Habitación " + dto.getCodigo() + " creada correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error al crear: " + e.getMessage());
        }
        return "redirect:/habitaciones";
    }

    // ===== FORM EDITAR =====
    @GetMapping("/{id}/editar")
    public String formEditar(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                             @PathVariable Integer id,
                             Model model,
                             RedirectAttributes redirect) {
        if (!sesion.isAdministrador()) {
            redirect.addFlashAttribute("mensajeError",
                "Solo el Administrador puede editar habitaciones");
            return "redirect:/habitaciones";
        }
        try {
            HabitacionDTO dto = habitacionService.buscarPorId(id);
            model.addAttribute("habitacion", dto);
            model.addAttribute("tipos", tipoHabitacionService.listarTodos());
            model.addAttribute("estados", EstadoHabitacion.values());
            model.addAttribute("modo", "editar");
            return "habitaciones/form";
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError", e.getMessage());
            return "redirect:/habitaciones";
        }
    }

    // ===== POST EDITAR =====
    @PostMapping("/{id}/actualizar")
    public String actualizar(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                             @PathVariable Integer id,
                             @Valid @ModelAttribute("habitacion") HabitacionDTO dto,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirect) {
        if (!sesion.isAdministrador()) {
            redirect.addFlashAttribute("mensajeError",
                "Solo el Administrador puede editar habitaciones");
            return "redirect:/habitaciones";
        }
        if (result.hasErrors()) {
            model.addAttribute("tipos", tipoHabitacionService.listarTodos());
            model.addAttribute("estados", EstadoHabitacion.values());
            model.addAttribute("modo", "editar");
            return "habitaciones/form";
        }
        try {
            habitacionService.actualizar(id, dto);
            redirect.addFlashAttribute("mensajeExito", "Habitación actualizada");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error al actualizar: " + e.getMessage());
        }
        return "redirect:/habitaciones";
    }

    // ===== ELIMINAR =====
    @PostMapping("/{id}/eliminar")
    public String eliminar(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                           @PathVariable Integer id,
                           RedirectAttributes redirect) {
        if (!sesion.isAdministrador()) {
            redirect.addFlashAttribute("mensajeError",
                "Solo el Administrador puede eliminar habitaciones");
            return "redirect:/habitaciones";
        }
        try {
            habitacionService.eliminar(id);
            redirect.addFlashAttribute("mensajeExito", "Habitación eliminada");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "No se pudo eliminar la habitación (probablemente tiene reservas asociadas)");
        }
        return "redirect:/habitaciones";
    }
}