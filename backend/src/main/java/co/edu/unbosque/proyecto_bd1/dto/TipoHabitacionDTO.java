package co.edu.unbosque.proyecto_bd1.dto;

import co.edu.unbosque.proyecto_bd1.enums.NombreTipoHabitacion;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TipoHabitacionDTO {

    private Integer idTipo;

    @NotNull(message = "El nombre del tipo es obligatorio")
    private NombreTipoHabitacion nombre;

    @NotNull(message = "La capacidad maxima es obligatoria")
@Min(value = 1, message = "La capacidad maxima debe ser al menos 1")
private Byte capacidadMax;

@NotNull(message = "El numero de camas es obligatorio")
@Min(value = 1, message = "El numero de camas debe ser al menos 1")
private Byte numeroCamas;

    @NotNull(message = "El precio base por noche es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private BigDecimal precioBaseNoche;

    // ===== Constructores =====
    public TipoHabitacionDTO() {
    }

    public TipoHabitacionDTO(Integer idTipo, NombreTipoHabitacion nombre,
                             Byte capacidadMax, Byte numeroCamas,
                             BigDecimal precioBaseNoche) {
        this.idTipo = idTipo;
        this.nombre = nombre;
        this.capacidadMax = capacidadMax;
        this.numeroCamas = numeroCamas;
        this.precioBaseNoche = precioBaseNoche;
    }

    // ===== Getters y Setters =====
    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public NombreTipoHabitacion getNombre() {
        return nombre;
    }

    public void setNombre(NombreTipoHabitacion nombre) {
        this.nombre = nombre;
    }

    public Byte getCapacidadMax() {
        return capacidadMax;
    }

    public void setCapacidadMax(Byte capacidadMax) {
        this.capacidadMax = capacidadMax;
    }

    public Byte getNumeroCamas() {
        return numeroCamas;
    }

    public void setNumeroCamas(Byte numeroCamas) {
        this.numeroCamas = numeroCamas;
    }

    public BigDecimal getPrecioBaseNoche() {
        return precioBaseNoche;
    }

    public void setPrecioBaseNoche(BigDecimal precioBaseNoche) {
        this.precioBaseNoche = precioBaseNoche;
    }
}