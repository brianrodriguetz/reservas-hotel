package co.edu.unbosque.proyecto_bd1.web;

import co.edu.unbosque.proyecto_bd1.dto.ContactoDTO;
import co.edu.unbosque.proyecto_bd1.dto.DireccionDTO;
import co.edu.unbosque.proyecto_bd1.dto.EmpresaDTO;
import co.edu.unbosque.proyecto_bd1.dto.PersonaDTO;
import co.edu.unbosque.proyecto_bd1.enums.EstadoActivo;
import co.edu.unbosque.proyecto_bd1.enums.TipoContacto;
import co.edu.unbosque.proyecto_bd1.enums.TipoDireccion;
import co.edu.unbosque.proyecto_bd1.enums.TipoDocumento;
import co.edu.unbosque.proyecto_bd1.service.ClienteService;
import co.edu.unbosque.proyecto_bd1.service.ContactoService;
import co.edu.unbosque.proyecto_bd1.service.DireccionService;
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
    private final ContactoService contactoService;
    private final DireccionService direccionService;

    public ClienteWebController(ClienteService clienteService,
                                ContactoService contactoService,
                                DireccionService direccionService) {
        this.clienteService = clienteService;
        this.contactoService = contactoService;
        this.direccionService = direccionService;
    }

    private boolean tieneAcceso(UsuarioSesion sesion) {
        return sesion.isAdministrador() || sesion.isRecepcionista();
    }

    /**
     * Helper para precargar los catalogos comunes que necesitan los modals
     * de contacto y direccion en los detalles.
     */
    private void agregarCatalogosClienteAlModel(Model model, Integer idCliente) {
        ContactoDTO nuevoContacto = new ContactoDTO();
        nuevoContacto.setIdCliente(idCliente);
        nuevoContacto.setEsPrincipal(false);

        DireccionDTO nuevaDireccion = new DireccionDTO();
        nuevaDireccion.setIdCliente(idCliente);
        nuevaDireccion.setEsPrincipal(false);

        model.addAttribute("nuevoContacto", nuevoContacto);
        model.addAttribute("nuevaDireccion", nuevaDireccion);
        model.addAttribute("tiposContacto", TipoContacto.values());
        model.addAttribute("tiposDireccion", TipoDireccion.values());
    }

    // ===========================================
    // ============== LISTAR =====================
    // ===========================================

    @GetMapping
    public String listar(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                         @RequestParam(value = "tab", required = false, defaultValue = "personas") String tab,
                         Model model,
                         RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "No tiene permisos para gestionar clientes");
            return "redirect:/";
        }
        model.addAttribute("personas", clienteService.listarPersonas());
        model.addAttribute("empresas", clienteService.listarEmpresas());
        model.addAttribute("tabActivo", tab);
        return "clientes/lista";
    }

    // ===========================================
    // ============== PERSONAS ===================
    // ===========================================

    @GetMapping("/personas/nueva")
    public String formCrearPersona(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                                   Model model,
                                   RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/clientes";
        }
        PersonaDTO dto = new PersonaDTO();
        dto.setEstado(EstadoActivo.Activo);
        model.addAttribute("persona", dto);
        model.addAttribute("tiposDocumento", TipoDocumento.values());
        model.addAttribute("estados", EstadoActivo.values());
        model.addAttribute("modo", "crear");
        return "clientes/form-persona";
    }

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
            List<ContactoDTO> contactos = contactoService.buscarPorCliente(id);
            List<DireccionDTO> direcciones = direccionService.buscarPorCliente(id);

            model.addAttribute("persona", dto);
            model.addAttribute("contactos", contactos);
            model.addAttribute("direcciones", direcciones);
            agregarCatalogosClienteAlModel(model, id);
            return "clientes/detalle-persona";
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError", e.getMessage());
            return "redirect:/clientes?tab=personas";
        }
    }

    // ===========================================
    // ============== EMPRESAS ===================
    // ===========================================

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
            List<ContactoDTO> contactos = contactoService.buscarPorCliente(id);
            List<DireccionDTO> direcciones = direccionService.buscarPorCliente(id);

            model.addAttribute("empresa", dto);
            model.addAttribute("contactos", contactos);
            model.addAttribute("direcciones", direcciones);
            agregarCatalogosClienteAlModel(model, id);
            return "clientes/detalle-empresa";
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError", e.getMessage());
            return "redirect:/clientes?tab=empresas";
        }
    }

    // ===========================================
    // ============== ELIMINAR CLIENTE ===========
    // ===========================================

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

    // ===========================================
    // ============== CONTACTOS ==================
    // ===========================================

    @PostMapping("/{id}/contactos")
    public String crearContacto(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                                @PathVariable Integer id,
                                @ModelAttribute("nuevoContacto") ContactoDTO dto,
                                @RequestParam("tipoCliente") String tipoCliente,
                                RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/clientes";
        }
        dto.setIdCliente(id);
        if (dto.getEsPrincipal() == null) {
            dto.setEsPrincipal(false);
        }
        try {
            contactoService.crear(dto);
            redirect.addFlashAttribute("mensajeExito", "Contacto agregado correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error al agregar contacto: " + e.getMessage());
        }
        // Redirigir al detalle del tipo correcto
        if ("empresa".equals(tipoCliente)) {
            return "redirect:/clientes/empresas/" + id;
        }
        return "redirect:/clientes/personas/" + id;
    }

    @PostMapping("/{id}/contactos/{idContacto}/eliminar")
    public String eliminarContacto(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                                    @PathVariable Integer id,
                                    @PathVariable Integer idContacto,
                                    @RequestParam("tipoCliente") String tipoCliente,
                                    RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/clientes";
        }
        try {
            contactoService.eliminar(idContacto);
            redirect.addFlashAttribute("mensajeExito", "Contacto eliminado");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error al eliminar contacto: " + e.getMessage());
        }
        if ("empresa".equals(tipoCliente)) {
            return "redirect:/clientes/empresas/" + id;
        }
        return "redirect:/clientes/personas/" + id;
    }

    // ===========================================
    // ============== DIRECCIONES ================
    // ===========================================

    @PostMapping("/{id}/direcciones")
    public String crearDireccion(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                                  @PathVariable Integer id,
                                  @ModelAttribute("nuevaDireccion") DireccionDTO dto,
                                  @RequestParam("tipoCliente") String tipoCliente,
                                  RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/clientes";
        }
        dto.setIdCliente(id);
        if (dto.getEsPrincipal() == null) {
            dto.setEsPrincipal(false);
        }
        try {
            direccionService.crear(dto);
            redirect.addFlashAttribute("mensajeExito", "Dirección agregada correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error al agregar dirección: " + e.getMessage());
        }
        if ("empresa".equals(tipoCliente)) {
            return "redirect:/clientes/empresas/" + id;
        }
        return "redirect:/clientes/personas/" + id;
    }

    @PostMapping("/{id}/direcciones/{idDireccion}/eliminar")
    public String eliminarDireccion(@SessionAttribute("usuarioSesion") UsuarioSesion sesion,
                                     @PathVariable Integer id,
                                     @PathVariable Integer idDireccion,
                                     @RequestParam("tipoCliente") String tipoCliente,
                                     RedirectAttributes redirect) {
        if (!tieneAcceso(sesion)) {
            redirect.addFlashAttribute("mensajeError", "Sin permisos");
            return "redirect:/clientes";
        }
        try {
            direccionService.eliminar(idDireccion);
            redirect.addFlashAttribute("mensajeExito", "Dirección eliminada");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError",
                "Error al eliminar dirección: " + e.getMessage());
        }
        if ("empresa".equals(tipoCliente)) {
            return "redirect:/clientes/empresas/" + id;
        }
        return "redirect:/clientes/personas/" + id;
    }
}