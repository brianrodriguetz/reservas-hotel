package co.edu.unbosque.proyecto_bd1.service;

import co.edu.unbosque.proyecto_bd1.dto.CancelacionDTO;
import co.edu.unbosque.proyecto_bd1.dto.CheckInDTO;
import co.edu.unbosque.proyecto_bd1.dto.CheckOutDTO;
import co.edu.unbosque.proyecto_bd1.mappers.EventoReservaMapper;
import co.edu.unbosque.proyecto_bd1.model.Cancelacion;
import co.edu.unbosque.proyecto_bd1.model.CheckIn;
import co.edu.unbosque.proyecto_bd1.model.CheckOut;
import co.edu.unbosque.proyecto_bd1.model.EventoReserva;
import co.edu.unbosque.proyecto_bd1.repository.CancelacionRepository;
import co.edu.unbosque.proyecto_bd1.repository.CheckInRepository;
import co.edu.unbosque.proyecto_bd1.repository.CheckOutRepository;
import co.edu.unbosque.proyecto_bd1.repository.EmpleadoRepository;
import co.edu.unbosque.proyecto_bd1.repository.EventoReservaRepository;
import co.edu.unbosque.proyecto_bd1.repository.ReservaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventoReservaService {

    private final EventoReservaRepository eventoReservaRepository;
    private final CheckInRepository checkInRepository;
    private final CheckOutRepository checkOutRepository;
    private final CancelacionRepository cancelacionRepository;
    private final EventoReservaMapper eventoReservaMapper;
    private final ReservaRepository reservaRepository;
    private final EmpleadoRepository empleadoRepository;

    public EventoReservaService(EventoReservaRepository eventoReservaRepository,
                                 CheckInRepository checkInRepository,
                                 CheckOutRepository checkOutRepository,
                                 CancelacionRepository cancelacionRepository,
                                 EventoReservaMapper eventoReservaMapper,
                                 ReservaRepository reservaRepository,
                                 EmpleadoRepository empleadoRepository) {
        this.eventoReservaRepository = eventoReservaRepository;
        this.checkInRepository = checkInRepository;
        this.checkOutRepository = checkOutRepository;
        this.cancelacionRepository = cancelacionRepository;
        this.eventoReservaMapper = eventoReservaMapper;
        this.reservaRepository = reservaRepository;
        this.empleadoRepository = empleadoRepository;
    }

    // ====== READ - CHECK INS ======

    @Transactional(readOnly = true)
    public List<CheckInDTO> listarCheckIns() {
        List<CheckIn> checkIns = checkInRepository.listarTodos();
        List<CheckInDTO> resultado = new ArrayList<>();
        for (int i = 0; i < checkIns.size(); i++) {
            Integer idEvento = checkIns.get(i).getIdEvento();
            Optional<EventoReserva> evento = eventoReservaRepository.buscarPorId(idEvento);
            if (evento.isPresent()) {
                resultado.add(eventoReservaMapper.aCheckInDTO(evento.get()));
            }
        }
        return resultado;
    }

    @Transactional(readOnly = true)
    public CheckInDTO buscarCheckInPorId(Integer id) {
        Optional<CheckIn> checkIn = checkInRepository.buscarPorIdEvento(id);
        Optional<EventoReserva> evento = eventoReservaRepository.buscarPorId(id);
        if (checkIn.isEmpty() || evento.isEmpty()) {
            throw new NoSuchElementException("CheckIn con id " + id + " no encontrado");
        }
        return eventoReservaMapper.aCheckInDTO(evento.get());
    }

    // ====== READ - CHECK OUTS ======

    @Transactional(readOnly = true)
    public List<CheckOutDTO> listarCheckOuts() {
        List<CheckOut> checkOuts = checkOutRepository.listarTodos();
        List<CheckOutDTO> resultado = new ArrayList<>();
        for (int i = 0; i < checkOuts.size(); i++) {
            Integer idEvento = checkOuts.get(i).getIdEvento();
            Optional<EventoReserva> evento = eventoReservaRepository.buscarPorId(idEvento);
            if (evento.isPresent()) {
                resultado.add(eventoReservaMapper.aCheckOutDTO(evento.get()));
            }
        }
        return resultado;
    }

    @Transactional(readOnly = true)
    public CheckOutDTO buscarCheckOutPorId(Integer id) {
        Optional<CheckOut> checkOut = checkOutRepository.buscarPorIdEvento(id);
        Optional<EventoReserva> evento = eventoReservaRepository.buscarPorId(id);
        if (checkOut.isEmpty() || evento.isEmpty()) {
            throw new NoSuchElementException("CheckOut con id " + id + " no encontrado");
        }
        return eventoReservaMapper.aCheckOutDTO(evento.get());
    }

    // ====== READ - CANCELACIONES ======

    @Transactional(readOnly = true)
    public List<CancelacionDTO> listarCancelaciones() {
        List<Cancelacion> cancelaciones = cancelacionRepository.listarTodos();
        List<CancelacionDTO> resultado = new ArrayList<>();
        for (int i = 0; i < cancelaciones.size(); i++) {
            Cancelacion c = cancelaciones.get(i);
            Optional<EventoReserva> evento = eventoReservaRepository.buscarPorId(c.getIdEvento());
            if (evento.isPresent()) {
                resultado.add(eventoReservaMapper.aCancelacionDTO(evento.get(), c));
            }
        }
        return resultado;
    }

    @Transactional(readOnly = true)
    public CancelacionDTO buscarCancelacionPorId(Integer id) {
        Optional<Cancelacion> cancelacion = cancelacionRepository.buscarPorIdEvento(id);
        Optional<EventoReserva> evento = eventoReservaRepository.buscarPorId(id);
        if (cancelacion.isEmpty() || evento.isEmpty()) {
            throw new NoSuchElementException("Cancelacion con id " + id + " no encontrada");
        }
        return eventoReservaMapper.aCancelacionDTO(evento.get(), cancelacion.get());
    }

    // ====== CREATE ======

    @Transactional
    public Integer crearCheckIn(CheckInDTO dto) {
        validarReservaExiste(dto.getIdReserva());
        validarEmpleadoExiste(dto.getIdEmpleado());

        eventoReservaRepository.insertar(dto.getFechaHora(), dto.getIdReserva(),
                                          dto.getIdEmpleado());
        Integer idGenerado = eventoReservaRepository.ultimoIdGenerado();
        checkInRepository.insertar(idGenerado);
        return idGenerado;
    }

    @Transactional
    public Integer crearCheckOut(CheckOutDTO dto) {
        validarReservaExiste(dto.getIdReserva());
        validarEmpleadoExiste(dto.getIdEmpleado());

        eventoReservaRepository.insertar(dto.getFechaHora(), dto.getIdReserva(),
                                          dto.getIdEmpleado());
        Integer idGenerado = eventoReservaRepository.ultimoIdGenerado();
        checkOutRepository.insertar(idGenerado);
        return idGenerado;
    }

    @Transactional
    public Integer crearCancelacion(CancelacionDTO dto) {
        validarReservaExiste(dto.getIdReserva());
        validarEmpleadoExiste(dto.getIdEmpleado());

        eventoReservaRepository.insertar(dto.getFechaHora(), dto.getIdReserva(),
                                          dto.getIdEmpleado());
        Integer idGenerado = eventoReservaRepository.ultimoIdGenerado();
        cancelacionRepository.insertar(idGenerado, dto.getMotivo(), dto.getPenalizacion());
        return idGenerado;
    }

    // ====== UPDATE (solo Cancelacion tiene atributos propios) ======

    @Transactional
    public void actualizarCancelacion(Integer id, CancelacionDTO dto) {
        int filas = cancelacionRepository.actualizar(id, dto.getMotivo(), dto.getPenalizacion());
        if (filas == 0) {
            throw new NoSuchElementException("Cancelacion con id " + id + " no encontrada");
        }
    }

    // ====== DELETE ======

    @Transactional
    public void eliminar(Integer id) {
        // CASCADE en BD se encarga de borrar la hija (CheckIn, CheckOut o Cancelacion)
        int filas = eventoReservaRepository.eliminar(id);
        if (filas == 0) {
            throw new NoSuchElementException("Evento con id " + id + " no encontrado");
        }
    }

    // ===== Helpers privados =====

    private void validarReservaExiste(Integer idReserva) {
        if (reservaRepository.buscarPorId(idReserva).isEmpty()) {
            throw new NoSuchElementException(
                "La reserva con id " + idReserva + " no existe");
        }
    }

    private void validarEmpleadoExiste(Integer idEmpleado) {
        if (empleadoRepository.buscarPorId(idEmpleado).isEmpty()) {
            throw new NoSuchElementException(
                "El empleado con id " + idEmpleado + " no existe");
        }
    }
}