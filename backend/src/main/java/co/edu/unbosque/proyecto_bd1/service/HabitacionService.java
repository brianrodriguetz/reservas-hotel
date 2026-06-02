package co.edu.unbosque.proyecto_bd1.service;

import co.edu.unbosque.proyecto_bd1.dto.HabitacionDTO;
import co.edu.unbosque.proyecto_bd1.enums.EstadoHabitacion;
import co.edu.unbosque.proyecto_bd1.mappers.HabitacionMapper;
import co.edu.unbosque.proyecto_bd1.model.Habitacion;
import co.edu.unbosque.proyecto_bd1.repository.HabitacionRepository;
import co.edu.unbosque.proyecto_bd1.repository.TipoHabitacionRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HabitacionService {

    private final HabitacionRepository habitacionRepository;
    private final HabitacionMapper habitacionMapper;
    private final TipoHabitacionRepository tipoHabitacionRepository;

    public HabitacionService(HabitacionRepository habitacionRepository,
                             HabitacionMapper habitacionMapper,
                             TipoHabitacionRepository tipoHabitacionRepository) {
        this.habitacionRepository = habitacionRepository;
        this.habitacionMapper = habitacionMapper;
        this.tipoHabitacionRepository = tipoHabitacionRepository;
    }

    // ====== READ ======

    @Transactional(readOnly = true)
    public List<HabitacionDTO> listarTodos() {
        List<Habitacion> habitaciones = habitacionRepository.listarTodos();
        return habitacionMapper.aDTOLista(habitaciones);
    }

    @Transactional(readOnly = true)
    public HabitacionDTO buscarPorId(Integer id) {
        Optional<Habitacion> opt = habitacionRepository.buscarPorId(id);
        if (opt.isEmpty()) {
            throw new NoSuchElementException("Habitacion con id " + id + " no encontrada");
        }
        return habitacionMapper.aDTO(opt.get());
    }

    @Transactional(readOnly = true)
    public HabitacionDTO buscarPorCodigo(String codigo) {
        Optional<Habitacion> opt = habitacionRepository.buscarPorCodigo(codigo);
        if (opt.isEmpty()) {
            throw new NoSuchElementException("Habitacion con codigo " + codigo + " no encontrada");
        }
        return habitacionMapper.aDTO(opt.get());
    }

    @Transactional(readOnly = true)
    public List<HabitacionDTO> buscarPorEstado(String estado) {
        List<Habitacion> habitaciones = habitacionRepository.buscarPorEstado(estado);
        return habitacionMapper.aDTOLista(habitaciones);
    }

    @Transactional(readOnly = true)
    public List<HabitacionDTO> buscarPorTipo(Integer idTipo) {
        List<Habitacion> habitaciones = habitacionRepository.buscarPorTipo(idTipo);
        return habitacionMapper.aDTOLista(habitaciones);
    }

    @Transactional(readOnly = true)
    public List<HabitacionDTO> buscarPorPiso(Byte piso) {
        List<Habitacion> habitaciones = habitacionRepository.buscarPorPiso(piso);
        return habitacionMapper.aDTOLista(habitaciones);
    }

    @Transactional(readOnly = true)
    public List<HabitacionDTO> listarDisponibles() {
        List<Habitacion> habitaciones = habitacionRepository.listarDisponibles();
        return habitacionMapper.aDTOLista(habitaciones);
    }

    // ====== CREATE ======

    @Transactional
    public void crear(HabitacionDTO dto) {
        validarTipoExiste(dto.getIdTipo());

        habitacionRepository.insertar(
            dto.getCodigo(),
            dto.getPiso(),
            dto.getEstado().name(),
            dto.getIdTipo()
        );
    }

    // ====== UPDATE ======

    @Transactional
    public void actualizar(Integer id, HabitacionDTO dto) {
        validarTipoExiste(dto.getIdTipo());

        int filasAfectadas = habitacionRepository.actualizar(
            id,
            dto.getCodigo(),
            dto.getPiso(),
            dto.getEstado().name(),
            dto.getIdTipo()
        );
        if (filasAfectadas == 0) {
            throw new NoSuchElementException("Habitacion con id " + id + " no encontrada");
        }
    }

    @Transactional
    public void cambiarEstado(Integer id, EstadoHabitacion nuevoEstado) {
        int filasAfectadas = habitacionRepository.actualizarEstado(id, nuevoEstado.name());
        if (filasAfectadas == 0) {
            throw new NoSuchElementException("Habitacion con id " + id + " no encontrada");
        }
    }

    // ====== DELETE ======

    @Transactional
    public void eliminar(Integer id) {
        int filasAfectadas = habitacionRepository.eliminar(id);
        if (filasAfectadas == 0) {
            throw new NoSuchElementException("Habitacion con id " + id + " no encontrada");
        }
    }

    // ===== Helpers privados =====

    private void validarTipoExiste(Integer idTipo) {
        if (tipoHabitacionRepository.buscarPorId(idTipo).isEmpty()) {
            throw new NoSuchElementException(
                "El tipo de habitacion con id " + idTipo + " no existe");
        }
    }
}