package co.edu.unbosque.proyecto_bd1.controller;

import co.edu.unbosque.proyecto_bd1.dto.HabitacionDTO;
import co.edu.unbosque.proyecto_bd1.enums.EstadoHabitacion;
import co.edu.unbosque.proyecto_bd1.service.HabitacionService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/habitaciones")
@Tag(name = "Habitaciones", description = "CRUD de habitaciones del hotel")
public class HabitacionController {

    private final HabitacionService habitacionService;

    public HabitacionController(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las habitaciones")
    public List<HabitacionDTO> listarTodos() {
        return habitacionService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar habitacion por ID")
    public HabitacionDTO buscarPorId(@PathVariable Integer id) {
        return habitacionService.buscarPorId(id);
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Buscar habitacion por codigo")
    public HabitacionDTO buscarPorCodigo(@PathVariable String codigo) {
        return habitacionService.buscarPorCodigo(codigo);
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar habitaciones por estado")
    public List<HabitacionDTO> buscarPorEstado(@PathVariable String estado) {
        return habitacionService.buscarPorEstado(estado);
    }

    @GetMapping("/tipo/{idTipo}")
    @Operation(summary = "Listar habitaciones de un tipo especifico")
    public List<HabitacionDTO> buscarPorTipo(@PathVariable Integer idTipo) {
        return habitacionService.buscarPorTipo(idTipo);
    }

    @GetMapping("/piso/{piso}")
    @Operation(summary = "Listar habitaciones de un piso especifico")
    public List<HabitacionDTO> buscarPorPiso(@PathVariable Byte piso) {
        return habitacionService.buscarPorPiso(piso);
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Listar todas las habitaciones en estado Disponible")
    public List<HabitacionDTO> listarDisponibles() {
        return habitacionService.listarDisponibles();
    }

    @PostMapping
    @Operation(summary = "Crear nueva habitacion")
    public ResponseEntity<Void> crear(@Valid @RequestBody HabitacionDTO dto) {
        habitacionService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar habitacion existente")
    public ResponseEntity<Void> actualizar(@PathVariable Integer id,
                                            @Valid @RequestBody HabitacionDTO dto) {
        habitacionService.actualizar(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado/{nuevoEstado}")
    @Operation(summary = "Cambiar solo el estado de una habitacion")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Integer id,
                                               @PathVariable EstadoHabitacion nuevoEstado) {
        habitacionService.cambiarEstado(id, nuevoEstado);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar habitacion")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        habitacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}