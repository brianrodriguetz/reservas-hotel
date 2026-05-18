package co.edu.unbosque.proyecto_bd1.dto;

import co.edu.unbosque.proyecto_bd1.enums.CanalReserva;
import co.edu.unbosque.proyecto_bd1.enums.EstadoReserva;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReservaDTO {

    private Integer idReserva;

    @NotNull(message = "El canal es obligatorio")
    private CanalReserva canal;

    @NotNull(message = "La fecha de creacion es obligatoria")
    private LocalDateTime fechaCreacion;

    @NotNull(message = "La fecha de check-in prevista es obligatoria")
    private LocalDateTime fechaCheckInPrevista;

    @NotNull(message = "La fecha de check-out prevista es obligatoria")
    private LocalDateTime fechaCheckOutPrevista;

    @NotNull(message = "El estado es obligatorio")
    private EstadoReserva estado;

    @NotNull(message = "El precio total es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio total no puede ser negativo")
    private BigDecimal precioTotal;

    @NotNull(message = "El id del cliente es obligatorio")
    @Positive(message = "El id del cliente debe ser positivo")
    private Integer idCliente;

    // ===== Atributo derivado (calculado en mapper) =====
    private Integer numeroNoches;

    // ===== Constructores =====
    public ReservaDTO() {
    }

    public ReservaDTO(Integer idReserva, CanalReserva canal, LocalDateTime fechaCreacion,
                      LocalDateTime fechaCheckInPrevista, LocalDateTime fechaCheckOutPrevista,
                      EstadoReserva estado, BigDecimal precioTotal, Integer idCliente) {
        this.idReserva = idReserva;
        this.canal = canal;
        this.fechaCreacion = fechaCreacion;
        this.fechaCheckInPrevista = fechaCheckInPrevista;
        this.fechaCheckOutPrevista = fechaCheckOutPrevista;
        this.estado = estado;
        this.precioTotal = precioTotal;
        this.idCliente = idCliente;
    }

    // ===== Getters y Setters =====
    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public CanalReserva getCanal() {
        return canal;
    }

    public void setCanal(CanalReserva canal) {
        this.canal = canal;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaCheckInPrevista() {
        return fechaCheckInPrevista;
    }

    public void setFechaCheckInPrevista(LocalDateTime fechaCheckInPrevista) {
        this.fechaCheckInPrevista = fechaCheckInPrevista;
    }

    public LocalDateTime getFechaCheckOutPrevista() {
        return fechaCheckOutPrevista;
    }

    public void setFechaCheckOutPrevista(LocalDateTime fechaCheckOutPrevista) {
        this.fechaCheckOutPrevista = fechaCheckOutPrevista;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getNumeroNoches() {
        return numeroNoches;
    }

    public void setNumeroNoches(Integer numeroNoches) {
        this.numeroNoches = numeroNoches;
    }
}