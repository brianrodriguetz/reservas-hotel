package co.edu.unbosque.proyecto_bd1.dto;

import co.edu.unbosque.proyecto_bd1.enums.EstadoActivo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class EmpleadoDTO {

    private Integer idEmpleado;

    @NotBlank(message = "El numero de documento es obligatorio")
    @Size(max = 20, message = "El numero de documento no debe superar 20 caracteres")
    private String numeroDocumento;

    @NotBlank(message = "El usuario es obligatorio")
    @Size(max = 50, message = "El usuario no debe superar 50 caracteres")
    private String usuario;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe superar 100 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no debe superar 100 caracteres")
    private String apellido;

    @NotNull(message = "El estado es obligatorio")
    private EstadoActivo estado;

    @NotNull(message = "El rol es obligatorio")
    @Positive(message = "El id del rol debe ser positivo")
    private Integer idRol;

    // Opcional: solo el Administrador no tiene supervisor
    @Positive(message = "El id del supervisor debe ser positivo")
    private Integer idSupervisor;

    // ===== Atributo derivado (NO se almacena, se calcula) =====
    private String nombreCompleto;

    // ===== Constructores =====
    public EmpleadoDTO() {
    }

    public EmpleadoDTO(Integer idEmpleado, String numeroDocumento, String usuario,
                       String nombre, String apellido, EstadoActivo estado,
                       Integer idRol, Integer idSupervisor) {
        this.idEmpleado = idEmpleado;
        this.numeroDocumento = numeroDocumento;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.estado = estado;
        this.idRol = idRol;
        this.idSupervisor = idSupervisor;
    }

    // ===== Getters y Setters =====
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

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
}