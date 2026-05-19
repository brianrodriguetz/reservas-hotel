package co.edu.unbosque.proyecto_bd1.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CancelacionDTO {

    private Integer idEvento;

    @NotNull(message = "La fecha y hora son obligatorias")
    private LocalDateTime fechaHora;

    @NotNull(message = "El id de la reserva es obligatorio")
    @Positive(message = "El id de la reserva debe ser positivo")
    private Integer idReserva;

    @NotNull(message = "El id del empleado es obligatorio")
    @Positive(message = "El id del empleado debe ser positivo")
    private Integer idEmpleado;

    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;

    @NotNull(message = "La penalizacion es obligatoria")
    @DecimalMin(value = "0.00", message = "La penalizacion debe ser >= 0")
    @DecimalMax(value = "100.00", message = "La penalizacion no puede superar 100%")
    private BigDecimal penalizacion;

    public CancelacionDTO() {
    }

   
    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public BigDecimal getPenalizacion() {
        return penalizacion;
    }

    public void setPenalizacion(BigDecimal penalizacion) {
        this.penalizacion = penalizacion;
    }
}