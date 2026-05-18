package co.edu.unbosque.proyecto_bd1.dto;

import co.edu.unbosque.proyecto_bd1.enums.EstadoSolicitudReembolso;
import co.edu.unbosque.proyecto_bd1.enums.MedioPago;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SolicitudReembolsoDTO {

    private Integer idSolicitud;

    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;

    @NotNull(message = "El estado es obligatorio")
    private EstadoSolicitudReembolso estado;

    @NotNull(message = "El medio de pago es obligatorio")
    private MedioPago medio;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0")
    private BigDecimal monto;

    // Nullable: solo se llena cuando se procesa la solicitud
    private LocalDateTime fechaProcesamiento;

    @NotNull(message = "El id de la cancelacion es obligatorio")
    @Positive(message = "El id de la cancelacion debe ser positivo")
    private Integer idCancelacion;

    // Nullable: solo se asigna cuando un empleado toma la solicitud
    private Integer idEmpleado;

    @NotNull(message = "La fecha de creacion es obligatoria")
    private LocalDateTime fecha;

    // ===== Constructores =====
    public SolicitudReembolsoDTO() {
    }

    public SolicitudReembolsoDTO(Integer idSolicitud, String motivo,
                                  EstadoSolicitudReembolso estado, MedioPago medio,
                                  BigDecimal monto, LocalDateTime fechaProcesamiento,
                                  Integer idCancelacion, Integer idEmpleado,
                                  LocalDateTime fecha) {
        this.idSolicitud = idSolicitud;
        this.motivo = motivo;
        this.estado = estado;
        this.medio = medio;
        this.monto = monto;
        this.fechaProcesamiento = fechaProcesamiento;
        this.idCancelacion = idCancelacion;
        this.idEmpleado = idEmpleado;
        this.fecha = fecha;
    }

    // ===== Getters y Setters =====
    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public EstadoSolicitudReembolso getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitudReembolso estado) {
        this.estado = estado;
    }

    public MedioPago getMedio() {
        return medio;
    }

    public void setMedio(MedioPago medio) {
        this.medio = medio;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaProcesamiento() {
        return fechaProcesamiento;
    }

    public void setFechaProcesamiento(LocalDateTime fechaProcesamiento) {
        this.fechaProcesamiento = fechaProcesamiento;
    }

    public Integer getIdCancelacion() {
        return idCancelacion;
    }

    public void setIdCancelacion(Integer idCancelacion) {
        this.idCancelacion = idCancelacion;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}