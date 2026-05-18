package co.edu.unbosque.proyecto_bd1.web;

import co.edu.unbosque.proyecto_bd1.enums.NombreRol;
import java.io.Serializable;

/**
 * Objeto que se guarda en HttpSession para representar al empleado logueado.
 * Implementa Serializable porque las sesiones de Spring se pueden serializar.
 */
public class UsuarioSesion implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idEmpleado;
    private String usuario;
    private String nombreCompleto;
    private NombreRol rol;

    public UsuarioSesion() {
    }

    public UsuarioSesion(Integer idEmpleado, String usuario,
                         String nombreCompleto, NombreRol rol) {
        this.idEmpleado = idEmpleado;
        this.usuario = usuario;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
    }

 
    public boolean isAdministrador() {
        return rol == NombreRol.Administrador;
    }

    public boolean isRecepcionista() {
        return rol == NombreRol.Recepcionista;
    }

    public boolean isPersonalLimpieza() {
        return rol == NombreRol.Personal_Limpieza;
    }

    // ===== Getters / Setters =====
    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public NombreRol getRol() {
        return rol;
    }

    public void setRol(NombreRol rol) {
        this.rol = rol;
    }
}