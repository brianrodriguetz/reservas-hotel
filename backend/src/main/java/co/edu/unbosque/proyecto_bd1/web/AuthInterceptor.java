package co.edu.unbosque.proyecto_bd1.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class AuthInterceptor implements HandlerInterceptor {

    public static final String CLAVE_SESION = "usuarioSesion";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        UsuarioSesion usuario = null;
        if (session != null) {
            Object obj = session.getAttribute(CLAVE_SESION);
            if (obj instanceof UsuarioSesion) {
                usuario = (UsuarioSesion) obj;
            }
        }

        if (usuario == null) {
         
            String urlOriginal = request.getRequestURI();
            String queryString = request.getQueryString();
            if (queryString != null) {
                urlOriginal = urlOriginal + "?" + queryString;
            }
            response.sendRedirect(request.getContextPath()
                + "/login?next=" + urlOriginal);
            return false;
        }
        return true;
    }
}