package co.edu.unbosque.proyecto_bd1.dto;

import co.edu.unbosque.proyecto_bd1.enums.EstadoActivo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class EmpresaDTO {

    // ===== Datos del Cliente padre =====
    private Integer idCliente;
    private LocalDateTime fechaRegistro;

    @NotNull(message = "El estado es obligatorio")
    private EstadoActivo estado;

    // ===== Datos especificos de Empresa =====
    @NotBlank(message = "El NIT es obligatorio")
    @Size(max = 20, message = "El NIT no debe superar 20 caracteres")
    private String nit;

    @NotBlank(message = "La razon social es obligatoria")
    @Size(max = 150, message = "La razon social no debe superar 150 caracteres")
    private String razonSocial;

    @NotBlank(message = "El representante legal es obligatorio")
    @Size(max = 200, message = "El representante legal no debe superar 200 caracteres")
    private String representanteLegal;

    @NotBlank(message = "El sector economico es obligatorio")
    @Size(max = 100, message = "El sector economico no debe superar 100 caracteres")
    private String sectorEconomico;

    // ===== Constructores =====
    public EmpresaDTO() {
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

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRepresentanteLegal() {
        return representanteLegal;
    }

    public void setRepresentanteLegal(String representanteLegal) {
        this.representanteLegal = representanteLegal;
    }

    public String getSectorEconomico() {
        return sectorEconomico;
    }

    public void setSectorEconomico(String sectorEconomico) {
        this.sectorEconomico = sectorEconomico;
    }
}