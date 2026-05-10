package co.edu.unbosque.proyecto_bd1;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String home() {
        return "¡Hola desde Spring Boot en vm-app!";
    }

    @GetMapping("/status")
    public String status() {
        return "OK - Servidor corriendo en la VM";
    }
}