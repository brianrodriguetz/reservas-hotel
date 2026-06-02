package co.edu.unbosque.proyecto_bd1.controller;

import co.edu.unbosque.proyecto_bd1.dto.ReservaHabitacionDTO;
import co.edu.unbosque.proyecto_bd1.service.ReservaHabitacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservas-habitaciones")
@Tag(name = "Reservas-Habitaciones",
     description = "Asignaciones de habitaciones a reservas (N:M con numero de huespedes)")
public class ReservaHabitacionController {

    private final ReservaHabitacionService reservaHabitacionService;

    public ReservaHabitacionController(ReservaHabitacionService reservaHabitacionService) {
        this.reservaHabitacionService = reservaHabitacionService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las asignaciones reserva-habitacion")
    public List<ReservaHabitacionDTO> listarTodos() {
        return reservaHabitacionService.listarTodos();
    }

    @GetMapping("/reserva/{idReserva}/habitacion/{idHabitacion}")
    @Operation(summary = "Buscar asignacion especifica por la PK compuesta")
    public ReservaHabitacionDTO buscarPorIds(@PathVariable Integer idReserva,
                                              @PathVariable Integer idHabitacion) {
        return reservaHabitacionService.buscarPorIds(idReserva, idHabitacion);
    }

    @GetMapping("/reserva/{idReserva}")
    @Operation(summary = "Listar habitaciones asignadas a una reserva")
    public List<ReservaHabitacionDTO> buscarPorReserva(@PathVariable Integer idReserva) {
        return reservaHabitacionService.buscarPorReserva(idReserva);
    }

    @GetMapping("/habitacion/{idHabitacion}")
    @Operation(summary = "Listar reservas que usaron una habitacion")
    public List<ReservaHabitacionDTO> buscarPorHabitacion(@PathVariable Integer idHabitacion) {
        return reservaHabitacionService.buscarPorHabitacion(idHabitacion);
    }

    @PostMapping
    @Operation(summary = "Asignar una habitacion a una reserva")
    public ResponseEntity<Void> crear(@Valid @RequestBody ReservaHabitacionDTO dto) {
        reservaHabitacionService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/reserva/{idReserva}/habitacion/{idHabitacion}/huespedes/{numHuespedes}")
    @Operation(summary = "Actualizar el numero de huespedes de una asignacion")
    public ResponseEntity<Void> actualizarNumeroHuespedes(@PathVariable Integer idReserva,
                                                           @PathVariable Integer idHabitacion,
                                                           @PathVariable Byte numHuespedes) {
        reservaHabitacionService.actualizarNumeroHuespedes(idReserva, idHabitacion, numHuespedes);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/reserva/{idReserva}/habitacion/{idHabitacion}")
    @Operation(summary = "Eliminar asignacion de habitacion a reserva")
    public ResponseEntity<Void> eliminar(@PathVariable Integer idReserva,
                                          @PathVariable Integer idHabitacion) {
        reservaHabitacionService.eliminar(idReserva, idHabitacion);
        return ResponseEntity.noContent().build();
    }
}