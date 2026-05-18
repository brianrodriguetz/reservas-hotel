package co.edu.unbosque.proyecto_bd1.repository;

import co.edu.unbosque.proyecto_bd1.model.Pago;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PagoRepository extends JpaRepository<Pago, Integer> {

    // ====== READ ======

    @Query(value = "SELECT * FROM pago", nativeQuery = true)
    List<Pago> listarTodos();

    @Query(value = "SELECT * FROM pago WHERE id_Pago = :id", nativeQuery = true)
    Optional<Pago> buscarPorId(@Param("id") Integer id);

    @Query(value = "SELECT * FROM pago WHERE id_Reserva = :idReserva", nativeQuery = true)
    List<Pago> buscarPorReserva(@Param("idReserva") Integer idReserva);

    @Query(value = "SELECT * FROM pago WHERE estado = :estado", nativeQuery = true)
    List<Pago> buscarPorEstado(@Param("estado") String estado);

    @Query(value = "SELECT * FROM pago WHERE medio = :medio", nativeQuery = true)
    List<Pago> buscarPorMedio(@Param("medio") String medio);

    @Query(value = """
    SELECT * FROM pago
    WHERE fecha BETWEEN :inicio AND :fin
    """, nativeQuery = true)
    List<Pago> buscarPorPeriodo(@Param("inicio") LocalDateTime inicio,
                                @Param("fin") LocalDateTime fin);

    // Suma de pagos procesados para una reserva (util para conciliar con precio_Total)
    @Query(value = """
    SELECT COALESCE(SUM(monto), 0) FROM pago
    WHERE id_Reserva = :idReserva AND estado = 'Aprobado'
    """, nativeQuery = true)
BigDecimal sumaPagadaDeReserva(@Param("idReserva") Integer idReserva);

    // ====== CREATE ======

    @Modifying
    @Query(value = """
    INSERT INTO pago (monto, medio, fecha, estado, id_Reserva)
    VALUES (:monto, :medio, :fechaPago, :estado, :idReserva)
    """, nativeQuery = true)
    void insertar(@Param("monto") BigDecimal monto,
                  @Param("medio") String medio,
                  @Param("fechaPago") LocalDateTime fechaPago,
                  @Param("estado") String estado,
                  @Param("idReserva") Integer idReserva);

    // ====== UPDATE ======

    @Modifying
 @Query(value = """
    UPDATE pago
    SET monto = :monto,
        medio = :medio,
        fecha = :fechaPago,
        estado = :estado,
        id_Reserva = :idReserva
    WHERE id_Pago = :id
    """, nativeQuery = true)
    int actualizar(@Param("id") Integer id,
                   @Param("monto") BigDecimal monto,
                   @Param("medio") String medio,
                   @Param("fechaPago") LocalDateTime fechaPago,
                   @Param("estado") String estado,
                   @Param("idReserva") Integer idReserva);

    @Modifying
    @Query(value = "UPDATE pago SET estado = :estado WHERE id_Pago = :id", nativeQuery = true)
    int actualizarEstado(@Param("id") Integer id, @Param("estado") String estado);

    // ====== DELETE ======

    @Modifying
    @Query(value = "DELETE FROM pago WHERE id_Pago = :id", nativeQuery = true)
    int eliminar(@Param("id") Integer id);
}