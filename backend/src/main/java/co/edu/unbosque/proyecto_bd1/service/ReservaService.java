package co.edu.unbosque.proyecto_bd1.service;

import co.edu.unbosque.proyecto_bd1.dto.ReservaDTO;
import co.edu.unbosque.proyecto_bd1.enums.EstadoReserva;
import co.edu.unbosque.proyecto_bd1.mappers.ReservaMapper;
import co.edu.unbosque.proyecto_bd1.model.Reserva;
import co.edu.unbosque.proyecto_bd1.repository.ClienteRepository;
import co.edu.unbosque.proyecto_bd1.repository.ReservaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;
    private final ClienteRepository clienteRepository;

    public ReservaService(ReservaRepository reservaRepository,
                          ReservaMapper reservaMapper,
                          ClienteRepository clienteRepository) {
        this.reservaRepository = reservaRepository;
        this.reservaMapper = reservaMapper;
        this.clienteRepository = clienteRepository;
    }

    // ====== READ ======

    @Transactional(readOnly = true)
    public List<ReservaDTO> listarTodos() {
        List<Reserva> reservas = reservaRepository.listarTodos();
        return reservaMapper.aDTOLista(reservas);
    }

    @Transactional(readOnly = true)
    public ReservaDTO buscarPorId(Integer id) {
        Optional<Reserva> opt = reservaRepository.buscarPorId(id);
        if (opt.isEmpty()) {
            throw new NoSuchElementException("Reserva con id " + id + " no encontrada");
        }
        return reservaMapper.aDTO(opt.get());
    }

    @Transactional(readOnly = true)
    public List<ReservaDTO> buscarPorCliente(Integer idCliente) {
        List<Reserva> reservas = reservaRepository.buscarPorCliente(idCliente);
        return reservaMapper.aDTOLista(reservas);
    }

    @Transactional(readOnly = true)
    public List<ReservaDTO> buscarPorEstado(String estado) {
        List<Reserva> reservas = reservaRepository.buscarPorEstado(estado);
        return reservaMapper.aDTOLista(reservas);
    }

    @Transactional(readOnly = true)
    public List<ReservaDTO> buscarPorCanal(String canal) {
        List<Reserva> reservas = reservaRepository.buscarPorCanal(canal);
        return reservaMapper.aDTOLista(reservas);
    }

    @Transactional(readOnly = true)
    public List<ReservaDTO> buscarActivasEnRango(LocalDateTime inicio, LocalDateTime fin) {
        List<Reserva> reservas = reservaRepository.buscarActivasEnRango(inicio, fin);
        return reservaMapper.aDTOLista(reservas);
    }

    @Transactional(readOnly = true)
    public List<ReservaDTO> buscarPorPeriodoCreacion(LocalDateTime inicio, LocalDateTime fin) {
        List<Reserva> reservas = reservaRepository.buscarPorPeriodoCreacion(inicio, fin);
        return reservaMapper.aDTOLista(reservas);
    }

    // ====== CREATE ======

    @Transactional
    public Integer crear(ReservaDTO dto) {
        validarFechas(dto.getFechaCheckInPrevista(), dto.getFechaCheckOutPrevista());
        validarClienteExiste(dto.getIdCliente());

        reservaRepository.insertar(
            dto.getCanal().name(),
            dto.getFechaCreacion(),
            dto.getFechaCheckInPrevista(),
            dto.getFechaCheckOutPrevista(),
            dto.getEstado().name(),
            dto.getPrecioTotal(),
            dto.getIdCliente()
        );

        return reservaRepository.ultimoIdGenerado();
    }

    // ====== UPDATE ======

    @Transactional
    public void actualizar(Integer id, ReservaDTO dto) {
        validarFechas(dto.getFechaCheckInPrevista(), dto.getFechaCheckOutPrevista());
        validarClienteExiste(dto.getIdCliente());

        int filasAfectadas = reservaRepository.actualizar(
            id,
            dto.getCanal().name(),
            dto.getFechaCheckInPrevista(),
            dto.getFechaCheckOutPrevista(),
            dto.getEstado().name(),
            dto.getPrecioTotal(),
            dto.getIdCliente()
        );
        if (filasAfectadas == 0) {
            throw new NoSuchElementException("Reserva con id " + id + " no encontrada");
        }
    }

    @Transactional
    public void cambiarEstado(Integer id, EstadoReserva nuevoEstado) {
        int filasAfectadas = reservaRepository.actualizarEstado(id, nuevoEstado.name());
        if (filasAfectadas == 0) {
            throw new NoSuchElementException("Reserva con id " + id + " no encontrada");
        }
    }

    // ====== DELETE ======

    @Transactional
    public void eliminar(Integer id) {
        int filasAfectadas = reservaRepository.eliminar(id);
        if (filasAfectadas == 0) {
            throw new NoSuchElementException("Reserva con id " + id + " no encontrada");
        }
    }

    // ===== Helpers privados =====

    private void validarFechas(LocalDateTime checkIn, LocalDateTime checkOut) {
        if (checkIn == null || checkOut == null) {
            throw new IllegalArgumentException("Las fechas son obligatorias");
        }
        if (!checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException(
                "La fecha de check-out debe ser estrictamente posterior a la fecha de check-in");
        }
    }

    private void validarClienteExiste(Integer idCliente) {
        if (clienteRepository.buscarPorId(idCliente).isEmpty()) {
            throw new NoSuchElementException(
                "El cliente con id " + idCliente + " no existe");
        }
    }
}