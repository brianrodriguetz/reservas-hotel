package co.edu.unbosque.proyecto_bd1.controller;

import co.edu.unbosque.proyecto_bd1.dto.EmpleadoDTO;
import co.edu.unbosque.proyecto_bd1.service.EmpleadoService;
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
@RequestMapping("/api/empleados")
@Tag(name = "Empleados", description = "CRUD de empleados del hotel")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los empleados")
    public List<EmpleadoDTO> listarTodos() {
        return empleadoService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar empleado por ID")
    public EmpleadoDTO buscarPorId(@PathVariable Integer id) {
        return empleadoService.buscarPorId(id);
    }

    @GetMapping("/usuario/{usuario}")
    @Operation(summary = "Buscar empleado por usuario")
    public EmpleadoDTO buscarPorUsuario(@PathVariable String usuario) {
        return empleadoService.buscarPorUsuario(usuario);
    }

    @GetMapping("/documento/{numeroDocumento}")
    @Operation(summary = "Buscar empleado por numero de documento")
    public EmpleadoDTO buscarPorNumeroDocumento(@PathVariable String numeroDocumento) {
        return empleadoService.buscarPorNumeroDocumento(numeroDocumento);
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar empleados por estado (Activo o Inactivo)")
    public List<EmpleadoDTO> buscarPorEstado(@PathVariable String estado) {
        return empleadoService.buscarPorEstado(estado);
    }

    @GetMapping("/rol/{idRol}")
    @Operation(summary = "Listar empleados de un rol especifico")
    public List<EmpleadoDTO> buscarPorRol(@PathVariable Integer idRol) {
        return empleadoService.buscarPorRol(idRol);
    }

    @GetMapping("/{idSupervisor}/subordinados")
    @Operation(summary = "Listar empleados supervisados por uno dado")
    public List<EmpleadoDTO> buscarSubordinados(@PathVariable Integer idSupervisor) {
        return empleadoService.buscarSubordinados(idSupervisor);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo empleado")
    public ResponseEntity<Void> crear(@Valid @RequestBody EmpleadoDTO dto) {
        empleadoService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar empleado existente")
    public ResponseEntity<Void> actualizar(@PathVariable Integer id,
                                            @Valid @RequestBody EmpleadoDTO dto) {
        empleadoService.actualizar(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar empleado")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        empleadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}