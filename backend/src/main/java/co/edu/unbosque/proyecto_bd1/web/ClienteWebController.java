package co.edu.unbosque.proyecto_bd1.web;

import co.edu.unbosque.proyecto_bd1.dto.EmpresaDTO;
import co.edu.unbosque.proyecto_bd1.dto.PersonaDTO;
import co.edu.unbosque.proyecto_bd1.enums.EstadoActivo;
import co.edu.unbosque.proyecto_bd1.enums.TipoDocumento;
import co.edu.unbosque.proyecto_bd1.service.ClienteService;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping("/clientes")
public class ClienteWebController {

    private final ClienteService clienteService;

    public ClienteWebController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // ===== Helper: validar acceso =====
    private boolean tieneAcceso(UsuarioSesion sesion) {
        return sesion.isAdministrador() || sesion.isRecepcionista();
    }

    // ===== LISTAR (Personas y Empresas en tabs) =====
    @GetMapping
    public String listar(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                         @RequestParam(value = "tab", required = false, defaultValue = "personas") String tab,
                         Model model,
                         RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "No tiene permisos para gestionar clientes");
            return "redirect:/";
        }

        List<PersonaDTO> personas = clienteService.listarPersonas();
        List<EmpresaDTO> empresas = clienteService.listarEmpresas();

        model.addAttribute("personas", personas);
        model.addAttribute("empresas", empresas);
        model.addAttribute("tabActivo", tab);
        return "clientes/lista";
    }

    // ===========================================
    // ============== PERSONAS ===================
    // ===========================================

    // ===== FORM CREAR PERSONA =====
    @GetMapping("/personas/nueva")
    public String formCrearPersona(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                                   Model model,
                                   RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/clientes";
        }
        PersonaDTO dto = new PersonaDTO();
        dto.setEstado(EstadoActivo.Activo); // valor por defecto
        model.addAttribute("persona", dto);
        model.addAttribute("tiposDocumento", TipoDocumento.values());
        model.addAttribute("estados", EstadoActivo.values());
        model.addAttribute("modo", "crear");
        return "clientes/form-persona";
    }

    // ===== POST CREAR PERSONA =====
    @PostMapping("/personas")
    public String crearPersona(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                               @Valid @ModelAttribute("persona") PersonaDTO dto,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/clientes";
        }
        if (result.hasErrors()) {
            model.addAttribute("tiposDocumento", TipoDocumento.values());
            model.addAttribute("estados", EstadoActivo.values());
            model.addAttribute("modo", "crear");
            return "clientes/form-persona";
        }
        try {
            Integer idGenerado = clienteService.crearPersona(dto);
            redirect.addFlashAttribute("mensajeExito",
                "Persona registrada correctamente (ID: " + idGenerado + ")");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error al crear persona: " + e.getMessage());
        }
        return "redirect:/clientes?tab=personas";
    }

    // ===== FORM EDITAR PERSONA =====
    @GetMapping("/personas/{id}/editar")
    public String formEditarPersona(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                                    @PathVariable Integer id,
                                    Model model,
                                    RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/clientes";
        }
        try {
            PersonaDTO dto = clienteService.buscarPersonaPorId(id);
            model.addAttribute("persona", dto);
            model.addAttribute("tiposDocumento", TipoDocumento.values());
            model.addAttribute("estados", EstadoActivo.values());
            model.addAttribute("modo", "editar");
            return "clientes/form-persona";
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError", e.getMessage());
            return "redirect:/clientes?tab=personas";
        }
    }

    // ===== POST EDITAR PERSONA =====
    @PostMapping("/personas/{id}/actualizar")
    public String actualizarPersona(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                                    @PathVariable Integer id,
                                    @Valid @ModelAttribute("persona") PersonaDTO dto,
                                    BindingResult result,
                                    Model model,
                                    RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/clientes";
        }
        if (result.hasErrors()) {
            model.addAttribute("tiposDocumento", TipoDocumento.values());
            model.addAttribute("estados", EstadoActivo.values());
            model.addAttribute("modo", "editar");
            return "clientes/form-persona";
        }
        try {
            clienteService.actualizarPersona(id, dto);
            redirect.addFlashAttribute("mensajeExito", "Persona actualizada correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error al actualizar: " + e.getMessage());
        }
        return "redirect:/clientes?tab=personas";
    }

    // ===== DETALLE PERSONA =====
    @GetMapping("/personas/{id}")
    public String detallePersona(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                                 @PathVariable Integer id,
                                 Model model,
                                 RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/clientes";
        }
        try {
            PersonaDTO dto = clienteService.buscarPersonaPorId(id);
            model.addAttribute("persona", dto);
            return "clientes/detalle-persona";
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError", e.getMessage());
            return "redirect:/clientes?tab=personas";
        }
    }

    // ===========================================
    // ============== EMPRESAS ===================
    // ===========================================

    // ===== FORM CREAR EMPRESA =====
    @GetMapping("/empresas/nueva")
    public String formCrearEmpresa(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                                   Model model,
                                   RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/clientes";
        }
        EmpresaDTO dto = new EmpresaDTO();
        dto.setEstado(EstadoActivo.Activo);
        model.addAttribute("empresa", dto);
        model.addAttribute("estados", EstadoActivo.values());
        model.addAttribute("modo", "crear");
        return "clientes/form-empresa";
    }

    // ===== POST CREAR EMPRESA =====
    @PostMapping("/empresas")
    public String crearEmpresa(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                               @Valid @ModelAttribute("empresa") EmpresaDTO dto,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/clientes";
        }
        if (result.hasErrors()) {
            model.addAttribute("estados", EstadoActivo.values());
            model.addAttribute("modo", "crear");
            return "clientes/form-empresa";
        }
        try {
            Integer idGenerado = clienteService.crearEmpresa(dto);
            redirect.addFlashAttribute("mensajeExito",
                "Empresa registrada correctamente (ID: " + idGenerado + ")");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error al crear empresa: " + e.getMessage());
        }
        return "redirect:/clientes?tab=empresas";
    }

    // ===== FORM EDITAR EMPRESA =====
    @GetMapping("/empresas/{id}/editar")
    public String formEditarEmpresa(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                                    @PathVariable Integer id,
                                    Model model,
                                    RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/clientes";
        }
        try {
            EmpresaDTO dto = clienteService.buscarEmpresaPorId(id);
            model.addAttribute("empresa", dto);
            model.addAttribute("estados", EstadoActivo.values());
            model.addAttribute("modo", "editar");
            return "clientes/form-empresa";
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError", e.getMessage());
            return "redirect:/clientes?tab=empresas";
        }
    }

    // ===== POST EDITAR EMPRESA =====
    @PostMapping("/empresas/{id}/actualizar")
    public String actualizarEmpresa(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                                    @PathVariable Integer id,
                                    @Valid @ModelAttribute("empresa") EmpresaDTO dto,
                                    BindingResult result,
                                    Model model,
                                    RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/clientes";
        }
        if (result.hasErrors()) {
            model.addAttribute("estados", EstadoActivo.values());
            model.addAttribute("modo", "editar");
            return "clientes/form-empresa";
        }
        try {
            clienteService.actualizarEmpresa(id, dto);
            redirect.addFlashAttribute("mensajeExito", "Empresa actualizada correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error al actualizar: " + e.getMessage());
        }
        return "redirect:/clientes?tab=empresas";
    }

    // ===== DETALLE EMPRESA =====
    @GetMapping("/empresas/{id}")
    public String detalleEmpresa(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                                 @PathVariable Integer id,
                                 Model model,
                                 RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/clientes";
        }
        try {
            EmpresaDTO dto = clienteService.buscarEmpresaPorId(id);
            model.addAttribute("empresa", dto);
            return "clientes/detalle-empresa";
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError", e.getMessage());
            return "redirect:/clientes?tab=empresas";
        }
    }

    // ===========================================
    // ============== ELIMINAR ===================
    // ===========================================

    // ===== ELIMINAR CLIENTE (cualquier tipo, usa ON DELETE CASCADE) =====
    @PostMapping("/{id}/eliminar")
    public String eliminar(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                           @PathVariable Integer id,
                           @RequestParam(value = "tab", required = false, defaultValue = "personas") String tab,
                           RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/clientes";
        }
        try {
            clienteService.eliminar(id);
            redirect.addFlashAttribute("mensajeExito", "Cliente eliminado correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "No se pudo eliminar el cliente (probablemente tiene reservas asociadas)");
        }
        return "redirect:/clientes?tab=" + tab;
    }
}