package co.edu.unbosque.proyecto_bd1.model;

import co.edu.unbosque.proyecto_bd1.enums.EstadoSolicitudReembolso;
import co.edu.unbosque.proyecto_bd1.enums.MedioPago;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "solicitud_reembolso")
public class SolicitudReembolso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Solicitud")
    private Integer idSolicitud;

    @Column(name = "motivo", nullable = false, columnDefinition = "TEXT")
    private String motivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 15)
    private EstadoSolicitudReembolso estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "medio", nullable = false, length = 25)
    private MedioPago medio;

    @Column(name = "monto", nullable = false, precision = 15, scale = 2)
    private BigDecimal monto;

    @Column(name = "fecha_Procesamiento")
    private LocalDateTime fechaProcesamiento;

    @Column(name = "id_Cancelacion", nullable = false, unique = true)
    private Integer idCancelacion;

    @Column(name = "id_Empleado")
    private Integer idEmpleado;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    // ===== Constructores =====
    public SolicitudReembolso() {
    }

    public SolicitudReembolso(String motivo, EstadoSolicitudReembolso estado, MedioPago medio,
                              BigDecimal monto, LocalDateTime fechaProcesamiento,
                              Integer idCancelacion, Integer idEmpleado, LocalDateTime fecha) {
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

    // ===== Equals / HashCode =====
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SolicitudReembolso otra = (SolicitudReembolso) o;
        return Objects.equals(idSolicitud, otra.idSolicitud);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSolicitud);
    }

    @Override
    public String toString() {
        return "SolicitudReembolso{idSolicitud=" + idSolicitud + ", motivo=" + motivo
                + ", estado=" + estado + ", medio=" + medio + ", monto=" + monto
                + ", fechaProcesamiento=" + fechaProcesamiento
                + ", idCancelacion=" + idCancelacion + ", idEmpleado=" + idEmpleado
                + ", fecha=" + fecha + "}";
    }
}