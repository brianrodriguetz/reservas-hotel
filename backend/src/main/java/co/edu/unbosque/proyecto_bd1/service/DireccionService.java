package co.edu.unbosque.proyecto_bd1.service;

import co.edu.unbosque.proyecto_bd1.dto.DireccionDTO;
import co.edu.unbosque.proyecto_bd1.mappers.DireccionMapper;
import co.edu.unbosque.proyecto_bd1.model.Direccion;
import co.edu.unbosque.proyecto_bd1.repository.ClienteRepository;
import co.edu.unbosque.proyecto_bd1.repository.DireccionRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DireccionService {

    private final DireccionRepository direccionRepository;
    private final DireccionMapper direccionMapper;
    private final ClienteRepository clienteRepository;

    public DireccionService(DireccionRepository direccionRepository,
                            DireccionMapper direccionMapper,
                            ClienteRepository clienteRepository) {
        this.direccionRepository = direccionRepository;
        this.direccionMapper = direccionMapper;
        this.clienteRepository = clienteRepository;
    }

    // ====== READ ======

    @Transactional(readOnly = true)
    public List<DireccionDTO> listarTodos() {
        List<Direccion> direcciones = direccionRepository.listarTodos();
        return direccionMapper.aDTOLista(direcciones);
    }

    @Transactional(readOnly = true)
    public DireccionDTO buscarPorId(Integer id) {
        Optional<Direccion> opt = direccionRepository.buscarPorId(id);
        if (opt.isEmpty()) {
            throw new NoSuchElementException("Direccion con id " + id + " no encontrada");
        }
        return direccionMapper.aDTO(opt.get());
    }

    @Transactional(readOnly = true)
    public List<DireccionDTO> buscarPorCliente(Integer idCliente) {
        List<Direccion> direcciones = direccionRepository.buscarPorCliente(idCliente);
        return direccionMapper.aDTOLista(direcciones);
    }

    @Transactional(readOnly = true)
    public List<DireccionDTO> buscarPorClienteYTipo(Integer idCliente, String tipo) {
        List<Direccion> direcciones = direccionRepository.buscarPorClienteYTipo(idCliente, tipo);
        return direccionMapper.aDTOLista(direcciones);
    }

    @Transactional(readOnly = true)
    public DireccionDTO buscarPrincipalDeClienteYTipo(Integer idCliente, String tipo) {
        Optional<Direccion> opt = direccionRepository.buscarPrincipalDeClienteYTipo(idCliente, tipo);
        if (opt.isEmpty()) {
            throw new NoSuchElementException(
                "El cliente " + idCliente + " no tiene direccion principal de tipo " + tipo);
        }
        return direccionMapper.aDTO(opt.get());
    }

    @Transactional(readOnly = true)
    public List<DireccionDTO> buscarPorCiudad(String ciudad) {
        List<Direccion> direcciones = direccionRepository.buscarPorCiudad(ciudad);
        return direccionMapper.aDTOLista(direcciones);
    }

    // ====== CREATE ======

    @Transactional
    public void crear(DireccionDTO dto) {
        validarClienteExiste(dto.getIdCliente());

        // Regla de negocio: una direccion principal POR TIPO por cliente
        if (Boolean.TRUE.equals(dto.getEsPrincipal())) {
            direccionRepository.desmarcarPrincipalesDeClienteYTipo(
                dto.getIdCliente(),
                dto.getTipoDireccion().name()
            );
        }

        direccionRepository.insertar(
            dto.getTipoDireccion().name(),
            dto.getCalle(),
            dto.getNumero(),
            dto.getCiudad(),
            dto.getDepartamento(),
            dto.getCodigoPostal(),
            dto.getPais(),
            dto.getEsPrincipal(),
            dto.getIdCliente()
        );
    }

    // ====== UPDATE ======

    @Transactional
    public void actualizar(Integer id, DireccionDTO dto) {
        validarClienteExiste(dto.getIdCliente());

        if (Boolean.TRUE.equals(dto.getEsPrincipal())) {
            direccionRepository.desmarcarPrincipalesDeClienteYTipo(
                dto.getIdCliente(),
                dto.getTipoDireccion().name()
            );
        }

        int filasAfectadas = direccionRepository.actualizar(
            id,
            dto.getTipoDireccion().name(),
            dto.getCalle(),
            dto.getNumero(),
            dto.getCiudad(),
            dto.getDepartamento(),
            dto.getCodigoPostal(),
            dto.getPais(),
            dto.getEsPrincipal(),
            dto.getIdCliente()
        );
        if (filasAfectadas == 0) {
            throw new NoSuchElementException("Direccion con id " + id + " no encontrada");
        }
    }

    // ====== DELETE ======

    @Transactional
    public void eliminar(Integer id) {
        int filasAfectadas = direccionRepository.eliminar(id);
        if (filasAfectadas == 0) {
            throw new NoSuchElementException("Direccion con id " + id + " no encontrada");
        }
    }

    // ===== Helpers privados =====

    private void validarClienteExiste(Integer idCliente) {
        if (clienteRepository.buscarPorId(idCliente).isEmpty()) {
            throw new NoSuchElementException(
                "El cliente con id " + idCliente + " no existe");
        }
    }
}