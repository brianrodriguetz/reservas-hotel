package co.edu.unbosque.proyecto_bd1.repository;

import co.edu.unbosque.proyecto_bd1.model.Reserva;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    // listar

    @Query(value = "SELECT * FROM reserva", nativeQuery = true)
    List<Reserva> listarTodos();

    @Query(value = "SELECT * FROM reserva WHERE id_Reserva = :id", nativeQuery = true)
    Optional<Reserva> buscarPorId(@Param("id") Integer id);

    @Query(value = "SELECT * FROM reserva WHERE id_Cliente = :idCliente", nativeQuery = true)
    List<Reserva> buscarPorCliente(@Param("idCliente") Integer idCliente);

    @Query(value = "SELECT * FROM reserva WHERE estado = :estado", nativeQuery = true)
    List<Reserva> buscarPorEstado(@Param("estado") String estado);

    @Query(value = "SELECT * FROM reserva WHERE canal = :canal", nativeQuery = true)
    List<Reserva> buscarPorCanal(@Param("canal") String canal);

    // reservas activas 
    @Query(value = """
        SELECT * FROM reserva
        WHERE estado IN ('Confirmada','En_Curso')
          AND fechaCheckInPrevista < :fin
          AND fechaCheckOutPrevista > :inicio
        """, nativeQuery = true)
    List<Reserva> buscarActivasEnRango(@Param("inicio") LocalDateTime inicio,
                                       @Param("fin") LocalDateTime fin);

    // flitrar por fecha
    @Query(value = """
        SELECT * FROM reserva
        WHERE fecha_Creacion BETWEEN :inicio AND :fin
        """, nativeQuery = true)
    List<Reserva> buscarPorPeriodoCreacion(@Param("inicio") LocalDateTime inicio,
                                            @Param("fin") LocalDateTime fin);

    // crear

    @Modifying
    @Query(value = """
        INSERT INTO reserva (canal, fecha_Creacion, fechaCheckInPrevista,
                             fechaCheckOutPrevista, estado, precio_Total, id_Cliente)
        VALUES (:canal, :fechaCreacion, :checkIn, :checkOut,
                :estado, :precio, :idCliente)
        """, nativeQuery = true)
    void insertar(@Param("canal") String canal,
                  @Param("fechaCreacion") LocalDateTime fechaCreacion,
                  @Param("checkIn") LocalDateTime fechaCheckInPrevista,
                  @Param("checkOut") LocalDateTime fechaCheckOutPrevista,
                  @Param("estado") String estado,
                  @Param("precio") BigDecimal precioTotal,
                  @Param("idCliente") Integer idCliente);

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    Integer ultimoIdGenerado();

    // act

    @Modifying
    @Query(value = """
        UPDATE reserva
        SET canal = :canal,
            fechaCheckInPrevista = :checkIn,
            fechaCheckOutPrevista = :checkOut,
            estado = :estado,
            precio_Total = :precio,
            id_Cliente = :idCliente
        WHERE id_Reserva = :id
        """, nativeQuery = true)
    int actualizar(@Param("id") Integer id,
                   @Param("canal") String canal,
                   @Param("checkIn") LocalDateTime fechaCheckInPrevista,
                   @Param("checkOut") LocalDateTime fechaCheckOutPrevista,
                   @Param("estado") String estado,
                   @Param("precio") BigDecimal precioTotal,
                   @Param("idCliente") Integer idCliente);

    @Modifying
    @Query(value = "UPDATE reserva SET estado = :estado WHERE id_Reserva = :id",
           nativeQuery = true)
    int actualizarEstado(@Param("id") Integer id, @Param("estado") String estado);

    //eliminar

    @Modifying
    @Query(value = "DELETE FROM reserva WHERE id_Reserva = :id", nativeQuery = true)
    int eliminar(@Param("id") Integer id);
}