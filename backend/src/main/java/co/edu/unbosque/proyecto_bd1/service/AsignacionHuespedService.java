package co.edu.unbosque.proyecto_bd1.service;

import co.edu.unbosque.proyecto_bd1.dto.AsignacionHuespedDTO;
import co.edu.unbosque.proyecto_bd1.mappers.AsignacionHuespedMapper;
import co.edu.unbosque.proyecto_bd1.model.AsignacionHuesped;
import co.edu.unbosque.proyecto_bd1.repository.AsignacionHuespedRepository;
import co.edu.unbosque.proyecto_bd1.repository.HuespedRepository;
import co.edu.unbosque.proyecto_bd1.repository.ReservaHabitacionRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AsignacionHuespedService {

    private final AsignacionHuespedRepository asignacionHuespedRepository;
    private final AsignacionHuespedMapper asignacionHuespedMapper;
    private final HuespedRepository huespedRepository;
    private final ReservaHabitacionRepository reservaHabitacionRepository;

    public AsignacionHuespedService(AsignacionHuespedRepository asignacionHuespedRepository,
                                     AsignacionHuespedMapper asignacionHuespedMapper,
                                     HuespedRepository huespedRepository,
                                     ReservaHabitacionRepository reservaHabitacionRepository) {
        this.asignacionHuespedRepository = asignacionHuespedRepository;
        this.asignacionHuespedMapper = asignacionHuespedMapper;
        this.huespedRepository = huespedRepository;
        this.reservaHabitacionRepository = reservaHabitacionRepository;
    }

    // ====== READ ======

    @Transactional(readOnly = true)
    public List<AsignacionHuespedDTO> listarTodos() {
        List<AsignacionHuesped> entidades = asignacionHuespedRepository.listarTodos();
        return asignacionHuespedMapper.aDTOLista(entidades);
    }

    @Transactional(readOnly = true)
    public AsignacionHuespedDTO buscarPorIds(Integer idHuesped, Integer idReserva,
                                              Integer idHabitacion) {
        Optional<AsignacionHuesped> opt = asignacionHuespedRepository.buscarPorIds(
            idHuesped, idReserva, idHabitacion);
        if (opt.isEmpty()) {
            throw new NoSuchElementException(
                "Asignacion (" + idHuesped + "," + idReserva + "," + idHabitacion + ") no encontrada");
        }
        return asignacionHuespedMapper.aDTO(opt.get());
    }

    @Transactional(readOnly = true)
    public List<AsignacionHuespedDTO> buscarPorHuesped(Integer idHuesped) {
        List<AsignacionHuesped> entidades = asignacionHuespedRepository.buscarPorHuesped(idHuesped);
        return asignacionHuespedMapper.aDTOLista(entidades);
    }

    @Transactional(readOnly = true)
    public List<AsignacionHuespedDTO> buscarPorReservaHabitacion(Integer idReserva,
                                                                  Integer idHabitacion) {
        List<AsignacionHuesped> entidades =
            asignacionHuespedRepository.buscarPorReservaHabitacion(idReserva, idHabitacion);
        return asignacionHuespedMapper.aDTOLista(entidades);
    }

    @Transactional(readOnly = true)
    public List<AsignacionHuespedDTO> buscarPorReserva(Integer idReserva) {
        List<AsignacionHuesped> entidades = asignacionHuespedRepository.buscarPorReserva(idReserva);
        return asignacionHuespedMapper.aDTOLista(entidades);
    }

    @Transactional(readOnly = true)
    public AsignacionHuespedDTO buscarTitular(Integer idReserva, Integer idHabitacion) {
        Optional<AsignacionHuesped> opt =
            asignacionHuespedRepository.buscarTitular(idReserva, idHabitacion);
        if (opt.isEmpty()) {
            throw new NoSuchElementException(
                "No hay titular para reserva " + idReserva + " habitacion " + idHabitacion);
        }
        return asignacionHuespedMapper.aDTO(opt.get());
    }

    // ====== CREATE ======

    @Transactional
    public void crear(AsignacionHuespedDTO dto) {
        validarHuespedExiste(dto.getIdHuesped());
        validarReservaHabitacionExiste(dto.getIdReserva(), dto.getIdHabitacion());

        // Validar PK compuesta no duplicada
        Optional<AsignacionHuesped> existente = asignacionHuespedRepository.buscarPorIds(
            dto.getIdHuesped(), dto.getIdReserva(), dto.getIdHabitacion());
        if (existente.isPresent()) {
            throw new IllegalArgumentException(
                "El huesped " + dto.getIdHuesped()
                + " ya esta asignado a esa reserva-habitacion");
        }

        // Regla: solo un titular por reserva-habitacion
        if (Boolean.TRUE.equals(dto.getEsTitular())) {
            asignacionHuespedRepository.desmarcarTitulares(
                dto.getIdReserva(), dto.getIdHabitacion());
        }

        asignacionHuespedRepository.insertar(
            dto.getIdHuesped(),
            dto.getIdReserva(),
            dto.getIdHabitacion(),
            dto.getEsTitular()
        );
    }

    // ====== UPDATE ======

    @Transactional
    public void actualizarTitular(Integer idHuesped, Integer idReserva,
                                   Integer idHabitacion, Boolean esTitular) {
        // Si se marca como titular, desmarcar a los otros del mismo (reserva, habitacion)
        if (Boolean.TRUE.equals(esTitular)) {
            asignacionHuespedRepository.desmarcarTitulares(idReserva, idHabitacion);
        }

        int filasAfectadas = asignacionHuespedRepository.actualizarTitular(
            idHuesped, idReserva, idHabitacion, esTitular);
        if (filasAfectadas == 0) {
            throw new NoSuchElementException(
                "Asignacion (" + idHuesped + "," + idReserva + "," + idHabitacion + ") no encontrada");
        }
    }

    // ====== DELETE ======

    @Transactional
    public void eliminar(Integer idHuesped, Integer idReserva, Integer idHabitacion) {
        int filasAfectadas = asignacionHuespedRepository.eliminar(
            idHuesped, idReserva, idHabitacion);
        if (filasAfectadas == 0) {
            throw new NoSuchElementException(
                "Asignacion (" + idHuesped + "," + idReserva + "," + idHabitacion + ") no encontrada");
        }
    }

    // ===== Helpers privados =====

    private void validarHuespedExiste(Integer idHuesped) {
        if (huespedRepository.buscarPorId(idHuesped).isEmpty()) {
            throw new NoSuchElementException(
                "El huesped con id " + idHuesped + " no existe");
        }
    }

    private void validarReservaHabitacionExiste(Integer idReserva, Integer idHabitacion) {
        if (reservaHabitacionRepository.buscarPorIds(idReserva, idHabitacion).isEmpty()) {
            throw new NoSuchElementException(
                "No existe asignacion previa de reserva " + idReserva
                + " a habitacion " + idHabitacion
                + " (debe crearse primero en ReservaHabitacion)");
        }
    }
}