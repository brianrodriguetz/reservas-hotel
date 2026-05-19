package co.edu.unbosque.proyecto_bd1.repository;

import co.edu.unbosque.proyecto_bd1.model.Contacto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactoRepository extends JpaRepository<Contacto, Integer> {

    // listar

    @Query(value = "SELECT * FROM contacto", nativeQuery = true)
    List<Contacto> listarTodos();

    @Query(value = "SELECT * FROM contacto WHERE id_Contacto = :id", nativeQuery = true)
    Optional<Contacto> buscarPorId(@Param("id") Integer id);

    @Query(value = "SELECT * FROM contacto WHERE id_Cliente = :idCliente", nativeQuery = true)
    List<Contacto> buscarPorCliente(@Param("idCliente") Integer idCliente);

    @Query(value = """
        SELECT * FROM contacto
        WHERE id_Cliente = :idCliente AND es_Principal = 1
        """, nativeQuery = true)
    Optional<Contacto> buscarPrincipalDeCliente(@Param("idCliente") Integer idCliente);

    @Query(value = "SELECT * FROM contacto WHERE tipo_Contacto = :tipo", nativeQuery = true)
    List<Contacto> buscarPorTipo(@Param("tipo") String tipoContacto);

    //crrrrraer

    @Modifying
    @Query(value = """
        INSERT INTO contacto (tipo_Contacto, valor, es_Principal, id_Cliente)
        VALUES (:tipo, :valor, :principal, :idCliente)
        """, nativeQuery = true)
    void insertar(@Param("tipo") String tipoContacto,
                  @Param("valor") String valor,
                  @Param("principal") Boolean esPrincipal,
                  @Param("idCliente") Integer idCliente);

    // actualizar

    @Modifying
    @Query(value = """
        UPDATE contacto
        SET tipo_Contacto = :tipo,
            valor = :valor,
            es_Principal = :principal,
            id_Cliente = :idCliente
        WHERE id_Contacto = :id
        """, nativeQuery = true)
    int actualizar(@Param("id") Integer id,
                   @Param("tipo") String tipoContacto,
                   @Param("valor") String valor,
                   @Param("principal") Boolean esPrincipal,
                   @Param("idCliente") Integer idCliente);

    // cquitar contacto princopal
    @Modifying
    @Query(value = """
        UPDATE contacto SET es_Principal = 0
        WHERE id_Cliente = :idCliente
        """, nativeQuery = true)
    int desmarcarPrincipalesDeCliente(@Param("idCliente") Integer idCliente);

    // borrar

    @Modifying
    @Query(value = "DELETE FROM contacto WHERE id_Contacto = :id", nativeQuery = true)
    int eliminar(@Param("id") Integer id);
}