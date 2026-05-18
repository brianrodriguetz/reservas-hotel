package co.edu.unbosque.proyecto_bd1.repository;

import co.edu.unbosque.proyecto_bd1.model.Empresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    @Query(value = "SELECT * FROM empresa", nativeQuery = true)
    List<Empresa> listarTodos();

    @Query(value = "SELECT * FROM empresa WHERE id_Cliente = :id", nativeQuery = true)
    Optional<Empresa> buscarPorIdCliente(@Param("id") Integer id);

    @Query(value = "SELECT * FROM empresa WHERE nit = :nit", nativeQuery = true)
    Optional<Empresa> buscarPorNit(@Param("nit") String nit);

    @Query(value = "SELECT * FROM empresa WHERE sector_Economico = :sector", nativeQuery = true)
    List<Empresa> buscarPorSector(@Param("sector") String sectorEconomico);

    @Modifying
    @Query(value = """
        INSERT INTO empresa (id_Cliente, nit, razon_Social,
                             representante_Legal, sector_Economico)
        VALUES (:idCliente, :nit, :razon, :representante, :sector)
        """, nativeQuery = true)
    void insertar(@Param("idCliente") Integer idCliente,
                  @Param("nit") String nit,
                  @Param("razon") String razonSocial,
                  @Param("representante") String representanteLegal,
                  @Param("sector") String sectorEconomico);

    @Modifying
    @Query(value = """
        UPDATE empresa
        SET nit = :nit,
            razon_Social = :razon,
            representante_Legal = :representante,
            sector_Economico = :sector
        WHERE id_Cliente = :id
        """, nativeQuery = true)
    int actualizar(@Param("id") Integer id,
                   @Param("nit") String nit,
                   @Param("razon") String razonSocial,
                   @Param("representante") String representanteLegal,
                   @Param("sector") String sectorEconomico);
}