package co.edu.unbosque.proyecto_bd1.model;

import co.edu.unbosque.proyecto_bd1.enums.CanalReserva;
import co.edu.unbosque.proyecto_bd1.enums.EstadoReserva;
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
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Reserva")
    private Integer idReserva;

    @Enumerated(EnumType.STRING)
    @Column(name = "canal", nullable = false, length = 15)
    private CanalReserva canal;

    @Column(name = "fecha_Creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fechaCheckInPrevista", nullable = false)
    private LocalDateTime fechaCheckInPrevista;

    @Column(name = "fechaCheckOutPrevista", nullable = false)
    private LocalDateTime fechaCheckOutPrevista;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 15)
    private EstadoReserva estado;

    @Column(name = "precio_Total", nullable = false, precision = 15, scale = 2)
    private BigDecimal precioTotal;

    @Column(name = "id_Cliente", nullable = false)
    private Integer idCliente;

    
    public Reserva() {
    }

    public Reserva(CanalReserva canal, LocalDateTime fechaCreacion,
                   LocalDateTime fechaCheckInPrevista, LocalDateTime fechaCheckOutPrevista,
                   EstadoReserva estado, BigDecimal precioTotal, Integer idCliente) {
        this.canal = canal;
        this.fechaCreacion = fechaCreacion;
        this.fechaCheckInPrevista = fechaCheckInPrevista;
        this.fechaCheckOutPrevista = fechaCheckOutPrevista;
        this.estado = estado;
        this.precioTotal = precioTotal;
        this.idCliente = idCliente;
    }

   
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

   
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reserva otra = (Reserva) o;
        return Objects.equals(idReserva, otra.idReserva);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReserva);
    }

    @Override
    public String toString() {
        return "Reserva{idReserva=" + idReserva + ", canal=" + canal
                + ", fechaCreacion=" + fechaCreacion
                + ", fechaCheckInPrevista=" + fechaCheckInPrevista
                + ", fechaCheckOutPrevista=" + fechaCheckOutPrevista
                + ", estado=" + estado + ", precioTotal=" + precioTotal
                + ", idCliente=" + idCliente + "}";
    }
}