package co.edu.unbosque.proyecto_bd1.controller;

import co.edu.unbosque.proyecto_bd1.dto.DireccionDTO;
import co.edu.unbosque.proyecto_bd1.service.DireccionService;
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
@RequestMapping("/api/direcciones")
@Tag(name = "Direcciones", description = "CRUD de direcciones de clientes")
public class DireccionController {

    private final DireccionService direccionService;

    public DireccionController(DireccionService direccionService) {
        this.direccionService = direccionService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las direcciones")
    public List<DireccionDTO> listarTodos() {
        return direccionService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar direccion por ID")
    public DireccionDTO buscarPorId(@PathVariable Integer id) {
        return direccionService.buscarPorId(id);
    }

    @GetMapping("/cliente/{idCliente}")
    @Operation(summary = "Listar todas las direcciones de un cliente")
    public List<DireccionDTO> buscarPorCliente(@PathVariable Integer idCliente) {
        return direccionService.buscarPorCliente(idCliente);
    }

    @GetMapping("/cliente/{idCliente}/tipo/{tipo}")
    @Operation(summary = "Listar direcciones de un cliente filtradas por tipo")
    public List<DireccionDTO> buscarPorClienteYTipo(@PathVariable Integer idCliente,
                                                     @PathVariable String tipo) {
        return direccionService.buscarPorClienteYTipo(idCliente, tipo);
    }

    @GetMapping("/cliente/{idCliente}/tipo/{tipo}/principal")
    @Operation(summary = "Obtener la direccion principal de un cliente para un tipo dado")
    public DireccionDTO buscarPrincipalDeClienteYTipo(@PathVariable Integer idCliente,
                                                       @PathVariable String tipo) {
        return direccionService.buscarPrincipalDeClienteYTipo(idCliente, tipo);
    }

    @GetMapping("/ciudad/{ciudad}")
    @Operation(summary = "Listar direcciones por ciudad")
    public List<DireccionDTO> buscarPorCiudad(@PathVariable String ciudad) {
        return direccionService.buscarPorCiudad(ciudad);
    }

    @PostMapping
    @Operation(summary = "Crear nueva direccion (si es principal, desmarca las del mismo tipo del cliente)")
    public ResponseEntity<Void> crear(@Valid @RequestBody DireccionDTO dto) {
        direccionService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar direccion existente")
    public ResponseEntity<Void> actualizar(@PathVariable Integer id,
                                            @Valid @RequestBody DireccionDTO dto) {
        direccionService.actualizar(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar direccion")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        direccionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}