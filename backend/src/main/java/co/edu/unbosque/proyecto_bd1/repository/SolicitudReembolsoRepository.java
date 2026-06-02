package co.edu.unbosque.proyecto_bd1.repository;

import co.edu.unbosque.proyecto_bd1.model.SolicitudReembolso;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SolicitudReembolsoRepository extends JpaRepository<SolicitudReembolso, Integer> {

    //listar

    @Query(value = "SELECT * FROM solicitud_reembolso", nativeQuery = true)
    List<SolicitudReembolso> listarTodos();

    @Query(value = "SELECT * FROM solicitud_reembolso WHERE id_Solicitud = :id",
           nativeQuery = true)
    Optional<SolicitudReembolso> buscarPorId(@Param("id") Integer id);

    @Query(value = "SELECT * FROM solicitud_reembolso WHERE id_Cancelacion = :idCancelacion",
           nativeQuery = true)
    Optional<SolicitudReembolso> buscarPorCancelacion(@Param("idCancelacion") Integer idCancelacion);

    @Query(value = "SELECT * FROM solicitud_reembolso WHERE estado = :estado", nativeQuery = true)
    List<SolicitudReembolso> buscarPorEstado(@Param("estado") String estado);

    @Query(value = "SELECT * FROM solicitud_reembolso WHERE id_Empleado = :idEmpleado",
           nativeQuery = true)
    List<SolicitudReembolso> buscarPorEmpleado(@Param("idEmpleado") Integer idEmpleado);

    @Query(value = """
        SELECT * FROM solicitud_reembolso
        WHERE id_Empleado IS NULL AND estado = 'Pendiente'
        """, nativeQuery = true)
    List<SolicitudReembolso> buscarPendientesSinAsignar();

    // crear

    @Modifying
    @Query(value = """
        INSERT INTO solicitud_reembolso (motivo, estado, medio, monto, fecha_Procesamiento,
                                          id_Cancelacion, id_Empleado, fecha)
        VALUES (:motivo, :estado, :medio, :monto, :fechaProcesamiento,
                :idCancelacion, :idEmpleado, :fecha)
        """, nativeQuery = true)
    void insertar(@Param("motivo") String motivo,
                  @Param("estado") String estado,
                  @Param("medio") String medio,
                  @Param("monto") BigDecimal monto,
                  @Param("fechaProcesamiento") LocalDateTime fechaProcesamiento,
                  @Param("idCancelacion") Integer idCancelacion,
                  @Param("idEmpleado") Integer idEmpleado,
                  @Param("fecha") LocalDateTime fecha);

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    Integer ultimoIdGenerado();

    // actualizar

    @Modifying
    @Query(value = """
        UPDATE solicitud_reembolso
        SET motivo = :motivo,
            estado = :estado,
            medio = :medio,
            monto = :monto,
            fecha_Procesamiento = :fechaProcesamiento,
            id_Empleado = :idEmpleado
        WHERE id_Solicitud = :id
        """, nativeQuery = true)
    int actualizar(@Param("id") Integer id,
                   @Param("motivo") String motivo,
                   @Param("estado") String estado,
                   @Param("medio") String medio,
                   @Param("monto") BigDecimal monto,
                   @Param("fechaProcesamiento") LocalDateTime fechaProcesamiento,
                   @Param("idEmpleado") Integer idEmpleado);

    // asignar empleado a una solicitud pendiente
    @Modifying
    @Query(value = """
        UPDATE solicitud_reembolso SET id_Empleado = :idEmpleado
        WHERE id_Solicitud = :id
        """, nativeQuery = true)
    int asignarEmpleado(@Param("id") Integer id, @Param("idEmpleado") Integer idEmpleado);

    // procesar solicitud
    @Modifying
    @Query(value = """
        UPDATE solicitud_reembolso
        SET estado = :estado, fecha_Procesamiento = :fechaProcesamiento
        WHERE id_Solicitud = :id
        """, nativeQuery = true)
    int procesar(@Param("id") Integer id,
                 @Param("estado") String estado,
                 @Param("fechaProcesamiento") LocalDateTime fechaProcesamiento);

    // eliminar

    @Modifying
    @Query(value = "DELETE FROM solicitud_reembolso WHERE id_Solicitud = :id", nativeQuery = true)
    int eliminar(@Param("id") Integer id);
}