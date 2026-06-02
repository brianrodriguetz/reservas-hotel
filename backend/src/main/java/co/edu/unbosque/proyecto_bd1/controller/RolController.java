package co.edu.unbosque.proyecto_bd1.controller;

import co.edu.unbosque.proyecto_bd1.dto.RolDTO;
import co.edu.unbosque.proyecto_bd1.service.RolService;
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
@RequestMapping("/api/roles")
@Tag(name = "Roles", description = "CRUD de roles de empleados")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los roles")
    public List<RolDTO> listarTodos() {
        return rolService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar rol por ID")
    public RolDTO buscarPorId(@PathVariable Integer id) {
        return rolService.buscarPorId(id);
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Filtrar roles por estado (Activo o Inactivo)")
    public List<RolDTO> buscarPorEstado(@PathVariable String estado) {
        return rolService.buscarPorEstado(estado);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo rol")
    public ResponseEntity<Void> crear(@Valid @RequestBody RolDTO dto) {
        rolService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar rol existente")
    public ResponseEntity<Void> actualizar(@PathVariable Integer id,
                                            @Valid @RequestBody RolDTO dto) {
        rolService.actualizar(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar rol")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        rolService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}