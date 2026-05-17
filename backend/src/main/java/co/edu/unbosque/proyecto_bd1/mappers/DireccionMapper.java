package co.edu.unbosque.proyecto_bd1.mappers;

import co.edu.unbosque.proyecto_bd1.dto.DireccionDTO;
import co.edu.unbosque.proyecto_bd1.model.Direccion;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DireccionMapper {

    public DireccionDTO aDTO(Direccion entidad) {
        if (entidad == null) {
            return null;
        }
        DireccionDTO dto = new DireccionDTO();
        dto.setIdDireccion(entidad.getIdDireccion());
        dto.setTipoDireccion(entidad.getTipoDireccion());
        dto.setCalle(entidad.getCalle());
        dto.setNumero(entidad.getNumero());
        dto.setCiudad(entidad.getCiudad());
        dto.setDepartamento(entidad.getDepartamento());
        dto.setCodigoPostal(entidad.getCodigoPostal());
        dto.setPais(entidad.getPais());
        dto.setEsPrincipal(entidad.getEsPrincipal());
        dto.setIdCliente(entidad.getIdCliente());
        return dto;
    }

    public Direccion aEntidad(DireccionDTO dto) {
        if (dto == null) {
            return null;
        }
        Direccion entidad = new Direccion();
        entidad.setIdDireccion(dto.getIdDireccion());
        entidad.setTipoDireccion(dto.getTipoDireccion());
        entidad.setCalle(dto.getCalle());
        entidad.setNumero(dto.getNumero());
        entidad.setCiudad(dto.getCiudad());
        entidad.setDepartamento(dto.getDepartamento());
        entidad.setCodigoPostal(dto.getCodigoPostal());
        entidad.setPais(dto.getPais());
        entidad.setEsPrincipal(dto.getEsPrincipal());
        entidad.setIdCliente(dto.getIdCliente());
        return entidad;
    }

    public List<DireccionDTO> aDTOLista(List<Direccion> entidades) {
        List<DireccionDTO> resultado = new ArrayList<>();
        if (entidades == null) {
            return resultado;
        }
        for (int i = 0; i < entidades.size(); i++) {
            resultado.add(aDTO(entidades.get(i)));
        }
        return resultado;
    }
}