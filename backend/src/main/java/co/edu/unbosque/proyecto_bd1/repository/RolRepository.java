package co.edu.unbosque.proyecto_bd1.repository;

import co.edu.unbosque.proyecto_bd1.model.Rol;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RolRepository extends JpaRepository<Rol, Integer> {

    // listar

    @Query(value = "SELECT * FROM rol", nativeQuery = true)
    List<Rol> listarTodos();

    @Query(value = "SELECT * FROM rol WHERE id_Rol = :id", nativeQuery = true)
    Optional<Rol> buscarPorId(@Param("id") Integer id);

    @Query(value = "SELECT * FROM rol WHERE estado = :estado", nativeQuery = true)
    List<Rol> buscarPorEstado(@Param("estado") String estado);

    // crear

    @Modifying
    @Query(value = "INSERT INTO rol (nombre, estado) VALUES (:nombre, :estado)",
           nativeQuery = true)
    void insertar(@Param("nombre") String nombre,
                  @Param("estado") String estado);

    // actualizar

    @Modifying
    @Query(value = "UPDATE rol SET nombre = :nombre, estado = :estado WHERE id_Rol = :id",
           nativeQuery = true)
    int actualizar(@Param("id") Integer id,
                   @Param("nombre") String nombre,
                   @Param("estado") String estado);

    // borrar

    @Modifying
    @Query(value = "DELETE FROM rol WHERE id_Rol = :id", nativeQuery = true)
    int eliminar(@Param("id") Integer id);
}