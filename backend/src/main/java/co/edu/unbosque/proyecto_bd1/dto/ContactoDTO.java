package co.edu.unbosque.proyecto_bd1.dto;

import co.edu.unbosque.proyecto_bd1.enums.TipoContacto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ContactoDTO {

    private Integer idContacto;

    @NotNull(message = "El tipo de contacto es obligatorio")
    private TipoContacto tipoContacto;

    @NotBlank(message = "El valor es obligatorio")
    @Size(max = 150, message = "El valor no debe superar 150 caracteres")
    private String valor;

    @NotNull(message = "Indicar si es principal es obligatorio")
    private Boolean esPrincipal;

    @NotNull(message = "El id del cliente es obligatorio")
    @Positive(message = "El id del cliente debe ser positivo")
    private Integer idCliente;

   
    public ContactoDTO() {
    }

    public ContactoDTO(Integer idContacto, TipoContacto tipoContacto, String valor,
                       Boolean esPrincipal, Integer idCliente) {
        this.idContacto = idContacto;
        this.tipoContacto = tipoContacto;
        this.valor = valor;
        this.esPrincipal = esPrincipal;
        this.idCliente = idCliente;
    }

    
    public Integer getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(Integer idContacto) {
        this.idContacto = idContacto;
    }

    public TipoContacto getTipoContacto() {
        return tipoContacto;
    }

    public void setTipoContacto(TipoContacto tipoContacto) {
        this.tipoContacto = tipoContacto;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
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