package co.edu.unbosque.proyecto_bd1.repository;

import co.edu.unbosque.proyecto_bd1.model.Habitacion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HabitacionRepository extends JpaRepository<Habitacion, Integer> {

    // ====== READ ======

    @Query(value = "SELECT * FROM habitacion", nativeQuery = true)
    List<Habitacion> listarTodos();

    @Query(value = "SELECT * FROM habitacion WHERE id_Habitacion = :id", nativeQuery = true)
    Optional<Habitacion> buscarPorId(@Param("id") Integer id);

    @Query(value = "SELECT * FROM habitacion WHERE codigo = :codigo", nativeQuery = true)
    Optional<Habitacion> buscarPorCodigo(@Param("codigo") String codigo);

    @Query(value = "SELECT * FROM habitacion WHERE estado = :estado", nativeQuery = true)
    List<Habitacion> buscarPorEstado(@Param("estado") String estado);

    @Query(value = "SELECT * FROM habitacion WHERE id_Tipo = :idTipo", nativeQuery = true)
    List<Habitacion> buscarPorTipo(@Param("idTipo") Integer idTipo);

    @Query(value = "SELECT * FROM habitacion WHERE piso = :piso", nativeQuery = true)
    List<Habitacion> buscarPorPiso(@Param("piso") Byte piso);

    // Habitaciones disponibles (regla de negocio importante para reservas)
    @Query(value = "SELECT * FROM habitacion WHERE estado = 'Disponible'", nativeQuery = true)
    List<Habitacion> listarDisponibles();

    // ====== CREATE ======

    @Modifying
    @Query(value = """
        INSERT INTO habitacion (codigo, piso, estado, id_Tipo)
        VALUES (:codigo, :piso, :estado, :idTipo)
        """, nativeQuery = true)
    void insertar(@Param("codigo") String codigo,
                  @Param("piso") Byte piso,
                  @Param("estado") String estado,
                  @Param("idTipo") Integer idTipo);

    // ====== UPDATE ======

    @Modifying
    @Query(value = """
        UPDATE habitacion
        SET codigo = :codigo,
            piso = :piso,
            estado = :estado,
            id_Tipo = :idTipo
        WHERE id_Habitacion = :id
        """, nativeQuery = true)
    int actualizar(@Param("id") Integer id,
                   @Param("codigo") String codigo,
                   @Param("piso") Byte piso,
                   @Param("estado") String estado,
                   @Param("idTipo") Integer idTipo);

    // Cambio de estado solo (util para transiciones)
    @Modifying
    @Query(value = "UPDATE habitacion SET estado = :estado WHERE id_Habitacion = :id",
           nativeQuery = true)
    int actualizarEstado(@Param("id") Integer id, @Param("estado") String estado);

    // ====== DELETE ======

    @Modifying
    @Query(value = "DELETE FROM habitacion WHERE id_Habitacion = :id", nativeQuery = true)
    int eliminar(@Param("id") Integer id);
}