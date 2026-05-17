package co.edu.unbosque.proyecto_bd1.controller;

import co.edu.unbosque.proyecto_bd1.dto.ContactoDTO;
import co.edu.unbosque.proyecto_bd1.service.ContactoService;
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
@RequestMapping("/api/contactos")
@Tag(name = "Contactos", description = "CRUD de contactos de clientes")
public class ContactoController {

    private final ContactoService contactoService;

    public ContactoController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los contactos")
    public List<ContactoDTO> listarTodos() {
        return contactoService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar contacto por ID")
    public ContactoDTO buscarPorId(@PathVariable Integer id) {
        return contactoService.buscarPorId(id);
    }

    @GetMapping("/cliente/{idCliente}")
    @Operation(summary = "Listar todos los contactos de un cliente")
    public List<ContactoDTO> buscarPorCliente(@PathVariable Integer idCliente) {
        return contactoService.buscarPorCliente(idCliente);
    }

    @GetMapping("/cliente/{idCliente}/principal")
    @Operation(summary = "Obtener el contacto principal de un cliente")
    public ContactoDTO buscarPrincipalDeCliente(@PathVariable Integer idCliente) {
        return contactoService.buscarPrincipalDeCliente(idCliente);
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Listar contactos por tipo (Telefono_Fijo, Telefono_Movil, Correo_Electronico)")
    public List<ContactoDTO> buscarPorTipo(@PathVariable String tipo) {
        return contactoService.buscarPorTipo(tipo);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo contacto (si es principal, desmarca los otros del cliente)")
    public ResponseEntity<Void> crear(@Valid @RequestBody ContactoDTO dto) {
        contactoService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar contacto existente")
    public ResponseEntity<Void> actualizar(@PathVariable Integer id,
                                            @Valid @RequestBody ContactoDTO dto) {
        contactoService.actualizar(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar contacto")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        contactoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}