package co.edu.unbosque.proyecto_bd1.model;

import co.edu.unbosque.proyecto_bd1.enums.TipoDocumento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "huesped",
       uniqueConstraints = @UniqueConstraint(
           name = "uk_huesped_documento",
           columnNames = {"tipo_Documento", "numero_Documento"}
       ))
public class Huesped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Huesped")
    private Integer idHuesped;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_Documento", nullable = false, length = 5)
    private TipoDocumento tipoDocumento;

    @Column(name = "numero_Documento", nullable = false, length = 20)
    private String numeroDocumento;

    @Column(name = "nacionalidad", nullable = false, length = 50)
    private String nacionalidad;

    @Column(name = "fecha_Nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    // ===== Constructores =====
    public Huesped() {
    }

    public Huesped(String nombre, String apellido, TipoDocumento tipoDocumento,
                   String numeroDocumento, String nacionalidad, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
    }

    // ===== Getters y Setters =====
    public Integer getIdHuesped() {
        return idHuesped;
    }

    public void setIdHuesped(Integer idHuesped) {
        this.idHuesped = idHuesped;
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

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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
        Huesped otro = (Huesped) o;
        return Objects.equals(idHuesped, otro.idHuesped);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idHuesped);
    }

    @Override
    public String toString() {
        return "Huesped{idHuesped=" + idHuesped + ", nombre=" + nombre
                + ", apellido=" + apellido + ", tipoDocumento=" + tipoDocumento
                + ", numeroDocumento=" + numeroDocumento + ", nacionalidad=" + nacionalidad
                + ", fechaNacimiento=" + fechaNacimiento + "}";
    }
}