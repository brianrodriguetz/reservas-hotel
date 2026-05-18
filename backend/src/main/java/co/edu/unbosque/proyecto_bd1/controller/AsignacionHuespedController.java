package co.edu.unbosque.proyecto_bd1.controller;

import co.edu.unbosque.proyecto_bd1.dto.AsignacionHuespedDTO;
import co.edu.unbosque.proyecto_bd1.service.AsignacionHuespedService;
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
@RequestMapping("/api/asignaciones-huespedes")
@Tag(name = "Asignaciones de Huespedes",
     description = "Huespedes asignados a habitaciones de reservas (N:M triple con titular)")
public class AsignacionHuespedController {

    private final AsignacionHuespedService asignacionHuespedService;

    public AsignacionHuespedController(AsignacionHuespedService asignacionHuespedService) {
        this.asignacionHuespedService = asignacionHuespedService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las asignaciones de huespedes")
    public List<AsignacionHuespedDTO> listarTodos() {
        return asignacionHuespedService.listarTodos();
    }

    @GetMapping("/huesped/{idHuesped}/reserva/{idReserva}/habitacion/{idHabitacion}")
    @Operation(summary = "Buscar asignacion especifica por la PK triple")
    public AsignacionHuespedDTO buscarPorIds(@PathVariable Integer idHuesped,
                                              @PathVariable Integer idReserva,
                                              @PathVariable Integer idHabitacion) {
        return asignacionHuespedService.buscarPorIds(idHuesped, idReserva, idHabitacion);
    }

    @GetMapping("/huesped/{idHuesped}")
    @Operation(summary = "Listar todas las asignaciones de un huesped (historial)")
    public List<AsignacionHuespedDTO> buscarPorHuesped(@PathVariable Integer idHuesped) {
        return asignacionHuespedService.buscarPorHuesped(idHuesped);
    }

    @GetMapping("/reserva/{idReserva}/habitacion/{idHabitacion}")
    @Operation(summary = "Listar huespedes en una habitacion especifica de una reserva")
    public List<AsignacionHuespedDTO> buscarPorReservaHabitacion(
            @PathVariable Integer idReserva,
            @PathVariable Integer idHabitacion) {
        return asignacionHuespedService.buscarPorReservaHabitacion(idReserva, idHabitacion);
    }

    @GetMapping("/reserva/{idReserva}")
    @Operation(summary = "Listar todos los huespedes asignados a una reserva")
    public List<AsignacionHuespedDTO> buscarPorReserva(@PathVariable Integer idReserva) {
        return asignacionHuespedService.buscarPorReserva(idReserva);
    }

    @GetMapping("/reserva/{idReserva}/habitacion/{idHabitacion}/titular")
    @Operation(summary = "Obtener el huesped titular de una habitacion en una reserva")
    public AsignacionHuespedDTO buscarTitular(@PathVariable Integer idReserva,
                                               @PathVariable Integer idHabitacion) {
        return asignacionHuespedService.buscarTitular(idReserva, idHabitacion);
    }

    @PostMapping
    @Operation(summary = "Asignar huesped a una habitacion de reserva (si es titular, desmarca los otros titulares)")
    public ResponseEntity<Void> crear(@Valid @RequestBody AsignacionHuespedDTO dto) {
        asignacionHuespedService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/huesped/{idHuesped}/reserva/{idReserva}/habitacion/{idHabitacion}/titular/{esTitular}")
    @Operation(summary = "Actualizar la bandera de titular de una asignacion")
    public ResponseEntity<Void> actualizarTitular(@PathVariable Integer idHuesped,
                                                    @PathVariable Integer idReserva,
                                                    @PathVariable Integer idHabitacion,
                                                    @PathVariable Boolean esTitular) {
        asignacionHuespedService.actualizarTitular(idHuesped, idReserva, idHabitacion, esTitular);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/huesped/{idHuesped}/reserva/{idReserva}/habitacion/{idHabitacion}")
    @Operation(summary = "Eliminar asignacion de huesped a reserva-habitacion")
    public ResponseEntity<Void> eliminar(@PathVariable Integer idHuesped,
                                          @PathVariable Integer idReserva,
                                          @PathVariable Integer idHabitacion) {
        asignacionHuespedService.eliminar(idHuesped, idReserva, idHabitacion);
        return ResponseEntity.noContent().build();
    }
}