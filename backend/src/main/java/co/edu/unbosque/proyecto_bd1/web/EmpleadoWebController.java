package co.edu.unbosque.proyecto_bd1.web;

import co.edu.unbosque.proyecto_bd1.dto.EmpleadoDTO;
import co.edu.unbosque.proyecto_bd1.dto.RolDTO;
import co.edu.unbosque.proyecto_bd1.enums.EstadoActivo;
import co.edu.unbosque.proyecto_bd1.service.EmpleadoService;
import co.edu.unbosque.proyecto_bd1.service.RolService;
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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/empleados")
public class EmpleadoWebController {

    private final EmpleadoService empleadoService;
    private final RolService rolService;

    public EmpleadoWebController(EmpleadoService empleadoService,
                                 RolService rolService) {
        this.empleadoService = empleadoService;
        this.rolService = rolService;
    }

    // ===== LISTAR =====
    @GetMapping
    public String listar(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                         Model model,
                         RedirectAttributes redirect) {
        if (!sesion.isAdministrador()) {
            redirect.addFlashAttribute("mensajeError",
                "Solo el Administrador puede acceder al módulo de empleados");
            return "redirect:/";
        }

        List<EmpleadoDTO> empleados = empleadoService.listarTodos();

        // Map idRol -> nombre del rol, para mostrar en la tabla
        List<RolDTO> roles = rolService.listarTodos();
        Map<Integer, String> rolesPorId = new HashMap<>();
        for (int i = 0; i < roles.size(); i++) {
            RolDTO r = roles.get(i);
            rolesPorId.put(r.getIdRol(), r.getNombre().name());
        }

        // Map idEmpleado -> usuario (para mostrar el supervisor por su nombre/usuario)
        Map<Integer, String> empleadosPorId = new HashMap<>();
        for (int i = 0; i < empleados.size(); i++) {
            EmpleadoDTO e = empleados.get(i);
            empleadosPorId.put(e.getIdEmpleado(),
                e.getUsuario() + " (" + e.getNombreCompleto() + ")");
        }

        model.addAttribute("empleados", empleados);
        model.addAttribute("rolesPorId", rolesPorId);
        model.addAttribute("empleadosPorId", empleadosPorId);
        return "empleados/lista";
    }

    // ===== FORM CREAR =====
    @GetMapping("/nuevo")
    public String formCrear(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                            Model model,
                            RedirectAttributes redirect) {
        if (!sesion.isAdministrador()) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/empleados";
        }
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setEstado(EstadoActivo.Activo);

        model.addAttribute("empleado", dto);
        model.addAttribute("roles", rolService.listarTodos());
        model.addAttribute("estados", EstadoActivo.values());
        // Todos los empleados pueden ser supervisor al crear uno nuevo
        model.addAttribute("posiblesSupervisores", empleadoService.listarTodos());
        model.addAttribute("modo", "crear");
        return "empleados/form";
    }

    // ===== POST CREAR =====
    @PostMapping
    public String crear(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                        @Valid @ModelAttribute("empleado") EmpleadoDTO dto,
                        BindingResult result,
                        Model model,
                        RedirectAttributes redirect) {
        if (!sesion.isAdministrador()) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/empleados";
        }
        if (result.hasErrors()) {
            model.addAttribute("roles", rolService.listarTodos());
            model.addAttribute("estados", EstadoActivo.values());
            model.addAttribute("posiblesSupervisores", empleadoService.listarTodos());
            model.addAttribute("modo", "crear");
            return "empleados/form";
        }
        try {
            empleadoService.crear(dto);
            redirect.addFlashAttribute("mensajeExito",
                "Empleado " + dto.getUsuario() + " creado correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error al crear: " + e.getMessage());
        }
        return "redirect:/empleados";
    }

    // ===== FORM EDITAR =====
    @GetMapping("/{id}/editar")
    public String formEditar(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                             @PathVariable Integer id,
                             Model model,
                             RedirectAttributes redirect) {
        if (!sesion.isAdministrador()) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/empleados";
        }
        try {
            EmpleadoDTO dto = empleadoService.buscarPorId(id);

            // Filtrar al propio empleado de la lista de posibles supervisores
            List<EmpleadoDTO> todos = empleadoService.listarTodos();
            List<EmpleadoDTO> supervisores = new ArrayList<>();
            for (int i = 0; i < todos.size(); i++) {
                EmpleadoDTO e = todos.get(i);
                if (!e.getIdEmpleado().equals(id)) {
                    supervisores.add(e);
                }
            }

            model.addAttribute("empleado", dto);
            model.addAttribute("roles", rolService.listarTodos());
            model.addAttribute("estados", EstadoActivo.values());
            model.addAttribute("posiblesSupervisores", supervisores);
            model.addAttribute("modo", "editar");
            return "empleados/form";
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError", e.getMessage());
            return "redirect:/empleados";
        }
    }

    // ===== POST EDITAR =====
    @PostMapping("/{id}/actualizar")
    public String actualizar(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                             @PathVariable Integer id,
                             @Valid @ModelAttribute("empleado") EmpleadoDTO dto,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirect) {
        if (!sesion.isAdministrador()) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/empleados";
        }
        if (result.hasErrors()) {
            List<EmpleadoDTO> todos = empleadoService.listarTodos();
            List<EmpleadoDTO> supervisores = new ArrayList<>();
            for (int i = 0; i < todos.size(); i++) {
                EmpleadoDTO e = todos.get(i);
                if (!e.getIdEmpleado().equals(id)) {
                    supervisores.add(e);
                }
            }
            model.addAttribute("roles", rolService.listarTodos());
            model.addAttribute("estados", EstadoActivo.values());
            model.addAttribute("posiblesSupervisores", supervisores);
            model.addAttribute("modo", "editar");
            return "empleados/form";
        }
        try {
            empleadoService.actualizar(id, dto);
            redirect.addFlashAttribute("mensajeExito", "Empleado actualizado correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error al actualizar: " + e.getMessage());
        }
        return "redirect:/empleados";
    }

    // ===== ELIMINAR =====
    @PostMapping("/{id}/eliminar")
    public String eliminar(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                           @PathVariable Integer id,
                           RedirectAttributes redirect) {
        if (!sesion.isAdministrador()) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/empleados";
        }

        // No permitir que el admin se elimine a sí mismo
        if (sesion.getIdEmpleado().equals(id)) {
            redirect.addFlashAttribute("mensajeError",
                "No puede eliminar su propia cuenta mientras está en sesión");
            return "redirect:/empleados";
        }

        try {
            empleadoService.eliminar(id);
            redirect.addFlashAttribute("mensajeExito", "Empleado eliminado correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "No se pudo eliminar el empleado (probablemente supervisa a otros o tiene eventos registrados)");
        }
        return "redirect:/empleados";
    }
}