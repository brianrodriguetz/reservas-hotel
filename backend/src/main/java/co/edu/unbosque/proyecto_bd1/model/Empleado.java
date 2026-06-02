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
import java.util.Objects;

@Entity
@Table(name = "empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Empleado")
    private Integer idEmpleado;

    @Column(name = "numero_documento", nullable = false, length = 20, unique = true)
    private String numeroDocumento;

    @Column(name = "usuario", nullable = false, length = 50, unique = true)
    private String usuario;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 10)
    private EstadoActivo estado;

    
    @Column(name = "id_Rol", nullable = false)
    private Integer idRol;

    
    @Column(name = "id_Supervisor")
    private Integer idSupervisor;

    
    public Empleado() {
    }

    public Empleado(String numeroDocumento, String usuario, String nombre, String apellido, EstadoActivo estado, Integer idRol, Integer idSupervisor) {
        this.numeroDocumento = numeroDocumento;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.estado = estado;
        this.idRol = idRol;
        this.idSupervisor = idSupervisor;
    }

    
    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public EstadoActivo getEstado() {
        return estado;
    }

    public void setEstado(EstadoActivo estado) {
        this.estado = estado;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public Integer getIdSupervisor() {
        return idSupervisor;
    }

    public void setIdSupervisor(Integer idSupervisor) {
        this.idSupervisor = idSupervisor;
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
        Empleado otro = (Empleado) o;
        return Objects.equals(idEmpleado, otro.idEmpleado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEmpleado);
    }

    @Override
    public String toString() {
        return "Empleado{idEmpleado=" + idEmpleado + ", numeroDocumento=" + numeroDocumento
                + ", usuario=" + usuario + ", nombre=" + nombre + ", apellido=" + apellido
                + ", estado=" + estado + ", idRol=" + idRol + ", idSupervisor=" + idSupervisor + "}";
    }
}