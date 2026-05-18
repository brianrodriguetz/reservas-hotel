package co.edu.unbosque.proyecto_bd1.repository;

import co.edu.unbosque.proyecto_bd1.model.CheckOut;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CheckOutRepository extends JpaRepository<CheckOut, Integer> {

    @Query(value = "SELECT * FROM check_out", nativeQuery = true)
    List<CheckOut> listarTodos();

    @Query(value = "SELECT * FROM check_out WHERE id_Evento = :id", nativeQuery = true)
    Optional<CheckOut> buscarPorIdEvento(@Param("id") Integer id);

    @Modifying
    @Query(value = "INSERT INTO check_out (id_Evento) VALUES (:idEvento)", nativeQuery = true)
    void insertar(@Param("idEvento") Integer idEvento);
}