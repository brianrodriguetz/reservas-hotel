package co.edu.unbosque.proyecto_bd1.dto;


import co.edu.unbosque.proyecto_bd1.enums.EstadoActivo;
import co.edu.unbosque.proyecto_bd1.enums.NombreRol;
import jakarta.validation.constraints.NotNull;

public class RolDTO {

    
    private Integer idRol;

    @NotNull(message = "El nombre del rol es obligatorio")
    private NombreRol nombre;

    @NotNull(message = "El estado es obligatorio")
    private EstadoActivo estado;

   
    public RolDTO() {
    }

    public RolDTO(Integer idRol, NombreRol nombre, EstadoActivo estado) {
        this.idRol = idRol;
        this.nombre = nombre;
        this.estado = estado;
    }

    
    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public NombreRol getNombre() {
        return nombre;
    }

    public void setNombre(NombreRol nombre) {
        this.nombre = nombre;
    }

    public EstadoActivo getEstado() {
        return estado;
    }

    public void setEstado(EstadoActivo estado) {
        this.estado = estado;
    }
}