package co.edu.unbosque.proyecto_bd1.mappers;

import co.edu.unbosque.proyecto_bd1.dto.AsignacionHuespedDTO;
import co.edu.unbosque.proyecto_bd1.model.AsignacionHuesped;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AsignacionHuespedMapper {

    public AsignacionHuespedDTO aDTO(AsignacionHuesped entidad) {
        if (entidad == null) {
            return null;
        }
        AsignacionHuespedDTO dto = new AsignacionHuespedDTO();
        dto.setIdHuesped(entidad.getIdHuesped());
        dto.setIdReserva(entidad.getIdReserva());
        dto.setIdHabitacion(entidad.getIdHabitacion());
        dto.setEsTitular(entidad.getEsTitular());
        return dto;
    }

    public AsignacionHuesped aEntidad(AsignacionHuespedDTO dto) {
        if (dto == null) {
            return null;
        }
        AsignacionHuesped entidad = new AsignacionHuesped();
        entidad.setIdHuesped(dto.getIdHuesped());
        entidad.setIdReserva(dto.getIdReserva());
        entidad.setIdHabitacion(dto.getIdHabitacion());
        entidad.setEsTitular(dto.getEsTitular());
        return entidad;
    }

    public List<AsignacionHuespedDTO> aDTOLista(List<AsignacionHuesped> entidades) {
        List<AsignacionHuespedDTO> resultado = new ArrayList<>();
        if (entidades == null) {
            return resultado;
        }
        for (int i = 0; i < entidades.size(); i++) {
            resultado.add(aDTO(entidades.get(i)));
        }
        return resultado;
    }
}