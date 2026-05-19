package co.edu.unbosque.proyecto_bd1.model;

import co.edu.unbosque.proyecto_bd1.enums.EstadoPago;
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
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Pago")
    private Integer idPago;

    @Column(name = "monto", nullable = false, precision = 15, scale = 2)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "medio", nullable = false, length = 20)
    private MedioPago medio;

    @Column(name = "fecha", nullable = false)
private LocalDateTime fechaPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 15)
    private EstadoPago estado;

    @Column(name = "id_Reserva", nullable = false)
    private Integer idReserva;

    
    public Pago() {
    }

    public Pago(BigDecimal monto, MedioPago medio, LocalDateTime fechaPago,
                EstadoPago estado, Integer idReserva) {
        this.monto = monto;
        this.medio = medio;
        this.fechaPago = fechaPago;
        this.estado = estado;
        this.idReserva = idReserva;
    }

    // ===== Getters y Setters =====
    public Integer getIdPago() {
        return idPago;
    }

    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public MedioPago getMedio() {
        return medio;
    }

    public void setMedio(MedioPago medio) {
        this.medio = medio;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public void setEstado(EstadoPago estado) {
        this.estado = estado;
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

  
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pago otro = (Pago) o;
        return Objects.equals(idPago, otro.idPago);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPago);
    }

    @Override
    public String toString() {
        return "Pago{idPago=" + idPago + ", monto=" + monto + ", medio=" + medio
                + ", fechaPago=" + fechaPago + ", estado=" + estado
                + ", idReserva=" + idReserva + "}";
    }
}