package co.edu.unbosque.proyecto_bd1.mappers;

import co.edu.unbosque.proyecto_bd1.dto.EmpresaDTO;
import co.edu.unbosque.proyecto_bd1.dto.PersonaDTO;
import co.edu.unbosque.proyecto_bd1.model.Cliente;
import co.edu.unbosque.proyecto_bd1.model.Empresa;
import co.edu.unbosque.proyecto_bd1.model.Persona;
import java.time.LocalDate;
import java.time.Period;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public PersonaDTO aPersonaDTO(Cliente cliente, Persona persona) {
        if (cliente == null || persona == null) {
            return null;
        }
        PersonaDTO dto = new PersonaDTO();
        // Datos de Cliente
        dto.setIdCliente(cliente.getIdCliente());
        dto.setFechaRegistro(cliente.getFechaRegistro());
        dto.setEstado(cliente.getEstado());
        // Datos de Persona
        dto.setTipoDocumento(persona.getTipoDocumento());
        dto.setNumeroDocumento(persona.getNumeroDocumento());
        dto.setNombre(persona.getNombre());
        dto.setApellido(persona.getApellido());
        dto.setFechaNacimiento(persona.getFechaNacimiento());
        dto.setNacionalidad(persona.getNacionalidad());
        // Atributos derivados
        dto.setNombreCompleto(calcularNombreCompleto(persona.getNombre(), persona.getApellido()));
        dto.setEdad(calcularEdad(persona.getFechaNacimiento()));
        return dto;
    }

    public EmpresaDTO aEmpresaDTO(Cliente cliente, Empresa empresa) {
        if (cliente == null || empresa == null) {
            return null;
        }
        EmpresaDTO dto = new EmpresaDTO();
        // Datos de Cliente
        dto.setIdCliente(cliente.getIdCliente());
        dto.setFechaRegistro(cliente.getFechaRegistro());
        dto.setEstado(cliente.getEstado());
        // Datos de Empresa
        dto.setNit(empresa.getNit());
        dto.setRazonSocial(empresa.getRazonSocial());
        dto.setRepresentanteLegal(empresa.getRepresentanteLegal());
        dto.setSectorEconomico(empresa.getSectorEconomico());
        return dto;
    }

    // ===== Helpers privados =====

    private String calcularNombreCompleto(String nombre, String apellido) {
        if (nombre == null && apellido == null) {
            return null;
        }
        String n = nombre != null ? nombre : "";
        String a = apellido != null ? apellido : "";
        return (n + " " + a).trim();
    }

    private Integer calcularEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            return null;
        }
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
}