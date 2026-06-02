package co.edu.unbosque.proyecto_bd1.model;

import java.io.Serializable;
import java.util.Objects;

public class ReservaHabitacionId implements Serializable {

    private Integer idReserva;
    private Integer idHabitacion;


    public ReservaHabitacionId() {
    }

    public ReservaHabitacionId(Integer idReserva, Integer idHabitacion) {
        this.idReserva = idReserva;
        this.idHabitacion = idHabitacion;
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

   
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReservaHabitacionId otro = (ReservaHabitacionId) o;
        return Objects.equals(idReserva, otro.idReserva)
            && Objects.equals(idHabitacion, otro.idHabitacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReserva, idHabitacion);
    }
}