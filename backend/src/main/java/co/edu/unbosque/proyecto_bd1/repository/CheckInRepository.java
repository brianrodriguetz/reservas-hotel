package co.edu.unbosque.proyecto_bd1.repository;

import co.edu.unbosque.proyecto_bd1.model.CheckIn;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {

    @Query(value = "SELECT * FROM check_in", nativeQuery = true)
    List<CheckIn> listarTodos();

    @Query(value = "SELECT * FROM check_in WHERE id_Evento = :id", nativeQuery = true)
    Optional<CheckIn> buscarPorIdEvento(@Param("id") Integer id);

    @Modifying
    @Query(value = "INSERT INTO check_in (id_Evento) VALUES (:idEvento)", nativeQuery = true)
    void insertar(@Param("idEvento") Integer idEvento);
}