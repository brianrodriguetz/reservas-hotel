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


@Service
public class AutenticacionService {

    private final EmpleadoRepository empleadoRepository;
    private final RolRepository rolRepository;
    private final EmpleadoMapper empleadoMapper;

    public AutenticacionService(EmpleadoRepository empleadoRepository, RolRepository rolRepository, EmpleadoMapper empleadoMapper) {
        this.empleadoRepository = empleadoRepository;
        this.rolRepository = rolRepository;
        this.empleadoMapper = empleadoMapper;
    }

    
    @Transactional(readOnly = true)
    public Optional<UsuarioSesion> autenticar(String usuario, String password) {
        if (usuario == null || password == null) {
            return Optional.empty();
        }

       
        Optional<Empleado> optEmpleado = empleadoRepository.buscarPorUsuario(usuario);
        if (optEmpleado.isEmpty()) {
            return Optional.empty();
        }
        Empleado empleado = optEmpleado.get();

      
        if (!password.equals(empleado.getUsuario())) {
            return Optional.empty();
        }

    
        if (empleado.getEstado() != EstadoActivo.Activo) {
            return Optional.empty();
        }

      
        NombreRol nombreRol = resolverNombreRol(empleado.getIdRol());
        if (nombreRol == null) {
            return Optional.empty();
        }

        
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