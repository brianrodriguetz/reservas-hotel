package co.edu.unbosque.proyecto_bd1.model;

import co.edu.unbosque.proyecto_bd1.enums.TipoDocumento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "persona",
       uniqueConstraints = @UniqueConstraint(
           name = "uk_persona_documento",
           columnNames = {"tipo_Documento", "numero_Documento"}
       ))
public class Persona {

    @Id
    @Column(name = "id_Cliente")
    private Integer idCliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_Documento", nullable = false, length = 5)
    private TipoDocumento tipoDocumento;

    @Column(name = "numero_Documento", nullable = false, length = 20)
    private String numeroDocumento;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "fecha_Nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "nacionalidad", nullable = false, length = 50)
    private String nacionalidad;

    
    public Persona() {
    }

    public Persona(Integer idCliente, TipoDocumento tipoDocumento, String numeroDocumento,
                   String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad) {
        this.idCliente = idCliente;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
    }

   
    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

   
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Persona otra = (Persona) o;
        return Objects.equals(idCliente, otra.idCliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCliente);
    }

    @Override
    public String toString() {
        return "Persona{idCliente=" + idCliente + ", tipoDocumento=" + tipoDocumento
                + ", numeroDocumento=" + numeroDocumento + ", nombre=" + nombre
                + ", apellido=" + apellido + ", fechaNacimiento=" + fechaNacimiento
                + ", nacionalidad=" + nacionalidad + "}";
    }
}