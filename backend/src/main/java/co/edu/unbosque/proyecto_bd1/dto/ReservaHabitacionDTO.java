package co.edu.unbosque.proyecto_bd1.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ReservaHabitacionDTO {

    @NotNull(message = "El id de la reserva es obligatorio")
    @Positive(message = "El id de la reserva debe ser positivo")
    private Integer idReserva;

    @NotNull(message = "El id de la habitacion es obligatorio")
    @Positive(message = "El id de la habitacion debe ser positivo")
    private Integer idHabitacion;

    @NotNull(message = "El numero de huespedes es obligatorio")
    @Min(value = 1, message = "El numero de huespedes debe ser al menos 1")
    private Byte numeroHuespedes;

    // ===== Constructores =====
    public ReservaHabitacionDTO() {
    }

    public ReservaHabitacionDTO(Integer idReserva, Integer idHabitacion, Byte numeroHuespedes) {
        this.idReserva = idReserva;
        this.idHabitacion = idHabitacion;
        this.numeroHuespedes = numeroHuespedes;
    }

    // ===== Getters y Setters =====
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
}