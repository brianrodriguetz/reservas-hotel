package co.edu.unbosque.proyecto_bd1.controller;

import co.edu.unbosque.proyecto_bd1.dto.SolicitudReembolsoDTO;
import co.edu.unbosque.proyecto_bd1.enums.EstadoSolicitudReembolso;
import co.edu.unbosque.proyecto_bd1.service.SolicitudReembolsoService;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/solicitudes-reembolso")
@Tag(name = "Solicitudes de Reembolso",
     description = "CRUD de solicitudes de reembolso (1:0..1 con Cancelacion)")
public class SolicitudReembolsoController {

    private final SolicitudReembolsoService solicitudReembolsoService;

    public SolicitudReembolsoController(SolicitudReembolsoService solicitudReembolsoService) {
        this.solicitudReembolsoService = solicitudReembolsoService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las solicitudes de reembolso")
    public List<SolicitudReembolsoDTO> listarTodos() {
        return solicitudReembolsoService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar solicitud por ID")
    public SolicitudReembolsoDTO buscarPorId(@PathVariable Integer id) {
        return solicitudReembolsoService.buscarPorId(id);
    }

    @GetMapping("/cancelacion/{idCancelacion}")
    @Operation(summary = "Buscar la solicitud asociada a una cancelacion (relacion 1:0..1)")
    public SolicitudReembolsoDTO buscarPorCancelacion(@PathVariable Integer idCancelacion) {
        return solicitudReembolsoService.buscarPorCancelacion(idCancelacion);
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar solicitudes por estado")
    public List<SolicitudReembolsoDTO> buscarPorEstado(@PathVariable String estado) {
        return solicitudReembolsoService.buscarPorEstado(estado);
    }

    @GetMapping("/empleado/{idEmpleado}")
    @Operation(summary = "Listar solicitudes asignadas a un empleado")
    public List<SolicitudReembolsoDTO> buscarPorEmpleado(@PathVariable Integer idEmpleado) {
        return solicitudReembolsoService.buscarPorEmpleado(idEmpleado);
    }

    @GetMapping("/pendientes-sin-asignar")
    @Operation(summary = "Listar solicitudes Pendientes sin empleado asignado")
    public List<SolicitudReembolsoDTO> buscarPendientesSinAsignar() {
        return solicitudReembolsoService.buscarPendientesSinAsignar();
    }

    @PostMapping
    @Operation(summary = "Registrar nueva solicitud de reembolso")
    public ResponseEntity<Map<String, Integer>> crear(@Valid @RequestBody SolicitudReembolsoDTO dto) {
        Integer id = solicitudReembolsoService.crear(dto);
        Map<String, Integer> resp = new HashMap<>();
        resp.put("idSolicitud", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar solicitud existente")
    public ResponseEntity<Void> actualizar(@PathVariable Integer id,
                                            @Valid @RequestBody SolicitudReembolsoDTO dto) {
        solicitudReembolsoService.actualizar(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/asignar-empleado/{idEmpleado}")
    @Operation(summary = "Asignar empleado a una solicitud pendiente")
    public ResponseEntity<Void> asignarEmpleado(@PathVariable Integer id,
                                                  @PathVariable Integer idEmpleado) {
        solicitudReembolsoService.asignarEmpleado(id, idEmpleado);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/procesar/{nuevoEstado}")
    @Operation(summary = "Procesar solicitud (cambia estado y registra fecha_Procesamiento)")
    public ResponseEntity<Void> procesar(@PathVariable Integer id,
                                          @PathVariable EstadoSolicitudReembolso nuevoEstado) {
        solicitudReembolsoService.procesar(id, nuevoEstado);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar solicitud de reembolso")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        solicitudReembolsoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}