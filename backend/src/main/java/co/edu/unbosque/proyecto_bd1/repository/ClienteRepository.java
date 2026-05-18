package co.edu.unbosque.proyecto_bd1.repository;

import co.edu.unbosque.proyecto_bd1.model.Cliente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    // ====== READ ======

    @Query(value = "SELECT * FROM cliente", nativeQuery = true)
    List<Cliente> listarTodos();

    @Query(value = "SELECT * FROM cliente WHERE id_Cliente = :id", nativeQuery = true)
    Optional<Cliente> buscarPorId(@Param("id") Integer id);

    @Query(value = "SELECT * FROM cliente WHERE estado = :estado", nativeQuery = true)
    List<Cliente> buscarPorEstado(@Param("estado") String estado);

    // ====== CREATE ======

    @Modifying
    @Query(value = """
        INSERT INTO cliente (fecha_Registro, estado)
        VALUES (NOW(), :estado)
        """, nativeQuery = true)
    void insertar(@Param("estado") String estado);

    // Recupera el id del ultimo INSERT en esta sesion (necesario para jerarquia)
    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    Integer ultimoIdGenerado();

    // ====== UPDATE ======

    @Modifying
    @Query(value = "UPDATE cliente SET estado = :estado WHERE id_Cliente = :id",
           nativeQuery = true)
    int actualizarEstado(@Param("id") Integer id, @Param("estado") String estado);

    // ====== DELETE ======

    @Modifying
    @Query(value = "DELETE FROM cliente WHERE id_Cliente = :id", nativeQuery = true)
    int eliminar(@Param("id") Integer id);
}