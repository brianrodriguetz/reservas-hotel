package co.edu.unbosque.proyecto_bd1.model;

import co.edu.unbosque.proyecto_bd1.enums.NombreTipoHabitacion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "tipo_habitacion")
public class TipoHabitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Tipo")
    private Integer idTipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre", nullable = false, length = 20)
    private NombreTipoHabitacion nombre;

    @Column(name = "capacidad_Max", nullable = false)
private Byte capacidadMax;

@Column(name = "numero_Camas", nullable = false)
private Byte numeroCamas;

    @Column(name = "precioBaseNoche", nullable = false, precision = 15, scale = 2)
    private BigDecimal precioBaseNoche;

    // ===== Constructores =====
    public TipoHabitacion() {
    }

   public TipoHabitacion(NombreTipoHabitacion nombre, Byte capacidadMax,
                      Byte numeroCamas, BigDecimal precioBaseNoche) {
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

    // ===== Equals / HashCode =====
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TipoHabitacion otro = (TipoHabitacion) o;
        return Objects.equals(idTipo, otro.idTipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTipo);
    }

    @Override
    public String toString() {
        return "TipoHabitacion{idTipo=" + idTipo + ", nombre=" + nombre
                + ", capacidadMax=" + capacidadMax + ", numeroCamas=" + numeroCamas
                + ", precioBaseNoche=" + precioBaseNoche + "}";
    }
}