package co.edu.unbosque.proyecto_bd1.controller;

import co.edu.unbosque.proyecto_bd1.dto.HabitacionDTO;
import co.edu.unbosque.proyecto_bd1.service.HabitacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HabitacionViewController {

    private final HabitacionService habitacionService;

    public HabitacionViewController(
            HabitacionService habitacionService) {

        this.habitacionService = habitacionService;
    }

    @GetMapping("/habitaciones")
    public String listarHabitaciones(Model model) {

        List<HabitacionDTO> habitaciones =
                habitacionService.listarTodos();

        model.addAttribute(
                "habitaciones",
                habitaciones
        );

        return "habitaciones";
    }
}