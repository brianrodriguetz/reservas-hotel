package co.edu.unbosque.proyecto_bd1.repository;

import co.edu.unbosque.proyecto_bd1.model.Direccion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DireccionRepository extends JpaRepository<Direccion, Integer> {

    // listar

    @Query(value = "SELECT * FROM direccion", nativeQuery = true)
    List<Direccion> listarTodos();

    @Query(value = "SELECT * FROM direccion WHERE id_Direccion = :id", nativeQuery = true)
    Optional<Direccion> buscarPorId(@Param("id") Integer id);

    @Query(value = "SELECT * FROM direccion WHERE id_Cliente = :idCliente", nativeQuery = true)
    List<Direccion> buscarPorCliente(@Param("idCliente") Integer idCliente);

    @Query(value = """
        SELECT * FROM direccion
        WHERE id_Cliente = :idCliente AND tipo_Direccion = :tipo
        """, nativeQuery = true)
    List<Direccion> buscarPorClienteYTipo(@Param("idCliente") Integer idCliente,
                                          @Param("tipo") String tipoDireccion);

    @Query(value = """
        SELECT * FROM direccion
        WHERE id_Cliente = :idCliente
          AND tipo_Direccion = :tipo
          AND es_Principal = 1
        """, nativeQuery = true)
    Optional<Direccion> buscarPrincipalDeClienteYTipo(@Param("idCliente") Integer idCliente,
                                                      @Param("tipo") String tipoDireccion);

    @Query(value = "SELECT * FROM direccion WHERE ciudad = :ciudad", nativeQuery = true)
    List<Direccion> buscarPorCiudad(@Param("ciudad") String ciudad);

    // crar

    @Modifying
    @Query(value = """
        INSERT INTO direccion (tipo_Direccion, calle, numero, ciudad, departamento, codigo_Postal, pais, es_Principal, id_Cliente)
        VALUES (:tipo, :calle, :numero, :ciudad, :departamento,
                :codigoPostal, :pais, :principal, :idCliente)
        """, nativeQuery = true)
    void insertar(@Param("tipo") String tipoDireccion,
                  @Param("calle") String calle,
                  @Param("numero") String numero,
                  @Param("ciudad") String ciudad,
                  @Param("departamento") String departamento,
                  @Param("codigoPostal") String codigoPostal,
                  @Param("pais") String pais,
                  @Param("principal") Boolean esPrincipal,
                  @Param("idCliente") Integer idCliente);

    // actualizar
    @Modifying
    @Query(value = """
        UPDATE direccion
        SET tipo_Direccion = :tipo,
            calle = :calle,
            numero = :numero,
            ciudad = :ciudad,
            departamento = :departamento,
            codigo_Postal = :codigoPostal,
            pais = :pais,
            es_Principal = :principal,
            id_Cliente = :idCliente
        WHERE id_Direccion = :id
        """, nativeQuery = true)
    int actualizar(@Param("id") Integer id,
                   @Param("tipo") String tipoDireccion,
                   @Param("calle") String calle,
                   @Param("numero") String numero,
                   @Param("ciudad") String ciudad,
                   @Param("departamento") String departamento,
                   @Param("codigoPostal") String codigoPostal,
                   @Param("pais") String pais,
                   @Param("principal") Boolean esPrincipal,
                   @Param("idCliente") Integer idCliente);

    // quitar d principal
    @Modifying
    @Query(value = """
        UPDATE direccion SET es_Principal = 0
        WHERE id_Cliente = :idCliente AND tipo_Direccion = :tipo
        """, nativeQuery = true)
    int desmarcarPrincipalesDeClienteYTipo(@Param("idCliente") Integer idCliente,
                                            @Param("tipo") String tipoDireccion);

    //borar

    @Modifying
    @Query(value = "DELETE FROM direccion WHERE id_Direccion = :id", nativeQuery = true)
    int eliminar(@Param("id") Integer id);
}