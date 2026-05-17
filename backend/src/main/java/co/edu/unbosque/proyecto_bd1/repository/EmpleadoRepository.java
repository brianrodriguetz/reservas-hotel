package co.edu.unbosque.proyecto_bd1.repository;

import co.edu.unbosque.proyecto_bd1.model.Empleado;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    // ====== READ ======

    @Query(value = "SELECT * FROM empleado", nativeQuery = true)
    List<Empleado> listarTodos();

    @Query(value = "SELECT * FROM empleado WHERE id_Empleado = :id", nativeQuery = true)
    Optional<Empleado> buscarPorId(@Param("id") Integer id);

    @Query(value = "SELECT * FROM empleado WHERE usuario = :usuario", nativeQuery = true)
    Optional<Empleado> buscarPorUsuario(@Param("usuario") String usuario);

    @Query(value = "SELECT * FROM empleado WHERE numero_documento = :numero", nativeQuery = true)
    Optional<Empleado> buscarPorNumeroDocumento(@Param("numero") String numeroDocumento);

    @Query(value = "SELECT * FROM empleado WHERE estado = :estado", nativeQuery = true)
    List<Empleado> buscarPorEstado(@Param("estado") String estado);

    @Query(value = "SELECT * FROM empleado WHERE id_Rol = :idRol", nativeQuery = true)
    List<Empleado> buscarPorRol(@Param("idRol") Integer idRol);

    // Empleados supervisados por uno dado (utilidad para la recursiva)
    @Query(value = "SELECT * FROM empleado WHERE id_Supervisor = :idSupervisor", nativeQuery = true)
    List<Empleado> buscarSubordinados(@Param("idSupervisor") Integer idSupervisor);

    // ====== CREATE ======

    @Modifying
    @Query(value = """
        INSERT INTO empleado (numero_documento, usuario, nombre, apellido,
                              estado, id_Rol, id_Supervisor)
        VALUES (:numeroDoc, :usuario, :nombre, :apellido, :estado, :idRol, :idSupervisor)
        """, nativeQuery = true)
    void insertar(@Param("numeroDoc") String numeroDocumento,
                  @Param("usuario") String usuario,
                  @Param("nombre") String nombre,
                  @Param("apellido") String apellido,
                  @Param("estado") String estado,
                  @Param("idRol") Integer idRol,
                  @Param("idSupervisor") Integer idSupervisor);

    // ====== UPDATE ======

    @Modifying
    @Query(value = """
        UPDATE empleado
        SET numero_documento = :numeroDoc,
            usuario = :usuario,
            nombre = :nombre,
            apellido = :apellido,
            estado = :estado,
            id_Rol = :idRol,
            id_Supervisor = :idSupervisor
        WHERE id_Empleado = :id
        """, nativeQuery = true)
    int actualizar(@Param("id") Integer id,
                   @Param("numeroDoc") String numeroDocumento,
                   @Param("usuario") String usuario,
                   @Param("nombre") String nombre,
                   @Param("apellido") String apellido,
                   @Param("estado") String estado,
                   @Param("idRol") Integer idRol,
                   @Param("idSupervisor") Integer idSupervisor);

    // ====== DELETE ======

    @Modifying
    @Query(value = "DELETE FROM empleado WHERE id_Empleado = :id", nativeQuery = true)
    int eliminar(@Param("id") Integer id);
}