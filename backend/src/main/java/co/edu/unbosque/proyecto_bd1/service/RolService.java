package co.edu.unbosque.proyecto_bd1.service;

import co.edu.unbosque.proyecto_bd1.dto.RolDTO;
import co.edu.unbosque.proyecto_bd1.mappers.RolMapper;
import co.edu.unbosque.proyecto_bd1.model.Rol;
import co.edu.unbosque.proyecto_bd1.repository.RolRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolService {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    public RolService(RolRepository rolRepository, RolMapper rolMapper) {
        this.rolRepository = rolRepository;
        this.rolMapper = rolMapper;
    }

    // ====== READ ======

    @Transactional(readOnly = true)
    public List<RolDTO> listarTodos() {
        List<Rol> roles = rolRepository.listarTodos();
        return rolMapper.aDTOLista(roles);
    }

    @Transactional(readOnly = true)
    public RolDTO buscarPorId(Integer id) {
        Optional<Rol> rolOpt = rolRepository.buscarPorId(id);
        if (rolOpt.isEmpty()) {
            throw new NoSuchElementException("Rol con id " + id + " no encontrado");
        }
        return rolMapper.aDTO(rolOpt.get());
    }

    @Transactional(readOnly = true)
    public List<RolDTO> buscarPorEstado(String estado) {
        List<Rol> roles = rolRepository.buscarPorEstado(estado);
        return rolMapper.aDTOLista(roles);
    }

    // ====== CREATE ======

    @Transactional
    public void crear(RolDTO dto) {
        rolRepository.insertar(
            dto.getNombre().name(),
            dto.getEstado().name()
        );
    }

    // ====== UPDATE ======

    @Transactional
    public void actualizar(Integer id, RolDTO dto) {
        int filasAfectadas = rolRepository.actualizar(
            id,
            dto.getNombre().name(),
            dto.getEstado().name()
        );
        if (filasAfectadas == 0) {
            throw new NoSuchElementException("Rol con id " + id + " no encontrado");
        }
    }

    // ====== DELETE ======

    @Transactional
    public void eliminar(Integer id) {
        int filasAfectadas = rolRepository.eliminar(id);
        if (filasAfectadas == 0) {
            throw new NoSuchElementException("Rol con id " + id + " no encontrado");
        }
    }
}