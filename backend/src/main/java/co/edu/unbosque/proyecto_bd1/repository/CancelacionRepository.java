package co.edu.unbosque.proyecto_bd1.repository;

import co.edu.unbosque.proyecto_bd1.model.Cancelacion;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CancelacionRepository extends JpaRepository<Cancelacion, Integer> {

    @Query(value = "SELECT * FROM cancelacion", nativeQuery = true)
    List<Cancelacion> listarTodos();

    @Query(value = "SELECT * FROM cancelacion WHERE id_Evento = :id", nativeQuery = true)
    Optional<Cancelacion> buscarPorIdEvento(@Param("id") Integer id);

    @Modifying
    @Query(value = """
        INSERT INTO cancelacion (id_Evento, motivo, penalizacion)
        VALUES (:idEvento, :motivo, :penalizacion)
        """, nativeQuery = true)
    void insertar(@Param("idEvento") Integer idEvento,
                  @Param("motivo") String motivo,
                  @Param("penalizacion") BigDecimal penalizacion);

    @Modifying
    @Query(value = """
        UPDATE cancelacion SET motivo = :motivo, penalizacion = :penalizacion
        WHERE id_Evento = :id
        """, nativeQuery = true)
    int actualizar(@Param("id") Integer id,
                   @Param("motivo") String motivo,
                   @Param("penalizacion") BigDecimal penalizacion);
}