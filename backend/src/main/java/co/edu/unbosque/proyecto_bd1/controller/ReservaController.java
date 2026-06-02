package co.edu.unbosque.proyecto_bd1.controller;

import co.edu.unbosque.proyecto_bd1.dto.ReservaDTO;
import co.edu.unbosque.proyecto_bd1.enums.EstadoReserva;
import co.edu.unbosque.proyecto_bd1.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservas")
@Tag(name = "Reservas", description = "CRUD de reservas hoteleras")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las reservas")
    public List<ReservaDTO> listarTodos() {
        return reservaService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar reserva por ID")
    public ReservaDTO buscarPorId(@PathVariable Integer id) {
        return reservaService.buscarPorId(id);
    }

    @GetMapping("/cliente/{idCliente}")
    @Operation(summary = "Listar reservas de un cliente")
    public List<ReservaDTO> buscarPorCliente(@PathVariable Integer idCliente) {
        return reservaService.buscarPorCliente(idCliente);
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar reservas por estado")
    public List<ReservaDTO> buscarPorEstado(@PathVariable String estado) {
        return reservaService.buscarPorEstado(estado);
    }

    @GetMapping("/canal/{canal}")
    @Operation(summary = "Listar reservas por canal (Telefonica o Presencial)")
    public List<ReservaDTO> buscarPorCanal(@PathVariable String canal) {
        return reservaService.buscarPorCanal(canal);
    }

    @GetMapping("/activas-en-rango")
    @Operation(summary = "Listar reservas Confirmadas/En_Curso que se traslapan con el rango dado")
    public List<ReservaDTO> buscarActivasEnRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return reservaService.buscarActivasEnRango(inicio, fin);
    }

    @GetMapping("/creadas-en-periodo")
    @Operation(summary = "Listar reservas creadas en un periodo")
    public List<ReservaDTO> buscarPorPeriodoCreacion(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return reservaService.buscarPorPeriodoCreacion(inicio, fin);
    }

    @PostMapping
    @Operation(summary = "Crear nueva reserva")
    public ResponseEntity<Map<String, Integer>> crear(@Valid @RequestBody ReservaDTO dto) {
        Integer idGenerado = reservaService.crear(dto);
        Map<String, Integer> respuesta = new HashMap<>();
        respuesta.put("idReserva", idGenerado);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reserva existente")
    public ResponseEntity<Void> actualizar(@PathVariable Integer id,
                                            @Valid @RequestBody ReservaDTO dto) {
        reservaService.actualizar(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado/{nuevoEstado}")
    @Operation(summary = "Cambiar solo el estado de una reserva")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Integer id,
                                               @PathVariable EstadoReserva nuevoEstado) {
        reservaService.cambiarEstado(id, nuevoEstado);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reserva")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        reservaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}