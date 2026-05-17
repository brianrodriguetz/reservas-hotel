package co.edu.unbosque.proyecto_bd1.repository;

import co.edu.unbosque.proyecto_bd1.model.TipoHabitacion;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TipoHabitacionRepository extends JpaRepository<TipoHabitacion, Integer> {

    // ====== READ ======

    @Query(value = "SELECT * FROM tipo_habitacion", nativeQuery = true)
    List<TipoHabitacion> listarTodos();

    @Query(value = "SELECT * FROM tipo_habitacion WHERE id_Tipo = :id", nativeQuery = true)
    Optional<TipoHabitacion> buscarPorId(@Param("id") Integer id);

    @Query(value = "SELECT * FROM tipo_habitacion WHERE nombre = :nombre", nativeQuery = true)
    Optional<TipoHabitacion> buscarPorNombre(@Param("nombre") String nombre);

   @Query(value = "SELECT * FROM tipo_habitacion WHERE capacidad_Max >= :capacidad",
       nativeQuery = true)
List<TipoHabitacion> buscarPorCapacidadMinima(@Param("capacidad") Byte capacidad);

    // ====== CREATE ======

    @Modifying
    @Query(value = """
        INSERT INTO tipo_habitacion (nombre, capacidad_Max, numero_Camas, precioBaseNoche)
        VALUES (:nombre, :capacidad, :camas, :precio)
        """, nativeQuery = true)
    void insertar(@Param("nombre") String nombre,
                  @Param("capacidad") Byte capacidadMax,
                  @Param("camas") Byte numeroCamas,
                  @Param("precio") BigDecimal precioBaseNoche);

    // ====== UPDATE ======

    @Modifying
    @Query(value = """
        UPDATE tipo_habitacion
        SET nombre = :nombre,
            capacidad_Max = :capacidad,
            numero_Camas = :camas,
            precioBaseNoche = :precio
        WHERE id_Tipo = :id
        """, nativeQuery = true)
  int actualizar(@Param("id") Integer id,
               @Param("nombre") String nombre,
               @Param("capacidad") Byte capacidadMax,
               @Param("camas") Byte numeroCamas,
               @Param("precio") BigDecimal precioBaseNoche);
    // ====== DELETE ======

    @Modifying
    @Query(value = "DELETE FROM tipo_habitacion WHERE id_Tipo = :id", nativeQuery = true)
    int eliminar(@Param("id") Integer id);
}