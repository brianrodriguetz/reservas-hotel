package co.edu.unbosque.proyecto_bd1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "reserva_habitacion")
@IdClass(ReservaHabitacionId.class)
public class ReservaHabitacion {

    @Id
    @Column(name = "id_Reserva")
    private Integer idReserva;

    @Id
    @Column(name = "id_Habitacion")
    private Integer idHabitacion;

    @Column(name = "numero_huespedes", nullable = false)
    private Byte numeroHuespedes;

   
    public ReservaHabitacion() {
    }

    public ReservaHabitacion(Integer idReserva, Integer idHabitacion, Byte numeroHuespedes) {
        this.idReserva = idReserva;
        this.idHabitacion = idHabitacion;
        this.numeroHuespedes = numeroHuespedes;
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

    public Byte getNumeroHuespedes() {
        return numeroHuespedes;
    }

    public void setNumeroHuespedes(Byte numeroHuespedes) {
        this.numeroHuespedes = numeroHuespedes;
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
        ReservaHabitacion otra = (ReservaHabitacion) o;
        return Objects.equals(idReserva, otra.idReserva)
            && Objects.equals(idHabitacion, otra.idHabitacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReserva, idHabitacion);
    }

    @Override
    public String toString() {
        return "ReservaHabitacion{idReserva=" + idReserva + ", idHabitacion=" + idHabitacion
                + ", numeroHuespedes=" + numeroHuespedes + "}";
    }
}