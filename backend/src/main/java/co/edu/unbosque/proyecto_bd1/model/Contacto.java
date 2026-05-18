package co.edu.unbosque.proyecto_bd1.model;

import co.edu.unbosque.proyecto_bd1.enums.TipoContacto;
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
@Table(name = "contacto")
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Contacto")
    private Integer idContacto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_Contacto", nullable = false, length = 20)
    private TipoContacto tipoContacto;

    @Column(name = "valor", nullable = false, length = 150)
    private String valor;

    @Column(name = "es_Principal", nullable = false)
    private Boolean esPrincipal;

    // FK a Cliente (solo el id)
    @Column(name = "id_Cliente", nullable = false)
    private Integer idCliente;

    // ===== Constructores =====
    public Contacto() {
    }

    public Contacto(TipoContacto tipoContacto, String valor, Boolean esPrincipal,
                    Integer idCliente) {
        this.tipoContacto = tipoContacto;
        this.valor = valor;
        this.esPrincipal = esPrincipal;
        this.idCliente = idCliente;
    }

    // ===== Getters y Setters =====
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

    // ===== Equals / HashCode =====
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contacto otro = (Contacto) o;
        return Objects.equals(idContacto, otro.idContacto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idContacto);
    }

    @Override
    public String toString() {
        return "Contacto{idContacto=" + idContacto + ", tipoContacto=" + tipoContacto
                + ", valor=" + valor + ", esPrincipal=" + esPrincipal
                + ", idCliente=" + idCliente + "}";
    }
}