package co.edu.unbosque.proyecto_bd1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

import co.edu.unbosque.proyecto_bd1.enums.EstadoActivo;
import co.edu.unbosque.proyecto_bd1.enums.NombreRol;

@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Rol")
    private Integer idRol;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre", nullable = false, length = 30)
    private NombreRol nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 10)
    private EstadoActivo estado;

    // ===== Constructores =====
    public Rol() {
    }

    public Rol(NombreRol nombre, EstadoActivo estado) {
        this.nombre = nombre;
        this.estado = estado;
    }

    // ===== Getters y Setters =====
    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public NombreRol getNombre() {
        return nombre;
    }

    public void setNombre(NombreRol nombre) {
        this.nombre = nombre;
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
        Rol otro = (Rol) o;
        return Objects.equals(idRol, otro.idRol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRol);
    }

    @Override
    public String toString() {
        return "Rol{idRol=" + idRol + ", nombre=" + nombre + ", estado=" + estado + "}";
    }
}