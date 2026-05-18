package co.edu.unbosque.proyecto_bd1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @Column(name = "id_Cliente")
    private Integer idCliente;

    @Column(name = "nit", nullable = false, length = 20, unique = true)
    private String nit;

    @Column(name = "razon_Social", nullable = false, length = 150)
    private String razonSocial;

    @Column(name = "representante_Legal", nullable = false, length = 200)
    private String representanteLegal;

    @Column(name = "sector_Economico", nullable = false, length = 100)
    private String sectorEconomico;

    // ===== Constructores =====
    public Empresa() {
    }

    public Empresa(Integer idCliente, String nit, String razonSocial,
                   String representanteLegal, String sectorEconomico) {
        this.idCliente = idCliente;
        this.nit = nit;
        this.razonSocial = razonSocial;
        this.representanteLegal = representanteLegal;
        this.sectorEconomico = sectorEconomico;
    }

    // ===== Getters y Setters =====
    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
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

    // ===== Equals / HashCode =====
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Empresa otra = (Empresa) o;
        return Objects.equals(idCliente, otra.idCliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCliente);
    }

    @Override
    public String toString() {
        return "Empresa{idCliente=" + idCliente + ", nit=" + nit
                + ", razonSocial=" + razonSocial + ", representanteLegal=" + representanteLegal
                + ", sectorEconomico=" + sectorEconomico + "}";
    }
}