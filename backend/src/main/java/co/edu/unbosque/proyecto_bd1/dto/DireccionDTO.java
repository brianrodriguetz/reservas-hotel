package co.edu.unbosque.proyecto_bd1.dto;

import co.edu.unbosque.proyecto_bd1.enums.TipoDireccion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class DireccionDTO {

    private Integer idDireccion;

    @NotNull(message = "El tipo de direccion es obligatorio")
    private TipoDireccion tipoDireccion;

    @NotBlank(message = "La calle es obligatoria")
    @Size(max = 100, message = "La calle no debe superar 100 caracteres")
    private String calle;

    @NotBlank(message = "El numero es obligatorio")
    @Size(max = 20, message = "El numero no debe superar 20 caracteres")
    private String numero;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 80, message = "La ciudad no debe superar 80 caracteres")
    private String ciudad;

    @NotBlank(message = "El departamento es obligatorio")
    @Size(max = 80, message = "El departamento no debe superar 80 caracteres")
    private String departamento;

    @NotBlank(message = "El codigo postal es obligatorio")
    @Size(max = 10, message = "El codigo postal no debe superar 10 caracteres")
    private String codigoPostal;

    @NotBlank(message = "El pais es obligatorio")
    @Size(max = 50, message = "El pais no debe superar 50 caracteres")
    private String pais;

    @NotNull(message = "Indicar si es principal es obligatorio")
    private Boolean esPrincipal;

    @NotNull(message = "El id del cliente es obligatorio")
    @Positive(message = "El id del cliente debe ser positivo")
    private Integer idCliente;

    // ===== Constructores =====
    public DireccionDTO() {
    }

    public DireccionDTO(Integer idDireccion, TipoDireccion tipoDireccion, String calle,
                        String numero, String ciudad, String departamento,
                        String codigoPostal, String pais, Boolean esPrincipal,
                        Integer idCliente) {
        this.idDireccion = idDireccion;
        this.tipoDireccion = tipoDireccion;
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.departamento = departamento;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
        this.esPrincipal = esPrincipal;
        this.idCliente = idCliente;
    }

    // ===== Getters y Setters =====
    public Integer getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }

    public TipoDireccion getTipoDireccion() {
        return tipoDireccion;
    }

    public void setTipoDireccion(TipoDireccion tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Boolean getEsPrincipal() {
        return esPrincipal;
    }

    public void setEsPrincipal(Boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }
}