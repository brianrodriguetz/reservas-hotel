package co.edu.unbosque.proyecto_bd1.mappers;

import co.edu.unbosque.proyecto_bd1.dto.CancelacionDTO;
import co.edu.unbosque.proyecto_bd1.dto.CheckInDTO;
import co.edu.unbosque.proyecto_bd1.dto.CheckOutDTO;
import co.edu.unbosque.proyecto_bd1.model.Cancelacion;
import co.edu.unbosque.proyecto_bd1.model.EventoReserva;
import org.springframework.stereotype.Component;

@Component
public class EventoReservaMapper {

    public CheckInDTO aCheckInDTO(EventoReserva evento) {
        if (evento == null) {
            return null;
        }
        CheckInDTO dto = new CheckInDTO();
        dto.setIdEvento(evento.getIdEvento());
        dto.setFechaHora(evento.getFechaHora());
        dto.setIdReserva(evento.getIdReserva());
        dto.setIdEmpleado(evento.getIdEmpleado());
        return dto;
    }

    public CheckOutDTO aCheckOutDTO(EventoReserva evento) {
        if (evento == null) {
            return null;
        }
        CheckOutDTO dto = new CheckOutDTO();
        dto.setIdEvento(evento.getIdEvento());
        dto.setFechaHora(evento.getFechaHora());
        dto.setIdReserva(evento.getIdReserva());
        dto.setIdEmpleado(evento.getIdEmpleado());
        return dto;
    }

    public CancelacionDTO aCancelacionDTO(EventoReserva evento, Cancelacion cancelacion) {
        if (evento == null || cancelacion == null) {
            return null;
        }
        CancelacionDTO dto = new CancelacionDTO();
        dto.setIdEvento(evento.getIdEvento());
        dto.setFechaHora(evento.getFechaHora());
        dto.setIdReserva(evento.getIdReserva());
        dto.setIdEmpleado(evento.getIdEmpleado());
        dto.setMotivo(cancelacion.getMotivo());
        dto.setPenalizacion(cancelacion.getPenalizacion());
        return dto;
    }
}