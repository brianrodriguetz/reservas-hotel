package co.edu.unbosque.proyecto_bd1.repository;

import co.edu.unbosque.proyecto_bd1.model.Huesped;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HuespedRepository extends JpaRepository<Huesped, Integer> {

    //listar

    @Query(value = "SELECT * FROM huesped", nativeQuery = true)
    List<Huesped> listarTodos();

    @Query(value = "SELECT * FROM huesped WHERE id_Huesped = :id", nativeQuery = true)
    Optional<Huesped> buscarPorId(@Param("id") Integer id);

    @Query(value = "SELECT * FROM huesped WHERE tipo_Documento = :tipo AND numero_Documento = :numero",
           nativeQuery = true)
    Optional<Huesped> buscarPorDocumento(@Param("tipo") String tipoDocumento,
                                         @Param("numero") String numeroDocumento);

    @Query(value = "SELECT * FROM huesped WHERE nacionalidad = :nacionalidad",
           nativeQuery = true)
    List<Huesped> buscarPorNacionalidad(@Param("nacionalidad") String nacionalidad);

    @Query(value = "SELECT * FROM huesped WHERE apellido LIKE CONCAT('%', :apellido, '%')",
           nativeQuery = true)
    List<Huesped> buscarPorApellido(@Param("apellido") String apellido);

    // crrer

    @Modifying
    @Query(value = """
        INSERT INTO huesped (nombre, apellido, tipo_Documento, numero_Documento, nacionalidad, fecha_Nacimiento)
        VALUES (:nombre, :apellido, :tipoDoc, :numeroDoc, :nacionalidad, :fechaNac)
        """, nativeQuery = true)
    void insertar(@Param("nombre") String nombre,
                  @Param("apellido") String apellido,
                  @Param("tipoDoc") String tipoDocumento,
                  @Param("numeroDoc") String numeroDocumento,
                  @Param("nacionalidad") String nacionalidad,
                  @Param("fechaNac") LocalDate fechaNacimiento);

    // actualizar

    @Modifying
    @Query(value = """
        UPDATE huesped
        SET nombre = :nombre,
            apellido = :apellido,
            tipo_Documento = :tipoDoc,
            numero_Documento = :numeroDoc,
            nacionalidad = :nacionalidad,
            fecha_Nacimiento = :fechaNac
        WHERE id_Huesped = :id
        """, nativeQuery = true)
    int actualizar(@Param("id") Integer id,
                   @Param("nombre") String nombre,
                   @Param("apellido") String apellido,
                   @Param("tipoDoc") String tipoDocumento,
                   @Param("numeroDoc") String numeroDocumento,
                   @Param("nacionalidad") String nacionalidad,
                   @Param("fechaNac") LocalDate fechaNacimiento);

    // borrar

    @Modifying
    @Query(value = "DELETE FROM huesped WHERE id_Huesped = :id", nativeQuery = true)
    int eliminar(@Param("id") Integer id);
}