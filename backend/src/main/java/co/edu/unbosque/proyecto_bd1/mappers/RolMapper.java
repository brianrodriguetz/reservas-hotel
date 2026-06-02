package co.edu.unbosque.proyecto_bd1.mappers;

import co.edu.unbosque.proyecto_bd1.dto.RolDTO;
import co.edu.unbosque.proyecto_bd1.model.Rol;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class RolMapper {

    public RolDTO aDTO(Rol rol) {
        if (rol == null) {
            return null;
        }
        RolDTO dto = new RolDTO();
        dto.setIdRol(rol.getIdRol());
        dto.setNombre(rol.getNombre());
        dto.setEstado(rol.getEstado());
        return dto;
    }

    public Rol aEntidad(RolDTO dto) {
        if (dto == null) {
            return null;
        }
        Rol rol = new Rol();
        rol.setIdRol(dto.getIdRol());
        rol.setNombre(dto.getNombre());
        rol.setEstado(dto.getEstado());
        return rol;
    }

    public List<RolDTO> aDTOLista(List<Rol> roles) {
        List<RolDTO> resultado = new ArrayList<>();
        if (roles == null) {
            return resultado;
        }
        for (int i = 0; i < roles.size(); i++) {
            resultado.add(aDTO(roles.get(i)));
        }
        return resultado;
    }
}