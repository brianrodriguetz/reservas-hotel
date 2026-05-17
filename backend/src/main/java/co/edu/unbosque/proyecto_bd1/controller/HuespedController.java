package co.edu.unbosque.proyecto_bd1.controller;

import co.edu.unbosque.proyecto_bd1.dto.HuespedDTO;
import co.edu.unbosque.proyecto_bd1.service.HuespedService;
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
@RequestMapping("/api/huespedes")
@Tag(name = "Huespedes", description = "CRUD de huespedes (personas que se hospedan)")
public class HuespedController {

    private final HuespedService huespedService;

    public HuespedController(HuespedService huespedService) {
        this.huespedService = huespedService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los huespedes")
    public List<HuespedDTO> listarTodos() {
        return huespedService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar huesped por ID")
    public HuespedDTO buscarPorId(@PathVariable Integer id) {
        return huespedService.buscarPorId(id);
    }

    @GetMapping("/documento/{tipo}/{numero}")
    @Operation(summary = "Buscar huesped por tipo y numero de documento")
    public HuespedDTO buscarPorDocumento(@PathVariable String tipo,
                                         @PathVariable String numero) {
        return huespedService.buscarPorDocumento(tipo, numero);
    }

    @GetMapping("/nacionalidad/{nacionalidad}")
    @Operation(summary = "Listar huespedes por nacionalidad")
    public List<HuespedDTO> buscarPorNacionalidad(@PathVariable String nacionalidad) {
        return huespedService.buscarPorNacionalidad(nacionalidad);
    }

    @GetMapping("/apellido/{apellido}")
    @Operation(summary = "Buscar huespedes cuyo apellido contiene el texto dado")
    public List<HuespedDTO> buscarPorApellido(@PathVariable String apellido) {
        return huespedService.buscarPorApellido(apellido);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo huesped")
    public ResponseEntity<Void> crear(@Valid @RequestBody HuespedDTO dto) {
        huespedService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar huesped existente")
    public ResponseEntity<Void> actualizar(@PathVariable Integer id,
                                            @Valid @RequestBody HuespedDTO dto) {
        huespedService.actualizar(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar huesped")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        huespedService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}