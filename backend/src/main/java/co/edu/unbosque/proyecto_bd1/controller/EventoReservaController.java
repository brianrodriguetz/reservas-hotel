package co.edu.unbosque.proyecto_bd1.controller;

import co.edu.unbosque.proyecto_bd1.dto.CancelacionDTO;
import co.edu.unbosque.proyecto_bd1.dto.CheckInDTO;
import co.edu.unbosque.proyecto_bd1.dto.CheckOutDTO;
import co.edu.unbosque.proyecto_bd1.service.EventoReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eventos")
@Tag(name = "Eventos de Reserva",
     description = "Eventos: Check-In, Check-Out, Cancelacion")
public class EventoReservaController {

    private final EventoReservaService eventoReservaService;

    public EventoReservaController(EventoReservaService eventoReservaService) {
        this.eventoReservaService = eventoReservaService;
    }

    // chck ines

    @GetMapping("/checkins")
    @Operation(summary = "Listar todos los check-ins")
    public List<CheckInDTO> listarCheckIns() {
        return eventoReservaService.listarCheckIns();
    }

    @GetMapping("/checkins/{id}")
    @Operation(summary = "Buscar check-in por id de evento")
    public CheckInDTO buscarCheckInPorId(@PathVariable Integer id) {
        return eventoReservaService.buscarCheckInPorId(id);
    }

    @PostMapping("/checkins")
    @Operation(summary = "Registrar nuevo check-in")
    public ResponseEntity<Map<String, Integer>> crearCheckIn(@Valid @RequestBody CheckInDTO dto) {
        Integer id = eventoReservaService.crearCheckIn(dto);
        Map<String, Integer> resp = new HashMap<>();
        resp.put("idEvento", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    // ckech outs



    @GetMapping("/checkouts")
    @Operation(summary = "Listar todos los check-outs")
    public List<CheckOutDTO> listarCheckOuts() {
        return eventoReservaService.listarCheckOuts();
    }

    @GetMapping("/checkouts/{id}")
    @Operation(summary = "Buscar check-out por id de evento")
    public CheckOutDTO buscarCheckOutPorId(@PathVariable Integer id) {
        return eventoReservaService.buscarCheckOutPorId(id);
    }

    @PostMapping("/checkouts")
    @Operation(summary = "Registrar nuevo check-out")
    public ResponseEntity<Map<String, Integer>> crearCheckOut(@Valid @RequestBody CheckOutDTO dto) {
        Integer id = eventoReservaService.crearCheckOut(dto);
        Map<String, Integer> resp = new HashMap<>();
        resp.put("idEvento", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    // cancelaciones

    @GetMapping("/cancelaciones")
    @Operation(summary = "Listar todas las cancelaciones")
    public List<CancelacionDTO> listarCancelaciones() {
        return eventoReservaService.listarCancelaciones();
    }

    @GetMapping("/cancelaciones/{id}")
    @Operation(summary = "Buscar cancelacion por id de evento")
    public CancelacionDTO buscarCancelacionPorId(@PathVariable Integer id) {
        return eventoReservaService.buscarCancelacionPorId(id);
    }

    @PostMapping("/cancelaciones")
    @Operation(summary = "Registrar nueva cancelacion con motivo y penalizacion")
    public ResponseEntity<Map<String, Integer>> crearCancelacion(
            @Valid @RequestBody CancelacionDTO dto) {
        Integer id = eventoReservaService.crearCancelacion(dto);
        Map<String, Integer> resp = new HashMap<>();
        resp.put("idEvento", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PutMapping("/cancelaciones/{id}")
    @Operation(summary = "Actualizar motivo o penalizacion de una cancelacion")
    public ResponseEntity<Void> actualizarCancelacion(@PathVariable Integer id,
                                                       @Valid @RequestBody CancelacionDTO dto) {
        eventoReservaService.actualizarCancelacion(id, dto);
        return ResponseEntity.noContent().build();
    }

    // los 3

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar evento (CASCADE borra la hija CheckIn/CheckOut/Cancelacion)")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        eventoReservaService.eliminar(id);
        return ResponseEntity.noContent().build();
   }
}