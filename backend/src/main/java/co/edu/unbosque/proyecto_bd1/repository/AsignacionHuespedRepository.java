package co.edu.unbosque.proyecto_bd1.repository;

import co.edu.unbosque.proyecto_bd1.model.AsignacionHuesped;
import co.edu.unbosque.proyecto_bd1.model.AsignacionHuespedId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AsignacionHuespedRepository
        extends JpaRepository<AsignacionHuesped, AsignacionHuespedId> {

    // ====== READ ======

    @Query(value = "SELECT * FROM asignacion_huesped", nativeQuery = true)
    List<AsignacionHuesped> listarTodos();

    @Query(value = """
        SELECT * FROM asignacion_huesped
        WHERE id_Huesped = :idHuesped
          AND id_Reserva = :idReserva
          AND id_Habitacion = :idHabitacion
        """, nativeQuery = true)
    Optional<AsignacionHuesped> buscarPorIds(@Param("idHuesped") Integer idHuesped,
                                              @Param("idReserva") Integer idReserva,
                                              @Param("idHabitacion") Integer idHabitacion);

    @Query(value = "SELECT * FROM asignacion_huesped WHERE id_Huesped = :idHuesped",
           nativeQuery = true)
    List<AsignacionHuesped> buscarPorHuesped(@Param("idHuesped") Integer idHuesped);

    @Query(value = """
        SELECT * FROM asignacion_huesped
        WHERE id_Reserva = :idReserva AND id_Habitacion = :idHabitacion
        """, nativeQuery = true)
    List<AsignacionHuesped> buscarPorReservaHabitacion(
            @Param("idReserva") Integer idReserva,
            @Param("idHabitacion") Integer idHabitacion);

    @Query(value = "SELECT * FROM asignacion_huesped WHERE id_Reserva = :idReserva",
           nativeQuery = true)
    List<AsignacionHuesped> buscarPorReserva(@Param("idReserva") Integer idReserva);

    @Query(value = """
        SELECT * FROM asignacion_huesped
        WHERE id_Reserva = :idReserva
          AND id_Habitacion = :idHabitacion
          AND es_Titular = 1
        """, nativeQuery = true)
    Optional<AsignacionHuesped> buscarTitular(@Param("idReserva") Integer idReserva,
                                               @Param("idHabitacion") Integer idHabitacion);

    // ====== CREATE ======

    @Modifying
    @Query(value = """
        INSERT INTO asignacion_huesped (id_Huesped, id_Reserva, id_Habitacion, es_Titular)
        VALUES (:idHuesped, :idReserva, :idHabitacion, :esTitular)
        """, nativeQuery = true)
    void insertar(@Param("idHuesped") Integer idHuesped,
                  @Param("idReserva") Integer idReserva,
                  @Param("idHabitacion") Integer idHabitacion,
                  @Param("esTitular") Boolean esTitular);

    // ====== UPDATE ======

    @Modifying
    @Query(value = """
        UPDATE asignacion_huesped
        SET es_Titular = :esTitular
        WHERE id_Huesped = :idHuesped
          AND id_Reserva = :idReserva
          AND id_Habitacion = :idHabitacion
        """, nativeQuery = true)
    int actualizarTitular(@Param("idHuesped") Integer idHuesped,
                           @Param("idReserva") Integer idReserva,
                           @Param("idHabitacion") Integer idHabitacion,
                           @Param("esTitular") Boolean esTitular);

    // Quitar la bandera de titular a todos los huespedes de una reserva-habitacion
    @Modifying
    @Query(value = """
        UPDATE asignacion_huesped
        SET es_Titular = 0
        WHERE id_Reserva = :idReserva AND id_Habitacion = :idHabitacion
        """, nativeQuery = true)
    int desmarcarTitulares(@Param("idReserva") Integer idReserva,
                            @Param("idHabitacion") Integer idHabitacion);

    // ====== DELETE ======

    @Modifying
    @Query(value = """
        DELETE FROM asignacion_huesped
        WHERE id_Huesped = :idHuesped
          AND id_Reserva = :idReserva
          AND id_Habitacion = :idHabitacion
        """, nativeQuery = true)
    int eliminar(@Param("idHuesped") Integer idHuesped,
                 @Param("idReserva") Integer idReserva,
                 @Param("idHabitacion") Integer idHabitacion);
}