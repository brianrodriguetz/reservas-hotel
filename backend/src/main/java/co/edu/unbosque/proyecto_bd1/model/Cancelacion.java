package co.edu.unbosque.proyecto_bd1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "cancelacion")
public class Cancelacion {

    @Id
    @Column(name = "id_Evento")
    private Integer idEvento;

    @Column(name = "motivo", nullable = false, columnDefinition = "TEXT")
    private String motivo;

    @Column(name = "penalizacion", nullable = false, precision = 5, scale = 2)
    private BigDecimal penalizacion;

    public Cancelacion() {
    }

    public Cancelacion(Integer idEvento, String motivo, BigDecimal penalizacion) {
        this.idEvento = idEvento;
        this.motivo = motivo;
        this.penalizacion = penalizacion;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cancelacion otra = (Cancelacion) o;
        return Objects.equals(idEvento, otra.idEvento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEvento);
    }

    @Override
    public String toString() {
        return "Cancelacion{idEvento=" + idEvento + ", motivo=" + motivo
                + ", penalizacion=" + penalizacion + "}";
    }
}