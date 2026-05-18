package co.edu.unbosque.proyecto_bd1.service;

import co.edu.unbosque.proyecto_bd1.dto.ReservaHabitacionDTO;
import co.edu.unbosque.proyecto_bd1.mappers.ReservaHabitacionMapper;
import co.edu.unbosque.proyecto_bd1.model.Habitacion;
import co.edu.unbosque.proyecto_bd1.model.ReservaHabitacion;
import co.edu.unbosque.proyecto_bd1.model.TipoHabitacion;
import co.edu.unbosque.proyecto_bd1.repository.HabitacionRepository;
import co.edu.unbosque.proyecto_bd1.repository.ReservaHabitacionRepository;
import co.edu.unbosque.proyecto_bd1.repository.ReservaRepository;
import co.edu.unbosque.proyecto_bd1.repository.TipoHabitacionRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaHabitacionService {

    private final ReservaHabitacionRepository reservaHabitacionRepository;
    private final ReservaHabitacionMapper reservaHabitacionMapper;
    private final ReservaRepository reservaRepository;
    private final HabitacionRepository habitacionRepository;
    private final TipoHabitacionRepository tipoHabitacionRepository;

    public ReservaHabitacionService(ReservaHabitacionRepository reservaHabitacionRepository,
                                     ReservaHabitacionMapper reservaHabitacionMapper,
                                     ReservaRepository reservaRepository,
                                     HabitacionRepository habitacionRepository,
                                     TipoHabitacionRepository tipoHabitacionRepository) {
        this.reservaHabitacionRepository = reservaHabitacionRepository;
        this.reservaHabitacionMapper = reservaHabitacionMapper;
        this.reservaRepository = reservaRepository;
        this.habitacionRepository = habitacionRepository;
        this.tipoHabitacionRepository = tipoHabitacionRepository;
    }

    // ====== READ ======

    @Transactional(readOnly = true)
    public List<ReservaHabitacionDTO> listarTodos() {
        List<ReservaHabitacion> entidades = reservaHabitacionRepository.listarTodos();
        return reservaHabitacionMapper.aDTOLista(entidades);
    }

    @Transactional(readOnly = true)
    public ReservaHabitacionDTO buscarPorIds(Integer idReserva, Integer idHabitacion) {
        Optional<ReservaHabitacion> opt =
            reservaHabitacionRepository.buscarPorIds(idReserva, idHabitacion);
        if (opt.isEmpty()) {
            throw new NoSuchElementException(
                "Asignacion reserva-habitacion (" + idReserva + "," + idHabitacion + ") no encontrada");
        }
        return reservaHabitacionMapper.aDTO(opt.get());
    }

    @Transactional(readOnly = true)
    public List<ReservaHabitacionDTO> buscarPorReserva(Integer idReserva) {
        List<ReservaHabitacion> entidades = reservaHabitacionRepository.buscarPorReserva(idReserva);
        return reservaHabitacionMapper.aDTOLista(entidades);
    }

    @Transactional(readOnly = true)
    public List<ReservaHabitacionDTO> buscarPorHabitacion(Integer idHabitacion) {
        List<ReservaHabitacion> entidades = reservaHabitacionRepository.buscarPorHabitacion(idHabitacion);
        return reservaHabitacionMapper.aDTOLista(entidades);
    }

    // ====== CREATE ======

    @Transactional
    public void crear(ReservaHabitacionDTO dto) {
        validarReservaExiste(dto.getIdReserva());
        validarHabitacionExisteYTieneCapacidad(dto.getIdHabitacion(), dto.getNumeroHuespedes());

        // Verificar que la asignacion no exista ya (PK compuesta no duplicada)
        Optional<ReservaHabitacion> existente =
            reservaHabitacionRepository.buscarPorIds(dto.getIdReserva(), dto.getIdHabitacion());
        if (existente.isPresent()) {
            throw new IllegalArgumentException(
                "Ya existe una asignacion para reserva " + dto.getIdReserva()
                + " y habitacion " + dto.getIdHabitacion());
        }

        reservaHabitacionRepository.insertar(
            dto.getIdReserva(),
            dto.getIdHabitacion(),
            dto.getNumeroHuespedes()
        );
    }

    // ====== UPDATE ======

    @Transactional
    public void actualizarNumeroHuespedes(Integer idReserva, Integer idHabitacion,
                                           Byte numeroHuespedes) {
        validarHabitacionExisteYTieneCapacidad(idHabitacion, numeroHuespedes);

        int filasAfectadas = reservaHabitacionRepository.actualizarNumeroHuespedes(
            idReserva, idHabitacion, numeroHuespedes
        );
        if (filasAfectadas == 0) {
            throw new NoSuchElementException(
                "Asignacion reserva-habitacion (" + idReserva + "," + idHabitacion + ") no encontrada");
        }
    }

    // ====== DELETE ======

    @Transactional
    public void eliminar(Integer idReserva, Integer idHabitacion) {
        int filasAfectadas = reservaHabitacionRepository.eliminar(idReserva, idHabitacion);
        if (filasAfectadas == 0) {
            throw new NoSuchElementException(
                "Asignacion reserva-habitacion (" + idReserva + "," + idHabitacion + ") no encontrada");
        }
    }

    // ===== Helpers privados =====

    private void validarReservaExiste(Integer idReserva) {
        if (reservaRepository.buscarPorId(idReserva).isEmpty()) {
            throw new NoSuchElementException(
                "La reserva con id " + idReserva + " no existe");
        }
    }

    private void validarHabitacionExisteYTieneCapacidad(Integer idHabitacion, Byte numHuespedes) {
        Optional<Habitacion> habOpt = habitacionRepository.buscarPorId(idHabitacion);
        if (habOpt.isEmpty()) {
            throw new NoSuchElementException(
                "La habitacion con id " + idHabitacion + " no existe");
        }
        // Validacion de capacidad: numero_huespedes no puede superar capacidad_Max del tipo
        Optional<TipoHabitacion> tipoOpt =
            tipoHabitacionRepository.buscarPorId(habOpt.get().getIdTipo());
        if (tipoOpt.isPresent()) {
            Byte capacidad = tipoOpt.get().getCapacidadMax();
            if (numHuespedes > capacidad) {
                throw new IllegalArgumentException(
                    "El numero de huespedes (" + numHuespedes
                    + ") supera la capacidad maxima del tipo (" + capacidad + ")");
            }
        }
    }
}