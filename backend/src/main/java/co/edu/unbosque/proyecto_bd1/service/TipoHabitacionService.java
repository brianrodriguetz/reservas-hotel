package co.edu.unbosque.proyecto_bd1.service;

import co.edu.unbosque.proyecto_bd1.dto.TipoHabitacionDTO;
import co.edu.unbosque.proyecto_bd1.mappers.TipoHabitacionMapper;
import co.edu.unbosque.proyecto_bd1.model.TipoHabitacion;
import co.edu.unbosque.proyecto_bd1.repository.TipoHabitacionRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TipoHabitacionService {

    private final TipoHabitacionRepository tipoHabitacionRepository;
    private final TipoHabitacionMapper tipoHabitacionMapper;

    public TipoHabitacionService(TipoHabitacionRepository tipoHabitacionRepository,
                                  TipoHabitacionMapper tipoHabitacionMapper) {
        this.tipoHabitacionRepository = tipoHabitacionRepository;
        this.tipoHabitacionMapper = tipoHabitacionMapper;
    }

    // ====== READ ======

    @Transactional(readOnly = true)
    public List<TipoHabitacionDTO> listarTodos() {
        List<TipoHabitacion> tipos = tipoHabitacionRepository.listarTodos();
        return tipoHabitacionMapper.aDTOLista(tipos);
    }

    @Transactional(readOnly = true)
    public TipoHabitacionDTO buscarPorId(Integer id) {
        Optional<TipoHabitacion> tipoOpt = tipoHabitacionRepository.buscarPorId(id);
        if (tipoOpt.isEmpty()) {
            throw new NoSuchElementException("TipoHabitacion con id " + id + " no encontrado");
        }
        return tipoHabitacionMapper.aDTO(tipoOpt.get());
    }

    @Transactional(readOnly = true)
    public TipoHabitacionDTO buscarPorNombre(String nombre) {
        Optional<TipoHabitacion> tipoOpt = tipoHabitacionRepository.buscarPorNombre(nombre);
        if (tipoOpt.isEmpty()) {
            throw new NoSuchElementException("TipoHabitacion con nombre " + nombre + " no encontrado");
        }
        return tipoHabitacionMapper.aDTO(tipoOpt.get());
    }

    @Transactional(readOnly = true)
  public List<TipoHabitacionDTO> buscarPorCapacidadMinima(Byte capacidad) {
    List<TipoHabitacion> tipos = tipoHabitacionRepository.buscarPorCapacidadMinima(capacidad);
    return tipoHabitacionMapper.aDTOLista(tipos);
}

    // ====== CREATE ======

    @Transactional
    public void crear(TipoHabitacionDTO dto) {
        tipoHabitacionRepository.insertar(
            dto.getNombre().name(),
            dto.getCapacidadMax(),
            dto.getNumeroCamas(),
            dto.getPrecioBaseNoche()
        );
    }

    // ====== UPDATE ======

    @Transactional
    public void actualizar(Integer id, TipoHabitacionDTO dto) {
        int filasAfectadas = tipoHabitacionRepository.actualizar(
            id,
            dto.getNombre().name(),
            dto.getCapacidadMax(),
            dto.getNumeroCamas(),
            dto.getPrecioBaseNoche()
        );
        if (filasAfectadas == 0) {
            throw new NoSuchElementException("TipoHabitacion con id " + id + " no encontrado");
        }
    }

    // ====== DELETE ======

    @Transactional
    public void eliminar(Integer id) {
        int filasAfectadas = tipoHabitacionRepository.eliminar(id);
        if (filasAfectadas == 0) {
            throw new NoSuchElementException("TipoHabitacion con id " + id + " no encontrado");
        }
    }
}