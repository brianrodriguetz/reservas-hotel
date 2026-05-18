package co.edu.unbosque.proyecto_bd1.mappers;

import co.edu.unbosque.proyecto_bd1.dto.ReservaDTO;
import co.edu.unbosque.proyecto_bd1.model.Reserva;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper {

    public ReservaDTO aDTO(Reserva entidad) {
        if (entidad == null) {
            return null;
        }
        ReservaDTO dto = new ReservaDTO();
        dto.setIdReserva(entidad.getIdReserva());
        dto.setCanal(entidad.getCanal());
        dto.setFechaCreacion(entidad.getFechaCreacion());
        dto.setFechaCheckInPrevista(entidad.getFechaCheckInPrevista());
        dto.setFechaCheckOutPrevista(entidad.getFechaCheckOutPrevista());
        dto.setEstado(entidad.getEstado());
        dto.setPrecioTotal(entidad.getPrecioTotal());
        dto.setIdCliente(entidad.getIdCliente());

        // Atributo derivado
        dto.setNumeroNoches(calcularNumeroNoches(
            entidad.getFechaCheckInPrevista(),
            entidad.getFechaCheckOutPrevista()
        ));

        return dto;
    }

    public Reserva aEntidad(ReservaDTO dto) {
        if (dto == null) {
            return null;
        }
        Reserva entidad = new Reserva();
        entidad.setIdReserva(dto.getIdReserva());
        entidad.setCanal(dto.getCanal());
        entidad.setFechaCreacion(dto.getFechaCreacion());
        entidad.setFechaCheckInPrevista(dto.getFechaCheckInPrevista());
        entidad.setFechaCheckOutPrevista(dto.getFechaCheckOutPrevista());
        entidad.setEstado(dto.getEstado());
        entidad.setPrecioTotal(dto.getPrecioTotal());
        entidad.setIdCliente(dto.getIdCliente());
        return entidad;
    }

    public List<ReservaDTO> aDTOLista(List<Reserva> entidades) {
        List<ReservaDTO> resultado = new ArrayList<>();
        if (entidades == null) {
            return resultado;
        }
        for (int i = 0; i < entidades.size(); i++) {
            resultado.add(aDTO(entidades.get(i)));
        }
        return resultado;
    }

    // ===== Calculo de atributo derivado =====

    private Integer calcularNumeroNoches(LocalDateTime checkIn, LocalDateTime checkOut) {
        if (checkIn == null || checkOut == null) {
            return null;
        }
        long horas = Duration.between(checkIn, checkOut).toHours();
        // Una noche cada 24 horas, redondeando para arriba si excede
        long noches = horas / 24;
        if (horas % 24 > 0) {
            noches = noches + 1;
        }
        return (int) noches;
    }
}