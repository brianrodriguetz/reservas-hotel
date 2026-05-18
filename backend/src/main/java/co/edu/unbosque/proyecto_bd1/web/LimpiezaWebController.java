package co.edu.unbosque.proyecto_bd1.web;

import co.edu.unbosque.proyecto_bd1.dto.HabitacionDTO;
import co.edu.unbosque.proyecto_bd1.dto.TipoHabitacionDTO;
import co.edu.unbosque.proyecto_bd1.enums.EstadoHabitacion;
import co.edu.unbosque.proyecto_bd1.service.HabitacionService;
import co.edu.unbosque.proyecto_bd1.service.TipoHabitacionService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/limpieza")
public class LimpiezaWebController {

    private final HabitacionService habitacionService;
    private final TipoHabitacionService tipoHabitacionService;

    public LimpiezaWebController(HabitacionService habitacionService,
                                 TipoHabitacionService tipoHabitacionService) {
        this.habitacionService = habitacionService;
        this.tipoHabitacionService = tipoHabitacionService;
    }

    // ===== LISTAR habitaciones en limpieza =====
    @GetMapping
    public String listar(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                         Model model,
                         RedirectAttributes redirect) {
        // Solo Administrador y Personal_Limpieza
        if (!sesion.isAdministrador() && !sesion.isPersonalLimpieza()) {
            redirect.addFlashAttribute("mensajeError",
                "No tiene permisos para acceder al módulo de limpieza");
            return "redirect:/";
        }

        // Buscar todas las habitaciones En_Limpieza
        List<HabitacionDTO> habitaciones =
            habitacionService.buscarPorEstado(EstadoHabitacion.En_Limpieza.name());

        // Map de tipos para mostrar el nombre del tipo en la tabla
        List<TipoHabitacionDTO> tipos = tipoHabitacionService.listarTodos();
        Map<Integer, String> tiposPorId = new HashMap<>();
        for (int i = 0; i < tipos.size(); i++) {
            TipoHabitacionDTO t = tipos.get(i);
            tiposPorId.put(t.getIdTipo(), t.getNombre().name());
        }

        model.addAttribute("habitaciones", habitaciones);
        model.addAttribute("tiposPorId", tiposPorId);
        return "limpieza/lista";
    }

    // ===== FINALIZAR limpieza: En_Limpieza -> Disponible =====
    @PostMapping("/{id}/finalizar")
    public String finalizar(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                            @PathVariable Integer id,
                            RedirectAttributes redirect) {
        if (!sesion.isAdministrador() && !sesion.isPersonalLimpieza()) {
            redirect.addFlashAttribute("mensajeError",
                "No tiene permisos para finalizar limpieza");
            return "redirect:/";
        }

        try {
            // Validar que la habitacion realmente este En_Limpieza antes de cambiar
            HabitacionDTO habitacion = habitacionService.buscarPorId(id);
            if (habitacion.getEstado() != EstadoHabitacion.En_Limpieza) {
                redirect.addFlashAttribute("mensajeError",
                    "La habitación " + habitacion.getCodigo()
                    + " no está en estado En Limpieza (estado actual: "
                    + habitacion.getEstado().name() + ")");
                return "redirect:/limpieza";
            }

            habitacionService.cambiarEstado(id, EstadoHabitacion.Disponible);
            redirect.addFlashAttribute("mensajeExito",
                "Limpieza finalizada. Habitación " + habitacion.getCodigo()
                + " marcada como Disponible.");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error al finalizar limpieza: " + e.getMessage());
        }
        return "redirect:/limpieza";
    }
}