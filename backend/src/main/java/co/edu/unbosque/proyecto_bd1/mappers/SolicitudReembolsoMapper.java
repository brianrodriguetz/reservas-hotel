package co.edu.unbosque.proyecto_bd1.mappers;

import co.edu.unbosque.proyecto_bd1.dto.SolicitudReembolsoDTO;
import co.edu.unbosque.proyecto_bd1.model.SolicitudReembolso;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SolicitudReembolsoMapper {

    public SolicitudReembolsoDTO aDTO(SolicitudReembolso entidad) {
        if (entidad == null) {
            return null;
        }
        SolicitudReembolsoDTO dto = new SolicitudReembolsoDTO();
        dto.setIdSolicitud(entidad.getIdSolicitud());
        dto.setMotivo(entidad.getMotivo());
        dto.setEstado(entidad.getEstado());
        dto.setMedio(entidad.getMedio());
        dto.setMonto(entidad.getMonto());
        dto.setFechaProcesamiento(entidad.getFechaProcesamiento());
        dto.setIdCancelacion(entidad.getIdCancelacion());
        dto.setIdEmpleado(entidad.getIdEmpleado());
        dto.setFecha(entidad.getFecha());
        return dto;
    }

    public SolicitudReembolso aEntidad(SolicitudReembolsoDTO dto) {
        if (dto == null) {
            return null;
        }
        SolicitudReembolso entidad = new SolicitudReembolso();
        entidad.setIdSolicitud(dto.getIdSolicitud());
        entidad.setMotivo(dto.getMotivo());
        entidad.setEstado(dto.getEstado());
        entidad.setMedio(dto.getMedio());
        entidad.setMonto(dto.getMonto());
        entidad.setFechaProcesamiento(dto.getFechaProcesamiento());
        entidad.setIdCancelacion(dto.getIdCancelacion());
        entidad.setIdEmpleado(dto.getIdEmpleado());
        entidad.setFecha(dto.getFecha());
        return entidad;
    }

    public List<SolicitudReembolsoDTO> aDTOLista(List<SolicitudReembolso> entidades) {
        List<SolicitudReembolsoDTO> resultado = new ArrayList<>();
        if (entidades == null) {
            return resultado;
        }
        for (int i = 0; i < entidades.size(); i++) {
            resultado.add(aDTO(entidades.get(i)));
        }
        return resultado;
    }
}