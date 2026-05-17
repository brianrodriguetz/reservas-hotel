package co.edu.unbosque.proyecto_bd1.mappers;

import co.edu.unbosque.proyecto_bd1.dto.HuespedDTO;
import co.edu.unbosque.proyecto_bd1.model.Huesped;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class HuespedMapper {

    public HuespedDTO aDTO(Huesped entidad) {
        if (entidad == null) {
            return null;
        }
        HuespedDTO dto = new HuespedDTO();
        dto.setIdHuesped(entidad.getIdHuesped());
        dto.setNombre(entidad.getNombre());
        dto.setApellido(entidad.getApellido());
        dto.setTipoDocumento(entidad.getTipoDocumento());
        dto.setNumeroDocumento(entidad.getNumeroDocumento());
        dto.setNacionalidad(entidad.getNacionalidad());
        dto.setFechaNacimiento(entidad.getFechaNacimiento());

        // Atributos derivados
        dto.setNombreCompleto(calcularNombreCompleto(entidad));
        dto.setEdad(calcularEdad(entidad.getFechaNacimiento()));

        return dto;
    }

    public Huesped aEntidad(HuespedDTO dto) {
        if (dto == null) {
            return null;
        }
        Huesped entidad = new Huesped();
        entidad.setIdHuesped(dto.getIdHuesped());
        entidad.setNombre(dto.getNombre());
        entidad.setApellido(dto.getApellido());
        entidad.setTipoDocumento(dto.getTipoDocumento());
        entidad.setNumeroDocumento(dto.getNumeroDocumento());
        entidad.setNacionalidad(dto.getNacionalidad());
        entidad.setFechaNacimiento(dto.getFechaNacimiento());
        return entidad;
    }

    public List<HuespedDTO> aDTOLista(List<Huesped> entidades) {
        List<HuespedDTO> resultado = new ArrayList<>();
        if (entidades == null) {
            return resultado;
        }
        for (int i = 0; i < entidades.size(); i++) {
            resultado.add(aDTO(entidades.get(i)));
        }
        return resultado;
    }

    // ===== Calculo de atributos derivados =====

    private String calcularNombreCompleto(Huesped entidad) {
        if (entidad.getNombre() == null && entidad.getApellido() == null) {
            return null;
        }
        String nombre = entidad.getNombre() != null ? entidad.getNombre() : "";
        String apellido = entidad.getApellido() != null ? entidad.getApellido() : "";
        return (nombre + " " + apellido).trim();
    }

    private Integer calcularEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            return null;
        }
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
}