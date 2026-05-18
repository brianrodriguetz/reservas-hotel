package co.edu.unbosque.proyecto_bd1.repository;

import co.edu.unbosque.proyecto_bd1.model.EventoReserva;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventoReservaRepository extends JpaRepository<EventoReserva, Integer> {

    @Query(value = "SELECT * FROM evento_reserva", nativeQuery = true)
    List<EventoReserva> listarTodos();

    @Query(value = "SELECT * FROM evento_reserva WHERE id_Evento = :id", nativeQuery = true)
    Optional<EventoReserva> buscarPorId(@Param("id") Integer id);

    @Query(value = "SELECT * FROM evento_reserva WHERE id_Reserva = :idReserva",
           nativeQuery = true)
    List<EventoReserva> buscarPorReserva(@Param("idReserva") Integer idReserva);

    @Query(value = "SELECT * FROM evento_reserva WHERE id_Empleado = :idEmpleado",
           nativeQuery = true)
    List<EventoReserva> buscarPorEmpleado(@Param("idEmpleado") Integer idEmpleado);

    @Modifying
    @Query(value = """
        INSERT INTO evento_reserva (fecha_Hora, id_Reserva, id_Empleado)
        VALUES (:fechaHora, :idReserva, :idEmpleado)
        """, nativeQuery = true)
    void insertar(@Param("fechaHora") LocalDateTime fechaHora,
                  @Param("idReserva") Integer idReserva,
                  @Param("idEmpleado") Integer idEmpleado);

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    Integer ultimoIdGenerado();

    @Modifying
    @Query(value = "DELETE FROM evento_reserva WHERE id_Evento = :id", nativeQuery = true)
    int eliminar(@Param("id") Integer id);

    @Query(value = """
        SELECT * FROM evento_reserva
        WHERE fecha_Hora BETWEEN :inicio AND :fin
        """, nativeQuery = true)
    List<EventoReserva> buscarPorPeriodo(@Param("inicio") LocalDateTime inicio,
                                          @Param("fin") LocalDateTime fin);
}