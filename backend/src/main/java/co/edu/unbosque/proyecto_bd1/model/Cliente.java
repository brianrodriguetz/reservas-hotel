package co.edu.unbosque.proyecto_bd1.model;

import co.edu.unbosque.proyecto_bd1.enums.EstadoActivo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Cliente")
    private Integer idCliente;

    @Column(name = "fecha_Registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 10)
    private EstadoActivo estado;

    // ===== Constructores =====
    public Cliente() {
    }

    public Cliente(LocalDateTime fechaRegistro, EstadoActivo estado) {
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
    }

    // ===== Getters y Setters =====
    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public EstadoActivo getEstado() {
        return estado;
    }

    public void setEstado(EstadoActivo estado) {
        this.estado = estado;
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
        Cliente otro = (Cliente) o;
        return Objects.equals(idCliente, otro.idCliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCliente);
    }

    @Override
    public String toString() {
        return "Cliente{idCliente=" + idCliente + ", fechaRegistro=" + fechaRegistro
                + ", estado=" + estado + "}";
    }
}