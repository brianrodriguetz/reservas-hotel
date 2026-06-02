package co.edu.unbosque.proyecto_bd1.service;

import co.edu.unbosque.proyecto_bd1.dto.SolicitudReembolsoDTO;
import co.edu.unbosque.proyecto_bd1.enums.EstadoSolicitudReembolso;
import co.edu.unbosque.proyecto_bd1.mappers.SolicitudReembolsoMapper;
import co.edu.unbosque.proyecto_bd1.model.SolicitudReembolso;
import co.edu.unbosque.proyecto_bd1.repository.CancelacionRepository;
import co.edu.unbosque.proyecto_bd1.repository.EmpleadoRepository;
import co.edu.unbosque.proyecto_bd1.repository.SolicitudReembolsoRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SolicitudReembolsoService {

    private final SolicitudReembolsoRepository solicitudReembolsoRepository;
    private final SolicitudReembolsoMapper solicitudReembolsoMapper;
    private final CancelacionRepository cancelacionRepository;
    private final EmpleadoRepository empleadoRepository;

    public SolicitudReembolsoService(
            SolicitudReembolsoRepository solicitudReembolsoRepository,
            SolicitudReembolsoMapper solicitudReembolsoMapper,
            CancelacionRepository cancelacionRepository,
            EmpleadoRepository empleadoRepository) {
        this.solicitudReembolsoRepository = solicitudReembolsoRepository;
        this.solicitudReembolsoMapper = solicitudReembolsoMapper;
        this.cancelacionRepository = cancelacionRepository;
        this.empleadoRepository = empleadoRepository;
    }

    // ====== READ ======

    @Transactional(readOnly = true)
    public List<SolicitudReembolsoDTO> listarTodos() {
        List<SolicitudReembolso> entidades = solicitudReembolsoRepository.listarTodos();
        return solicitudReembolsoMapper.aDTOLista(entidades);
    }

    @Transactional(readOnly = true)
    public SolicitudReembolsoDTO buscarPorId(Integer id) {
        Optional<SolicitudReembolso> opt = solicitudReembolsoRepository.buscarPorId(id);
        if (opt.isEmpty()) {
            throw new NoSuchElementException(
                "Solicitud de reembolso con id " + id + " no encontrada");
        }
        return solicitudReembolsoMapper.aDTO(opt.get());
    }

    @Transactional(readOnly = true)
    public SolicitudReembolsoDTO buscarPorCancelacion(Integer idCancelacion) {
        Optional<SolicitudReembolso> opt =
            solicitudReembolsoRepository.buscarPorCancelacion(idCancelacion);
        if (opt.isEmpty()) {
            throw new NoSuchElementException(
                "No existe solicitud para la cancelacion " + idCancelacion);
        }
        return solicitudReembolsoMapper.aDTO(opt.get());
    }

    @Transactional(readOnly = true)
    public List<SolicitudReembolsoDTO> buscarPorEstado(String estado) {
        List<SolicitudReembolso> entidades = solicitudReembolsoRepository.buscarPorEstado(estado);
        return solicitudReembolsoMapper.aDTOLista(entidades);
    }

    @Transactional(readOnly = true)
    public List<SolicitudReembolsoDTO> buscarPorEmpleado(Integer idEmpleado) {
        List<SolicitudReembolso> entidades =
            solicitudReembolsoRepository.buscarPorEmpleado(idEmpleado);
        return solicitudReembolsoMapper.aDTOLista(entidades);
    }

    @Transactional(readOnly = true)
    public List<SolicitudReembolsoDTO> buscarPendientesSinAsignar() {
        List<SolicitudReembolso> entidades =
            solicitudReembolsoRepository.buscarPendientesSinAsignar();
        return solicitudReembolsoMapper.aDTOLista(entidades);
    }

    // ====== CREATE ======

    @Transactional
    public Integer crear(SolicitudReembolsoDTO dto) {
        validarCancelacionExiste(dto.getIdCancelacion());
        validarCancelacionSinSolicitud(dto.getIdCancelacion());
        if (dto.getIdEmpleado() != null) {
            validarEmpleadoExiste(dto.getIdEmpleado());
        }

        solicitudReembolsoRepository.insertar(
            dto.getMotivo(),
            dto.getEstado().name(),
            dto.getMedio().name(),
            dto.getMonto(),
            dto.getFechaProcesamiento(),
            dto.getIdCancelacion(),
            dto.getIdEmpleado(),
            dto.getFecha()
        );

        return solicitudReembolsoRepository.ultimoIdGenerado();
    }

    // ====== UPDATE ======

    @Transactional
    public void actualizar(Integer id, SolicitudReembolsoDTO dto) {
        if (dto.getIdEmpleado() != null) {
            validarEmpleadoExiste(dto.getIdEmpleado());
        }

        int filas = solicitudReembolsoRepository.actualizar(
            id,
            dto.getMotivo(),
            dto.getEstado().name(),
            dto.getMedio().name(),
            dto.getMonto(),
            dto.getFechaProcesamiento(),
            dto.getIdEmpleado()
        );
        if (filas == 0) {
            throw new NoSuchElementException(
                "Solicitud de reembolso con id " + id + " no encontrada");
        }
    }

    @Transactional
    public void asignarEmpleado(Integer id, Integer idEmpleado) {
        validarEmpleadoExiste(idEmpleado);

        int filas = solicitudReembolsoRepository.asignarEmpleado(id, idEmpleado);
        if (filas == 0) {
            throw new NoSuchElementException(
                "Solicitud de reembolso con id " + id + " no encontrada");
        }
    }

    @Transactional
    public void procesar(Integer id, EstadoSolicitudReembolso nuevoEstado) {
        // Solo Aprobado, Procesado o Rechazado pueden ser estados finales
        if (nuevoEstado == EstadoSolicitudReembolso.Pendiente) {
            throw new IllegalArgumentException(
                "No se puede procesar a estado Pendiente");
        }

        int filas = solicitudReembolsoRepository.procesar(
            id, nuevoEstado.name(), LocalDateTime.now()
        );
        if (filas == 0) {
            throw new NoSuchElementException(
                "Solicitud de reembolso con id " + id + " no encontrada");
        }
    }

    // ====== DELETE ======

    @Transactional
    public void eliminar(Integer id) {
        int filas = solicitudReembolsoRepository.eliminar(id);
        if (filas == 0) {
            throw new NoSuchElementException(
                "Solicitud de reembolso con id " + id + " no encontrada");
        }
    }

    // ===== Helpers privados =====

    private void validarCancelacionExiste(Integer idCancelacion) {
        if (cancelacionRepository.buscarPorIdEvento(idCancelacion).isEmpty()) {
            throw new NoSuchElementException(
                "La cancelacion con id " + idCancelacion + " no existe");
        }
    }

    private void validarCancelacionSinSolicitud(Integer idCancelacion) {
        if (solicitudReembolsoRepository.buscarPorCancelacion(idCancelacion).isPresent()) {
            throw new IllegalArgumentException(
                "La cancelacion " + idCancelacion + " ya tiene una solicitud de reembolso "
                + "(relacion 1:0..1)");
        }
    }

    private void validarEmpleadoExiste(Integer idEmpleado) {
        if (empleadoRepository.buscarPorId(idEmpleado).isEmpty()) {
            throw new NoSuchElementException(
                "El empleado con id " + idEmpleado + " no existe");
        }
    }
}