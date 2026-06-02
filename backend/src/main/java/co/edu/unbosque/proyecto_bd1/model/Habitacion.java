package co.edu.unbosque.proyecto_bd1.model;

import co.edu.unbosque.proyecto_bd1.enums.EstadoHabitacion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "habitacion")
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Habitacion")
    private Integer idHabitacion;

    @Column(name = "codigo", nullable = false, length = 10, unique = true)
    private String codigo;

    @Column(name = "piso", nullable = false)
    private Byte piso;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoHabitacion estado;

  
    @Column(name = "id_Tipo", nullable = false)
    private Integer idTipo;

    
    public Habitacion() {
    }

    public Habitacion(String codigo, Byte piso, EstadoHabitacion estado, Integer idTipo) {
        this.codigo = codigo;
        this.piso = piso;
        this.estado = estado;
        this.idTipo = idTipo;
    }

  
    public Integer getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(Integer idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Byte getPiso() {
        return piso;
    }

    public void setPiso(Byte piso) {
        this.piso = piso;
    }

    public EstadoHabitacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoHabitacion estado) {
        this.estado = estado;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

   
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Habitacion otra = (Habitacion) o;
        return Objects.equals(idHabitacion, otra.idHabitacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idHabitacion);
    }

    @Override
    public String toString() {
        return "Habitacion{idHabitacion=" + idHabitacion + ", codigo=" + codigo
                + ", piso=" + piso + ", estado=" + estado + ", idTipo=" + idTipo + "}";
    }
}