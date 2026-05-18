package co.edu.unbosque.proyecto_bd1.repository;

import co.edu.unbosque.proyecto_bd1.model.ReservaHabitacion;
import co.edu.unbosque.proyecto_bd1.model.ReservaHabitacionId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservaHabitacionRepository
        extends JpaRepository<ReservaHabitacion, ReservaHabitacionId> {

    // ====== READ ======

    @Query(value = "SELECT * FROM reserva_habitacion", nativeQuery = true)
    List<ReservaHabitacion> listarTodos();

    @Query(value = """
        SELECT * FROM reserva_habitacion
        WHERE id_Reserva = :idReserva AND id_Habitacion = :idHabitacion
        """, nativeQuery = true)
    Optional<ReservaHabitacion> buscarPorIds(@Param("idReserva") Integer idReserva,
                                              @Param("idHabitacion") Integer idHabitacion);

    @Query(value = "SELECT * FROM reserva_habitacion WHERE id_Reserva = :idReserva",
           nativeQuery = true)
    List<ReservaHabitacion> buscarPorReserva(@Param("idReserva") Integer idReserva);

    @Query(value = "SELECT * FROM reserva_habitacion WHERE id_Habitacion = :idHabitacion",
           nativeQuery = true)
    List<ReservaHabitacion> buscarPorHabitacion(@Param("idHabitacion") Integer idHabitacion);

    // ====== CREATE ======

    @Modifying
    @Query(value = """
        INSERT INTO reserva_habitacion (id_Reserva, id_Habitacion, numero_huespedes)
        VALUES (:idReserva, :idHabitacion, :numHuespedes)
        """, nativeQuery = true)
    void insertar(@Param("idReserva") Integer idReserva,
                  @Param("idHabitacion") Integer idHabitacion,
                  @Param("numHuespedes") Byte numeroHuespedes);

    // ====== UPDATE ======

    @Modifying
    @Query(value = """
        UPDATE reserva_habitacion
        SET numero_huespedes = :numHuespedes
        WHERE id_Reserva = :idReserva AND id_Habitacion = :idHabitacion
        """, nativeQuery = true)
    int actualizarNumeroHuespedes(@Param("idReserva") Integer idReserva,
                                   @Param("idHabitacion") Integer idHabitacion,
                                   @Param("numHuespedes") Byte numeroHuespedes);

    // ====== DELETE ======

    @Modifying
    @Query(value = """
        DELETE FROM reserva_habitacion
        WHERE id_Reserva = :idReserva AND id_Habitacion = :idHabitacion
        """, nativeQuery = true)
    int eliminar(@Param("idReserva") Integer idReserva,
                 @Param("idHabitacion") Integer idHabitacion);
}