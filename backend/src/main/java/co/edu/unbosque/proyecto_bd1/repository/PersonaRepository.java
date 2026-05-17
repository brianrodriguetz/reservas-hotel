package co.edu.unbosque.proyecto_bd1.repository;

import co.edu.unbosque.proyecto_bd1.model.Persona;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {

    @Query(value = "SELECT * FROM persona", nativeQuery = true)
    List<Persona> listarTodos();

    @Query(value = "SELECT * FROM persona WHERE id_Cliente = :id", nativeQuery = true)
    Optional<Persona> buscarPorIdCliente(@Param("id") Integer id);

    @Query(value = """
        SELECT * FROM persona
        WHERE tipo_Documento = :tipo AND numero_Documento = :numero
        """, nativeQuery = true)
    Optional<Persona> buscarPorDocumento(@Param("tipo") String tipoDocumento,
                                         @Param("numero") String numeroDocumento);

    @Modifying
    @Query(value = """
        INSERT INTO persona (id_Cliente, tipo_Documento, numero_Documento,
                             nombre, apellido, fecha_Nacimiento, nacionalidad)
        VALUES (:idCliente, :tipoDoc, :numeroDoc, :nombre, :apellido, :fechaNac, :nacionalidad)
        """, nativeQuery = true)
    void insertar(@Param("idCliente") Integer idCliente,
                  @Param("tipoDoc") String tipoDocumento,
                  @Param("numeroDoc") String numeroDocumento,
                  @Param("nombre") String nombre,
                  @Param("apellido") String apellido,
                  @Param("fechaNac") LocalDate fechaNacimiento,
                  @Param("nacionalidad") String nacionalidad);

    @Modifying
    @Query(value = """
        UPDATE persona
        SET tipo_Documento = :tipoDoc,
            numero_Documento = :numeroDoc,
            nombre = :nombre,
            apellido = :apellido,
            fecha_Nacimiento = :fechaNac,
            nacionalidad = :nacionalidad
        WHERE id_Cliente = :id
        """, nativeQuery = true)
    int actualizar(@Param("id") Integer id,
                   @Param("tipoDoc") String tipoDocumento,
                   @Param("numeroDoc") String numeroDocumento,
                   @Param("nombre") String nombre,
                   @Param("apellido") String apellido,
                   @Param("fechaNac") LocalDate fechaNacimiento,
                   @Param("nacionalidad") String nacionalidad);
}