package co.edu.unbosque.proyecto_bd1.mappers;

import co.edu.unbosque.proyecto_bd1.dto.ContactoDTO;
import co.edu.unbosque.proyecto_bd1.model.Contacto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ContactoMapper {

    public ContactoDTO aDTO(Contacto entidad) {
        if (entidad == null) {
            return null;
        }
        ContactoDTO dto = new ContactoDTO();
        dto.setIdContacto(entidad.getIdContacto());
        dto.setTipoContacto(entidad.getTipoContacto());
        dto.setValor(entidad.getValor());
        dto.setEsPrincipal(entidad.getEsPrincipal());
        dto.setIdCliente(entidad.getIdCliente());
        return dto;
    }

    public Contacto aEntidad(ContactoDTO dto) {
        if (dto == null) {
            return null;
        }
        Contacto entidad = new Contacto();
        entidad.setIdContacto(dto.getIdContacto());
        entidad.setTipoContacto(dto.getTipoContacto());
        entidad.setValor(dto.getValor());
        entidad.setEsPrincipal(dto.getEsPrincipal());
        entidad.setIdCliente(dto.getIdCliente());
        return entidad;
    }

    public List<ContactoDTO> aDTOLista(List<Contacto> entidades) {
        List<ContactoDTO> resultado = new ArrayList<>();
        if (entidades == null) {
            return resultado;
        }
        for (int i = 0; i < entidades.size(); i++) {
            resultado.add(aDTO(entidades.get(i)));
        }
        return resultado;
    }
}