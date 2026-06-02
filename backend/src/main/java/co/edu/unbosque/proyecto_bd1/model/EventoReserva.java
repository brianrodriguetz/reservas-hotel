package co.edu.unbosque.proyecto_bd1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "evento_reserva")
public class EventoReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Evento")
    private Integer idEvento;

    @Column(name = "fecha_Hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "id_Reserva", nullable = false)
    private Integer idReserva;

    @Column(name = "id_Empleado", nullable = false)
    private Integer idEmpleado;

    public EventoReserva() {
    }

    public EventoReserva(LocalDateTime fechaHora, Integer idReserva, Integer idEmpleado) {
        this.fechaHora = fechaHora;
        this.idReserva = idReserva;
        this.idEmpleado = idEmpleado;
    }

    // ===== Getters y Setters =====
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventoReserva otro = (EventoReserva) o;
        return Objects.equals(idEvento, otro.idEvento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEvento);
    }

    @Override
    public String toString() {
        return "EventoReserva{idEvento=" + idEvento + ", fechaHora=" + fechaHora
                + ", idReserva=" + idReserva + ", idEmpleado=" + idEmpleado + "}";
    }
}