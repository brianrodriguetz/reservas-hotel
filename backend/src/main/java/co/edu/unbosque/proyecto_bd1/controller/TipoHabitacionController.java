package co.edu.unbosque.proyecto_bd1.controller;

import co.edu.unbosque.proyecto_bd1.dto.TipoHabitacionDTO;
import co.edu.unbosque.proyecto_bd1.service.TipoHabitacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping("/api/tipos-habitacion")
@Tag(name = "Tipos de Habitacion", description = "CRUD de tipos de habitacion (Sencilla, Doble, Triple, Suite)")
public class TipoHabitacionController {

    private final TipoHabitacionService tipoHabitacionService;

    public TipoHabitacionController(TipoHabitacionService tipoHabitacionService) {
        this.tipoHabitacionService = tipoHabitacionService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los tipos de habitacion")
    public List<TipoHabitacionDTO> listarTodos() {
        return tipoHabitacionService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tipo de habitacion por ID")
    public TipoHabitacionDTO buscarPorId(@PathVariable Integer id) {
        return tipoHabitacionService.buscarPorId(id);
    }

    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Buscar tipo de habitacion por nombre")
    public TipoHabitacionDTO buscarPorNombre(@PathVariable String nombre) {
        return tipoHabitacionService.buscarPorNombre(nombre);
    }

    @GetMapping("/capacidad/{capacidad}")
@Operation(summary = "Listar tipos con capacidad maxima mayor o igual al valor dado")
public List<TipoHabitacionDTO> buscarPorCapacidadMinima(@PathVariable Byte capacidad) {
    return tipoHabitacionService.buscarPorCapacidadMinima(capacidad);
}

    @PostMapping
    @Operation(summary = "Crear nuevo tipo de habitacion")
    public ResponseEntity<Void> crear(@Valid @RequestBody TipoHabitacionDTO dto) {
        tipoHabitacionService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar tipo de habitacion existente")
    public ResponseEntity<Void> actualizar(@PathVariable Integer id,
                                            @Valid @RequestBody TipoHabitacionDTO dto) {
        tipoHabitacionService.actualizar(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tipo de habitacion")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        tipoHabitacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}