package co.edu.unbosque.proyecto_bd1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "check_out")
public class CheckOut {

    @Id
    @Column(name = "id_Evento")
    private Integer idEvento;

    public CheckOut() {
    }

    public CheckOut(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckOut otro = (CheckOut) o;
        return Objects.equals(idEvento, otro.idEvento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEvento);
    }

    @Override
    public String toString() {
        return "CheckOut{idEvento=" + idEvento + "}";
    }
}