package co.edu.unbosque.proyecto_bd1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "asignacion_huesped")
@IdClass(AsignacionHuespedId.class)
public class AsignacionHuesped {

    @Id
    @Column(name = "id_Huesped")
    private Integer idHuesped;

    @Id
    @Column(name = "id_Reserva")
    private Integer idReserva;

    @Id
    @Column(name = "id_Habitacion")
    private Integer idHabitacion;

    @Column(name = "es_Titular", nullable = false)
    private Boolean esTitular;

    // ===== Constructores =====
    public AsignacionHuesped() {
    }

    public AsignacionHuesped(Integer idHuesped, Integer idReserva,
                             Integer idHabitacion, Boolean esTitular) {
        this.idHuesped = idHuesped;
        this.idReserva = idReserva;
        this.idHabitacion = idHabitacion;
        this.esTitular = esTitular;
    }

    // ===== Getters y Setters =====
    public Integer getIdHuesped() {
        return idHuesped;
    }

    public void setIdHuesped(Integer idHuesped) {
        this.idHuesped = idHuesped;
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public Integer getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(Integer idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public Boolean getEsTitular() {
        return esTitular;
    }

    public void setEsTitular(Boolean esTitular) {
        this.esTitular = esTitular;
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
        AsignacionHuesped otra = (AsignacionHuesped) o;
        return Objects.equals(idHuesped, otra.idHuesped)
            && Objects.equals(idReserva, otra.idReserva)
            && Objects.equals(idHabitacion, otra.idHabitacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idHuesped, idReserva, idHabitacion);
    }

    @Override
    public String toString() {
        return "AsignacionHuesped{idHuesped=" + idHuesped + ", idReserva=" + idReserva
                + ", idHabitacion=" + idHabitacion + ", esTitular=" + esTitular + "}";
    }
}