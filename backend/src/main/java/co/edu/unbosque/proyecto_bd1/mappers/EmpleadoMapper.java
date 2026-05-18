package co.edu.unbosque.proyecto_bd1.mappers;

import co.edu.unbosque.proyecto_bd1.dto.EmpleadoDTO;
import co.edu.unbosque.proyecto_bd1.model.Empleado;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoMapper {

    public EmpleadoDTO aDTO(Empleado entidad) {
        if (entidad == null) {
            return null;
        }
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setIdEmpleado(entidad.getIdEmpleado());
        dto.setNumeroDocumento(entidad.getNumeroDocumento());
        dto.setUsuario(entidad.getUsuario());
        dto.setNombre(entidad.getNombre());
        dto.setApellido(entidad.getApellido());
        dto.setEstado(entidad.getEstado());
        dto.setIdRol(entidad.getIdRol());
        dto.setIdSupervisor(entidad.getIdSupervisor());

        // Atributo derivado
        dto.setNombreCompleto(calcularNombreCompleto(entidad));

        return dto;
    }

    public Empleado aEntidad(EmpleadoDTO dto) {
        if (dto == null) {
            return null;
        }
        Empleado entidad = new Empleado();
        entidad.setIdEmpleado(dto.getIdEmpleado());
        entidad.setNumeroDocumento(dto.getNumeroDocumento());
        entidad.setUsuario(dto.getUsuario());
        entidad.setNombre(dto.getNombre());
        entidad.setApellido(dto.getApellido());
        entidad.setEstado(dto.getEstado());
        entidad.setIdRol(dto.getIdRol());
        entidad.setIdSupervisor(dto.getIdSupervisor());
        return entidad;
    }

    public List<EmpleadoDTO> aDTOLista(List<Empleado> entidades) {
        List<EmpleadoDTO> resultado = new ArrayList<>();
        if (entidades == null) {
            return resultado;
        }
        for (int i = 0; i < entidades.size(); i++) {
            resultado.add(aDTO(entidades.get(i)));
        }
        return resultado;
    }

    // ===== Calculo de atributo derivado =====

    private String calcularNombreCompleto(Empleado entidad) {
        if (entidad.getNombre() == null && entidad.getApellido() == null) {
            return null;
        }
        String nombre = entidad.getNombre() != null ? entidad.getNombre() : "";
        String apellido = entidad.getApellido() != null ? entidad.getApellido() : "";
        return (nombre + " " + apellido).trim();
    }
}