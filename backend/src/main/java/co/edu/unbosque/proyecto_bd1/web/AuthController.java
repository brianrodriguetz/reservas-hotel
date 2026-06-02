package co.edu.unbosque.proyecto_bd1.web;

import co.edu.unbosque.proyecto_bd1.service.AutenticacionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final AutenticacionService autenticacionService;

    public AuthController(AutenticacionService autenticacionService) {
        this.autenticacionService = autenticacionService;
    }

    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(value = "next", required = false) String next,
                               @RequestParam(value = "error", required = false) String error,
                               Model model) {
        model.addAttribute("next", next != null ? next : "/");
        if (error != null) {
            model.addAttribute("mensajeError", "Usuario o contraseña incorrectos");
        }
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam("usuario") String usuario,
                                @RequestParam("password") String password,
                                @RequestParam(value = "next", required = false) String next,
                                HttpServletRequest request) {
        Optional<UsuarioSesion> optSesion =
            autenticacionService.autenticar(usuario, password);

        if (optSesion.isEmpty()) {
            String nextParam = next != null ? "&next=" + next : "";
            return "redirect:/login?error=1" + nextParam;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute(AuthInterceptor.CLAVE_SESION, optSesion.get());

        String destino = (next != null && !next.isBlank()) ? next : "/";
        return "redirect:" + destino;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}