package co.edu.unbosque.proyecto_bd1.service;
import co.edu.unbosque.proyecto_bd1.model.Rol;
import co.edu.unbosque.proyecto_bd1.dto.RolDTO;
import co.edu.unbosque.proyecto_bd1.enums.EstadoActivo;
import co.edu.unbosque.proyecto_bd1.enums.NombreRol;
import co.edu.unbosque.proyecto_bd1.mappers.EmpleadoMapper;
import co.edu.unbosque.proyecto_bd1.model.Empleado;
import co.edu.unbosque.proyecto_bd1.repository.EmpleadoRepository;
import co.edu.unbosque.proyecto_bd1.repository.RolRepository;
import co.edu.unbosque.proyecto_bd1.web.UsuarioSesion;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service de autenticacion.
 *
 * IMPORTANTE: en esta demo academica, la contraseña es igual al usuario.
 * Es decir: 'admin1' se loguea con password 'admin1'.
 * En produccion esto seria un hash bcrypt; aqui se acepta a proposito
 * para simplificar la demo del proyecto final.
 */
@Service
public class AutenticacionService {

    private final EmpleadoRepository empleadoRepository;
    private final RolRepository rolRepository;
    private final EmpleadoMapper empleadoMapper;

    public AutenticacionService(EmpleadoRepository empleadoRepository,
                                RolRepository rolRepository,
                                EmpleadoMapper empleadoMapper) {
        this.empleadoRepository = empleadoRepository;
        this.rolRepository = rolRepository;
        this.empleadoMapper = empleadoMapper;
    }

    /**
     * Intenta autenticar al empleado.
     * Retorna Optional vacio si las credenciales no son validas o el empleado
     * esta inactivo.
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioSesion> autenticar(String usuario, String password) {
        if (usuario == null || password == null) {
            return Optional.empty();
        }

        // 1. Buscar al empleado por usuario
        Optional<Empleado> optEmpleado = empleadoRepository.buscarPorUsuario(usuario);
        if (optEmpleado.isEmpty()) {
            return Optional.empty();
        }
        Empleado empleado = optEmpleado.get();

        // 2. Validar password == usuario (regla de la demo)
        if (!password.equals(empleado.getUsuario())) {
            return Optional.empty();
        }

        // 3. Validar que este Activo
        if (empleado.getEstado() != EstadoActivo.Activo) {
            return Optional.empty();
        }

        // 4. Obtener el nombre del rol desde la BD
        NombreRol nombreRol = resolverNombreRol(empleado.getIdRol());
        if (nombreRol == null) {
            return Optional.empty();
        }

        // 5. Construir el UsuarioSesion
        String nombreCompleto = empleado.getNombre() + " " + empleado.getApellido();
        UsuarioSesion sesion = new UsuarioSesion(
            empleado.getIdEmpleado(),
            empleado.getUsuario(),
            nombreCompleto,
            nombreRol
        );
        return Optional.of(sesion);
    }
private NombreRol resolverNombreRol(Integer idRol) {

    if (idRol == null) {
        return null;
    }

    Optional<Rol> optRol =
            rolRepository.buscarPorId(idRol);

    if (optRol.isEmpty()) {
        return null;
    }

    return optRol.get().getNombre();
}
}