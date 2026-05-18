package co.edu.unbosque.proyecto_bd1.service;

import co.edu.unbosque.proyecto_bd1.dto.EmpleadoDTO;
import co.edu.unbosque.proyecto_bd1.mappers.EmpleadoMapper;
import co.edu.unbosque.proyecto_bd1.model.Empleado;
import co.edu.unbosque.proyecto_bd1.repository.EmpleadoRepository;
import co.edu.unbosque.proyecto_bd1.repository.RolRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoMapper empleadoMapper;
    private final RolRepository rolRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository,
                           EmpleadoMapper empleadoMapper,
                           RolRepository rolRepository) {
        this.empleadoRepository = empleadoRepository;
        this.empleadoMapper = empleadoMapper;
        this.rolRepository = rolRepository;
    }

    // ====== READ ======

    @Transactional(readOnly = true)
    public List<EmpleadoDTO> listarTodos() {
        List<Empleado> empleados = empleadoRepository.listarTodos();
        return empleadoMapper.aDTOLista(empleados);
    }

    @Transactional(readOnly = true)
    public EmpleadoDTO buscarPorId(Integer id) {
        Optional<Empleado> opt = empleadoRepository.buscarPorId(id);
        if (opt.isEmpty()) {
            throw new NoSuchElementException("Empleado con id " + id + " no encontrado");
        }
        return empleadoMapper.aDTO(opt.get());
    }

    @Transactional(readOnly = true)
    public EmpleadoDTO buscarPorUsuario(String usuario) {
        Optional<Empleado> opt = empleadoRepository.buscarPorUsuario(usuario);
        if (opt.isEmpty()) {
            throw new NoSuchElementException("Empleado con usuario " + usuario + " no encontrado");
        }
        return empleadoMapper.aDTO(opt.get());
    }

    @Transactional(readOnly = true)
    public EmpleadoDTO buscarPorNumeroDocumento(String numeroDocumento) {
        Optional<Empleado> opt = empleadoRepository.buscarPorNumeroDocumento(numeroDocumento);
        if (opt.isEmpty()) {
            throw new NoSuchElementException(
                "Empleado con documento " + numeroDocumento + " no encontrado");
        }
        return empleadoMapper.aDTO(opt.get());
    }

    @Transactional(readOnly = true)
    public List<EmpleadoDTO> buscarPorEstado(String estado) {
        List<Empleado> empleados = empleadoRepository.buscarPorEstado(estado);
        return empleadoMapper.aDTOLista(empleados);
    }

    @Transactional(readOnly = true)
    public List<EmpleadoDTO> buscarPorRol(Integer idRol) {
        List<Empleado> empleados = empleadoRepository.buscarPorRol(idRol);
        return empleadoMapper.aDTOLista(empleados);
    }

    @Transactional(readOnly = true)
    public List<EmpleadoDTO> buscarSubordinados(Integer idSupervisor) {
        List<Empleado> empleados = empleadoRepository.buscarSubordinados(idSupervisor);
        return empleadoMapper.aDTOLista(empleados);
    }

    // ====== CREATE ======

    @Transactional
    public void crear(EmpleadoDTO dto) {
        validarReferencias(dto);

        empleadoRepository.insertar(
            dto.getNumeroDocumento(),
            dto.getUsuario(),
            dto.getNombre(),
            dto.getApellido(),
            dto.getEstado().name(),
            dto.getIdRol(),
            dto.getIdSupervisor()
        );
    }

    // ====== UPDATE ======

    @Transactional
    public void actualizar(Integer id, EmpleadoDTO dto) {
        validarReferencias(dto);

        // Validar que no se auto-supervise
        if (dto.getIdSupervisor() != null && dto.getIdSupervisor().equals(id)) {
            throw new IllegalArgumentException("Un empleado no puede supervisarse a si mismo");
        }

        int filasAfectadas = empleadoRepository.actualizar(
            id,
            dto.getNumeroDocumento(),
            dto.getUsuario(),
            dto.getNombre(),
            dto.getApellido(),
            dto.getEstado().name(),
            dto.getIdRol(),
            dto.getIdSupervisor()
        );
        if (filasAfectadas == 0) {
            throw new NoSuchElementException("Empleado con id " + id + " no encontrado");
        }
    }

    // ====== DELETE ======

    @Transactional
    public void eliminar(Integer id) {
        int filasAfectadas = empleadoRepository.eliminar(id);
        if (filasAfectadas == 0) {
            throw new NoSuchElementException("Empleado con id " + id + " no encontrado");
        }
    }

    // ===== Helpers privados =====

    private void validarReferencias(EmpleadoDTO dto) {
        // Validar que el rol existe
        if (rolRepository.buscarPorId(dto.getIdRol()).isEmpty()) {
            throw new NoSuchElementException(
                "El rol con id " + dto.getIdRol() + " no existe");
        }

        // Validar que el supervisor existe (si se especifica)
        if (dto.getIdSupervisor() != null) {
            if (empleadoRepository.buscarPorId(dto.getIdSupervisor()).isEmpty()) {
                throw new NoSuchElementException(
                    "El supervisor con id " + dto.getIdSupervisor() + " no existe");
            }
        }
    }
}