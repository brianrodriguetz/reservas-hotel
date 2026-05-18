package co.edu.unbosque.proyecto_bd1.service;

import co.edu.unbosque.proyecto_bd1.dto.PagoDTO;
import co.edu.unbosque.proyecto_bd1.enums.EstadoPago;
import co.edu.unbosque.proyecto_bd1.mappers.PagoMapper;
import co.edu.unbosque.proyecto_bd1.model.Pago;
import co.edu.unbosque.proyecto_bd1.repository.PagoRepository;
import co.edu.unbosque.proyecto_bd1.repository.ReservaRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final PagoMapper pagoMapper;
    private final ReservaRepository reservaRepository;

    public PagoService(PagoRepository pagoRepository,
                       PagoMapper pagoMapper,
                       ReservaRepository reservaRepository) {
        this.pagoRepository = pagoRepository;
        this.pagoMapper = pagoMapper;
        this.reservaRepository = reservaRepository;
    }

    // ====== READ ======

    @Transactional(readOnly = true)
    public List<PagoDTO> listarTodos() {
        List<Pago> pagos = pagoRepository.listarTodos();
        return pagoMapper.aDTOLista(pagos);
    }

    @Transactional(readOnly = true)
    public PagoDTO buscarPorId(Integer id) {
        Optional<Pago> opt = pagoRepository.buscarPorId(id);
        if (opt.isEmpty()) {
            throw new NoSuchElementException("Pago con id " + id + " no encontrado");
        }
        return pagoMapper.aDTO(opt.get());
    }

    @Transactional(readOnly = true)
    public List<PagoDTO> buscarPorReserva(Integer idReserva) {
        List<Pago> pagos = pagoRepository.buscarPorReserva(idReserva);
        return pagoMapper.aDTOLista(pagos);
    }

    @Transactional(readOnly = true)
    public List<PagoDTO> buscarPorEstado(String estado) {
        List<Pago> pagos = pagoRepository.buscarPorEstado(estado);
        return pagoMapper.aDTOLista(pagos);
    }

    @Transactional(readOnly = true)
    public List<PagoDTO> buscarPorMedio(String medio) {
        List<Pago> pagos = pagoRepository.buscarPorMedio(medio);
        return pagoMapper.aDTOLista(pagos);
    }

    @Transactional(readOnly = true)
    public List<PagoDTO> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fin) {
        List<Pago> pagos = pagoRepository.buscarPorPeriodo(inicio, fin);
        return pagoMapper.aDTOLista(pagos);
    }

    @Transactional(readOnly = true)
    public BigDecimal sumaPagadaDeReserva(Integer idReserva) {
        return pagoRepository.sumaPagadaDeReserva(idReserva);
    }

    // ====== CREATE ======

    @Transactional
    public void crear(PagoDTO dto) {
        validarReservaExiste(dto.getIdReserva());

        pagoRepository.insertar(
            dto.getMonto(),
            dto.getMedio().name(),
            dto.getFechaPago(),
            dto.getEstado().name(),
            dto.getIdReserva()
        );
    }

    // ====== UPDATE ======

    @Transactional
    public void actualizar(Integer id, PagoDTO dto) {
        validarReservaExiste(dto.getIdReserva());

        int filasAfectadas = pagoRepository.actualizar(
            id,
            dto.getMonto(),
            dto.getMedio().name(),
            dto.getFechaPago(),
            dto.getEstado().name(),
            dto.getIdReserva()
        );
        if (filasAfectadas == 0) {
            throw new NoSuchElementException("Pago con id " + id + " no encontrado");
        }
    }

    @Transactional
    public void cambiarEstado(Integer id, EstadoPago nuevoEstado) {
        int filasAfectadas = pagoRepository.actualizarEstado(id, nuevoEstado.name());
        if (filasAfectadas == 0) {
            throw new NoSuchElementException("Pago con id " + id + " no encontrado");
        }
    }

    // ====== DELETE ======

    @Transactional
    public void eliminar(Integer id) {
        int filasAfectadas = pagoRepository.eliminar(id);
        if (filasAfectadas == 0) {
            throw new NoSuchElementException("Pago con id " + id + " no encontrado");
        }
    }

    // ===== Helpers privados =====

    private void validarReservaExiste(Integer idReserva) {
        if (reservaRepository.buscarPorId(idReserva).isEmpty()) {
            throw new NoSuchElementException(
                "La reserva con id " + idReserva + " no existe");
        }
    }
}