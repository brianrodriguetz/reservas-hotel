package co.edu.unbosque.proyecto_bd1.controller;

import co.edu.unbosque.proyecto_bd1.dto.EmpresaDTO;
import co.edu.unbosque.proyecto_bd1.dto.PersonaDTO;
import co.edu.unbosque.proyecto_bd1.service.ClienteService;
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
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Gestion de clientes (Persona o Empresa)")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // PERSONAS 

    @GetMapping("/personas")
    @Operation(summary = "Listar todos los clientes tipo Persona")
    public List<PersonaDTO> listarPersonas() {
        return clienteService.listarPersonas();
    }

    @GetMapping("/personas/{id}")
    @Operation(summary = "Buscar persona por ID de cliente")
    public PersonaDTO buscarPersonaPorId(@PathVariable Integer id) {
        return clienteService.buscarPersonaPorId(id);
    }

    @GetMapping("/personas/documento/{tipo}/{numero}")
    @Operation(summary = "Buscar persona por tipo y numero de documento")
    public PersonaDTO buscarPersonaPorDocumento(@PathVariable String tipo,
                                                 @PathVariable String numero) {
        return clienteService.buscarPersonaPorDocumento(tipo, numero);
    }

    @PostMapping("/personas")
    @Operation(summary = "Crear cliente tipo Persona (inserta en CLIENTE y PERSONA atomicamente)")
    public ResponseEntity<Map<String, Integer>> crearPersona(@Valid @RequestBody PersonaDTO dto) {
        Integer idGenerado = clienteService.crearPersona(dto);
        Map<String, Integer> respuesta = new HashMap<>();
        respuesta.put("idCliente", idGenerado);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PutMapping("/personas/{id}")
    @Operation(summary = "Actualizar persona existente")
    public ResponseEntity<Void> actualizarPersona(@PathVariable Integer id,
                                                    @Valid @RequestBody PersonaDTO dto) {
        clienteService.actualizarPersona(id, dto);
        return ResponseEntity.noContent().build();
    }

    // EMPRESAS 

    @GetMapping("/empresas")
    @Operation(summary = "Listar todos los clientes tipo Empresa")
    public List<EmpresaDTO> listarEmpresas() {
        return clienteService.listarEmpresas();
    }

    @GetMapping("/empresas/{id}")
    @Operation(summary = "Buscar empresa por ID de cliente")
    public EmpresaDTO buscarEmpresaPorId(@PathVariable Integer id) {
        return clienteService.buscarEmpresaPorId(id);
    }

    @GetMapping("/empresas/nit/{nit}")
    @Operation(summary = "Buscar empresa por NIT")
    public EmpresaDTO buscarEmpresaPorNit(@PathVariable String nit) {
        return clienteService.buscarEmpresaPorNit(nit);
    }

    @PostMapping("/empresas")
    @Operation(summary = "Crear cliente tipo Empresa (inserta en CLIENTE y EMPRESA atomicamente)")
    public ResponseEntity<Map<String, Integer>> crearEmpresa(@Valid @RequestBody EmpresaDTO dto) {
        Integer idGenerado = clienteService.crearEmpresa(dto);
        Map<String, Integer> respuesta = new HashMap<>();
        respuesta.put("idCliente", idGenerado);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PutMapping("/empresas/{id}")
    @Operation(summary = "Actualizar empresa existente")
    public ResponseEntity<Void> actualizarEmpresa(@PathVariable Integer id,
                                                    @Valid @RequestBody EmpresaDTO dto) {
        clienteService.actualizarEmpresa(id, dto);
        return ResponseEntity.noContent().build();
    }

    // ambas

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente (CASCADE borra Persona o Empresa)")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}