package co.edu.unbosque.proyecto_bd1.dto;

import co.edu.unbosque.proyecto_bd1.enums.EstadoHabitacion;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class HabitacionDTO {

    private Integer idHabitacion;

    @NotBlank(message = "El codigo es obligatorio")
    @Size(max = 10, message = "El codigo no debe superar 10 caracteres")
    private String codigo;

    @NotNull(message = "El piso es obligatorio")
    @Min(value = 1, message = "El piso debe ser al menos 1")
    private Byte piso;

    @NotNull(message = "El estado es obligatorio")
    private EstadoHabitacion estado;

    @NotNull(message = "El tipo de habitacion es obligatorio")
    @Positive(message = "El id del tipo debe ser positivo")
    private Integer idTipo;

    // ===== Constructores =====
    public HabitacionDTO() {
    }

    public HabitacionDTO(Integer idHabitacion, String codigo, Byte piso,
                         EstadoHabitacion estado, Integer idTipo) {
        this.idHabitacion = idHabitacion;
        this.codigo = codigo;
        this.piso = piso;
        this.estado = estado;
        this.idTipo = idTipo;
    }

    // ===== Getters y Setters =====
    public Integer getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(Integer idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Byte getPiso() {
        return piso;
    }

    public void setPiso(Byte piso) {
        this.piso = piso;
    }

    public EstadoHabitacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoHabitacion estado) {
        this.estado = estado;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }
}