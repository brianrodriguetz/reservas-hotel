package co.edu.unbosque.proyecto_bd1.mappers;

import co.edu.unbosque.proyecto_bd1.dto.PagoDTO;
import co.edu.unbosque.proyecto_bd1.model.Pago;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {

    public PagoDTO aDTO(Pago entidad) {
        if (entidad == null) {
            return null;
        }
        PagoDTO dto = new PagoDTO();
        dto.setIdPago(entidad.getIdPago());
        dto.setMonto(entidad.getMonto());
        dto.setMedio(entidad.getMedio());
        dto.setFechaPago(entidad.getFechaPago());
        dto.setEstado(entidad.getEstado());
        dto.setIdReserva(entidad.getIdReserva());
        return dto;
    }

    public Pago aEntidad(PagoDTO dto) {
        if (dto == null) {
            return null;
        }
        Pago entidad = new Pago();
        entidad.setIdPago(dto.getIdPago());
        entidad.setMonto(dto.getMonto());
        entidad.setMedio(dto.getMedio());
        entidad.setFechaPago(dto.getFechaPago());
        entidad.setEstado(dto.getEstado());
        entidad.setIdReserva(dto.getIdReserva());
        return entidad;
    }

    public List<PagoDTO> aDTOLista(List<Pago> entidades) {
        List<PagoDTO> resultado = new ArrayList<>();
        if (entidades == null) {
            return resultado;
        }
        for (int i = 0; i < entidades.size(); i++) {
            resultado.add(aDTO(entidades.get(i)));
        }
        return resultado;
    }
}