package co.edu.unbosque.proyecto_bd1.controller;

import co.edu.unbosque.proyecto_bd1.dto.PagoDTO;
import co.edu.unbosque.proyecto_bd1.enums.EstadoPago;
import co.edu.unbosque.proyecto_bd1.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.math.BigDecimal;
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
@RequestMapping("/api/pagos")
@Tag(name = "Pagos", description = "CRUD de pagos asociados a reservas")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los pagos")
    public List<PagoDTO> listarTodos() {
        return pagoService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pago por ID")
    public PagoDTO buscarPorId(@PathVariable Integer id) {
        return pagoService.buscarPorId(id);
    }

    @GetMapping("/reserva/{idReserva}")
    @Operation(summary = "Listar pagos de una reserva")
    public List<PagoDTO> buscarPorReserva(@PathVariable Integer idReserva) {
        return pagoService.buscarPorReserva(idReserva);
    }

    @GetMapping("/reserva/{idReserva}/suma-pagada")
    @Operation(summary = "Suma de pagos Procesados para una reserva")
    public Map<String, BigDecimal> sumaPagadaDeReserva(@PathVariable Integer idReserva) {
        BigDecimal suma = pagoService.sumaPagadaDeReserva(idReserva);
        Map<String, BigDecimal> respuesta = new HashMap<>();
        respuesta.put("sumaPagada", suma);
        return respuesta;
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar pagos por estado")
    public List<PagoDTO> buscarPorEstado(@PathVariable String estado) {
        return pagoService.buscarPorEstado(estado);
    }

    @GetMapping("/medio/{medio}")
    @Operation(summary = "Listar pagos por medio de pago")
    public List<PagoDTO> buscarPorMedio(@PathVariable String medio) {
        return pagoService.buscarPorMedio(medio);
    }

    @GetMapping("/periodo")
    @Operation(summary = "Listar pagos en un periodo")
    public List<PagoDTO> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return pagoService.buscarPorPeriodo(inicio, fin);
    }

    @PostMapping
    @Operation(summary = "Registrar nuevo pago")
    public ResponseEntity<Void> crear(@Valid @RequestBody PagoDTO dto) {
        pagoService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar pago existente")
    public ResponseEntity<Void> actualizar(@PathVariable Integer id,
                                            @Valid @RequestBody PagoDTO dto) {
        pagoService.actualizar(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado/{nuevoEstado}")
    @Operation(summary = "Cambiar solo el estado de un pago")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Integer id,
                                               @PathVariable EstadoPago nuevoEstado) {
        pagoService.cambiarEstado(id, nuevoEstado);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pago")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}