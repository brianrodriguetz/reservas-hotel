package co.edu.unbosque.proyecto_bd1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AsignacionHuespedDTO {

    @NotNull(message = "El id del huesped es obligatorio")
    @Positive(message = "El id del huesped debe ser positivo")
    private Integer idHuesped;

    @NotNull(message = "El id de la reserva es obligatorio")
    @Positive(message = "El id de la reserva debe ser positivo")
    private Integer idReserva;

    @NotNull(message = "El id de la habitacion es obligatorio")
    @Positive(message = "El id de la habitacion debe ser positivo")
    private Integer idHabitacion;

    @NotNull(message = "Indicar si es titular es obligatorio")
    private Boolean esTitular;

    // ===== Constructores =====
    public AsignacionHuespedDTO() {
    }

    public AsignacionHuespedDTO(Integer idHuesped, Integer idReserva,
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
}