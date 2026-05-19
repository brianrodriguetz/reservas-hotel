package co.edu.unbosque.proyecto_bd1.model;

import java.io.Serializable;
import java.util.Objects;

public class AsignacionHuespedId implements Serializable {

    private Integer idHuesped;
    private Integer idReserva;
    private Integer idHabitacion;


    public AsignacionHuespedId() {
    }

    public AsignacionHuespedId(Integer idHuesped, Integer idReserva, Integer idHabitacion) {
        this.idHuesped = idHuesped;
        this.idReserva = idReserva;
        this.idHabitacion = idHabitacion;
    }


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

    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AsignacionHuespedId otro = (AsignacionHuespedId) o;
        return Objects.equals(idHuesped, otro.idHuesped)
            && Objects.equals(idReserva, otro.idReserva)
            && Objects.equals(idHabitacion, otro.idHabitacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idHuesped, idReserva, idHabitacion);
    }
}