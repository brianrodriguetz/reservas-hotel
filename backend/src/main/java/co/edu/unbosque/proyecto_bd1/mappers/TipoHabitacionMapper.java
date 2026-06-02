package co.edu.unbosque.proyecto_bd1.mappers;

import co.edu.unbosque.proyecto_bd1.dto.TipoHabitacionDTO;
import co.edu.unbosque.proyecto_bd1.model.TipoHabitacion;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TipoHabitacionMapper {

    public TipoHabitacionDTO aDTO(TipoHabitacion entidad) {
        if (entidad == null) {
            return null;
        }
        TipoHabitacionDTO dto = new TipoHabitacionDTO();
        dto.setIdTipo(entidad.getIdTipo());
        dto.setNombre(entidad.getNombre());
        dto.setCapacidadMax(entidad.getCapacidadMax());
        dto.setNumeroCamas(entidad.getNumeroCamas());
        dto.setPrecioBaseNoche(entidad.getPrecioBaseNoche());
        return dto;
    }

    public TipoHabitacion aEntidad(TipoHabitacionDTO dto) {
        if (dto == null) {
            return null;
        }
        TipoHabitacion entidad = new TipoHabitacion();
        entidad.setIdTipo(dto.getIdTipo());
        entidad.setNombre(dto.getNombre());
        entidad.setCapacidadMax(dto.getCapacidadMax());
        entidad.setNumeroCamas(dto.getNumeroCamas());
        entidad.setPrecioBaseNoche(dto.getPrecioBaseNoche());
        return entidad;
    }

    public List<TipoHabitacionDTO> aDTOLista(List<TipoHabitacion> entidades) {
        List<TipoHabitacionDTO> resultado = new ArrayList<>();
        if (entidades == null) {
            return resultado;
        }
        for (int i = 0; i < entidades.size(); i++) {
            resultado.add(aDTO(entidades.get(i)));
        }
        return resultado;
    }
}