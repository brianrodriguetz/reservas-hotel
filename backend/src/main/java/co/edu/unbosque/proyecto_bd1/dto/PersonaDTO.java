package co.edu.unbosque.proyecto_bd1.dto;

import co.edu.unbosque.proyecto_bd1.enums.EstadoActivo;
import co.edu.unbosque.proyecto_bd1.enums.TipoDocumento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PersonaDTO {

    // ===== Datos del Cliente padre =====
    // idCliente y fechaRegistro: la BD los genera (al crear, se ignoran)
    private Integer idCliente;
    private LocalDateTime fechaRegistro;

    @NotNull(message = "El estado es obligatorio")
    private EstadoActivo estado;

    // ===== Datos especificos de Persona =====
    @NotNull(message = "El tipo de documento es obligatorio")
    private TipoDocumento tipoDocumento;

    @NotBlank(message = "El numero de documento es obligatorio")
    @Size(max = 20, message = "El numero de documento no debe superar 20 caracteres")
    private String numeroDocumento;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe superar 100 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no debe superar 100 caracteres")
    private String apellido;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "La nacionalidad es obligatoria")
    @Size(max = 50, message = "La nacionalidad no debe superar 50 caracteres")
    private String nacionalidad;

    // ===== Atributos derivados (read-only, calculados por mapper) =====
    private String nombreCompleto;
    private Integer edad;

    // ===== Constructores =====
    public PersonaDTO() {
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

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }
}