package co.edu.unbosque.proyecto_bd1.mappers;

import co.edu.unbosque.proyecto_bd1.dto.HabitacionDTO;
import co.edu.unbosque.proyecto_bd1.model.Habitacion;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class HabitacionMapper {

    public HabitacionDTO aDTO(Habitacion entidad) {
        if (entidad == null) {
            return null;
        }
        HabitacionDTO dto = new HabitacionDTO();
        dto.setIdHabitacion(entidad.getIdHabitacion());
        dto.setCodigo(entidad.getCodigo());
        dto.setPiso(entidad.getPiso());
        dto.setEstado(entidad.getEstado());
        dto.setIdTipo(entidad.getIdTipo());
        return dto;
    }

    public Habitacion aEntidad(HabitacionDTO dto) {
        if (dto == null) {
            return null;
        }
        Habitacion entidad = new Habitacion();
        entidad.setIdHabitacion(dto.getIdHabitacion());
        entidad.setCodigo(dto.getCodigo());
        entidad.setPiso(dto.getPiso());
        entidad.setEstado(dto.getEstado());
        entidad.setIdTipo(dto.getIdTipo());
        return entidad;
    }

    public List<HabitacionDTO> aDTOLista(List<Habitacion> entidades) {
        List<HabitacionDTO> resultado = new ArrayList<>();
        if (entidades == null) {
            return resultado;
        }
        for (int i = 0; i < entidades.size(); i++) {
            resultado.add(aDTO(entidades.get(i)));
        }
        return resultado;
    }
}