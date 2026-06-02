package co.edu.unbosque.proyecto_bd1.mappers;

import co.edu.unbosque.proyecto_bd1.dto.ReservaHabitacionDTO;
import co.edu.unbosque.proyecto_bd1.model.ReservaHabitacion;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ReservaHabitacionMapper {

    public ReservaHabitacionDTO aDTO(ReservaHabitacion entidad) {
        if (entidad == null) {
            return null;
        }
        ReservaHabitacionDTO dto = new ReservaHabitacionDTO();
        dto.setIdReserva(entidad.getIdReserva());
        dto.setIdHabitacion(entidad.getIdHabitacion());
        dto.setNumeroHuespedes(entidad.getNumeroHuespedes());
        return dto;
    }

    public ReservaHabitacion aEntidad(ReservaHabitacionDTO dto) {
        if (dto == null) {
            return null;
        }
        ReservaHabitacion entidad = new ReservaHabitacion();
        entidad.setIdReserva(dto.getIdReserva());
        entidad.setIdHabitacion(dto.getIdHabitacion());
        entidad.setNumeroHuespedes(dto.getNumeroHuespedes());
        return entidad;
    }

    public List<ReservaHabitacionDTO> aDTOLista(List<ReservaHabitacion> entidades) {
        List<ReservaHabitacionDTO> resultado = new ArrayList<>();
        if (entidades == null) {
            return resultado;
        }
        for (int i = 0; i < entidades.size(); i++) {
            resultado.add(aDTO(entidades.get(i)));
        }
        return resultado;
    }
}