package co.edu.unbosque.proyecto_bd1.model;

import co.edu.unbosque.proyecto_bd1.enums.TipoDireccion;
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
@Table(name = "direccion")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Direccion")
    private Integer idDireccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_Direccion", nullable = false, length = 20)
    private TipoDireccion tipoDireccion;

    @Column(name = "calle", nullable = false, length = 100)
    private String calle;

    @Column(name = "numero", nullable = false, length = 20)
    private String numero;

    @Column(name = "ciudad", nullable = false, length = 80)
    private String ciudad;

    @Column(name = "departamento", nullable = false, length = 80)
    private String departamento;

    @Column(name = "codigo_Postal", nullable = false, length = 10)
    private String codigoPostal;

    @Column(name = "pais", nullable = false, length = 50)
    private String pais;

    @Column(name = "es_Principal", nullable = false)
    private Boolean esPrincipal;

    @Column(name = "id_Cliente", nullable = false)
    private Integer idCliente;

   
    public Direccion() {
    }

    public Direccion(TipoDireccion tipoDireccion, String calle, String numero,String ciudad, String departamento, String codigoPostal, String pais, Boolean esPrincipal, Integer idCliente) {
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

    // ===== Equals / HashCode =====
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Direccion otra = (Direccion) o;
        return Objects.equals(idDireccion, otra.idDireccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDireccion);
    }

    @Override
    public String toString() {
        return "Direccion{idDireccion=" + idDireccion + ", tipoDireccion=" + tipoDireccion
                + ", calle=" + calle + ", numero=" + numero + ", ciudad=" + ciudad
                + ", departamento=" + departamento + ", codigoPostal=" + codigoPostal
                + ", pais=" + pais + ", esPrincipal=" + esPrincipal
                + ", idCliente=" + idCliente + "}";
    }
}